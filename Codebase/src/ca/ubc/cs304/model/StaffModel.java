package ca.ubc.cs304.model;

public class StaffModel implements Model {
	private String tableName = "Staff";
	private final String phone_number;
	private final String name;
	private final int id;
	private final String role;
	
	public StaffModel(String phone_number, String name, int id, String role) {
		this.phone_number = phone_number;
		this.name = name;
		this.id = id;
		this.role = role;
	}

	public String getPhoneNumber() {
		return phone_number;
	}

	public String getName() {
		return name;
	}

	public int getId() {
		return id;
	}

	public String getRole() {
		return role;
	}

	@Override
	public String getTableName() {
		return this.tableName;
	}
}
