// reference: https://github.students.cs.ubc.ca/CPSC304/CPSC304_Java_Project

package ca.ubc.cs304.controller;

import ca.ubc.cs304.database.GameAwardsDbHandler;
import ca.ubc.cs304.delegates.GUIWindowDelegate;
import ca.ubc.cs304.delegates.LoginWindowDelegate;
import ca.ubc.cs304.delegates.TerminalGamesDelegate;
import ca.ubc.cs304.model.*;
import ca.ubc.cs304.ui.MainMenuWindow;
import ca.ubc.cs304.ui.LoginWindow;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

/**
 * This is the main controller class that will orchestrate everything.
 */
public class GameAwardsShow implements LoginWindowDelegate, TerminalGamesDelegate, GUIWindowDelegate {
	private GameAwardsDbHandler dbHandler = null;
	private LoginWindow loginWindow = null;

	public GameAwardsShow() {
		dbHandler = new GameAwardsDbHandler();
	}

	private void start() {
		loginWindow = new LoginWindow();
		loginWindow.showFrame(this);
	}

	/**
	 * LoginWindowDelegate Implementation
	 *
     * connects to Oracle database with supplied username and password
     */
	public void login(String username, String password) throws IOException {
		boolean didConnect = dbHandler.login(username, password);

		if (didConnect) {
			// Once connected, remove login window and start text transaction flow
			loginWindow.dispose();

			MainMenuWindow mainMenuWindow = new MainMenuWindow();
			databaseSetup();
			mainMenuWindow.mainMenuHandler(this);
		} else {
			loginWindow.handleLoginFailed();

			if (loginWindow.hasReachedMaxLoginAttempts()) {
				loginWindow.dispose();
				System.out.println("You have exceeded your number of allowed attempts");
				System.exit(-1);
			}
		}
	}

	/**
	 * TermainalTransactionsDelegate Implementation
	 *
	 * Insert a branch with the given info
	 */
    public void insertVideoGame(VideoGameModel model) throws SQLException {
    	dbHandler.insertVideoGame(model);
    }

	public void showVideoGame() throws SQLException {
		VideoGameModel[] models = dbHandler.getVideoGameInfo();

		for (int i = 0; i < models.length; i++) {
			VideoGameModel model = models[i];

			// simplified output formatting; truncation may occur
			System.out.println(model.getTitle());
			System.out.println(model.getYear());
			System.out.println(model.getGenre());
			System.out.println(model.getDeveloperName());
			System.out.println();
		}
	}

	public VideoGameModel[] getVideoGamesObjects() throws SQLException {
		return dbHandler.getVideoGameInfo();
	}

	public DeveloperNameModel[] getDeveloperNamesObjects() throws SQLException {
		return dbHandler.getDeveloperNameInfo();
	}

	public void showDeveloperName() throws SQLException {
		DeveloperNameModel[] models = dbHandler.getDeveloperNameInfo();

		for (int i = 0; i < models.length; i++) {
			DeveloperNameModel model = models[i];

			System.out.println(model.getLeadDeveloper());
			System.out.println(model.getWebsite());
			System.out.println(model.getName());
			System.out.println();
		}
	}

	public void updateVideoGame(String newTitle, int year, String oldTitle) throws SQLException {
		dbHandler.updateVideoGame(newTitle, year, oldTitle);
	}

	public void terminalGamesFinished() {
		dbHandler.close();
		dbHandler = null;

		System.exit(0);
	}

	public void insertDeveloperName(DeveloperNameModel model) throws SQLException {
		dbHandler.insertDeveloperName(model);
	}

	public void deleteDeveloperName(String developerName) throws SQLException {
		dbHandler.deleteDeveloperName(developerName);
	}

	public void updateDeveloperName(String newLeadDev, String developerName) throws SQLException {
		dbHandler.updateDeveloperName(newLeadDev, developerName);
	}

	public void updateDeveloperNameLead(String newLeadDev, String developerName) throws SQLException {
		dbHandler.updateDeveloperNameLead(newLeadDev, developerName);
	}

	public void updateDeveloperNameWebsite(String website, String developerName) throws SQLException {
		dbHandler.updateDeveloperNameWebsite(website, developerName);
	}

	public void updateDeveloperNameName(String newDeveloperName, String developerName) throws SQLException {
		dbHandler.updateDeveloperNameName(newDeveloperName, developerName);
	}

	public void deleteDeveloperNameLead(String deleteLeadDev) throws SQLException {
		dbHandler.deleteDeveloperNameLead(deleteLeadDev);
	}

	public void deleteDeveloperNameWeb(String deleteWebsite) throws SQLException {
		dbHandler.deleteDeveloperNameWeb(deleteWebsite);
	}

	public DeveloperNameModel[] filterDevelopers(String leadDev, String website, String name) throws SQLException {
		return dbHandler.filterDevelopers(leadDev, website, name);
	}

		public DeveloperNameModel[] selectLeadDev(String leadDev) throws SQLException {
		DeveloperNameModel[] models = dbHandler.selectLeadDev(leadDev);

		for (int i = 0; i < models.length; i++) {
			DeveloperNameModel model = models[i];

			// simplified output formatting; truncation may occur
			System.out.println(model.getLeadDeveloper());
			System.out.println(model.getWebsite());
			System.out.println(model.getName());
			System.out.println();
		}
		return models;

	}

