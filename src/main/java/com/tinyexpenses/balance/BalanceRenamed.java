package com.tinyexpenses.balance;

class BalanceRenamed extends BalanceEvent {

	private String name;

	BalanceRenamed(long balanceId, String name) {
		super(balanceId);
		this.name = name;
	}

	String name() {
		return this.name;
	}

	protected void apply(Balance balance) {
		balance.handle(this);
	}

}
