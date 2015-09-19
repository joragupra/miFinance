package com.tinyexpenses.balance;

import com.tinyexpenses.common.Money;

import java.util.Date;
import java.util.List;

class CreateEntry extends BalanceCommand {

	private String description;
	private Date creationDate;
	private Money amount;

	CreateEntry(String balanceGuid, String description, Date creationDate,
			Money amount) {
		super(balanceGuid);
		this.description = description;
		this.creationDate = creationDate;
		this.amount = amount;
	}

	String description() {
		return this.description;
	}

	Date creationDate() {
		return this.creationDate;
	}

	Money amount() {
		return this.amount;
	}

	List<BalanceEvent> execute(Balance balance) {
		return balance.handle(this);
	}

}
