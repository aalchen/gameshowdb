package ca.ubc.cs304.model;

public class CompanyModel implements Model {
	private String tableName = "Company";
	private final String name;
	private final String contact_info;
	
	public CompanyModel(String name, String contact_info) {
		this.name = name;
		this.contact_info = contact_info;
	}

	public String getName() {
		return name;
	}

	public String getContactInfo() {
		return contact_info;
	}

	@Override
	public String getTableName() {
		return this.tableName;
	}
}
