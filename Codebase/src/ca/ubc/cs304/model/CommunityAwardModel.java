package ca.ubc.cs304.model;

import java.util.Date;

public class CommunityAwardModel implements Model {
	private String tableName = "Community";
	private final int votes;
	private final Date deadline;
	private final String award_name;
	private final Date award_date;
	
	public CommunityAwardModel(int votes, Date deadline, String award_name, Date award_date) {
		this.votes = votes;
		this.deadline = deadline;
		this.award_name = award_name;
		this.award_date = award_date;
	}

	public int getVotes() {
		return votes;
	}

	public Date getDeadline() {
		return deadline;
	}

	public String getAwardName() {
		return award_name;
	}

	public Date getAwardDate() {
		return award_date;
	}

	@Override
	public String getTableName() {
		return this.tableName;
	}

}
