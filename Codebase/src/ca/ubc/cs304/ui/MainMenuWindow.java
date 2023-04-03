package ca.ubc.cs304.ui;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;

import javax.swing.*;

import ca.ubc.cs304.delegates.GUIWindowDelegate;
import ca.ubc.cs304.model.*;

import static java.lang.Integer.parseInt;

/**
 * The class is only responsible for displaying and handling the login GUI.
 */
public class MainMenuWindow extends JFrame implements ActionListener {
	private static final int FRAME_WIDTH = 500;
	private static final int FRAME_HEIGHT = 300;
	private static final int TABLE_FRAME_WIDTH = 1000;
	private static final int TABLE_FRAME_HEIGHT = 500;

	// Buttons
	private JButton manageVideoGameButton, finderButton, manageDeveloperNameButton, joinSubmitButton, quitButton, mainMenuButton;
	private JButton joinTablesButton, divisionTablesButton, returnToFinderToolButton;
	private JButton addVideoGameButton, addSubmitButton, removeSubmitButton, removeVideoGameButton, showAllVideoGameButton;
	private JButton addDeveloperButton, removeDeveloperButton, updateDeveloperButton, showAllDevelopersButton;

	// Text fields
	private JTextField titleField, yearField, genreField, developerNameField, devNameJoinField;

	// Panel and layout
	private JPanel contentPanel;
	private GridBagLayout gb;
	private GridBagConstraints c;
	private GUIWindowDelegate delegate = null;

	public MainMenuWindow() {
		super("VideoGame Contender Manager");
		setSize(FRAME_WIDTH, FRAME_HEIGHT);
		setUpJpanel();
		// size the window to obtain a best fit for the components
		this.pack();

		// center the frame
		Dimension d = this.getToolkit().getScreenSize();
		Rectangle r = this.getBounds();
		this.setLocation((d.width - r.width) / 2, (d.height - r.height) / 2);

		// make the window visible
		this.setVisible(true);
		this.setSize(500, 300);

	}

	public void mainMenuHandler(GUIWindowDelegate delegate) {
		this.delegate = delegate;
		this.manageVideoGameButton = new JButton("Manage VideoGames");
		this.manageDeveloperNameButton = new JButton("Manage Developers");
		this.joinTablesButton = new JButton("Find Lead Developers' Games");
		this.mainMenuButton = new JButton("Return to Main Menu");
		this.returnToFinderToolButton = new JButton("Return to Finder Tool Menu");
		this.finderButton = new JButton("Finder Tool");
		this.divisionTablesButton = new JButton("Find contenders for EveryGenre Award");
		this.quitButton = new JButton("Quit");
		setUpJpanel();

		// add buttons
		addButton(finderButton);
		addButton(manageVideoGameButton);
		addButton(manageDeveloperNameButton);
		addButton(quitButton);
		revalidate();
		repaint();
	}

	/**
	 * ActionListener Methods
	 */
	@Override
	public void actionPerformed(ActionEvent evt) {
		{
			if (evt.getSource()==quitButton)
			{
				delegate.terminalGamesFinished();
			}
			else if (evt.getSource()== manageVideoGameButton)
			{
				manageVideoGames();
			}
			else if (evt.getSource()== mainMenuButton)
			{
				mainMenuHandler(delegate);
			}
			else if (evt.getSource()== addVideoGameButton)
			{
				AddVideoGameHandler();
			}
			else if (evt.getSource()== showAllVideoGameButton)
			{
				displayVideoGamesHandler();
			}
			else if (evt.getSource()== addSubmitButton)
			{
				addVideoGameSubmitHandler(delegate);
			}
			else if (evt.getSource()== removeVideoGameButton)
			{
				displayRemoveVideoGameHandler();
			}
			else if (evt.getSource()== removeSubmitButton)
			{
				removeVideoGameHandler(delegate);
			}
			else if (evt.getSource()== joinTablesButton)
			{
				findDevCompanyAndGamesHandler();
			}
			else if (evt.getSource()== joinSubmitButton)
			{
				joinTableHandler();
			}
			else if (evt.getSource()== finderButton || evt.getSource()== returnToFinderToolButton)
			{
				finderWindow();
			}
			else if (evt.getSource()== divisionTablesButton)
			{
				divisionHandler();
			}
		}
	}

