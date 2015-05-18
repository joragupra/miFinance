package com.tinyexpenses.balance;

class BalanceCreated extends BalanceEvent {

	BalanceCreated(String balanceGuid) {
		super(balanceGuid);
	}

	protected void apply(Balance balance) {
		balance.handle(this);
	}

}
