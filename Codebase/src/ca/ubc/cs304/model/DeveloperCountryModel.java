package ca.ubc.cs304.model;

public class DeveloperCountryModel implements Model {
	private String tableName = "DeveloperCountry";

	private final String name;
	private final String country;

	public DeveloperCountryModel(String name, String country) {
		this.name = name;
		this.country = country;
	}

	public String getName() {
		return name;
	}

	public String getCountry() {
		return country;
	}

	@Override
	public String getTableName() {
		return this.tableName;
	}
}
