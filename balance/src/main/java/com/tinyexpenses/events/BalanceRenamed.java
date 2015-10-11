package com.tinyexpenses.events;

import com.tinyexpenses.balance.Balance;

public class BalanceRenamed extends BalanceEvent {

	private String name;

	public BalanceRenamed(String balanceGuid, String name) {
		super(balanceGuid);
		this.name = name;
	}

	public String name() {
		return this.name;
	}

	public void apply(Balance balance) {
		balance.handle(this);
	}

}
