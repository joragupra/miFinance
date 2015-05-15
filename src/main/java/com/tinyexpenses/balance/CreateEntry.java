package com.tinyexpenses.balance;

class CreateEntry {

	private long balanceId;
	private String description;
	private java.util.Date creationDate;
	private Money amount;

	CreateEntry(long balanceId, String description,
			java.util.Date creationDate, Money amount) {
		this.balanceId = balanceId;
		this.description = description;
		this.creationDate = creationDate;
		this.amount = amount;
	}

	long balanceId() {
		return this.balanceId;
	}

	String description() {
		return this.description;
	}

	java.util.Date creationDate() {
		return this.creationDate;
	}

	Money amount() {
		return this.amount;
	}

}
