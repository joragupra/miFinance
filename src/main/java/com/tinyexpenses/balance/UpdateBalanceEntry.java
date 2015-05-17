package com.tinyexpenses.balance;

import java.util.List;

class UpdateBalanceEntry extends BalanceCommand {

	private String entryGuid;
	private String entryDescription;
	private java.util.Date createdAt;
	private Money amount;

	UpdateBalanceEntry(long balanceId, String entryGuid,
			String entryDescription, java.util.Date createdAt, Money amount) {
		super(balanceId);
		this.entryGuid = entryGuid;
		this.entryDescription = entryDescription;
		this.createdAt = createdAt;
		this.amount = amount;
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

	List<BalanceEvent> execute(Balance balance) {
		return balance.handle(this);
	}

}
