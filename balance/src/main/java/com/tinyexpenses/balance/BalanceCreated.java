package com.tinyexpenses.balance;

public class BalanceCreated extends BalanceEvent {

	public BalanceCreated(String balanceGuid) {
		super(balanceGuid);
	}

	protected void apply(Balance balance) {
		balance.handle(this);
	}

}
