package ca.ubc.cs304.ui;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.swing.*;
import javax.swing.table.AbstractTableModel;

import ca.ubc.cs304.delegates.GUIWindowDelegate;
import ca.ubc.cs304.model.*;

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
	private JButton joinTablesButton, divisionTablesButton, aggregationGroupByButton, aggregationGroupByHavingButton,
			nestedAggregationButton, projectionButton, returnToFinderToolButton;
	private JButton addVideoGameButton, addVideoGameSubmitButton, removeVideoGameSubmitButton, removeVideoGameButton, showAllVideoGameButton;
	private JButton addDeveloperButton, removeDeveloperButton, updateDeveloperButton, showAllDevelopersButton,
			addDeveloperSubmitButton, removeDeveloperSubmitButton, updateDeveloperSubmitButton, projectionSubmitButton,
			projectionTableSelectButton;
	private List<JCheckBox> tableColumns;
	private JComboBox<String> tableDropDown;

	// Text fields
	private JTextField titleField, yearField, genreField, developerNameField, devNameJoinField;
	private JTextField nameField, websiteField, leadDeveloperField, newLeadDeveloperField, newWebsiteField,
			newDeveloperNameField;

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

		// make the window visible
		this.setVisible(true);
		this.setSize(1500, 500);
		Dimension d = this.getToolkit().getScreenSize();
		Rectangle r = this.getBounds();

		this.setLocation((d.width - r.width) / 2, (d.height - r.height) / 2);


		this.joinSubmitButton = new JButton("Find Games and Company");
		this.manageVideoGameButton = new JButton("Manage VideoGames");
		this.manageDeveloperNameButton = new JButton("Manage Developers");
		this.joinTablesButton = new JButton("Find Lead Developers' Games");
		this.mainMenuButton = new JButton("Return to Main Menu");
		this.returnToFinderToolButton = new JButton("Return to Finder Tool Menu");
		this.finderButton = new JButton("Finder Tool");
		this.divisionTablesButton = new JButton("Find contenders for EveryGenre Award");
		this.nestedAggregationButton = new JButton("Find Developer Release Count after 2015");
		this.aggregationGroupByButton = new JButton("View Number of Genres per Developer");
		this.aggregationGroupByHavingButton = new JButton("View Most Recent Genre Release for Developer after 2015");
		this.projectionTableSelectButton = new JButton("View Tables");
		this.projectionButton = new JButton("Select Table");
		this.projectionSubmitButton = new JButton("Submit");
		this.quitButton = new JButton("Quit");
		this.removeVideoGameSubmitButton = new JButton("Submit");
		this.addDeveloperButton = new JButton("Add developer");
		this.updateDeveloperButton = new JButton("Find and update developer info");
		this.removeDeveloperButton = new JButton("Find and remove a developer");
		this.showAllDevelopersButton = new JButton("Show all developers");
		this.mainMenuButton = new JButton("Return to Main Menu");
		this.addVideoGameSubmitButton = new JButton("Submit");
		this.addVideoGameButton = new JButton("Add video game");
		this.removeVideoGameButton = new JButton("Find and remove video game");
		this.showAllVideoGameButton = new JButton("Show all video game");
		this.updateDeveloperSubmitButton = new JButton("Update");
		this.addDeveloperSubmitButton = new JButton("Submit");
		this.removeDeveloperSubmitButton = new JButton("Submit");
		this.joinSubmitButton.addActionListener(this);
		this.manageVideoGameButton.addActionListener(this);
		this.manageDeveloperNameButton.addActionListener(this);
		this.joinTablesButton.addActionListener(this);
		this.mainMenuButton.addActionListener(this);
		this.returnToFinderToolButton.addActionListener(this);
		this.finderButton.addActionListener(this);
		this.divisionTablesButton.addActionListener(this);
		this.aggregationGroupByButton.addActionListener(this);
		this.aggregationGroupByHavingButton.addActionListener(this);
		this.projectionButton.addActionListener(this);
		this.projectionTableSelectButton.addActionListener(this);
		this.projectionSubmitButton.addActionListener(this);
		this.nestedAggregationButton.addActionListener(this);
		this.quitButton.addActionListener(this);
		this.removeVideoGameSubmitButton.addActionListener(this);
		this.addDeveloperButton.addActionListener(this);
		this.updateDeveloperButton.addActionListener(this);
		this.removeDeveloperButton.addActionListener(this);
		this.showAllDevelopersButton.addActionListener(this);
		this.mainMenuButton.addActionListener(this);
		this.addVideoGameSubmitButton.addActionListener(this);
		this.addVideoGameButton.addActionListener(this);
		this.removeVideoGameButton.addActionListener(this);
		this.showAllVideoGameButton.addActionListener(this);
		this.updateDeveloperSubmitButton.addActionListener(this);
		this.addDeveloperSubmitButton.addActionListener(this);
		this.removeDeveloperSubmitButton.addActionListener(this);
	}

	public void mainMenuHandler(GUIWindowDelegate delegate) {
		this.delegate = delegate;

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
			else if (evt.getSource()== addVideoGameSubmitButton)
			{
				addVideoGameSubmitHandler(delegate);
			}
			else if (evt.getSource()== removeVideoGameButton)
			{
				displayRemoveVideoGameHandler();
			}
			else if (evt.getSource()== removeVideoGameSubmitButton)
			{
				removeVideoGameSubmitHandler(delegate);
			}
			else if (evt.getSource()== joinTablesButton)
			{
				findDevCompanyAndGamesHandler();
			}
			else if (evt.getSource()== joinSubmitButton)
			{
				joinTableHandler();
			}
			else if (evt.getSource()== aggregationGroupByButton)
			{
				aggregationGroupByHandler(delegate);
			}
			else if (evt.getSource()== aggregationGroupByHavingButton)
			{
				aggregationGroupByHavingHandler(delegate);
			}
			else if (evt.getSource()== nestedAggregationButton)
			{
				groupByNumberOfTitlesPerDevAfter2015(delegate);
			}
			else if (evt.getSource()== projectionTableSelectButton)
			{
				projectionTableHandler(delegate);
			}
			else if (evt.getSource()== projectionButton)
			{
				projectionHandler(delegate);
			}
			else if (evt.getSource()== projectionSubmitButton)
			{
				projectionSubmitHandler(delegate);
			}
			else if (evt.getSource()== finderButton || evt.getSource()== returnToFinderToolButton)
			{
				finderWindow();
			}
			else if (evt.getSource()== divisionTablesButton)
			{
				divisionHandler();
			}
			else if (evt.getSource()== manageDeveloperNameButton) {
				manageDevelopers();
			} else if (evt.getSource() == addDeveloperButton) {
				addDeveloperHandler();
			} else if (evt.getSource() == removeDeveloperButton) {
				removeDeveloperHandler();
			} else if (evt.getSource() == updateDeveloperButton) {
				updateDeveloperHandler();
			} else if (evt.getSource() == updateDeveloperSubmitButton) {
				updateDeveloperSubmitHandler(delegate);
			} else if (evt.getSource() == showAllDevelopersButton) {
				showAllDevelopersHandler();
			} else if (evt.getSource() == addDeveloperSubmitButton) {
				addDeveloperSubmitHandler(delegate);
			} else if (evt.getSource() == removeDeveloperSubmitButton) {
				removeDeveloperSubmitHandler(delegate);
			}
		}
	}

	private void aggregationGroupByHavingHandler(GUIWindowDelegate delegate) {
		setUpJpanel();

		try {
			VideoGameCountModel[] models = delegate.aggregateGroupByHaving();
			VideoGameMaxTableModel nestedAggregationTable = new VideoGameMaxTableModel(models);
			setupTable(new JTable(nestedAggregationTable));

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
		} catch (SQLException e) {
			JOptionPane.showMessageDialog(null, "An error occurred: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
		}
	}

	private void aggregationGroupByHandler(GUIWindowDelegate delegate) {
		setUpJpanel();
		try {
			VideoGameCountModel[] models = delegate.aggregateGroupBy();
			VideoGameCountTableModel nestedAggregationTable = new VideoGameCountTableModel(models);
			setupTable(new JTable(nestedAggregationTable));

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
		} catch (SQLException e) {
			JOptionPane.showMessageDialog(null, "An error occurred: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
		}
	}

	private void projectionSubmitHandler(GUIWindowDelegate delegate) {
		setUpJpanel();
		List<String> columns = new ArrayList<>();

		for (int i = 0; i < tableColumns.size(); i++) {
			JCheckBox column = tableColumns.get(i);
			if (column.isSelected()) {
				columns.add(column.getText().toLowerCase());
			}
		}

		try {
			Model[] models = null;
			AbstractTableModel projectionTable = null;
			String tableName = ((String) tableDropDown.getSelectedItem()).toLowerCase();
			switch (tableName){
				case "award":
					projectionTable = new AwardProjectionTableModel(models, columns);
					break;
				case "awardceremony":
					projectionTable = new AwardCeremonyProjectionTableModel(models, columns);
					break;
				case "communityaward":
					projectionTable = new CommunityAwardProjectionTableModel(models, columns);
					break;
				case "company":
					projectionTable = new CompanyProjectionTableModel(models, columns);
					break;
				case "developercountry":
					projectionTable = new DeveloperCountryProjectionTableModel(models, columns);
					break;
				case "developername":
					projectionTable = new DeveloperNameProjectionTableModel(models, columns);
					break;
				case "livestreamurl":
					projectionTable = new LivestreamUrlProjectionTableModel(models, columns);
					break;
				case "livestreamviewercount":
					projectionTable = new LivestreamViewerCountProjectionTableModel(models, columns);
					break;
				case "sponsoredaward":
					projectionTable = new SponsoredAwardProjectionTableModel(models, columns);
					break;
				case "sponsors":
					projectionTable = new SponsorsProjectionTableModel(models, columns);
					break;
				case "staff":
					projectionTable = new StaffProjectionTableModel(models, columns);
					break;
				case "staffawardceremony":
					projectionTable = new StaffAwardCeremonyProjectionTableModel(models, columns);
					break;
				case "venue":
					models = delegate.projectionVenue(columns);
					projectionTable = new VenueProjectionTableModel(models, columns);
					break;
				case "videogamedlc":
					projectionTable = new VideoGameDLCProjectionTableModel(models, columns);
					break;
				case "videogame":
					models = delegate.projectionVideoGame(columns);
					projectionTable = new VideoGameProjectionTableModel(models, columns);
					break;
				default:
					System.out.println("invalid table selected");
					break;
			}

			setupTable(new JTable(projectionTable));

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
		} catch (SQLException e) {
			JOptionPane.showMessageDialog(null, "An error occurred: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
		}
	}

	private void projectionTableHandler(GUIWindowDelegate delegate) {
		setUpJpanel();

		String[] tableList = delegate.tableList().toArray(new String[0]);

		tableDropDown = new JComboBox<String>(tableList);
		addDropDown(tableDropDown);

		addButton(projectionButton);
		addButton(finderButton);
		addButton(mainMenuButton);
	}

	private void projectionHandler(GUIWindowDelegate delegate) {
		setUpJpanel();

		String[] columns = delegate.projectionColList(tableDropDown.getSelectedItem().toString()).toArray(new String[0]);
		tableColumns = new ArrayList<>();
		for (int i = 0; i < columns.length; i++) {
			JCheckBox newCheckBox = new JCheckBox(columns[i]);
			newCheckBox.setSelected(false);
			addCheckBox(newCheckBox);
			tableColumns.add(newCheckBox);
		}

		addButton(projectionSubmitButton);
		addButton(finderButton);
		addButton(mainMenuButton);
	}

	private void groupByNumberOfTitlesPerDevAfter2015(GUIWindowDelegate delegate) {
		setUpJpanel();

		try {
			VideoGameCountModel[] models = delegate.nestedAggregation();
			VideoGameCountTableModel nestedAggregationTable = new VideoGameCountTableModel(models);
			setupTable(new JTable(nestedAggregationTable));

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
		}catch (SQLException e) {
			JOptionPane.showMessageDialog(null, "An error occurred: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);

		}
	}

	private void divisionHandler() {
		setUpJpanel();

		try {
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
		} catch (SQLException e) {
			JOptionPane.showMessageDialog(null, "An error occurred: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
		}
	}

	private void finderWindow() {
		setUpJpanel();

		// add buttons
		addButton(joinTablesButton);
		addButton(divisionTablesButton);
		addButton(projectionTableSelectButton);
		addButton(aggregationGroupByButton);
		addButton(aggregationGroupByHavingButton);
		addButton(nestedAggregationButton);
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
		JLabel fieldLabel = new JLabel("Lead Developer: ");

		devNameJoinField = new JTextField(20);

		setUpJpanel();

		addLabel(fieldLabel);
		addField(devNameJoinField);

		addButton(joinSubmitButton);
		addButton(returnToFinderToolButton);
	}

	private void displayRemoveVideoGameHandler() {
		JLabel titleLabel = new JLabel("Videogame Title: ");
		JLabel yearLabel = new JLabel("Year: ");

		titleField = new JTextField(20);
		yearField = new JTextField(4);

		setUpJpanel();

		addLabel(titleLabel);
		addField(titleField);

		addLabel(yearLabel);
		addField(yearField);

		addButton(removeVideoGameSubmitButton);
		addButton(manageVideoGameButton);
	}

	private void removeVideoGameSubmitHandler(GUIWindowDelegate delegate) {
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

		addButton(addVideoGameSubmitButton);
		addButton(manageVideoGameButton);
	}

	private void displayVideoGamesHandler() {
		setUpJpanel();

		try {
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
		} catch (SQLException e) {
			JOptionPane.showMessageDialog(null, "An error occurred: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
		}
	}

	private void manageVideoGames() {
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
	}

	private void addCheckBox(JCheckBox checkBox) {
		Dimension currentPreferredSize = checkBox.getPreferredSize();
		checkBox.setPreferredSize(new Dimension(300, currentPreferredSize.height));
		c.gridwidth = GridBagConstraints.REMAINDER;
		c.insets = new Insets(2, 10, 2, 10);
		c.anchor = GridBagConstraints.CENTER;
		gb.setConstraints(checkBox, c);
		contentPanel.add(checkBox);
		checkBox.addActionListener(this);
	}

	private void addDropDown(JComboBox<String> comboBox) {
		Dimension currentPreferredSize = comboBox.getPreferredSize();
		comboBox.setPreferredSize(new Dimension(300, currentPreferredSize.height));
		c.gridwidth = GridBagConstraints.REMAINDER;
		c.insets = new Insets(2, 10, 2, 10);
		c.anchor = GridBagConstraints.CENTER;
		gb.setConstraints(comboBox, c);
		contentPanel.add(comboBox);
		comboBox.addActionListener(this);
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

	// Developer Handling Section Begins
	private void manageDevelopers() {
		setUpJpanel();

		addButton(addDeveloperButton);
		addButton(updateDeveloperButton);
		addButton(removeDeveloperButton);
		addButton(showAllDevelopersButton);
		addButton(mainMenuButton);

		contentPanel.setPreferredSize(new Dimension(FRAME_WIDTH - 20, FRAME_HEIGHT - 30));
		revalidate();
		repaint();
	}

	private void addDeveloperHandler() {
		JLabel nameLabel = new JLabel("Name: ");
		JLabel websiteLabel = new JLabel("Website: ");
		JLabel leadDeveloperLabel = new JLabel("Lead Developer: ");

		nameField = new JTextField(50);
		websiteField = new JTextField(50);
		leadDeveloperField = new JTextField(50);

		setUpJpanel();

		addLabel(nameLabel);
		addField(nameField);

		addLabel(websiteLabel);
		addField(websiteField);

		addLabel(leadDeveloperLabel);
		addField(leadDeveloperField);

		addButton(addDeveloperSubmitButton);
		addButton(manageDeveloperNameButton);
	}

	private void removeDeveloperHandler() {
		JLabel nameLabel = new JLabel("Developer Name: ");

		nameField = new JTextField(50);

		setUpJpanel();

		addLabel(nameLabel);
		addField(nameField);

		addButton(removeDeveloperSubmitButton);
		addButton(manageDeveloperNameButton);
	}

	private void removeDeveloperSubmitHandler(GUIWindowDelegate delegate) {
		String name = nameField.getText();

		if (isValidRemoveDeveloperInput(name)) {
			try {
				delegate.deleteDeveloperName(name);
				// Show a success popup
				JOptionPane.showMessageDialog(null, "Developer removed successfully.", "Success", JOptionPane.INFORMATION_MESSAGE);
				manageDevelopers();
			} catch (Exception e) {
				// Show the exception message in a popup
				JOptionPane.showMessageDialog(null, "An error occurred: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
			}
		}
	}

	private boolean isValidRemoveDeveloperInput(String name) {
		if (name.isEmpty()) {
			JOptionPane.showMessageDialog(null, "Name field is empty. Please enter a valid name.", "Error", JOptionPane.ERROR_MESSAGE);
			return false;
		} else if (name.length() > 50) {
			JOptionPane.showMessageDialog(null, "Name is too long. Please enter a title with 50 characters or less.", "Error", JOptionPane.ERROR_MESSAGE);
			return false;
		}

		return true;
	}

	private void showAllDevelopersHandler() {
		setUpJpanel();
		try {
			DeveloperNameTableModel developerNameTable = new DeveloperNameTableModel(delegate.getDeveloperNamesObjects());
			JTable table = new JTable(developerNameTable);
			setupTable(table);

			// Create a new JPanel for buttons with FlowLayout
			JPanel buttonsPanel = new JPanel();
			buttonsPanel.setLayout(new BoxLayout(buttonsPanel, BoxLayout.X_AXIS));

			// Add text fields for filtering
			JTextField leadDevFilter = new JTextField(30);
			JTextField websiteFilter = new JTextField(30);
			JTextField nameFilter = new JTextField(30);

			// Set minimum size for the text fields
			Dimension minTextFieldSize = new Dimension(200, leadDevFilter.getPreferredSize().height);
			leadDevFilter.setMinimumSize(minTextFieldSize);
			websiteFilter.setMinimumSize(minTextFieldSize);
			nameFilter.setMinimumSize(minTextFieldSize);

			buttonsPanel.add(new JLabel("Lead Developer:"));
			buttonsPanel.add(leadDevFilter);
			buttonsPanel.add(new JLabel("Website:"));
			buttonsPanel.add(websiteFilter);
			buttonsPanel.add(new JLabel("Name:"));
			buttonsPanel.add(nameFilter);

			// Add filter button
			JButton filterButton = new JButton("Filter");
			filterButton.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					String leadDev = leadDevFilter.getText().trim();
					String website = websiteFilter.getText().trim();
					String name = nameFilter.getText().trim();

					try {
						DeveloperNameModel[] filteredDevelopers = delegate.filterDevelopers(leadDev, website, name);

						if (filteredDevelopers != null) {
							developerNameTable.updateData(filteredDevelopers);
						} else {
							JOptionPane.showMessageDialog(null, "An error occurred while filtering developers. Please check your input and try again.", "Error", JOptionPane.ERROR_MESSAGE);
						}
					} catch (SQLException error) {
						JOptionPane.showMessageDialog(null, "An error occurred while filtering developers. Please check your input and try again.", "Error", JOptionPane.ERROR_MESSAGE);

					}
				}
			});

			buttonsPanel.add(filterButton);
			// add delete button
			JButton deleteButton = new JButton("Delete");
			deleteButton.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					int selectedRow = table.getSelectedRow();

					if (selectedRow != -1) {
						int modelRow = table.convertRowIndexToModel(selectedRow);
						String developerName = (String) developerNameTable.getValueAt(modelRow, 0);

						try {
							delegate.deleteDeveloperName(developerName);
						} catch (SQLException error) {
							JOptionPane.showMessageDialog(null, "Unexpected Error", "Error", JOptionPane.ERROR_MESSAGE);
						}
						showAllDevelopersHandler();
					} else {
						JOptionPane.showMessageDialog(null, "Please select a row to delete.", "Error", JOptionPane.ERROR_MESSAGE);
					}
				}
			});

			buttonsPanel.add(deleteButton);
			// Add a 5-pixel spacer between the buttons
			Dimension spacer = new Dimension(2, 0);
			buttonsPanel.add(new Box.Filler(spacer, spacer, spacer));

			buttonsPanel.add(addDeveloperButton);

			// Add a 5-pixel spacer between the buttons
			buttonsPanel.add(new Box.Filler(spacer, spacer, spacer));

			buttonsPanel.add(manageDeveloperNameButton);

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
		} catch (SQLException e) {
			JOptionPane.showMessageDialog(null, "An error occurred: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
		}
	}

	private void addDeveloperSubmitHandler(GUIWindowDelegate delegate) {
		String name = nameField.getText();
		String website = websiteField.getText();
		String leadDeveloper = leadDeveloperField.getText();

		if (isValidDeveloperInput(name, website, leadDeveloper)) {
			DeveloperNameModel model = new DeveloperNameModel(
					leadDeveloper,
					website,
					name);
			try {
				delegate.insertDeveloperName(model);
				JOptionPane.showMessageDialog(null, "Developer added successfully.", "Success", JOptionPane.INFORMATION_MESSAGE);
				manageDevelopers();
			} catch (Exception e) {
				// Show the exception message in a popup
				JOptionPane.showMessageDialog(null, "An error occurred: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
			}
		}
	}

	private boolean isValidDeveloperInput(String name, String website, String leadDeveloper) {
		if (name.isEmpty()) {
			JOptionPane.showMessageDialog(null, "Name field is empty. Please enter a valid name.", "Error", JOptionPane.ERROR_MESSAGE);
			return false;
		} else if (name.length() > 50) {
			JOptionPane.showMessageDialog(null, "Name is too long. Please enter a name with 50 characters or less.", "Error", JOptionPane.ERROR_MESSAGE);
			return false;
		}

		if (website.isEmpty()) {
			JOptionPane.showMessageDialog(null, "Website field is empty. Please enter a valid website.", "Error", JOptionPane.ERROR_MESSAGE);
			return false;
		} else if (website.length() > 50) {
			JOptionPane.showMessageDialog(null, "Website is too long. Please enter a website with 50 characters or less.", "Error", JOptionPane.ERROR_MESSAGE);
			return false;
		}

		if (leadDeveloper.isEmpty()) {
			JOptionPane.showMessageDialog(null, "Lead Developer field is empty. Please enter a valid lead developer.", "Error", JOptionPane.ERROR_MESSAGE);
			return false;
		} else if (leadDeveloper.length() > 50) {
			JOptionPane.showMessageDialog(null, "Lead Developer is too long. Please enter a Lead Developer with 50 characters or less.", "Error", JOptionPane.ERROR_MESSAGE);
			return false;
		}
		return true;
	}

	private void updateDeveloperHandler() {
		JLabel nameLabel = new JLabel("Current Name: ");
		nameField = new JTextField(50);

		JLabel newDeveloperNameLabel = new JLabel("New Name: ");
		newDeveloperNameField = new JTextField(50);

		JLabel newWebsiteLabel = new JLabel("New Website: ");
		newWebsiteField = new JTextField(50);

		JLabel newLeadDeveloperLabel = new JLabel("New Lead: ");
		newLeadDeveloperField = new JTextField(50);

		setUpJpanel();

		addLabel(nameLabel);
		addField(nameField);

		addLabel(newDeveloperNameLabel);
		addField(newDeveloperNameField);

		addLabel(newWebsiteLabel);
		addField(newWebsiteField);

		addLabel(newLeadDeveloperLabel);
		addField(newLeadDeveloperField);

		addButton(updateDeveloperSubmitButton);
		addButton(manageDeveloperNameButton);
	}

	private void updateDeveloperSubmitHandler(GUIWindowDelegate delegate) {
		String name = nameField.getText();
		String newName = newDeveloperNameField.getText();
		String newWebsite = newWebsiteField.getText();
		String newLeadDeveloper = newLeadDeveloperField.getText();

		if (isValidUpdateDeveloperInput(name, newName, newWebsite, newLeadDeveloper)) {
			try {
				if (!newWebsite.isEmpty()) {
					delegate.updateDeveloperNameWebsite(newWebsite, name);
				}

				if (!newLeadDeveloper.isEmpty()) {
					delegate.updateDeveloperNameLead(newLeadDeveloper, name);
				}

				// Update developer name last
				if (!newName.isEmpty()) {
					delegate.updateDeveloperNameName(newName, name);
				}

				JOptionPane.showMessageDialog(null, "Developer updated successfully.", "Success", JOptionPane.INFORMATION_MESSAGE);
				manageDevelopers();
			} catch (Exception e) {
				// Show the exception message in a popup
				JOptionPane.showMessageDialog(null, "An error occurred: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
			}
		}
	}

	private boolean isValidUpdateDeveloperInput(String name, String newName, String newWebsite, String newLeadDeveloper) {
		if (name.isEmpty()) {
			JOptionPane.showMessageDialog(null, "Name field is empty. Please enter a valid name.", "Error", JOptionPane.ERROR_MESSAGE);
			return false;
		} else if (name.length() > 50) {
			JOptionPane.showMessageDialog(null, "Name is too long. Please enter a name with 50 characters or less.", "Error", JOptionPane.ERROR_MESSAGE);
			return false;
		}

		if (newName.isEmpty() && newWebsite.isEmpty() && newLeadDeveloper.isEmpty()) {
			JOptionPane.showMessageDialog(null, "Update fields are all empty. Please enter an update value.", "Error", JOptionPane.ERROR_MESSAGE);
			return false;
		}


		if (newName.length() > 50) {
			JOptionPane.showMessageDialog(null, "New Name is too long. Please enter a name with 50 characters or less.", "Error", JOptionPane.ERROR_MESSAGE);
			return false;
		}

		if (newWebsite.length() > 50) {
			JOptionPane.showMessageDialog(null, "New Website is too long. Please enter a new website with 50 characters or less.", "Error", JOptionPane.ERROR_MESSAGE);
			return false;
		}

		if (newLeadDeveloper.length() > 50) {
			JOptionPane.showMessageDialog(null, "New Lead Developer is too long. Please enter a new lead developer with 50 characters or less.", "Error", JOptionPane.ERROR_MESSAGE);
			return false;
		}
		return true;
	}
}

