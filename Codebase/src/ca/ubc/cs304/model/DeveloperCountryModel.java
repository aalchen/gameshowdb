package ca.ubc.cs304.model;

public class DeveloperCountryModel {

	private final String lead_developer;
	private final String country;

	public DeveloperCountryModel(String lead_developer, String country) {
		this.lead_developer = lead_developer;
		this.country = country;
	}

	public String getLeadDeveloper() {
		return lead_developer;
	}

	public String getCountry() {
		return country;
	}
}
