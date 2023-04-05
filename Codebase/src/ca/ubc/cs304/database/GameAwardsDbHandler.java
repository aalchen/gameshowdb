package ca.ubc.cs304.database;

import ca.ubc.cs304.model.*;
import ca.ubc.cs304.util.PrintablePreparedStatement;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.sql.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;

/**
 * This class handles all database related transactions
 */
public class GameAwardsDbHandler {
	// Use this version of the ORACLE_URL if you are running the code off of the server
//	private static final String ORACLE_URL = "jdbc:oracle:thin:@dbhost.students.cs.ubc.ca:1522:stu";
	// Use this version of the ORACLE_URL if you are tunneling into the undergrad servers
	private static final String ORACLE_URL = "jdbc:oracle:thin:@localhost:1522:stu";
	private static final String EXCEPTION_TAG = "[EXCEPTION]";
	private static final String WARNING_TAG = "[WARNING]";

	private static final int INVALID_INPUT = Integer.MIN_VALUE;
	private Connection connection = null;

	public GameAwardsDbHandler() {
		try {
			// Load the Oracle JDBC driver
			// Note that the path could change for new drivers
			DriverManager.registerDriver(new oracle.jdbc.driver.OracleDriver());
		} catch (SQLException e) {
			System.out.println(EXCEPTION_TAG + " " + e.getMessage());
		}
	}

	public void close() {
		try {
			if (connection != null) {
				connection.close();
			}
		} catch (SQLException e) {
			System.out.println(EXCEPTION_TAG + " " + e.getMessage());
		}
	}

	public void deleteVideoGame(String name, int year) throws SQLException {
		try {
			String query = "DELETE FROM VideoGame WHERE title = ? AND year = ?";
			PrintablePreparedStatement ps = new PrintablePreparedStatement(connection.prepareStatement(query), query, false);
			ps.setString(1, name);
			ps.setInt(2, year);

			int rowCount = ps.executeUpdate();
			if (rowCount == 0) {
				System.out.println(WARNING_TAG + " Video game name " + name + " and/or year " + year + " does not exist!");
				throw new SQLException(" Video game name " + name + " and/or year " + year + " does not exist!");
			}

			connection.commit();

			ps.close();
		} catch (SQLException e) {
			System.out.println(EXCEPTION_TAG + " " + e.getMessage());
			rollbackConnection();
			throw e;
		}
	}

	public void insertVideoGame(VideoGameModel model) throws SQLException {
		try {
			String query = "INSERT INTO VideoGame VALUES (?,?,?,?)";
			PrintablePreparedStatement ps = new PrintablePreparedStatement(connection.prepareStatement(query), query, false);
			ps.setString(1, model.getTitle());
			ps.setInt(2, model.getYear());
			ps.setString(3, model.getGenre());
			ps.setString(4, model.getDeveloperName());

			ps.executeUpdate();
			connection.commit();

			ps.close();
		} catch (SQLException e) {
			System.out.println(EXCEPTION_TAG + " " + e.getMessage());
			rollbackConnection();
			throw e;
		}
	}

	public void insertDeveloperName(DeveloperNameModel model) throws SQLException {
		try {
			String query = "INSERT INTO DeveloperName VALUES (?,?,?)";
			PrintablePreparedStatement ps = new PrintablePreparedStatement(connection.prepareStatement(query), query, false);
			ps.setString(1, model.getLeadDeveloper());
			ps.setString(2, model.getWebsite());
			ps.setString(3, model.getName());

			ps.executeUpdate();
			connection.commit();

			ps.close();
		} catch (SQLException e) {
			System.out.println(EXCEPTION_TAG + " " + e.getMessage());
			rollbackConnection();
			throw e;
		}
	}

	public VideoGameModel[] getVideoGameInfo() throws SQLException {
		ArrayList<VideoGameModel> result = new ArrayList<VideoGameModel>();

		try {
			String query = "SELECT * FROM VideoGame";
			PrintablePreparedStatement ps = new PrintablePreparedStatement(connection.prepareStatement(query), query, false);
			ResultSet rs = ps.executeQuery();

			while (rs.next()) {
				VideoGameModel model = new VideoGameModel(rs.getString("title"),
						rs.getInt("year"),
						rs.getString("genre"),
						rs.getString("developer_name"));
				result.add(model);
			}

			rs.close();
			ps.close();
		} catch (SQLException e) {
			System.out.println(EXCEPTION_TAG + " " + e.getMessage());
			throw e;
		}

		return result.toArray(new VideoGameModel[result.size()]);
	}

	public void updateVideoGame(String newName, int year, String oldName) throws SQLException {
		try {
			String query = "UPDATE VideoGame SET title = ? WHERE year = ? AND title = ?";
			PrintablePreparedStatement ps = new PrintablePreparedStatement(connection.prepareStatement(query), query, false);
			ps.setString(1, newName);
			ps.setInt(2, year);
			ps.setString(3, oldName);

			int rowCount = ps.executeUpdate();
			if (rowCount == 0) {
				System.out.println(WARNING_TAG + " Video game name " + oldName + " does not exist!");
			}

			connection.commit();

			ps.close();
		} catch (SQLException e) {
			System.out.println(EXCEPTION_TAG + " " + e.getMessage());
			rollbackConnection();
			throw e;
		}
	}

	public boolean login(String username, String password) {
		try {
			if (connection != null) {
				connection.close();
			}

			connection = DriverManager.getConnection(ORACLE_URL, username, password);
			connection.setAutoCommit(false);

			System.out.println("\nConnected to Oracle!");
			return true;
		} catch (SQLException e) {
			System.out.println(EXCEPTION_TAG + " " + e.getMessage());
			return false;
		}
	}

	private void rollbackConnection() {
		try {
			connection.rollback();
		} catch (SQLException e) {
			System.out.println(EXCEPTION_TAG + " " + e.getMessage());
		}
	}

