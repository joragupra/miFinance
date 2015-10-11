package com.tinyexpenses.processing;

import com.tinyexpenses.balance.Balance;
import com.tinyexpenses.balance.BalanceEvent;
import com.tinyexpenses.common.Money;
import com.tinyexpenses.processing.BalanceCommand;

import java.util.List;

public class UpdateBalanceEntry extends BalanceCommand {

	private String entryGuid;
	private String entryDescription;
	private java.util.Date createdAt;
	private Money amount;

	public UpdateBalanceEntry(String balanceGuid, String entryGuid,
			String entryDescription, java.util.Date createdAt, Money amount) {
		super(balanceGuid);
		this.entryGuid = entryGuid;
		this.entryDescription = entryDescription;
		this.createdAt = createdAt;
		this.amount = amount;
	}

	public String entryGuid() {
		return this.entryGuid;
	}

	public String entryDescription() {
		return this.entryDescription;
	}

	public java.util.Date createdAt() {
		return this.createdAt;
	}

	public Money amount() {
		return this.amount;
	}

	List<BalanceEvent> execute(Balance balance) {
		return balance.handle(this);
	}

}
