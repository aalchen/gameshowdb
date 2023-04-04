package ca.ubc.cs304.model;

import java.util.Date;

public class LivestreamUrlModel implements Model {
	private String tableName = "LivestreamUrl";
	private final String url;
	private final String language;
	private final String name;
	private final Date awardceremony_date;
	
	public LivestreamUrlModel(String url, String language, String name, Date awardceremony_date) {
		this.url = url;
		this.language = language;
		this.name = name;
		this.awardceremony_date = awardceremony_date;
	}

	public String getUrl() {
		return url;
	}

	public String getLanguage() {
		return language;
	}

	public String getName() {
		return name;
	}

	public Date getAwardCeremonyDate() {
		return awardceremony_date;
	}

	@Override
	public String getTableName() {
		return this.tableName;
	}
}