	public void databaseSetup() throws Exception {
		dropBranchTableIfExists();
		// https://stackoverflow.com/questions/326390/how-do-i-create-a-java-string-from-the-contents-of-a-file
		String sqlString = new String(Files.readAllBytes(Paths.get("../project_c9p6e_d0c3b_f2x4t/Codebase/src/ca/ubc/cs304/sql/scripts/gameDbSetup.sql")));
		String[] sqlArray = sqlString.split(";");
		for (String statement : sqlArray) {
			PrintablePreparedStatement ps = new PrintablePreparedStatement(connection.prepareStatement(statement), statement, false);
			ps.executeUpdate();
			ps.close();
		}
	}

	private void dropBranchTableIfExists() throws SQLException {
		try {
			String query = "select table_name from user_tables";
			PrintablePreparedStatement ps = new PrintablePreparedStatement(connection.prepareStatement(query), query, false);
			List<String> lowercaseTableNames = new ArrayList<String>(Arrays.asList("communityaward", "sponsoredaward", "staff_awardceremony", "sponsors", "videogame_dlc", "livestreamviewercount", "award", "videogame", "developercountry", "staff", "livestreamurl", "awardceremony", "venue", "company", "developername"));
			List<String> dropTables = new ArrayList<String>();
			ResultSet rs = ps.executeQuery();
			String table = "";
			while (rs.next()) {
				table = rs.getString("table_name").toLowerCase();
				for (int i = 0; i < lowercaseTableNames.size(); i++) {
					if (table.equals(lowercaseTableNames.get(i))) {
						dropTables.add(table);
					}
				}
			}
			//	https://stackoverflow.com/questions/18129807/in-java-how-do-you-sort-one-list-based-on-another
			dropTables.sort(Comparator.comparingInt(lowercaseTableNames::indexOf));

			for (int i = 0; i < dropTables.size(); i++) {
				String tableToDrop = dropTables.get(i);
				ps.execute("DROP TABLE " + tableToDrop);
			}

			rs.close();
			ps.close();
		} catch (SQLException e) {
			System.out.println(EXCEPTION_TAG + " " + e.getMessage());
			throw e;
		}
	}

	public DeveloperNameModel[] getDeveloperNameInfo() throws SQLException {
		ArrayList<DeveloperNameModel> result = new ArrayList<DeveloperNameModel>();

		try {
			String query = "SELECT * FROM DeveloperName";
			PrintablePreparedStatement ps = new PrintablePreparedStatement(connection.prepareStatement(query), query, false);
			ResultSet rs = ps.executeQuery();

			while (rs.next()) {
				DeveloperNameModel model = new DeveloperNameModel(rs.getString("lead_developer"),
						rs.getString("website"),
						rs.getString("name"));
				result.add(model);
			}

			rs.close();
			ps.close();
		} catch (SQLException e) {
			System.out.println(EXCEPTION_TAG + " " + e.getMessage());
			throw e;
		}

		return result.toArray(new DeveloperNameModel[result.size()]);
	}

	public void updateDeveloperName(String newLeadDev, String developerName) throws SQLException {
		try {
			String query = "UPDATE DeveloperName SET lead_developer = ? WHERE name = ?";
			PrintablePreparedStatement ps = new PrintablePreparedStatement(connection.prepareStatement(query), query, false);
			ps.setString(1, newLeadDev);
			ps.setString(2, developerName);

			int rowCount = ps.executeUpdate();
			if (rowCount == 0) {
				System.out.println(WARNING_TAG + " Developer name" + developerName + " does not exist!");
			}

			connection.commit();

			ps.close();
		} catch (SQLException e) {
			System.out.println(EXCEPTION_TAG + " " + e.getMessage());
			rollbackConnection();
			throw e;
		}
	}

	public void deleteDeveloperName(String developerName) throws SQLException {
		try {
			String query = "DELETE FROM DeveloperName WHERE name = ?";
			PrintablePreparedStatement ps = new PrintablePreparedStatement(connection.prepareStatement(query), query, false);
			ps.setString(1, developerName);

			int rowCount = ps.executeUpdate();
			if (rowCount == 0) {
				System.out.println(WARNING_TAG + " Developer name " + developerName + " does not exist!");
				throw new SQLException("Developer " + developerName + " not found. Could not delete.");
			}

			connection.commit();

			ps.close();
		} catch (SQLException e) {
			System.out.println(EXCEPTION_TAG + " " + e.getMessage());
			rollbackConnection();
			throw e;
		}
	}

	public void updateDeveloperNameLead(String newLeadDev, String developerName) throws SQLException {
		try {
			String query = "UPDATE DeveloperName SET lead_developer = ? WHERE name = ?";
			PrintablePreparedStatement ps = new PrintablePreparedStatement(connection.prepareStatement(query), query, false);
			ps.setString(1, newLeadDev);
			ps.setString(2, developerName);

			int rowCount = ps.executeUpdate();
			if (rowCount == 0) {
				System.out.println(WARNING_TAG + " Developer name" + developerName + " does not exist!");
				throw new SQLException("Developer " + developerName + " does not exist!");
			}

			connection.commit();

			ps.close();
		} catch (SQLException e) {
			System.out.println(EXCEPTION_TAG + " " + e.getMessage());
			rollbackConnection();
			throw e;
		}
	}

	public void updateDeveloperNameWebsite(String website, String developerName) throws SQLException {
		try {
			String query = "UPDATE DeveloperName SET website = ? WHERE name = ?";
			PrintablePreparedStatement ps = new PrintablePreparedStatement(connection.prepareStatement(query), query, false);
			ps.setString(1, website);
			ps.setString(2, developerName);

			int rowCount = ps.executeUpdate();
			if (rowCount == 0) {
				System.out.println(WARNING_TAG + " Developer name" + developerName + " does not exist!");
			}

			connection.commit();

			ps.close();
		} catch (SQLException e) {
			System.out.println(EXCEPTION_TAG + " " + e.getMessage());
			rollbackConnection();
			throw e;
		}
	}

