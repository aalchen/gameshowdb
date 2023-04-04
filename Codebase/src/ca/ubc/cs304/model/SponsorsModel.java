package ca.ubc.cs304.model;

import java.util.Date;

public class SponsorsModel implements Model {
	private String tableName = "Sponsors";

	private final String company_name;
	private final Date awardceremony_date;
	private final int money;
	
	public SponsorsModel(String company_name, Date awardceremony_date, int money) {
		this.company_name = company_name;
		this.awardceremony_date = awardceremony_date;
		this.money = money;
	}

	public String getCompanyName() {
		return company_name;
	}

	public Date getAwardCeremonyDate() {
		return awardceremony_date;
	}

	public int getMoney() {
		return money;
	}

	@Override
	public String getTableName() {
		return this.tableName;
	}
}