	private void divisionHandler() {
		setUpJpanel();

		VideoGameModel[] models = delegate.division();
		LeadDevTableModel divisionTable = new LeadDevTableModel(models);
		setupTable(new JTable(divisionTable));

		// Create a new JPanel for buttons with FlowLayout
		JPanel buttonsPanel = new JPanel();
		buttonsPanel.setLayout(new BoxLayout(buttonsPanel, BoxLayout.X_AXIS));

		// Add a 5-pixel spacer between the buttons
		Dimension spacer = new Dimension(2, 0);
		buttonsPanel.add(new Box.Filler(spacer, spacer, spacer));

		buttonsPanel.add(mainMenuButton);

		// Add a 5-pixel spacer between the buttons
		buttonsPanel.add(new Box.Filler(spacer, spacer, spacer));

		buttonsPanel.add(returnToFinderToolButton);
		returnToFinderToolButton.addActionListener(this);

		// Add the buttonsPanel to the contentPanel with GridBagLayout
		c.gridx = 0;
		c.gridy = 1;
		c.gridwidth = GridBagConstraints.REMAINDER;
		c.fill = GridBagConstraints.NONE;
		c.weightx = 0.0;
		c.weighty = 0.0;
		c.insets = new Insets(0, 5, 5, 5);
		contentPanel.add(buttonsPanel, c);

		contentPanel.setPreferredSize(new Dimension(TABLE_FRAME_WIDTH - 20, TABLE_FRAME_HEIGHT - 30));
		revalidate();
		repaint();
	}

	private void finderWindow() {
		// TODO ADD AGGREGATIONS AND PROJECTIONS
		setUpJpanel();

		// add buttons
		addButton(joinTablesButton);
		addButton(divisionTablesButton);
		addButton(mainMenuButton);
		revalidate();
		repaint();
	}

