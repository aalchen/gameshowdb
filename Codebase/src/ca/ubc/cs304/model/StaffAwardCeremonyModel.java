package ca.ubc.cs304.model;

import java.util.Date;

public class StaffAwardCeremonyModel {

	private final int staff_id;
	private final Date awardceremony_date;
	
	public StaffAwardCeremonyModel(int staff_id, Date awardceremony_date) {
		this.staff_id = staff_id;
		this.awardceremony_date = awardceremony_date;
	}

	public int getStaffId() {
		return staff_id;
	}

	public Date getAwardCeremonyDate() {
		return awardceremony_date;
	}
}
