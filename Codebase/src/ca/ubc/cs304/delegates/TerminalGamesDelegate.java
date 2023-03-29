package ca.ubc.cs304.delegates;

import ca.ubc.cs304.model.BranchModel;
import ca.ubc.cs304.model.VideoGameModel;

/**
 * This interface uses the delegation design pattern where instead of having
 * the TerminalTransactions class try to do everything, it will only
 * focus on handling the UI. The actual logic/operation will be delegated to the 
 * controller class (in this case Bank).
 * 
 * TerminalTransactions calls the methods that we have listed below but 
 * Bank is the actual class that will implement the methods.
 */
public interface TerminalGamesDelegate {
	public void databaseSetup();
	
	public void deleteVideoGame(String title, int year);
	public void insertVideoGame(VideoGameModel videoGame);
	public void showVideoGame();
	public void updateVideoGame(String title, int year);
	
	public void terminalGamesFinished();
}
