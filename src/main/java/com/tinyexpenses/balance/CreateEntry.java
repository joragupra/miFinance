package com.tinyexpenses.balance;

import java.util.List;

class CreateEntry extends BalanceCommand {

	private String description;
	private java.util.Date creationDate;
	private Money amount;

	CreateEntry(long balanceId, String description,
			java.util.Date creationDate, Money amount) {
		super(balanceId);
		this.description = description;
		this.creationDate = creationDate;
		this.amount = amount;
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

	List<BalanceEvent> execute(Balance balance) {
		return balance.handle(this);
	}

}
