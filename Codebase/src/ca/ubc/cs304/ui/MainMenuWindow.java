package ca.ubc.cs304.ui;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;

import javax.swing.*;

import ca.ubc.cs304.delegates.GUIWindowDelegate;
import ca.ubc.cs304.model.DeveloperNameTableModel;
import ca.ubc.cs304.model.VideoGameModel;
import ca.ubc.cs304.model.VideoGameTableModel;

/**
 * The class is only responsible for displaying and handling the login GUI.
 */
public class MainMenuWindow extends JFrame implements ActionListener {
	private static final int FRAME_WIDTH = 500;
	private static final int FRAME_HEIGHT = 300;
	private static final int TABLE_FRAME_WIDTH = 1000;
	private static final int TABLE_FRAME_HEIGHT = 500;

	// Buttons
	private JButton manageVideoGameButton, manageDeveloperNameButton, quitButton, mainMenuButton;
	private JButton addVideoGameButton, addVideoGameSubmitButton, removeVideoGameSubmitButton, removeVideoGameButton,
			showAllVideoGameButton;
	private JButton addDeveloperButton, removeDeveloperButton, updateDeveloperButton, showAllDevelopersButton,
			addDeveloperSubmitButton, removeDeveloperSubmitButton;

	// Text fields
	private JTextField titleField, yearField, genreField, developerNameField;
	private JTextField nameField, websiteField, leadDeveloperField;

	// Panel and layout
	private JPanel contentPanel;
	private GridBagLayout gb;
	private GridBagConstraints c;
	private GUIWindowDelegate delegate = null;

	public MainMenuWindow() {
		super("VideoGame Award Manager");
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
		this.manageVideoGameButton = new JButton("Manage VideoGame Table");
		this.manageDeveloperNameButton = new JButton("Manage DeveloperName Table");
		this.quitButton = new JButton("Quit");
		setUpJpanel();

		// add buttons
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
				removeVideoGameHandler(delegate);
			}
			else if (evt.getSource()== manageDeveloperNameButton)
			{
				manageDevelopers();
			} else if (evt.getSource() == addDeveloperButton) {
				addDeveloperHandler();
			} else if (evt.getSource() == removeDeveloperButton) {
				removeDeveloperHandler(delegate);
			} else if (evt.getSource() == updateDeveloperButton) {

			} else if (evt.getSource() == showAllDevelopersButton) {
				showAllDevelopersHandler();
			} else if (evt.getSource() == addDeveloperSubmitButton) {

			} else if (evt.getSource() == removeDeveloperSubmitButton) {

			}
		}
	}

	private void displayRemoveVideoGameHandler() {
		removeVideoGameSubmitButton = new JButton("Submit");
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
		} else if (title.length() > 20) {
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
		} else if (title.length() > 20) {
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
		} else if (genre.length() > 20) {
			JOptionPane.showMessageDialog(null, "Genre is too long. Please enter a genre with 20 characters or less.", "Error", JOptionPane.ERROR_MESSAGE);
			return false;
		}

		if (name.isEmpty()) {
			JOptionPane.showMessageDialog(null, "Developer name field is empty. Please enter a valid name.", "Error", JOptionPane.ERROR_MESSAGE);
			return false;
		} else if (name.length() > 20) {
			JOptionPane.showMessageDialog(null, "Developer name is too long. Please enter a name with 20 characters or less.", "Error", JOptionPane.ERROR_MESSAGE);
			return false;
		}

		return true;
	}

	private void AddVideoGameHandler() {
		addVideoGameSubmitButton = new JButton("Submit");
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

		VideoGameTableModel videogameTable = new VideoGameTableModel(delegate.getVideoGamesObjects());
		JTable table = new JTable(videogameTable);
		JScrollPane scrollPane = new JScrollPane(table);

		// Add the JScrollPane to the JPanel with GridBagLayout
		c.fill = GridBagConstraints.BOTH;

		c.gridx = 0;
		c.gridy = 0;
		c.weightx = 1.0;
		c.weighty = 1.0;
		contentPanel.add(scrollPane, c);

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
		this.mainMenuButton = new JButton("Return to Main Menu");

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
			button.setPreferredSize(new Dimension(250, currentPreferredSize.height));
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

	// Developer Handling Section Begins
	private void manageDevelopers() {
		this.addDeveloperButton = new JButton("Add developer");
		this.updateDeveloperButton = new JButton("Find and update developer info");
		this.removeDeveloperButton = new JButton("Find and remove a developer");
		this.showAllDevelopersButton = new JButton("Show all developers");
		this.mainMenuButton = new JButton("Return to Main Menu");

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
		addDeveloperSubmitButton = new JButton("Submit");
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

	private void removeDeveloperHandler(GUIWindowDelegate delegate) {
		String name = nameField.getText();
		String website = websiteField.getText();
		String leadDeveloper = leadDeveloperField.getText();

		if (isValidRemoveDeveloperInput(name, website, leadDeveloper)) {
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

	private boolean isValidRemoveDeveloperInput(String name, String website, String leadDeveloper) {
		// TODO: Implement this!
		return false;
	}

	private void showAllDevelopersHandler() {
		setUpJpanel();

		DeveloperNameTableModel developerNameTable = new DeveloperNameTableModel(delegate.getDeveloperNamesObjects());
		JTable table = new JTable(developerNameTable);
		JScrollPane scrollPane = new JScrollPane(table);

		// Add the JScrollPane to the JPanel with GridBagLayout
		c.fill = GridBagConstraints.BOTH;

		c.gridx = 0;
		c.gridy = 0;
		c.weightx = 1.0;
		c.weighty = 1.0;
		contentPanel.add(scrollPane, c);

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
					String gameName = (String) developerNameTable.getValueAt(modelRow, 0);
					int gameYear = (int) developerNameTable.getValueAt(modelRow, 1);

					try {
						delegate.deleteVideoGame(gameName, gameYear);
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
	}
}
