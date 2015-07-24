package com.tinyexpenses.balance;

public class BalanceRenamed extends BalanceEvent {

	private String name;

	public BalanceRenamed(String balanceGuid, String name) {
		super(balanceGuid);
		this.name = name;
	}

	public String name() {
		return this.name;
	}

	protected void apply(Balance balance) {
		balance.handle(this);
	}

}