	private void joinTableHandler() {
		String developerName = devNameJoinField.getText();

		if (isValidDeveloperNameInput(developerName)) {
			try {
				DeveloperNameVideoGameModel[] table = delegate.joinTables(developerName);
				if (table.length == 0) {
					JOptionPane.showMessageDialog(null, "No developers called " + developerName + " found.", "Error", JOptionPane.ERROR_MESSAGE);
				} else {
					displayJoinedTables(table);
				}
			} catch (Exception e) {
				// Show the exception message in a popup
				JOptionPane.showMessageDialog(null, "An error occurred: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
			}
		}
	}

	private boolean isValidDeveloperNameInput(String developerName) {
		if (developerName.isEmpty()) {
			JOptionPane.showMessageDialog(null, "Name field is empty. Please enter a valid name.", "Error", JOptionPane.ERROR_MESSAGE);
			return false;
		} else if (developerName.length() > 50) {
			JOptionPane.showMessageDialog(null, "Name field is too large. Please enter a valid name.", "Error", JOptionPane.ERROR_MESSAGE);
			return false;
		}
		return true;
	}

	private void displayJoinedTables(DeveloperNameVideoGameModel[] data) {
		setUpJpanel();

		DeveloperNameVideoGameTableModel joinedTable = new DeveloperNameVideoGameTableModel(data);
		setupTable(new JTable(joinedTable));

		// Create a new JPanel for buttons with FlowLayout
		JPanel buttonsPanel = new JPanel();
		buttonsPanel.setLayout(new BoxLayout(buttonsPanel, BoxLayout.X_AXIS));

		// add delete button
		JButton deleteButton = new JButton("Delete");

		// Add a 5-pixel spacer between the buttons
		Dimension spacer = new Dimension(2, 0);
		buttonsPanel.add(new Box.Filler(spacer, spacer, spacer));

		buttonsPanel.add(joinTablesButton);

		// Add a 5-pixel spacer between the buttons
		buttonsPanel.add(new Box.Filler(spacer, spacer, spacer));

		buttonsPanel.add(mainMenuButton);

		// Add the buttonsPanel to the contentPanel with GridBagLayout
		c.gridx = 0;
		c.gridy = 1;
		c.gridwidth = GridBagConstraints.REMAINDER;
		c.fill = GridBagConstraints.NONE;
		c.weightx = 0.0;
		c.weighty = 0.0;
		c.insets = new Insets(0, 5, 5, 5);
		contentPanel.add(buttonsPanel, c);

		contentPanel.setPreferredSize(new Dimension(TABLE_FRAME_WIDTH - 20, TABLE_FRAME_HEIGHT - 30));
		revalidate();
		repaint();
	}

	private void findDevCompanyAndGamesHandler() {
		joinSubmitButton = new JButton("Find Games and Company");
		JLabel fieldLabel = new JLabel("Lead Developer: ");

		devNameJoinField = new JTextField(20);

		setUpJpanel();

		addLabel(fieldLabel);
		addField(devNameJoinField);

		addButton(joinSubmitButton);
		addButton(returnToFinderToolButton);
	}

	private void displayRemoveVideoGameHandler() {
		removeSubmitButton = new JButton("Submit");
		JLabel titleLabel = new JLabel("Videogame Title: ");
		JLabel yearLabel = new JLabel("Year: ");

		titleField = new JTextField(20);
		yearField = new JTextField(4);

		setUpJpanel();

		addLabel(titleLabel);
		addField(titleField);

		addLabel(yearLabel);
		addField(yearField);

		addButton(removeSubmitButton);
		addButton(manageVideoGameButton);
	}

	private void removeVideoGameHandler(GUIWindowDelegate delegate) {
		String title = titleField.getText();
		String yearText = yearField.getText();
		int year = 0;

		if (isValidRemoveInput(title, yearText)) {
			year = Integer.parseInt(yearText);
			try {
				delegate.deleteVideoGame(title, year);
				// Show a success popup
				JOptionPane.showMessageDialog(null, "Video game removed successfully.", "Success", JOptionPane.INFORMATION_MESSAGE);
				manageVideoGames();
			} catch (Exception e) {
				// Show the exception message in a popup
				JOptionPane.showMessageDialog(null, "An error occurred: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
			}
		}
	}

	private boolean isValidRemoveInput(String title, String yearText) {
		int year = 0;

		if (title.isEmpty()) {
			JOptionPane.showMessageDialog(null, "Title field is empty. Please enter a valid title.", "Error", JOptionPane.ERROR_MESSAGE);
			return false;
		} else if (title.length() > 50) {
			JOptionPane.showMessageDialog(null, "Title is too long. Please enter a title with 20 characters or less.", "Error", JOptionPane.ERROR_MESSAGE);
			return false;
		}

		if (yearText.isEmpty()) {
			JOptionPane.showMessageDialog(null, "Year field is empty. Please enter a valid year.", "Error", JOptionPane.ERROR_MESSAGE);
			return false;
		}

		try {
			year = Integer.parseInt(yearText);
		} catch (NumberFormatException e) {
			JOptionPane.showMessageDialog(null, "Invalid input. Please enter a valid year.", "Error", JOptionPane.ERROR_MESSAGE);
			return false;
		}

		if (year < 2000 || year > 2023) {
			JOptionPane.showMessageDialog(null, "Invalid year. Please enter a year between 2000 and 2023.", "Error", JOptionPane.ERROR_MESSAGE);
			return false;
		}

		return true;
	}

	private void addVideoGameSubmitHandler(GUIWindowDelegate delegate) {
		String title = titleField.getText();
		String yearText = yearField.getText();
		int year = 0;

		if (isValidVideoGameInput(title, yearText, genreField.getText(), developerNameField.getText())) {
			year = Integer.parseInt(yearText);
			VideoGameModel model = new VideoGameModel(
					title,
					year,
					genreField.getText(),
					developerNameField.getText());
			try {
				delegate.insertVideoGame(model);
				JOptionPane.showMessageDialog(null, "Video game added successfully.", "Success", JOptionPane.INFORMATION_MESSAGE);
				manageVideoGames();
			} catch (Exception e) {
				// Show the exception message in a popup
				JOptionPane.showMessageDialog(null, "An error occurred: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
			}
		}
	}

	private boolean isValidVideoGameInput(String title, String yearText, String genre, String name) {
		int year = 0;

		if (title.isEmpty()) {
			JOptionPane.showMessageDialog(null, "Title field is empty. Please enter a valid title.", "Error", JOptionPane.ERROR_MESSAGE);
			return false;
		} else if (title.length() > 50) {
			JOptionPane.showMessageDialog(null, "Title is too long. Please enter a title with 20 characters or less.", "Error", JOptionPane.ERROR_MESSAGE);
			return false;
		}

		if (yearText.isEmpty()) {
			JOptionPane.showMessageDialog(null, "Year field is empty. Please enter a valid year.", "Error", JOptionPane.ERROR_MESSAGE);
			return false;
		}

		try {
			year = Integer.parseInt(yearText);
		} catch (NumberFormatException e) {
			JOptionPane.showMessageDialog(null, "Invalid input. Please enter a valid year.", "Error", JOptionPane.ERROR_MESSAGE);
			return false;
		}

		if (year < 2000 || year > 2023) {
			JOptionPane.showMessageDialog(null, "Invalid year. Please enter a year between 2000 and 2023.", "Error", JOptionPane.ERROR_MESSAGE);
			return false;
		}

		if (genre.isEmpty()) {
			JOptionPane.showMessageDialog(null, "Genre field is empty. Please enter a valid genre.", "Error", JOptionPane.ERROR_MESSAGE);
			return false;
		} else if (genre.length() > 50) {
			JOptionPane.showMessageDialog(null, "Genre is too long. Please enter a genre with 20 characters or less.", "Error", JOptionPane.ERROR_MESSAGE);
			return false;
		}

		if (name.isEmpty()) {
			JOptionPane.showMessageDialog(null, "Developer name field is empty. Please enter a valid name.", "Error", JOptionPane.ERROR_MESSAGE);
			return false;
		} else if (name.length() > 50) {
			JOptionPane.showMessageDialog(null, "Developer name is too long. Please enter a name with 20 characters or less.", "Error", JOptionPane.ERROR_MESSAGE);
			return false;
		}

		return true;
	}

	private void AddVideoGameHandler() {
		addSubmitButton = new JButton("Submit");
		JLabel titleLabel = new JLabel("Videogame Title: ");
		JLabel yearLabel = new JLabel("Year: ");
		JLabel genreLabel = new JLabel("Genre: ");
		JLabel developerNameLabel = new JLabel("Developer Name: ");

		titleField = new JTextField(20);
		yearField = new JTextField(4);
		genreField = new JTextField(20);
		developerNameField= new JTextField(20);

		setUpJpanel();

		addLabel(titleLabel);
		addField(titleField);

		addLabel(yearLabel);
		addField(yearField);

		addLabel(genreLabel);
		addField(genreField);

		addLabel(developerNameLabel);
		addField(developerNameField);

		addButton(addSubmitButton);
		addButton(manageVideoGameButton);
	}

	private void displayVideoGamesHandler() {
		setUpJpanel();

		VideoGameTableModel videogameTable = new VideoGameTableModel(delegate.getVideoGamesObjects());
		JTable table = new JTable(videogameTable);
		setupTable(table);

		// Create a new JPanel for buttons with FlowLayout
		JPanel buttonsPanel = new JPanel();
		buttonsPanel.setLayout(new BoxLayout(buttonsPanel, BoxLayout.X_AXIS));

		// add delete button
		JButton deleteButton = new JButton("Delete");
		deleteButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				int selectedRow = table.getSelectedRow();

				if (selectedRow != -1) {
					int modelRow = table.convertRowIndexToModel(selectedRow);
					String gameName = (String) videogameTable.getValueAt(modelRow, 0);
					int gameYear = (int) videogameTable.getValueAt(modelRow, 1);

					try {
						delegate.deleteVideoGame(gameName, gameYear);
						JOptionPane.showMessageDialog(null, "Video game removed successfully.", "Success", JOptionPane.INFORMATION_MESSAGE);
					} catch (SQLException error) {
						JOptionPane.showMessageDialog(null, "Unexpected Error", "Error", JOptionPane.ERROR_MESSAGE);
					}
					displayVideoGamesHandler();
				} else {
					JOptionPane.showMessageDialog(null, "Please select a row to delete.", "Error", JOptionPane.ERROR_MESSAGE);
				}
			}
		});

		buttonsPanel.add(deleteButton);
		// Add a 5-pixel spacer between the buttons
		Dimension spacer = new Dimension(2, 0);
		buttonsPanel.add(new Box.Filler(spacer, spacer, spacer));

		buttonsPanel.add(addVideoGameButton);

		// Add a 5-pixel spacer between the buttons
		buttonsPanel.add(new Box.Filler(spacer, spacer, spacer));

		buttonsPanel.add(manageVideoGameButton);

		// Add the buttonsPanel to the contentPanel with GridBagLayout
		c.gridx = 0;
		c.gridy = 1;
		c.gridwidth = GridBagConstraints.REMAINDER;
		c.fill = GridBagConstraints.NONE;
		c.weightx = 0.0;
		c.weighty = 0.0;
		c.insets = new Insets(0, 5, 5, 5);
		contentPanel.add(buttonsPanel, c);

		contentPanel.setPreferredSize(new Dimension(TABLE_FRAME_WIDTH - 20, TABLE_FRAME_HEIGHT - 30));
		revalidate();
		repaint();
	}

