package ca.ubc.cs304.database;

import ca.ubc.cs304.model.DeveloperNameModel;
import ca.ubc.cs304.model.VideoGameModel;
import ca.ubc.cs304.util.PrintablePreparedStatement;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

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

	public void deleteVideoGame(String name, int year) {
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
		}
	}

	public void insertVideoGame(VideoGameModel model) {
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
		}
	}

	public void insertDevName(DeveloperNameModel model) {
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

			while(rs.next()) {
				VideoGameModel model = new VideoGameModel(rs.getString("game_title"),
						rs.getInt("game_year"),
						rs.getString("game_genre"),
						rs.getString("genre_developer_name"));
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
		try  {
			connection.rollback();
		} catch (SQLException e) {
			System.out.println(EXCEPTION_TAG + " " + e.getMessage());
		}
	}

	public void databaseSetup() {
		dropBranchTableIfExists();

		try {
//			// removed ON UPDATE CASCADE as it's not supported in Oracle
//			// https://stackoverflow.com/questions/48399874/oracle-on-delete-on-update
			String query0 = "CREATE TABLE DeveloperName(lead_developer VARCHAR(50), dev_website VARCHAR(50), name VARCHAR(50) PRIMARY KEY)";
			String query1 = "CREATE TABLE VideoGame(title VARCHAR(128), year INTEGER, genre VARCHAR(50), developer_name VARCHAR(50), CONSTRAINT pk_game PRIMARY KEY (title, year), CONSTRAINT fk_devname FOREIGN KEY (developer_name) REFERENCES DeveloperName(name) ON DELETE SET NULL)";
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
		insertDevName(devName1);

		DeveloperNameModel devName2 = new DeveloperNameModel("Hidetaka Miyazaki", "https://www.fromsoftware.jp/ww/", "FromSoftware");
		insertDevName(devName2);

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

			while(rs.next()) {
				if(rs.getString(1).toLowerCase().equals("developername")) {
					System.out.println(rs.getString(1).toLowerCase());
					ps.execute("DROP TABLE DeveloperName");
					break;
				}
				if(rs.getString(1).toLowerCase().equals("videogame")) {
					System.out.println(rs.getString(1).toLowerCase());
					ps.execute("DROP TABLE VideoGame");
					break;
				}
			}

			rs.close();
			ps.close();
		} catch (SQLException e) {
			System.out.println(EXCEPTION_TAG + " " + e.getMessage());
		}
	}
}