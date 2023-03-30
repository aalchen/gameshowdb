package ca.ubc.cs304.controller;

import ca.ubc.cs304.database.GameAwardsDbHandler;
import ca.ubc.cs304.delegates.LoginWindowDelegate;
import ca.ubc.cs304.delegates.TerminalGamesDelegate;
import ca.ubc.cs304.model.DeveloperNameModel;
import ca.ubc.cs304.model.VideoGameModel;
import ca.ubc.cs304.ui.LoginWindow;
import ca.ubc.cs304.ui.TerminalGames;

import java.io.IOException;

/**
 * This is the main controller class that will orchestrate everything.
 */
public class GameAwardsShow implements LoginWindowDelegate, TerminalGamesDelegate {
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

			TerminalGames transaction = new TerminalGames();
			transaction.setupDatabase(this);
			transaction.showMainMenu(this);
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
    public void insertVideoGame(VideoGameModel model) {
    	dbHandler.insertVideoGame(model);
    }

	public void showVideoGame() {
		VideoGameModel[] models = dbHandler.getVideoGameInfo();

		for (int i = 0; i < models.length; i++) {
			VideoGameModel model = models[i];

			// simplified output formatting; truncation may occur
			System.out.printf("%-10.10s", model.getTitle());
			System.out.printf("%-20.20s", model.getYear());
			System.out.printf("%-15.15s", model.getGenre());
			System.out.printf("%-15.15s", model.getDeveloperName());
			System.out.println();
		}
	}

	public void showDeveloperName() {
		DeveloperNameModel[] models = dbHandler.getDeveloperNameInfo();

		for (int i = 0; i < models.length; i++) {
			DeveloperNameModel model = models[i];

			// simplified output formatting; truncation may occur
			System.out.printf("%-10.10s", model.getLeadDeveloper());
			System.out.printf("%-20.20s", model.getWebsite());
			System.out.printf("%-15.15s", model.getName());
			System.out.println();
		}
	}

	public void updateVideoGame(String newTitle, int year, String oldTitle) {
		dbHandler.updateVideoGame(newTitle, year, oldTitle);
	}

	public void terminalGamesFinished() {
		dbHandler.close();
		dbHandler = null;

		System.exit(0);
	}

	public void insertDeveloperName(DeveloperNameModel model) {
		dbHandler.insertDeveloperName(model);
	}

	public void deleteDeveloperName(String developerName) {
		dbHandler.deleteDeveloperName(developerName);
	}

	public void updateDeveloperName(String newLeadDev, String developerName) {
		dbHandler.updateDeveloperName(newLeadDev, developerName);
	}

	public void updateDeveloperNameLead(String newLeadDev, String developerName) {
		dbHandler.updateDeveloperNameLead(newLeadDev, developerName);
	}

	public void updateDeveloperNameWebsite(String website, String developerName) {
		dbHandler.updateDeveloperNameWebsite(website, developerName);
	}

	public void updateDeveloperNameName(String newDeveloperName, String developerName) {
		dbHandler.updateDeveloperNameName(newDeveloperName, developerName);
	}

	public void deleteDeveloperNameLead(String deleteLeadDev) {
		dbHandler.deleteDeveloperNameLead(deleteLeadDev);
	}

	public void deleteDeveloperNameWeb(String deleteWebsite) {
		dbHandler.deleteDeveloperNameWeb(deleteWebsite);
	}

	public void selectLeadDev(String leadDev) {
		DeveloperNameModel[] models = dbHandler.selectLeadDev(leadDev);

		for (int i = 0; i < models.length; i++) {
			DeveloperNameModel model = models[i];

			// simplified output formatting; truncation may occur
			System.out.printf("%-10.10s", model.getLeadDeveloper());
			System.out.printf("%-20.20s", model.getWebsite());
			System.out.printf("%-15.15s", model.getName());
			System.out.println();
		}
	}

	public void selectWebsite(String website) {
		DeveloperNameModel[] models = dbHandler.selectWebsite(website);

		for (int i = 0; i < models.length; i++) {
			DeveloperNameModel model = models[i];

			// simplified output formatting; truncation may occur
			System.out.printf("%-10.10s", model.getLeadDeveloper());
			System.out.printf("%-20.20s", model.getWebsite());
			System.out.printf("%-15.15s", model.getName());
			System.out.println();
		}
	}

	public void selectName(String name) {
		DeveloperNameModel[] models = dbHandler.selectName(name);;

		for (int i = 0; i < models.length; i++) {
			DeveloperNameModel model = models[i];

			// simplified output formatting; truncation may occur
			System.out.printf("%-10.10s", model.getLeadDeveloper());
			System.out.printf("%-20.20s", model.getWebsite());
			System.out.printf("%-15.15s", model.getName());
			System.out.println();
		}
	}

	/**
	 * TermainalTransactionsDelegate Implementation
	 * 
	 * Delete branch with given branch ID.
	 */ 
    public void deleteVideoGame(String gameName, int gameYear) {
    	dbHandler.deleteVideoGame(gameName, gameYear);
    }
    
    /**
	 * TerminalTransactionsDelegate Implementation
	 * 
     * The TerminalTransaction instance tells us that the user is fine with dropping any existing table
     * called branch and creating a new one for this project to use
     */ 
	public void databaseSetup() {
		dbHandler.databaseSetup();;
		
	}
    
	/**
	 * Main method called at launch time
	 */
	public static void main(String args[]) {
		GameAwardsShow game = new GameAwardsShow();
		game.start();
	}
}
