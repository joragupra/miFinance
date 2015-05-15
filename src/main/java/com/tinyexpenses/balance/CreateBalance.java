package com.tinyexpenses.balance;

public class CreateBalance {

	private long balanceId;
	private String balanceName;

	public CreateBalance(long balanceId, String balanceName) {
		this.balanceId = balanceId;
		this.balanceName = balanceName;
	}

	public long balanceId() {
		return this.balanceId;
	}

	public String balanceName() {
		return this.balanceName;
	}

}
