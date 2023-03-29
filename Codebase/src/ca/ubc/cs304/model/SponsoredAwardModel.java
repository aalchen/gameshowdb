package ca.ubc.cs304.model;

public class SponsoredAwardModel {

	private final String sponsor;
	private final String award_name;
	private final Date award_date;
	
	public SponsoredAwardModel(String sponsor, String award_name, Date award_date) {
		this.sponsor = sponsor;
		this.award_name = award_name;
		this.award_date = award_date;
	}

	public String getSponsor() {
		return sponsor;
	}

	public String getAwardName() {
		return award_name;
	}

	public Date getAwardDate() {
		return award_date;
	}
}
