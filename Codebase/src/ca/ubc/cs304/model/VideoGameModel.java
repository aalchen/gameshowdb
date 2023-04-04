package ca.ubc.cs304.model;

public class VideoGameModel implements Model {
	private String tableName = "VideoGame";
	private final String title;
	private final int year;
	private final String genre;
	private final String developer_name;

	public VideoGameModel(String title, int year, String genre, String developer_name) {
		this.title = title;
		this.year = year;
		this.genre = genre;
		this.developer_name = developer_name;
	}

	public String getTitle() {
		return title;
	}

	public int getYear() {
		return year;
	}

	public String getGenre() {
		return genre;
	}

	public String getDeveloperName() {
		return developer_name;
	}

	@Override
	public String getTableName() {
		return this.tableName;
	}
}
