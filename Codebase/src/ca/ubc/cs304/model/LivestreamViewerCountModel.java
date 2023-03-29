package ca.ubc.cs304.model;

import java.util.Date;

public class LivestreamViewerCountModel {

	private final String language;
	private final int viewer_count;
	private final String name;
	private final Date awardceremony_date;
	
	public LivestreamViewerCountModel(String language, int viewer_count, String name, Date awardceremony_date) {
		this.language = language;
		this.viewer_count = viewer_count;
		this.name = name;
		this.awardceremony_date = awardceremony_date;
	}

	public String getLanguage() {
		return language;
	}

	public int getViewerCount() {
		return viewer_count;
	}

	public String getName() {
		return name;
	}

	public Date getAwardCeremonyDate() {
		return awardceremony_date;
	}
}
