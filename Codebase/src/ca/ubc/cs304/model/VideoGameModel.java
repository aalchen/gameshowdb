package ca.ubc.cs304.model;

public class VideoGameModel {
	private final String title;
	private final int year;
	private final String genre;
	private final String name;
	
	public VideoGameModel(String title, int year, String genre, String name) {
		this.title = title;
		this.year = year;
		this.genre = genre;
		this.name = name;
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
		return name;
	}
}
