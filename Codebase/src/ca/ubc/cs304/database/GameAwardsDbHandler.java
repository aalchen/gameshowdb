package ca.ubc.cs304.database;

import ca.ubc.cs304.model.DeveloperNameModel;
import ca.ubc.cs304.model.DeveloperNameVideoGameModel;
import ca.ubc.cs304.model.VideoGameCountModel;
import ca.ubc.cs304.model.VideoGameModel;
import ca.ubc.cs304.util.PrintablePreparedStatement;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
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

	public void insertDeveloperName(DeveloperNameModel model) {
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
		}
	}

	public VideoGameModel[] getVideoGameInfo() {
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
		}

		return result.toArray(new VideoGameModel[result.size()]);
	}

	public void updateVideoGame(String newName, int year, String oldName) {
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

	public void databaseSetup() throws SQLException {
		dropBranchTableIfExists();

		try {
			// removed ON UPDATE CASCADE as it's not supported in Oracle
			// https://stackoverflow.com/questions/48399874/oracle-on-delete-on-update
			String query0 = "CREATE TABLE DeveloperName(lead_developer VARCHAR(50), website VARCHAR(50), name VARCHAR(50) PRIMARY KEY)";
			String query1 = "CREATE TABLE VideoGame(title VARCHAR(128), year INTEGER, genre VARCHAR(50), developer_name VARCHAR(50), CONSTRAINT pk_game PRIMARY KEY (title, year), CONSTRAINT fk_devname FOREIGN KEY (developer_name) REFERENCES DeveloperName(name) ON DELETE CASCADE)";
			PrintablePreparedStatement ps0 = new PrintablePreparedStatement(connection.prepareStatement(query0), query0, false);
			ps0.executeUpdate();
			ps0.close();

			PrintablePreparedStatement ps2 = new PrintablePreparedStatement(connection.prepareStatement(query1), query1, false);
			ps2.executeUpdate();
			ps2.close();
		} catch (SQLException e) {
			System.out.println(EXCEPTION_TAG + " " + e.getMessage());
		}

		DeveloperNameModel devName1 = new DeveloperNameModel("Todd Howard", "https://bethesdagamestudios.com/", "Bethesda Game Studio");
		insertDeveloperName(devName1);

		DeveloperNameModel devName2 = new DeveloperNameModel("Hidetaka Miyazaki", "https://www.fromsoftware.jp/ww/", "FromSoftware");
		insertDeveloperName(devName2);

		VideoGameModel game1 = new VideoGameModel("Skyrim", 2013, "Action role-playing", "Bethesda Game Studio");
		insertVideoGame(game1);

		VideoGameModel game2 = new VideoGameModel("Sekiro: Shadows Die Twice", 2019, "Action-adventure", "FromSoftware");
		insertVideoGame(game2);
	}

	private void dropBranchTableIfExists() {
		try {
			String query = "select table_name from user_tables";
			PrintablePreparedStatement ps = new PrintablePreparedStatement(connection.prepareStatement(query), query, false);
			ResultSet rs = ps.executeQuery();

			while (rs.next()) {
				if (rs.getString(1).toLowerCase().equals("videogame")) {
					System.out.println(rs.getString(1).toLowerCase());
					ps.execute("DROP TABLE VideoGame");
					break;
				}
				if (rs.getString(1).toLowerCase().equals("developername")) {
					System.out.println(rs.getString(1).toLowerCase());
					ps.execute("DROP TABLE DeveloperName");
					break;
				}
			}

			rs.close();
			ps.close();
		} catch (SQLException e) {
			System.out.println(EXCEPTION_TAG + " " + e.getMessage());
		}
	}

	public DeveloperNameModel[] getDeveloperNameInfo() {
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
		}

		return result.toArray(new DeveloperNameModel[result.size()]);
	}

	public void updateDeveloperName(String newLeadDev, String developerName) {
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
		}
	}

	public void deleteDeveloperName(String developerName) {
		try {
			String query = "DELETE FROM DeveloperName WHERE name = ?";
			PrintablePreparedStatement ps = new PrintablePreparedStatement(connection.prepareStatement(query), query, false);
			ps.setString(1, developerName);

			int rowCount = ps.executeUpdate();
			if (rowCount == 0) {
				System.out.println(WARNING_TAG + " Developer name " + developerName + " does not exist!");
			}

			connection.commit();

			ps.close();
		} catch (SQLException e) {
			System.out.println(EXCEPTION_TAG + " " + e.getMessage());
			rollbackConnection();
		}
	}

	public void updateDeveloperNameLead(String newLeadDev, String developerName) {
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
		}
	}

	public void updateDeveloperNameWebsite(String website, String developerName) {
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
		}
	}

	public void updateDeveloperNameName(String newDeveloperName, String developerName) {
		try {
			String query = "UPDATE DeveloperName SET name = ? WHERE name = ?";
			PrintablePreparedStatement ps = new PrintablePreparedStatement(connection.prepareStatement(query), query, false);
			ps.setString(1, newDeveloperName);
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
		}
	}

	public void deleteDeveloperNameLead(String deleteDevLead) {
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
		}
	}

	public void deleteDeveloperNameWeb(String deleteWebsite) {
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
		}
	}

	public DeveloperNameModel[] selectLeadDev(String leadDev) {
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

	public VideoGameModel[] projectionColumns(List<String> projectionColumns) {
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
				if (projectionColumns.contains("Title")) {
					title = rs.getString("title");
				}
				if (projectionColumns.contains("Year")) {
					year = rs.getInt("year");
				}
				if (projectionColumns.contains("Genre")) {
					genre = rs.getString("genre");
				}
				if (projectionColumns.contains("Developer_Name")) {
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
		}

		return result.toArray(new VideoGameModel[result.size()]);
	}

	public DeveloperNameModel[] selectName(String name) {
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
		}

		return result.toArray(new DeveloperNameModel[result.size()]);
	}

	public DeveloperNameVideoGameModel[] joinTables(String joinWhere) {
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
		}

		return result.toArray(new DeveloperNameVideoGameModel[result.size()]);
	}

	public VideoGameCountModel[] aggregateGroupBy() {
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
		}

		return result.toArray(new VideoGameCountModel[result.size()]);

	}

	public VideoGameCountModel[] aggregateGroupByHaving() {
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
		}

		return result.toArray(new VideoGameCountModel[result.size()]);
	}

	public VideoGameCountModel[] nestedAggregation() {
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
		}

		return result.toArray(new VideoGameCountModel[result.size()]);
	}

	public VideoGameModel[] division() {
		ArrayList<VideoGameModel> result = new ArrayList<VideoGameModel>();

		try {
			String query = "SELECT D.name FROM DeveloperName D WHERE NOT EXISTS ( SELECT G.genre FROM VideoGame G WHERE NOT EXISTS ( SELECT * FROM VideoGame G2 WHERE G2.developer_name = D.name AND G2.genre = G.genre ) GROUP BY G.genre HAVING COUNT(*) = ( SELECT COUNT(DISTINCT genre) FROM VideoGame))";
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
		}

		return result.toArray(new VideoGameModel[result.size()]);
	}
}
