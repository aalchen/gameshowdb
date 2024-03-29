package ca.ubc.cs304.model;

import java.util.Date;

public class AwardModel implements Model {
	private String tableName = "Award";
	private final String name;
	private final int prize;
	private final Date award_date;
	private final String videogame_title;
	private final int videogame_year;
	
	public AwardModel(String name, int prize, Date award_date, String videogame_title, int videogame_year) {
		this.name = name;
		this.prize = prize;
		this.award_date = award_date;
		this.videogame_title = videogame_title;
		this.videogame_year = videogame_year;
	}

	public String getName() {
		return name;
	}

	public int getPrize() {
		return prize;
	}

	public Date getDate() {
		return award_date;
	}

	public String getVideoGameTitle() {
		return videogame_title;
	}

	public int getVideoGameYear() {
		return videogame_year;
	}
	@Override
	public String getTableName() {
		return this.tableName;
	}
}
