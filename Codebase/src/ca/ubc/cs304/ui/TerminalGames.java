package ca.ubc.cs304.ui;

import ca.ubc.cs304.delegates.TerminalGamesDelegate;
import ca.ubc.cs304.model.DeveloperNameModel;
import ca.ubc.cs304.model.VideoGameModel;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 * The class is only responsible for handling terminal text inputs. 
 */
public class TerminalGames {
	private static final String EXCEPTION_TAG = "[EXCEPTION]";
	private static final String WARNING_TAG = "[WARNING]";
	private static final int INVALID_INPUT = Integer.MIN_VALUE;
	private static final int EMPTY_INPUT = 0;

	private BufferedReader bufferedReader = null;
	private TerminalGamesDelegate delegate = null;

	public TerminalGames() {
	}
	
	/**
	 * Sets up the database to have a branch table with two tuples so we can insert/update/delete from it.
	 * Refer to the databaseSetup.sql file to determine what tuples are going to be in the table.
	 */
	public void setupDatabase(TerminalGamesDelegate delegate) {
		this.delegate = delegate;
		
		bufferedReader = new BufferedReader(new InputStreamReader(System.in));
		int choice = INVALID_INPUT;
		
		while(choice != 1 && choice != 2) {
			System.out.println("If you have a table called VideoGame in your database (capitialization of the name does not matter), it will be dropped and a new VideoGame table will be created.\nIf you want to proceed, enter 1; if you want to quit, enter 2.");
			
			choice = readInteger(false);
			
			if (choice != INVALID_INPUT) {
				switch (choice) {
				case 1:  
					delegate.databaseSetup(); 
					break;
				case 2:  
					handleQuitOption();
					break;
				default:
					System.out.println(WARNING_TAG + " The number that you entered was not a valid option.\n");
					break;
				}
			}
		}
	}

	/**
	 * Displays simple text interface
	 */ 
	public void showMainMenu(TerminalGamesDelegate delegate) throws IOException {
		this.delegate = delegate;
		
	    bufferedReader = new BufferedReader(new InputStreamReader(System.in));
		int choice = INVALID_INPUT;
		
		while (choice != 5) {
			System.out.println();
			System.out.println("1. Insert into VideoGame");
			System.out.println("2. Delete from VideoGame");
			System.out.println("3. Update a VideoGame");
			System.out.println("4. Show all VideoGame values");
			System.out.println("==========");
			System.out.println("5. Selection of tables");
			System.out.println("6. Projection of tables");
			System.out.println("7. Join tables");
			System.out.println("8. Aggregation with GROUP BY");
			System.out.println("9. Aggregation with HAVING");
			System.out.println("10. Nested aggregation with GROUP BY");
			System.out.println("11. Division of tables");
			System.out.println("==========");
			System.out.println("12. Insert into DeveloperName");
			System.out.println("13. Delete from DeveloperName");
			System.out.println("14. Update a DeveloperName");
			System.out.println("15. Show all DeveloperName values");
			System.out.println("==========");
			System.out.println("16. Quit");
			System.out.print("Please choose one of the above options: ");

			choice = readInteger(false);

			System.out.println(" ");

			if (choice != INVALID_INPUT) {
				switch (choice) {
				case 1:  
					handleInsertOption(); 
					break;
				case 2:  
					handleDeleteOption(); 
					break;
				case 3: 
					handleUpdateOption();
					break;
				case 4:  
					delegate.showVideoGame();
					break;
				case 5:
					handleSelectionOption();
					break;	
				case 16:
					handleQuitOption();
					break;
				case 12:
					handleDevInsertOption();
				break;
				case 13:
					handleDevDeleteOptionChoose(delegate);
					break;
				case 14:
					handleDevUpdateChoose(delegate);
					break;
				case 15:
					delegate.showDeveloperName();
				break;
				default:
					System.out.println(WARNING_TAG + " The number that you entered was not a valid option.");
					break;
				}
			}
		}		
	}

	private void handleSelectionOption() throws IOException {
		// choose table
		// choose which column to filter
		// give column value
		String table = null;


		while (table == null  || table.length() <= 0) {
			System.out.println("Please enter the TABLE name for Selection: ");
			table = readLine().trim();
		}

		if (table.equals("DeveloperName")) {
			System.out.println("Table selected is: " + table);

			System.out.println("Select columns to filter: ");
			bufferedReader = new BufferedReader(new InputStreamReader(System.in));
			int choice = INVALID_INPUT;

			while (choice != 4) {
				System.out.println();
				System.out.println("1. Lead_developer");
				System.out.println("2. Website");
				System.out.println("3. Name");
				System.out.println("4. Return to Main Menu");
				System.out.print("Please choose one of the above 4 options: ");

				String separatedInput = bufferedReader.readLine();
				System.out.println(separatedInput);

				if (separatedInput.contains("4")) {
					showMainMenu(delegate);
				}

				handleDevSelect(separatedInput);
			}
		}

	}

