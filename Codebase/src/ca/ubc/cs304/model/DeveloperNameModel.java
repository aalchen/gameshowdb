package ca.ubc.cs304.model;

public class DeveloperNameModel {

	private final String lead_developer;
	private final String website;
	private final String name;
	
	public DeveloperNameModel(String lead_developer, String website, String name) {
		this.lead_developer = lead_developer;
		this.website = website;
		this.name = name;
	}

	public String getLeadDeveloper() {
		return lead_developer;
	}

	public String getWebsite() {
		return website;
	}

	public String getName() {
		return name;
	}
}
