package ca.ubc.cs304.delegates;

import ca.ubc.cs304.model.DeveloperNameModel;
import ca.ubc.cs304.model.DeveloperNameVideoGameModel;
import ca.ubc.cs304.model.VideoGameCountModel;
import ca.ubc.cs304.model.VideoGameModel;

import java.sql.SQLException;
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
	public void deleteVideoGame(String title, int year) throws SQLException;
	public void insertVideoGame(VideoGameModel videoGame) throws SQLException;
	public void showVideoGame() throws SQLException;
	public void showDeveloperName() throws SQLException;
	public void updateVideoGame(String newTitle, int year, String oldTitle) throws SQLException;
	public void terminalGamesFinished();
	public void insertDeveloperName(DeveloperNameModel model) throws SQLException;
	public void deleteDeveloperName(String developerName) throws SQLException;
	public void updateDeveloperNameLead(String newLeadDev, String developerName) throws SQLException;
	public void updateDeveloperNameWebsite(String website, String developerName) throws SQLException;
	public void updateDeveloperNameName(String newDeveloperName, String developerName) throws SQLException;
	public void deleteDeveloperNameLead(String deleteLeadDev) throws SQLException;
	public void deleteDeveloperNameWeb(String deleteWebsite) throws SQLException;
    public DeveloperNameModel[] selectLeadDev(String leadDev) throws SQLException;
	public DeveloperNameModel[] selectWebsite(String website);
	public DeveloperNameModel[] selectName(String name) throws SQLException;
	public VideoGameModel[] projectionColumns(List<String> columns) throws SQLException;
	public DeveloperNameVideoGameModel[] joinTables(String joinWhere) throws SQLException;
	public VideoGameCountModel[] aggregateGroupBy() throws SQLException;
	public VideoGameCountModel[] aggregateGroupByHaving() throws SQLException;
	public VideoGameModel[] division() throws SQLException;
	public VideoGameCountModel[] nestedAggregation() throws SQLException;
	public List<String> tableList();
	public List<String> projectionColList(String table);
}