	private void handleDevSelect(String separatedInput) {
		if (separatedInput.contains("1")) {
			String lead_dev = null;

			while (lead_dev == null  || lead_dev.length() <= 0) {
				System.out.print("Please enter the lead developer to filter for: ");
				lead_dev = readLine().trim();
			}

			delegate.selectLeadDev(lead_dev);
		}
		if (separatedInput.contains("2")) {
			String website = null;

			while (website == null  || website.length() <= 0) {
				System.out.print("Please enter the website to filter for: ");
				website = readLine().trim();
			}

			delegate.selectWebsite(website);
		}
		if (separatedInput.contains("3")) {
			String name = null;

			while (name == null  || name.length() <= 0) {
				System.out.print("Please enter the developer name to filter for: ");
				name = readLine().trim();
			}

			delegate.selectName(name);
		}
	}

	private void handleDeleteOption() {
		String gameTitle = null;
		int gameYear = INVALID_INPUT;

		while (gameTitle == null  || gameTitle.length() <= 0) {
			System.out.print("Please enter the video game title you wish to delete: ");
			gameTitle = readLine().trim();
		}
		while (gameTitle != null && gameYear == INVALID_INPUT) {
			System.out.print("Please enter the video game year you wish to delete: ");
			gameYear = readInteger(false);
		}

		if (gameYear != INVALID_INPUT) {
			delegate.deleteVideoGame(gameTitle, gameYear);
		}
	}

	private void handleDevDeleteOptionChoose(TerminalGamesDelegate delegate) throws IOException {
		System.out.println("Select deletion method: ");
		bufferedReader = new BufferedReader(new InputStreamReader(System.in));
		int choice = INVALID_INPUT;
		
		while (choice != 4) {
			System.out.println();
			System.out.println("1. Delete by lead_developer");
			System.out.println("2. Delete by website");
			System.out.println("3. Delete by name");
			System.out.println("4. Return to Main Menu");
			System.out.print("Please choose one of the above 4 options: ");

			String separatedInput = bufferedReader.readLine();
			System.out.println(separatedInput);

			if (separatedInput.contains("4")) {
				showMainMenu(delegate);
			}

			if (separatedInput.contains("1")) {
				handleDevDeleteLead();
			}
			if (separatedInput.contains("2")) {
				handleDevDeleteWebsite();
			}
			if (separatedInput.contains("3")) {
				delegate.deleteDeveloperName(getDevNameDelete());
			}
		}
	}

	private void handleDevDeleteLead() {
		String lead_dev = null;

		while (lead_dev == null  || lead_dev.length() <= 0) {
			System.out.print("Please enter the lead developer you wish to delete: ");
			lead_dev = readLine().trim();
			delegate.deleteDeveloperNameLead(lead_dev);
		}
	}

	private void handleDevDeleteWebsite() {
		String website = null;

		while (website == null  || website.length() <= 0) {
			System.out.print("Please enter the website you wish to delete: ");
			website = readLine().trim();
			delegate.deleteDeveloperNameWeb(website);
		}
	}

	private void handleDevInsertOption() {
		String lead_dev = null;
		String website = null;
		String developer_name = null;

		while (lead_dev == null || lead_dev.length() <= 0) {
			System.out.print("Please enter the lead developer name you wish to insert: ");
			lead_dev = readLine().trim();
		}

		while (website == null || website.length() <= 0) {
			System.out.print("Please enter the website you wish to insert: ");
			website = readLine().trim();
		}

		while (developer_name == null || developer_name.length() <= 0) {
			System.out.print("Please enter the video game developer name you wish to insert: ");
			developer_name = readLine().trim();
		}

		DeveloperNameModel model = new DeveloperNameModel(lead_dev, website, developer_name);
		delegate.insertDeveloperName(model);
	}
	
	private void handleInsertOption() {
		String title = null;
		int year = INVALID_INPUT;
		String genre = null;
		String developer_name = null;

		while (title == null || title.length() <= 0) {
			System.out.print("Please enter the video game name you wish to insert: ");
			title = readLine().trim();
		}

		while (year == INVALID_INPUT) {
			System.out.print("Please enter the video game year you wish to insert: ");
			year = readInteger(false);
		}

		while (genre == null || genre.length() <= 0) {
			System.out.print("Please enter the video game genre you wish to insert: ");
			genre = readLine().trim();
		}

		while (developer_name == null || developer_name.length() <= 0) {
			System.out.print("Please enter the video game developer name you wish to insert: ");
			developer_name = readLine().trim();
		}

		VideoGameModel model = new VideoGameModel(title, year, genre, developer_name);
		delegate.insertVideoGame(model);
	}
	