	public DeveloperNameModel[] selectWebsite(String website) {
		DeveloperNameModel[] models = dbHandler.selectWebsite(website);

		for (int i = 0; i < models.length; i++) {
			DeveloperNameModel model = models[i];

			// simplified output formatting; truncation may occur
			System.out.println(model.getLeadDeveloper());
			System.out.println(model.getWebsite());
			System.out.println(model.getName());
			System.out.println();
		}
		return models;

	}

	public DeveloperNameModel[] selectName(String name) throws SQLException {
		DeveloperNameModel[] models = dbHandler.selectName(name);

		for (int i = 0; i < models.length; i++) {
			DeveloperNameModel model = models[i];

			// simplified output formatting; truncation may occur
			System.out.println(model.getLeadDeveloper());
			System.out.println(model.getWebsite());
			System.out.println(model.getName());
			System.out.println();
		}
		return models;
	}

	public VideoGameModel[] projectionVideoGame(List<String> columns) throws SQLException {
		VideoGameModel[] models = dbHandler.projectionVideoGame(columns);

//		for (int i = 0; i < models.length; i++) {
//			VideoGameModel model = models[i];
//
//			if (columns.contains("Title")) {
//				System.out.println(model.getTitle());
//			}
//			if (columns.contains("Year")) {
//				System.out.println(model.getYear());
//			}
//			if (columns.contains("Genre")) {
//				System.out.println(model.getGenre());
//			}
//			if (columns.contains("Developer_Name")) {
//				System.out.println(model.getDeveloperName());
//			}
//
//			System.out.println();
//		}
		return models;
	}

	public VenueModel[] projectionVenue(List<String> columns) throws SQLException {
		VenueModel[] models = dbHandler.projectionVenue(columns);
		return models;
	}


	public DeveloperNameVideoGameModel[] joinTables(String joinWhere) throws SQLException {
		DeveloperNameVideoGameModel[] models = dbHandler.joinTables(joinWhere);

		for (int i = 0; i < models.length; i++) {
			DeveloperNameVideoGameModel model = models[i];
			System.out.println(model.getTitle());
			System.out.println(model.getYear());
			System.out.println(model.getGenre());
			System.out.println(model.getDeveloperName());
			System.out.println(model.getLeadDeveloper());
			System.out.println(model.getWebsite());
			System.out.println();
		}
		return models;
	}

	public VideoGameCountModel[] aggregateGroupBy() throws SQLException {
		VideoGameCountModel[] models = dbHandler.aggregateGroupBy();
		System.out.println("Aggregate Group By query : number of genres per developer");
		for (int i = 0; i < models.length; i++) {
			VideoGameCountModel model = models[i];
			System.out.println(model.getDeveloperName());
			System.out.println(model.getColumnNum());
		}
		System.out.println();

		return models;
	}

	public VideoGameCountModel[] aggregateGroupByHaving() throws SQLException {
		VideoGameCountModel[] models = dbHandler.aggregateGroupByHaving();
		System.out.println("Aggregate Group By Having query : find most recent releases after 2015, by genre and developer");
		for (int i = 0; i < models.length; i++) {
			VideoGameCountModel model = models[i];
			System.out.println(model.getGenre());
			System.out.println(model.getDeveloperName());
			System.out.println(model.getColumnNum());
		}
		System.out.println();

		return models;
	}

	public VideoGameModel[] division() throws SQLException {
		VideoGameModel[] models = dbHandler.division();
		System.out.println("Division query : developer names who worked in every genre");
		for (int i = 0; i < models.length; i++) {
			VideoGameModel model = models[i];
			System.out.println(model.getDeveloperName());
		}
		System.out.println();
		return models;
	}

	public VideoGameCountModel[] nestedAggregation() throws SQLException {
		VideoGameCountModel[] models = dbHandler.nestedAggregation();
		System.out.println("Nested Aggregation query : number of titles per developer where game was released after 2015");
		for (int i = 0; i < models.length; i++) {
			VideoGameCountModel model = models[i];
			System.out.println(model.getDeveloperName());
			System.out.println(model.getColumnNum());
			System.out.println();
		}

		return models;
	}

	/**
	 * TermainalTransactionsDelegate Implementation
	 *
	 * Delete branch with given branch ID.
	 */
    public void deleteVideoGame(String gameName, int gameYear) throws SQLException {
    	dbHandler.deleteVideoGame(gameName, gameYear);
    }

    /**
	 * TerminalTransactionsDelegate Implementation
	 *
     * The TerminalTransaction instance tells us that the user is fine with dropping any existing table
     * called branch and creating a new one for this project to use
     */
	public void databaseSetup() {
		try {
			dbHandler.databaseSetup();
		} catch (SQLException e) {

		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	public List<String> tableList() {
		return dbHandler.tableList();
	}

	public List<String> projectionColList(String table) {
		return dbHandler.projectionColList(table);
	}

	/**
	 * Main method called at launch time
	 */
	public static void main(String args[]) {
		GameAwardsShow game = new GameAwardsShow();
		game.start();
	}
}
