package com.tinyexpenses.balance;

class BalanceEntryCreated extends BalanceEvent {

	private String entryDescription;
	private java.util.Date creationDate;
	private Money amount;

	BalanceEntryCreated(long balanceId, String entryDescription,
			java.util.Date createdAt, Money amount) {
		super(balanceId);
		this.entryDescription = entryDescription;
		this.creationDate = createdAt;
		this.amount = amount;
	}

	String entryDescription() {
		return this.entryDescription;
	}

	java.util.Date creationDate() {
		return this.creationDate;
	}

	Money amount() {
		return this.amount;
	}

}