	private void handleQuitOption() {
		System.out.println("Good Bye!");
		
		if (bufferedReader != null) {
			try {
				bufferedReader.close();
			} catch (IOException e) {
				System.out.println("IOException!");
			}
		}
		
		delegate.terminalGamesFinished();
	}
	
	private void handleUpdateOption() {
		String oldName = null;
		while (oldName == null || oldName.length() <= 0) {
			System.out.print("Please enter the video game name you wish to update: ");
			oldName = readLine().trim();
		}

		int year = INVALID_INPUT;
		while (year == INVALID_INPUT) {
			System.out.print("Please enter the video game year you wish to update: ");
			year = readInteger(false);
		}

		String name = null;
		while (name == null || name.length() <= 0) {
			System.out.print("Please enter the new video game name: ");
			name = readLine().trim();
		}

		delegate.updateVideoGame(name, year, oldName);
	}

	private void handleDevUpdateChoose(TerminalGamesDelegate delegate) throws IOException {
		System.out.println("Which column do you want to update? Enter a space separated list (ex. 1 OR 1 2 3)");

		bufferedReader = new BufferedReader(new InputStreamReader(System.in));
		int choice = INVALID_INPUT;
		String devNameToChange = null;
		while (choice != 4) {
			System.out.println();
			System.out.println("1. Update lead_developer");
			System.out.println("2. Update website");
			System.out.println("3. Update name");
			System.out.println("4. Return to Main Menu");
			System.out.print("Please choose one of the above 4 options: ");

			String separatedInput = bufferedReader.readLine();
			System.out.println(separatedInput);

			if (separatedInput.contains("4")) {
				showMainMenu(delegate);
			} else {
				devNameToChange = getDevName();
			}

			if (separatedInput.contains("1")) {
				handleDevUpdateLead(devNameToChange);
			}
			if (separatedInput.contains("2")) {
				handleDevUpdateWeb(devNameToChange);
			}
			if (separatedInput.contains("3")) {
				handleDevUpdateName(devNameToChange);
			}
		}
	}

	private String getDevName() {
		String developer_name = null;

		while (developer_name == null || developer_name.length() <= 0) {
			System.out.print("Please enter the developer name you wish to update: ");
			developer_name = readLine().trim();
		}
		return developer_name;
	}

	private String getDevNameDelete() {
		String developer_name = null;

		while (developer_name == null || developer_name.length() <= 0) {
			System.out.print("Please enter the developer name you wish to delete: ");
			developer_name = readLine().trim();
		}
		return developer_name;
	}

	private void handleDevUpdateLead(String developer_name) {
		String new_lead_dev = null;

		while (new_lead_dev == null || new_lead_dev.length() <= 0) {
			System.out.print("Please enter the new lead developer name: ");
			new_lead_dev = readLine().trim();
		}

		delegate.updateDeveloperNameLead(new_lead_dev, developer_name);
	}

	private void handleDevUpdateWeb(String developer_name) {
		String website = null;

		while (website == null || website.length() <= 0) {
			System.out.print("Please enter the new website name: ");
			website = readLine().trim();
		}

		delegate.updateDeveloperNameWebsite(website, developer_name);
	}

	private void handleDevUpdateName(String developer_name) {
		String new_developer_name = null;

		while (new_developer_name == null || new_developer_name.length() <= 0) {
			System.out.print("Please enter the new developer name: ");
			new_developer_name = readLine().trim();
		}

		delegate.updateDeveloperNameName(new_developer_name, developer_name);
	}

	private int readInteger(boolean allowEmpty) {
		String line = null;
		int input = INVALID_INPUT;
		try {
			line = bufferedReader.readLine();
			input = Integer.parseInt(line);
		} catch (IOException e) {
			System.out.println(EXCEPTION_TAG + " " + e.getMessage());
		} catch (NumberFormatException e) {
			if (allowEmpty && line.length() == 0) {
				input = EMPTY_INPUT;
			} else {
				System.out.println(WARNING_TAG + " Your input was not an integer");
			}
		}
		return input;
	}

	private String readLine() {
		String result = null;
		try {
			result = bufferedReader.readLine();
		} catch (IOException e) {
			System.out.println(EXCEPTION_TAG + " " + e.getMessage());
		}
		return result;
	}
}
