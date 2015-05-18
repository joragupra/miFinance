package com.tinyexpenses.balance;

class BalanceRenamed extends BalanceEvent {

	private String name;

	BalanceRenamed(String balanceGuid, String name) {
		super(balanceGuid);
		this.name = name;
	}

	String name() {
		return this.name;
	}

	protected void apply(Balance balance) {
		balance.handle(this);
	}

}
