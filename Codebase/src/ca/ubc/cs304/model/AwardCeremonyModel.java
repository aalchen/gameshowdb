package ca.ubc.cs304.model;

import java.util.Date;

public class AwardCeremonyModel implements Model {
	private String tableName = "AwardCeremony";
	private final int viewer_count;
	private final Date award_ceremony_date;
	private final String venue_name;
	
	public AwardCeremonyModel(int viewer_count, Date award_ceremony_date, String venue_name) {
		this.viewer_count = viewer_count;
		this.award_ceremony_date = award_ceremony_date;
		this.venue_name = venue_name;
	}

	public int getViewerCount() {
		return viewer_count;
	}

	public Date getDate() {
		return award_ceremony_date;
	}

	public String getVenueName() {
		return venue_name;
	}

	@Override
	public String getTableName() {
		return this.tableName;
	}
}
