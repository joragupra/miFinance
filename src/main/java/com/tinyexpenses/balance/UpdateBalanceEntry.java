package com.tinyexpenses.balance;

class UpdateBalanceEntry {

	private long balanceId;
	private String entryGuid;
	private String entryDescription;
	private java.util.Date createdAt;
	private Money amount;

	UpdateBalanceEntry(long balanceId, String entryGuid,
			String entryDescription, java.util.Date createdAt, Money amount) {
		this.balanceId = balanceId;
		this.entryGuid = entryGuid;
		this.entryDescription = entryDescription;
		this.createdAt = createdAt;
		this.amount = amount;
	}

	long balanceId() {
		return this.balanceId;
	}

	String entryGuid() {
		return this.entryGuid;
	}

	String entryDescription() {
		return this.entryDescription;
	}

	java.util.Date createdAt() {
		return this.createdAt;
	}

	Money amount() {
		return this.amount;
	}

}