	public void updateDeveloperNameName(String newDeveloperName, String developerName) throws SQLException {
		try {
			String sqlString = new String(Files.readAllBytes(Paths.get("../project_c9p6e_d0c3b_f2x4t/Codebase/src/ca/ubc/cs304/sql/scripts/disableDeveloperNameFK.sql")));
			String[] sqlArray = sqlString.split(";");
			for (String statement : sqlArray) {
				PrintablePreparedStatement ps = new PrintablePreparedStatement(connection.prepareStatement(statement), statement, false);
				ps.executeUpdate();
				ps.close();
			}
			String query = "UPDATE DeveloperName SET name = ? WHERE name = ?";
			PrintablePreparedStatement ps = new PrintablePreparedStatement(connection.prepareStatement(query), query, false);
			ps.setString(1, newDeveloperName);
			ps.setString(2, developerName);

			int rowCount = ps.executeUpdate();
			if (rowCount == 0) {
				System.out.println(WARNING_TAG + " Developer name" + developerName + " does not exist!");
			}
			ps.close();

			String query1 = "UPDATE DeveloperCountry SET name = ? WHERE name = ?";
			PrintablePreparedStatement ps1 = new PrintablePreparedStatement(connection.prepareStatement(query1), query1, false);
			ps1.setString(1, newDeveloperName);
			ps1.setString(2, developerName);
			ps1.executeUpdate();
			ps1.close();

			String query2 = "UPDATE VideoGame SET developer_name = ? WHERE developer_name = ?";
			PrintablePreparedStatement ps2 = new PrintablePreparedStatement(connection.prepareStatement(query2), query2, false);
			ps2.setString(1, newDeveloperName);
			ps2.setString(2, developerName);
			ps2.executeUpdate();
			ps2.close();

			String sqlString3 = new String(Files.readAllBytes(Paths.get("../project_c9p6e_d0c3b_f2x4t/Codebase/src/ca/ubc/cs304/sql/scripts/enableDeveloperNameFK.sql")));
			String[] sqlArray3 = sqlString3.split(";");
			for (String statement : sqlArray3) {
				PrintablePreparedStatement ps3 = new PrintablePreparedStatement(connection.prepareStatement(statement), statement, false);
				ps3.executeUpdate();
				ps3.close();
			}
			connection.commit();
		} catch (SQLException e) {
			System.out.println(EXCEPTION_TAG + " " + e.getMessage());
			rollbackConnection();
			throw e;

		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}

	public void deleteDeveloperNameLead(String deleteDevLead) throws SQLException {
		try {
			String query = "DELETE FROM DeveloperName WHERE lead_developer = ?";
			PrintablePreparedStatement ps = new PrintablePreparedStatement(connection.prepareStatement(query), query, false);
			ps.setString(1, deleteDevLead);

			int rowCount = ps.executeUpdate();
			if (rowCount == 0) {
				System.out.println(WARNING_TAG + " Lead developer " + deleteDevLead + " does not exist!");
			}

			connection.commit();

			ps.close();
		} catch (SQLException e) {
			System.out.println(EXCEPTION_TAG + " " + e.getMessage());
			rollbackConnection();
			throw e;
		}
	}

	public void deleteDeveloperNameWeb(String deleteWebsite) throws SQLException {
		try {
			String query = "DELETE FROM DeveloperName WHERE website = ?";
			PrintablePreparedStatement ps = new PrintablePreparedStatement(connection.prepareStatement(query), query, false);
			ps.setString(1, deleteWebsite);

			int rowCount = ps.executeUpdate();
			if (rowCount == 0) {
				System.out.println(WARNING_TAG + " Website " + deleteWebsite + " does not exist!");
			}

			connection.commit();

			ps.close();
		} catch (SQLException e) {
			System.out.println(EXCEPTION_TAG + " " + e.getMessage());
			rollbackConnection();
			throw e;
		}
	}

	public DeveloperNameModel[] filterDevelopers(String leadDev, String website, String name) throws SQLException {
		StringBuilder sql = new StringBuilder("SELECT * FROM DeveloperName");

		boolean hasCondition = false;
		if (!leadDev.isEmpty() || !website.isEmpty() || !name.isEmpty()) {
			sql.append(" WHERE ");
		}

		if (!leadDev.isEmpty()) {
			sql.append("lead_developer = ? ");
			hasCondition = true;
		}

		if (!website.isEmpty()) {
			if (hasCondition) {
				sql.append("AND ");
			}
			sql.append("website = ? ");
			hasCondition = true;
		}

		if (!name.isEmpty()) {
			if (hasCondition) {
				sql.append("AND ");
			}
			sql.append("name = ?");
		}

		try {
			PreparedStatement stmt = connection.prepareStatement(sql.toString());
			int paramIndex = 1;
			if (!leadDev.isEmpty()) {
				stmt.setString(paramIndex++, leadDev);
			}
			if (!website.isEmpty()) {
				stmt.setString(paramIndex++, website);
			}
			if (!name.isEmpty()) {
				stmt.setString(paramIndex, name);
			}

			ResultSet rs = stmt.executeQuery();
			ArrayList<DeveloperNameModel> developerNameModels = new ArrayList<>();

			while (rs.next()) {
				developerNameModels.add(new DeveloperNameModel(rs.getString("lead_developer"), rs.getString("website"), rs.getString("name")));
			}

			DeveloperNameModel[] modelsArray = new DeveloperNameModel[developerNameModels.size()];
			return developerNameModels.toArray(modelsArray);

		} catch (SQLException e) {
			e.printStackTrace();
			rollbackConnection();
			throw e;
		}
	}

	public DeveloperNameModel[] selectLeadDev(String leadDev) throws SQLException {
		ArrayList<DeveloperNameModel> result = new ArrayList<DeveloperNameModel>();

		try {
			String query = "SELECT * FROM DeveloperName WHERE lead_developer = ?";
			PrintablePreparedStatement ps = new PrintablePreparedStatement(connection.prepareStatement(query), query, false);
			ps.setString(1, leadDev);
			ResultSet rs = ps.executeQuery();

			while (rs.next()) {
				DeveloperNameModel model = new DeveloperNameModel(rs.getString("lead_developer"),
						rs.getString("website"),
						rs.getString("name"));
				result.add(model);
			}

			rs.close();
			ps.close();
		} catch (SQLException e) {
			System.out.println(EXCEPTION_TAG + " " + e.getMessage());
			throw e;

		}

		return result.toArray(new DeveloperNameModel[result.size()]);
	}

	public DeveloperNameModel[] selectWebsite(String website) {
		ArrayList<DeveloperNameModel> result = new ArrayList<DeveloperNameModel>();

		try {
			String query = "SELECT * FROM DeveloperName WHERE website = ?";
			PrintablePreparedStatement ps = new PrintablePreparedStatement(connection.prepareStatement(query), query, false);
			ps.setString(1, website);
			ResultSet rs = ps.executeQuery();

			while (rs.next()) {
				DeveloperNameModel model = new DeveloperNameModel(rs.getString("lead_developer"),
						rs.getString("website"),
						rs.getString("name"));
				result.add(model);
			}

			rs.close();
			ps.close();
		} catch (SQLException e) {
			System.out.println(EXCEPTION_TAG + " " + e.getMessage());
		}

		return result.toArray(new DeveloperNameModel[result.size()]);
	}

	public VideoGameModel[] projectionVideoGame(List<String> projectionColumns) throws SQLException {
		ArrayList<VideoGameModel> result = new ArrayList<VideoGameModel>();
		String queryColumns = "";
		for (int i = 0; i < projectionColumns.size(); i++) {
			queryColumns = queryColumns + projectionColumns.get(i) + ", ";
		}
		queryColumns = queryColumns.substring(0, queryColumns.length() - 2);

		try {
			String query = "SELECT " + queryColumns + " FROM VideoGame";
			PrintablePreparedStatement ps = new PrintablePreparedStatement(connection.prepareStatement(query), query, false);
			ResultSet rs = ps.executeQuery();
			String title = "";
			int year = INVALID_INPUT;
			String genre = "";
			String name = "";

			while (rs.next()) {
				if (projectionColumns.contains("title")) {
					title = rs.getString("title");
				}
				if (projectionColumns.contains("year")) {
					year = rs.getInt("year");
				}
				if (projectionColumns.contains("genre")) {
					genre = rs.getString("genre");
				}
				if (projectionColumns.contains("developer_name")) {
					name = rs.getString("developer_name");
				}
				VideoGameModel model = new VideoGameModel(title,
						year,
						genre,
						name);
				result.add(model);
			}

			rs.close();
			ps.close();
		} catch (SQLException e) {
			System.out.println(EXCEPTION_TAG + " " + e.getMessage());
			throw e;

		}

		return result.toArray(new VideoGameModel[result.size()]);
	}

	public DeveloperNameModel[] selectName(String name) throws SQLException {
		ArrayList<DeveloperNameModel> result = new ArrayList<DeveloperNameModel>();

		try {
			String query = "SELECT * FROM DeveloperName WHERE name = ?";
			PrintablePreparedStatement ps = new PrintablePreparedStatement(connection.prepareStatement(query), query, false);
			ps.setString(1, name);
			ResultSet rs = ps.executeQuery();

			while (rs.next()) {
				DeveloperNameModel model = new DeveloperNameModel(rs.getString("lead_developer"),
						rs.getString("website"),
						rs.getString("name"));
				result.add(model);
			}

			rs.close();
			ps.close();
		} catch (SQLException e) {
			System.out.println(EXCEPTION_TAG + " " + e.getMessage());
			throw e;

		}

		return result.toArray(new DeveloperNameModel[result.size()]);
	}

	public DeveloperNameVideoGameModel[] joinTables(String joinWhere) throws SQLException {
		ArrayList<DeveloperNameVideoGameModel> result = new ArrayList<DeveloperNameVideoGameModel>();

		try {
			String query = "SELECT * FROM VideoGame INNER JOIN DeveloperName ON VideoGame.developer_name = DeveloperName.name WHERE DeveloperName.Lead_Developer = ?";
			PrintablePreparedStatement ps = new PrintablePreparedStatement(connection.prepareStatement(query), query, false);
			ps.setString(1, joinWhere);
			ResultSet rs = ps.executeQuery();

			while (rs.next()) {
				DeveloperNameVideoGameModel model = new DeveloperNameVideoGameModel(rs.getString("title"),
						rs.getInt("year"),
						rs.getString("genre"),
						rs.getString("developer_name"),
						rs.getString("website"),
						rs.getString("lead_developer"));
				result.add(model);
			}

			rs.close();
			ps.close();
		} catch (SQLException e) {
			System.out.println(EXCEPTION_TAG + " " + e.getMessage());
			throw e;
		}

		return result.toArray(new DeveloperNameVideoGameModel[result.size()]);
	}

	public VideoGameCountModel[] aggregateGroupBy() throws SQLException {
		ArrayList<VideoGameCountModel> result = new ArrayList<VideoGameCountModel>();

		try {
			String query = "SELECT COUNT(Genre), Developer_Name FROM VideoGame GROUP BY Developer_Name";
			PrintablePreparedStatement ps = new PrintablePreparedStatement(connection.prepareStatement(query), query, false);
			ResultSet rs = ps.executeQuery();

			while (rs.next()) {
				VideoGameCountModel model = new VideoGameCountModel(null,
						INVALID_INPUT,
						null,
						rs.getString("developer_name"),
						rs.getInt("COUNT(Genre)"));
				result.add(model);
			}

			rs.close();
			ps.close();
		} catch (SQLException e) {
			System.out.println(EXCEPTION_TAG + " " + e.getMessage());
			throw e;

		}

		return result.toArray(new VideoGameCountModel[result.size()]);

	}

	public VideoGameCountModel[] aggregateGroupByHaving() throws SQLException {
		ArrayList<VideoGameCountModel> result = new ArrayList<VideoGameCountModel>();

		try {
			String query = "SELECT MAX(Year), Genre, developer_name FROM VideoGame GROUP BY Genre, developer_name HAVING MAX(Year) > 2015";
			PrintablePreparedStatement ps = new PrintablePreparedStatement(connection.prepareStatement(query), query, false);
			ResultSet rs = ps.executeQuery();

			while (rs.next()) {
				VideoGameCountModel model = new VideoGameCountModel(null,
						INVALID_INPUT,
						rs.getString("genre"),
						rs.getString("developer_name"),
						rs.getInt("MAX(Year)"));
				result.add(model);
			}

			rs.close();
			ps.close();
		} catch (SQLException e) {
			System.out.println(EXCEPTION_TAG + " " + e.getMessage());
			throw e;

		}

		return result.toArray(new VideoGameCountModel[result.size()]);
	}

	public VideoGameCountModel[] nestedAggregation() throws SQLException {
		ArrayList<VideoGameCountModel> result = new ArrayList<VideoGameCountModel>();

		try {
			String query = "SELECT COUNT(Title), Developer_Name FROM VideoGame WHERE Year IN ( SELECT Year FROM VideoGame WHERE Year > 2015 ) GROUP BY Developer_Name";
			PrintablePreparedStatement ps = new PrintablePreparedStatement(connection.prepareStatement(query), query, false);
			ResultSet rs = ps.executeQuery();

			while (rs.next()) {
				VideoGameCountModel model = new VideoGameCountModel(null,
						INVALID_INPUT,
						null,
						rs.getString("developer_name"),
						rs.getInt("COUNT(Title)"));
				result.add(model);
			}

			rs.close();
			ps.close();
		} catch (SQLException e) {
			System.out.println(EXCEPTION_TAG + " " + e.getMessage());
			throw e;
		}

		return result.toArray(new VideoGameCountModel[result.size()]);
	}

	public VideoGameModel[] division() throws SQLException {
		ArrayList<VideoGameModel> result = new ArrayList<VideoGameModel>();

		try {
			String query = "SELECT D.name FROM DeveloperName D WHERE NOT EXISTS ( SELECT G1.genre FROM VideoGame G1 WHERE NOT EXISTS ( SELECT * FROM VideoGame G2 WHERE G2.developer_name = D.name AND G2.genre = G1.genre ))";
			PrintablePreparedStatement ps = new PrintablePreparedStatement(connection.prepareStatement(query), query, false);
			ResultSet rs = ps.executeQuery();

			while (rs.next()) {
				VideoGameModel model = new VideoGameModel(null,
						INVALID_INPUT,
						null,
						rs.getString("name"));
				result.add(model);
			}

			rs.close();
			ps.close();
		} catch (SQLException e) {
			System.out.println(EXCEPTION_TAG + " " + e.getMessage());
			throw e;

		}

		return result.toArray(new VideoGameModel[result.size()]);
	}

	public List<String> tableList() {
		List<String> existingTables = new ArrayList<>();
		try {
			String query = "select table_name from user_tables";
			PrintablePreparedStatement ps = new PrintablePreparedStatement(connection.prepareStatement(query), query, false);
			ResultSet rs = ps.executeQuery();
			String table = "";
			while (rs.next()) {
				table = rs.getString("table_name");
				existingTables.add(table);
			}

			rs.close();
			ps.close();
		} catch (SQLException e) {
			System.out.println(EXCEPTION_TAG + " " + e.getMessage());
		}
		return existingTables;
	}

	public List<String> projectionColList(String table) {
		List<String> existingColumns = new ArrayList<>();
		try {
			String query = "select column_name from ALL_TAB_COLUMNS where table_name = ?";
			PrintablePreparedStatement ps = new PrintablePreparedStatement(connection.prepareStatement(query), query, false);
			ps.setString(1, table);
			ResultSet rs = ps.executeQuery();
			String column = "";
			while (rs.next()) {
				column = rs.getString("column_name");
				existingColumns.add(column);
			}

			rs.close();
			ps.close();
		} catch (SQLException e) {
			System.out.println(EXCEPTION_TAG + " " + e.getMessage());
		}
		return existingColumns;
	}

	public VenueModel[] projectionVenue(List<String> projectionColumns) throws SQLException {
		ArrayList<VenueModel> result = new ArrayList<VenueModel>();
		String queryColumns = "";
		for (int i = 0; i < projectionColumns.size(); i++) {
			queryColumns = queryColumns + projectionColumns.get(i) + ", ";
		}
		queryColumns = queryColumns.substring(0, queryColumns.length() - 2);

		try {
			String query = "SELECT " + queryColumns + " FROM Venue";
			PrintablePreparedStatement ps = new PrintablePreparedStatement(connection.prepareStatement(query), query, false);
			ResultSet rs = ps.executeQuery();
			String name = "";
			int capacity = INVALID_INPUT;
			String address = "";

			while (rs.next()) {
				if (projectionColumns.contains("name")) {
					name = rs.getString("name");
				}
				if (projectionColumns.contains("capacity")) {
					capacity = rs.getInt("capacity");
				}
				if (projectionColumns.contains("address")) {
					address = rs.getString("address");
				}
				VenueModel model = new VenueModel(name, address, capacity);
				result.add(model);
			}

			rs.close();
			ps.close();
		} catch (SQLException e) {
			System.out.println(EXCEPTION_TAG + " " + e.getMessage());
			throw e;

		}

		return result.toArray(new VenueModel[result.size()]);
	}

	public AwardModel[] projectionAward(List<String> projectionColumns) throws SQLException {
		ArrayList<AwardModel> result = new ArrayList<AwardModel>();
		String queryColumns = "";
		for (int i = 0; i < projectionColumns.size(); i++) {
			queryColumns = queryColumns + projectionColumns.get(i) + ", ";
		}
		queryColumns = queryColumns.substring(0, queryColumns.length() - 2);

		try {
			String query = "SELECT " + queryColumns + " FROM Award";
			PrintablePreparedStatement ps = new PrintablePreparedStatement(connection.prepareStatement(query), query, false);
			ResultSet rs = ps.executeQuery();
			String name = "";
			int prize = INVALID_INPUT;
			Date award_date = null;
			String videogame_title = "";
			int videogame_year = INVALID_INPUT;

			while (rs.next()) {
				if (projectionColumns.contains("name")) {
					name = rs.getString("name");
				}
				if (projectionColumns.contains("prize")) {
					prize = rs.getInt("prize");
				}
				if (projectionColumns.contains("award_date")) {
					award_date = rs.getDate("award_date");
				}
				if (projectionColumns.contains("videogame_title")) {
					videogame_title = rs.getString("videogame_title");
				}
				if (projectionColumns.contains("videogame_year")) {
					videogame_year = rs.getInt("videogame_year");
				}
				AwardModel model = new AwardModel(name, prize, award_date, videogame_title, videogame_year);
				result.add(model);
			}

			rs.close();
			ps.close();
		} catch (SQLException e) {
			System.out.println(EXCEPTION_TAG + " " + e.getMessage());
			throw e;

		}

		return result.toArray(new AwardModel[result.size()]);
	}

	public AwardCeremonyModel[] projectionAwardCeremony(List<String> projectionColumns) throws SQLException {
		ArrayList<AwardCeremonyModel> result = new ArrayList<AwardCeremonyModel>();
		String queryColumns = "";
		for (int i = 0; i < projectionColumns.size(); i++) {
			queryColumns = queryColumns + projectionColumns.get(i) + ", ";
		}
		queryColumns = queryColumns.substring(0, queryColumns.length() - 2);

		try {
			String query = "SELECT " + queryColumns + " FROM AwardCeremony";
			PrintablePreparedStatement ps = new PrintablePreparedStatement(connection.prepareStatement(query), query, false);
			ResultSet rs = ps.executeQuery();
			String venue_name = "";
			int viewer_count = INVALID_INPUT;
			Date award_ceremony_date = null;

			while (rs.next()) {
				if (projectionColumns.contains("venue_name")) {
					venue_name = rs.getString("venue_name");
				}
				if (projectionColumns.contains("award_ceremony_date")) {
					award_ceremony_date = rs.getDate("award_ceremony_date");
				}
				if (projectionColumns.contains("viewer_count")) {
					viewer_count = rs.getInt("viewer_count");
				}
				AwardCeremonyModel model = new AwardCeremonyModel(viewer_count, award_ceremony_date, venue_name);
				result.add(model);
			}

			rs.close();
			ps.close();
		} catch (SQLException e) {
			System.out.println(EXCEPTION_TAG + " " + e.getMessage());
			throw e;

		}

		return result.toArray(new AwardCeremonyModel[result.size()]);
	}

	public CommunityAwardModel[] projectionCommunityAward(List<String> projectionColumns) throws SQLException {
		ArrayList<CommunityAwardModel> result = new ArrayList<CommunityAwardModel>();
		String queryColumns = "";
		for (int i = 0; i < projectionColumns.size(); i++) {
			queryColumns = queryColumns + projectionColumns.get(i) + ", ";
		}
		queryColumns = queryColumns.substring(0, queryColumns.length() - 2);

		try {
			String query = "SELECT " + queryColumns + " FROM CommunityAward";
			PrintablePreparedStatement ps = new PrintablePreparedStatement(connection.prepareStatement(query), query, false);
			ResultSet rs = ps.executeQuery();
			String award_name = "";
			int votes = INVALID_INPUT;
			Date deadline = null;
			Date award_date = null;

			while (rs.next()) {
				if (projectionColumns.contains("award_name")) {
					award_name = rs.getString("award_name");
				}
				if (projectionColumns.contains("deadline")) {
					deadline = rs.getDate("deadline");
				}
				if (projectionColumns.contains("award_date")) {
					award_date = rs.getDate("award_date");
				}
				if (projectionColumns.contains("votes")) {
					votes = rs.getInt("votes");
				}
				CommunityAwardModel model = new CommunityAwardModel(votes, deadline, award_name, award_date);
				result.add(model);
			}

			rs.close();
			ps.close();
		} catch (SQLException e) {
			System.out.println(EXCEPTION_TAG + " " + e.getMessage());
			throw e;

		}

		return result.toArray(new CommunityAwardModel[result.size()]);
	}

	public CompanyModel[] projectionCompany(List<String> projectionColumns) throws SQLException {
		ArrayList<CompanyModel> result = new ArrayList<CompanyModel>();
		String queryColumns = "";
		for (int i = 0; i < projectionColumns.size(); i++) {
			queryColumns = queryColumns + projectionColumns.get(i) + ", ";
		}
		queryColumns = queryColumns.substring(0, queryColumns.length() - 2);

		try {
			String query = "SELECT " + queryColumns + " FROM Company";
			PrintablePreparedStatement ps = new PrintablePreparedStatement(connection.prepareStatement(query), query, false);
			ResultSet rs = ps.executeQuery();
			String name = "";
			String contact_info = "";

			while (rs.next()) {
				if (projectionColumns.contains("name")) {
					name = rs.getString("name");
				}
				if (projectionColumns.contains("contact_info")) {
					contact_info = rs.getString("contact_info");
				}
				CompanyModel model = new CompanyModel(name, contact_info);
				result.add(model);
			}

			rs.close();
			ps.close();
		} catch (SQLException e) {
			System.out.println(EXCEPTION_TAG + " " + e.getMessage());
			throw e;

		}

		return result.toArray(new CompanyModel[result.size()]);
	}

	public DeveloperCountryModel[] projectionDeveloperCountry(List<String> projectionColumns) throws SQLException {
		ArrayList<DeveloperCountryModel> result = new ArrayList<DeveloperCountryModel>();
		String queryColumns = "";
		for (int i = 0; i < projectionColumns.size(); i++) {
			queryColumns = queryColumns + projectionColumns.get(i) + ", ";
		}
		queryColumns = queryColumns.substring(0, queryColumns.length() - 2);

		try {
			String query = "SELECT " + queryColumns + " FROM DeveloperCountry";
			PrintablePreparedStatement ps = new PrintablePreparedStatement(connection.prepareStatement(query), query, false);
			ResultSet rs = ps.executeQuery();
			String name = "";
			String country = "";

			while (rs.next()) {
				if (projectionColumns.contains("name")) {
					name = rs.getString("name");
				}
				if (projectionColumns.contains("country")) {
					country = rs.getString("country");
				}
				DeveloperCountryModel model = new DeveloperCountryModel(name, country);
				result.add(model);
			}

			rs.close();
			ps.close();
		} catch (SQLException e) {
			System.out.println(EXCEPTION_TAG + " " + e.getMessage());
			throw e;

		}

		return result.toArray(new DeveloperCountryModel[result.size()]);
	}

	public DeveloperNameModel[] projectionDeveloperName(List<String> projectionColumns) throws SQLException {
		ArrayList<DeveloperNameModel> result = new ArrayList<DeveloperNameModel>();
		String queryColumns = "";
		for (int i = 0; i < projectionColumns.size(); i++) {
			queryColumns = queryColumns + projectionColumns.get(i) + ", ";
		}
		queryColumns = queryColumns.substring(0, queryColumns.length() - 2);

		try {
			String query = "SELECT " + queryColumns + " FROM DeveloperName";
			PrintablePreparedStatement ps = new PrintablePreparedStatement(connection.prepareStatement(query), query, false);
			ResultSet rs = ps.executeQuery();
			String name = "";
			String lead_developer = "";
			String website = "";

			while (rs.next()) {
				if (projectionColumns.contains("name")) {
					name = rs.getString("name");
				}
				if (projectionColumns.contains("lead_developer")) {
					lead_developer = rs.getString("lead_developer");
				}
				if (projectionColumns.contains("website")) {
					website = rs.getString("website");
				}
				DeveloperNameModel model = new DeveloperNameModel(lead_developer, website, name);
				result.add(model);
			}

			rs.close();
			ps.close();
		} catch (SQLException e) {
			System.out.println(EXCEPTION_TAG + " " + e.getMessage());
			throw e;

		}

		return result.toArray(new DeveloperNameModel[result.size()]);
	}

	public LivestreamUrlModel[] projectionLivestreamUrl(List<String> projectionColumns) throws SQLException {
		ArrayList<LivestreamUrlModel> result = new ArrayList<LivestreamUrlModel>();
		String queryColumns = "";
		for (int i = 0; i < projectionColumns.size(); i++) {
			queryColumns = queryColumns + projectionColumns.get(i) + ", ";
		}
		queryColumns = queryColumns.substring(0, queryColumns.length() - 2);

		try {
			String query = "SELECT " + queryColumns + " FROM LivestreamUrl";
			PrintablePreparedStatement ps = new PrintablePreparedStatement(connection.prepareStatement(query), query, false);
			ResultSet rs = ps.executeQuery();
			String name = "";
			String language = "";
			String url = "";
			Date awardceremony_date = null;

			while (rs.next()) {
				if (projectionColumns.contains("name")) {
					name = rs.getString("name");
				}
				if (projectionColumns.contains("language")) {
					language = rs.getString("language");
				}
				if (projectionColumns.contains("url")) {
					url = rs.getString("url");
				}
				if (projectionColumns.contains("awardceremony_date")) {
					awardceremony_date = rs.getDate("awardceremony_date");
				}
				LivestreamUrlModel model = new LivestreamUrlModel(url, language, name, awardceremony_date);
				result.add(model);
			}

			rs.close();
			ps.close();
		} catch (SQLException e) {
			System.out.println(EXCEPTION_TAG + " " + e.getMessage());
			throw e;

		}

		return result.toArray(new LivestreamUrlModel[result.size()]);
	}

	public LivestreamViewerCountModel[] projectionLivestreamViewerCount(List<String> projectionColumns) throws SQLException {
		ArrayList<LivestreamViewerCountModel> result = new ArrayList<LivestreamViewerCountModel>();
		String queryColumns = "";
		for (int i = 0; i < projectionColumns.size(); i++) {
			queryColumns = queryColumns + projectionColumns.get(i) + ", ";
		}
		queryColumns = queryColumns.substring(0, queryColumns.length() - 2);

		try {
			String query = "SELECT " + queryColumns + " FROM LivestreamViewerCount";
			PrintablePreparedStatement ps = new PrintablePreparedStatement(connection.prepareStatement(query), query, false);
			ResultSet rs = ps.executeQuery();
			String name = "";
			String language = "";
			int viewer_count = INVALID_INPUT;
			Date awardceremony_date = null;

			while (rs.next()) {
				if (projectionColumns.contains("name")) {
					name = rs.getString("name");
				}
				if (projectionColumns.contains("language")) {
					language = rs.getString("language");
				}
				if (projectionColumns.contains("viewer_count")) {
					viewer_count = rs.getInt("viewer_count");
				}
				if (projectionColumns.contains("awardceremony_date")) {
					awardceremony_date = rs.getDate("awardceremony_date");
				}
				LivestreamViewerCountModel model = new LivestreamViewerCountModel(language, viewer_count, name, awardceremony_date);
				result.add(model);
			}

			rs.close();
			ps.close();
		} catch (SQLException e) {
			System.out.println(EXCEPTION_TAG + " " + e.getMessage());
			throw e;

		}

		return result.toArray(new LivestreamViewerCountModel[result.size()]);
	}

	public SponsoredAwardModel[] projectionSponsoredAward(List<String> projectionColumns) throws SQLException {
		ArrayList<SponsoredAwardModel> result = new ArrayList<SponsoredAwardModel>();
		String queryColumns = "";
		for (int i = 0; i < projectionColumns.size(); i++) {
			queryColumns = queryColumns + projectionColumns.get(i) + ", ";
		}
		queryColumns = queryColumns.substring(0, queryColumns.length() - 2);

		try {
			String query = "SELECT " + queryColumns + " FROM SponsoredAward";
			PrintablePreparedStatement ps = new PrintablePreparedStatement(connection.prepareStatement(query), query, false);
			ResultSet rs = ps.executeQuery();
			String sponsor = "";
			String award_name = "";
			Date award_date = null;

			while (rs.next()) {
				if (projectionColumns.contains("sponsor")) {
					sponsor = rs.getString("sponsor");
				}
				if (projectionColumns.contains("award_name")) {
					award_name = rs.getString("award_name");
				}
				if (projectionColumns.contains("award_date")) {
					award_date = rs.getDate("award_date");
				}
				SponsoredAwardModel model = new SponsoredAwardModel(sponsor, award_name, award_date);
				result.add(model);
			}

			rs.close();
			ps.close();
		} catch (SQLException e) {
			System.out.println(EXCEPTION_TAG + " " + e.getMessage());
			throw e;

		}

		return result.toArray(new SponsoredAwardModel[result.size()]);
	}

	public SponsorsModel[] projectionSponsors(List<String> projectionColumns) throws SQLException {
		ArrayList<SponsorsModel> result = new ArrayList<SponsorsModel>();
		String queryColumns = "";
		for (int i = 0; i < projectionColumns.size(); i++) {
			queryColumns = queryColumns + projectionColumns.get(i) + ", ";
		}
		queryColumns = queryColumns.substring(0, queryColumns.length() - 2);

		try {
			String query = "SELECT " + queryColumns + " FROM Sponsors";
			PrintablePreparedStatement ps = new PrintablePreparedStatement(connection.prepareStatement(query), query, false);
			ResultSet rs = ps.executeQuery();
			String company_name = "";
			int money = INVALID_INPUT;
			Date awardceremony_date = null;

			while (rs.next()) {
				if (projectionColumns.contains("company_name")) {
					company_name = rs.getString("company_name");
				}
				if (projectionColumns.contains("money")) {
					money = rs.getInt("money");
				}
				if (projectionColumns.contains("awardceremony_date")) {
					awardceremony_date = rs.getDate("awardceremony_date");
				}
				SponsorsModel model = new SponsorsModel(company_name, awardceremony_date, money);
				result.add(model);
			}

			rs.close();
			ps.close();
		} catch (SQLException e) {
			System.out.println(EXCEPTION_TAG + " " + e.getMessage());
			throw e;
		}

		return result.toArray(new SponsorsModel[result.size()]);
	}

	public StaffModel[] projectionStaff(List<String> projectionColumns) throws SQLException {
		ArrayList<StaffModel> result = new ArrayList<StaffModel>();
		String queryColumns = "";
		for (int i = 0; i < projectionColumns.size(); i++) {
			queryColumns = queryColumns + projectionColumns.get(i) + ", ";
		}
		queryColumns = queryColumns.substring(0, queryColumns.length() - 2);

		try {
			String query = "SELECT " + queryColumns + " FROM Staff";
			PrintablePreparedStatement ps = new PrintablePreparedStatement(connection.prepareStatement(query), query, false);
			ResultSet rs = ps.executeQuery();
			String name = "";
			int phone_number = INVALID_INPUT;
			String role = "";
			int id = INVALID_INPUT;

			while (rs.next()) {
				if (projectionColumns.contains("name")) {
					name = rs.getString("name");
				}
				if (projectionColumns.contains("phone_number")) {
					phone_number = rs.getInt("phone_number");
				}
				if (projectionColumns.contains("role")) {
					role = rs.getString("role");
				}
				if (projectionColumns.contains("id")) {
					id = rs.getInt("id");
				}
				StaffModel model = new StaffModel(phone_number, name, id, role);
				result.add(model);
			}

			rs.close();
			ps.close();
		} catch (SQLException e) {
			System.out.println(EXCEPTION_TAG + " " + e.getMessage());
			throw e;
		}

		return result.toArray(new StaffModel[result.size()]);
	}

	public StaffAwardCeremonyModel[] projectionStaffAwardCeremony(List<String> projectionColumns) throws SQLException {
		ArrayList<StaffAwardCeremonyModel> result = new ArrayList<StaffAwardCeremonyModel>();
		String queryColumns = "";
		for (int i = 0; i < projectionColumns.size(); i++) {
			queryColumns = queryColumns + projectionColumns.get(i) + ", ";
		}
		queryColumns = queryColumns.substring(0, queryColumns.length() - 2);

		try {
			String query = "SELECT " + queryColumns + " FROM Staff_AwardCeremony";
			PrintablePreparedStatement ps = new PrintablePreparedStatement(connection.prepareStatement(query), query, false);
			ResultSet rs = ps.executeQuery();
			Date awardceremony_date = null;
			int staff_id = INVALID_INPUT;

			while (rs.next()) {
				if (projectionColumns.contains("awardceremony_date")) {
					awardceremony_date = rs.getDate("awardceremony_date");
				}
				if (projectionColumns.contains("staff_id")) {
					staff_id = rs.getInt("staff_id");
				}
				StaffAwardCeremonyModel model = new StaffAwardCeremonyModel(staff_id, awardceremony_date);
				result.add(model);
			}

			rs.close();
			ps.close();
		} catch (SQLException e) {
			System.out.println(EXCEPTION_TAG + " " + e.getMessage());
			throw e;
		}

		return result.toArray(new StaffAwardCeremonyModel[result.size()]);
	}

	public VideoGameDLCModel[] projectionVideoGameDLC(List<String> projectionColumns) throws SQLException {
		ArrayList<VideoGameDLCModel> result = new ArrayList<VideoGameDLCModel>();
		String queryColumns = "";
		for (int i = 0; i < projectionColumns.size(); i++) {
			queryColumns = queryColumns + projectionColumns.get(i) + ", ";
		}
		queryColumns = queryColumns.substring(0, queryColumns.length() - 2);

		try {
			String query = "SELECT " + queryColumns + " FROM VideoGame_DLC";
			PrintablePreparedStatement ps = new PrintablePreparedStatement(connection.prepareStatement(query), query, false);
			ResultSet rs = ps.executeQuery();
			String title = "";
			int year = INVALID_INPUT;
			String videogame_title = "";
			int videogame_year = INVALID_INPUT;

			while (rs.next()) {
				if (projectionColumns.contains("title")) {
					title = rs.getString("title");
				}
				if (projectionColumns.contains("year")) {
					year = rs.getInt("year");
				}
				if (projectionColumns.contains("videogame_title")) {
					videogame_title = rs.getString("videogame_title");
				}
				if (projectionColumns.contains("videogame_year")) {
					videogame_year = rs.getInt("videogame_year");
				}
				VideoGameDLCModel model = new VideoGameDLCModel(title, year, videogame_title, videogame_year);
				result.add(model);
			}

			rs.close();
			ps.close();
		} catch (SQLException e) {
			System.out.println(EXCEPTION_TAG + " " + e.getMessage());
			throw e;
		}

		return result.toArray(new VideoGameDLCModel[result.size()]);
	}
}