	private void manageVideoGames() {
		this.addVideoGameButton = new JButton("Add video game");
		this.removeVideoGameButton = new JButton("Find and remove video game");
		this.showAllVideoGameButton = new JButton("Show all video game");

		setUpJpanel();

		addButton(addVideoGameButton);
		addButton(removeVideoGameButton);
		addButton(showAllVideoGameButton);
		addButton(mainMenuButton);

		contentPanel.setPreferredSize(new Dimension(FRAME_WIDTH - 20, FRAME_HEIGHT - 30));
		revalidate();
		repaint();
	}

	private void addButton(JButton button) {
			Dimension currentPreferredSize = button.getPreferredSize();
			button.setPreferredSize(new Dimension(300, currentPreferredSize.height));
			c.gridwidth = GridBagConstraints.REMAINDER;
			c.insets = new Insets(2, 10, 2, 10);
			c.anchor = GridBagConstraints.CENTER;
			gb.setConstraints(button, c);
			contentPanel.add(button);
			button.addActionListener(this);
	}

	private void addLabel(JLabel label) {
		c.gridwidth = GridBagConstraints.RELATIVE;
		c.fill = GridBagConstraints.NONE;
		gb.setConstraints(label, c);
		contentPanel.add(label);
	}

	private void addField(JTextField field) {
		c.gridwidth = GridBagConstraints.REMAINDER;
		c.fill = GridBagConstraints.HORIZONTAL;
		gb.setConstraints(field, c);
		contentPanel.add(field);
	}

	private void setUpJpanel() {
		this.contentPanel = new JPanel();
		this.setContentPane(contentPanel);

		// layout components using the GridBag layout manager
		this.gb = new GridBagLayout();
		this.c = new GridBagConstraints();

		contentPanel.setLayout(gb);
		contentPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 20, 10));
	}

	private void setupTable(JTable divisionTable) {
		JTable table = divisionTable;
		JScrollPane scrollPane = new JScrollPane(table);

		// Add the JScrollPane to the JPanel with GridBagLayout
		c.fill = GridBagConstraints.BOTH;

		c.gridx = 0;
		c.gridy = 0;
		c.weightx = 1.0;
		c.weighty = 1.0;
		contentPanel.add(scrollPane, c);
	}

}
