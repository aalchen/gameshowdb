package ca.ubc.cs304.delegates;

import ca.ubc.cs304.model.DeveloperNameModel;
import ca.ubc.cs304.model.VideoGameModel;

import java.util.List;

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
	public void showDeveloperName();
	public void updateVideoGame(String newTitle, int year, String oldTitle);
	public void terminalGamesFinished();
	public void insertDeveloperName(DeveloperNameModel model);
	public void deleteDeveloperName(String developerName);
	public void updateDeveloperNameLead(String newLeadDev, String developerName);
	public void updateDeveloperNameWebsite(String website, String developerName);
	public void updateDeveloperNameName(String newDeveloperName, String developerName);
	public void deleteDeveloperNameLead(String deleteLeadDev);
	public void deleteDeveloperNameWeb(String deleteWebsite);
    public void selectLeadDev(String leadDev);
	public void selectWebsite(String website);
	public void selectName(String name);
	public void projectionColumns(List<String> columns);
	public void joinTables(List<String> colsArray, String joinWhereCol, String joinWhere, String table1, String table2);
	public void aggregateGroupBy(String table, String aggregationOp, String aggregateCol, List<String> otherCol, String groupByCol);
}
