package com.tinyexpenses.processing;

import com.tinyexpenses.balance.Balance;
import com.tinyexpenses.events.BalanceEvent;
import com.tinyexpenses.common.Money;
import com.tinyexpenses.events.EventGenerator;

import java.util.Date;
import java.util.List;

public class CreateEntry extends BalanceCommand {

	private String description;
	private Date creationDate;
	private Money amount;

	public CreateEntry(String balanceGuid, String description, Date creationDate,
			Money amount) {
		super(balanceGuid);
		this.description = description;
		this.creationDate = creationDate;
		this.amount = amount;
	}

	public String description() {
		return this.description;
	}

	public Date creationDate() {
		return this.creationDate;
	}

	public Money amount() {
		return this.amount;
	}

	List<BalanceEvent> execute(Balance balance) {
		return EventGenerator.generateEvents(this);
	}

}
