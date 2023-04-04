package ca.ubc.cs304.model;

import java.util.Date;

public class AwardCeremonyModel implements Model {
	private String tableName = "AwardCeremony";
	private final int viewer_count;
	private final Date date;
	private final String venue_name;
	
	public AwardCeremonyModel(int viewer_count, Date date, String venue_name) {
		this.viewer_count = viewer_count;
		this.date = date;
		this.venue_name = venue_name;
	}

	public int getViewerCount() {
		return viewer_count;
	}

	public Date getDate() {
		return date;
	}

	public String getVenueName() {
		return venue_name;
	}

	@Override
	public String getTableName() {
		return this.tableName;
	}
}
