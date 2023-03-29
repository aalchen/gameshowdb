package ca.ubc.cs304.ui;

import ca.ubc.cs304.delegates.TerminalGamesDelegate;
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
	public void showMainMenu(TerminalGamesDelegate delegate) {
		this.delegate = delegate;
		
	    bufferedReader = new BufferedReader(new InputStreamReader(System.in));
		int choice = INVALID_INPUT;
		
		while (choice != 5) {
			System.out.println();
			System.out.println("1. Insert video game");
			System.out.println("2. Delete video game");
			System.out.println("3. Update video game");
			System.out.println("4. Show video game");
			System.out.println("5. Quit");
			System.out.print("Please choose one of the above 5 options: ");

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
					handleQuitOption();
					break;
				default:
					System.out.println(WARNING_TAG + " The number that you entered was not a valid option.");
					break;
				}
			}
		}		
	}
	
	private void handleDeleteOption() {
		String gameTitle = null;
		int gameYear = INVALID_INPUT;

		while (gameTitle == null) {
			System.out.print("Please enter the video game title you wish to delete: ");
		}
		while (gameYear == INVALID_INPUT) {
			System.out.print("Please enter the video game year you wish to delete: ");
			gameYear = readInteger(false);
		}

		if (gameYear != INVALID_INPUT) {
			delegate.deleteVideoGame(gameTitle, gameYear);
		}
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
		String name = null;
		while (name == null || name.length() <= 0) {
			System.out.print("Please enter the video game name you wish to update: ");
			name = readLine().trim();
		}

		int year = INVALID_INPUT;
		while (year == INVALID_INPUT) {
			System.out.print("Please enter the video game year you wish to update: ");
			year = readInteger(false);
		}

		delegate.updateVideoGame(name, year);
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
