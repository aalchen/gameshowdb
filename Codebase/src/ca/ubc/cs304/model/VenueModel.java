package ca.ubc.cs304.model;

public class VenueModel implements Model {
	private String tableName = "Venue";
	private final String name;
	private final String address;
	private final int capacity;
	
	public VenueModel(String name, String address, int capacity) {
		this.name = name;
		this.address = address;
		this.capacity = capacity;
	}

	public String getName() {
		return name;
	}

	public String getAddress() {
		return address;
	}

	public int getCapacity() {
		return capacity;
	}

	@Override
	public String getTableName() {
		return this.tableName;
	}
}
