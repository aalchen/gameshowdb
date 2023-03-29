package ca.ubc.cs304.model;

public class VideoGameDLCModel {

	private final String title;
	private final int year;
	private final String videogame_title;
	private final int videogame_year;
	
	public VideoGameDLCModel(String title, int year, String videogame_title, int videogame_year) {
		this.title = title;
		this.year = year;
		this.videogame_title = videogame_title;
		this.videogame_year = videogame_year;
	}

	public String getTitle() {
		return title;
	}

	public int getYear() {
		return year;
	}

	public String getVideoGameTitle() {
		return videogame_title;
	}

	public int getVideoGameYear() {
		return videogame_year;
	}
}
