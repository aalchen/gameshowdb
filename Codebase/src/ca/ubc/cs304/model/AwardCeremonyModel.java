package ca.ubc.cs304.model;

public class AwardCeremonyModel {

	private final int viewer_count;
	private final Date date;
	private final String venue_name;
	
	public AwardCeremonyModel(int viewer_count, Date date, String venue_name) {
		this.viewer_count = viewer_count;
		this.date = date;
		this.venue_name = venue_name;
	}

	public String getViewerCount() {
		return viewer_count;
	}

	public Date getDate() {
		return date;
	}

	public String getVenueName() {
		return venue_name;
	}
}
