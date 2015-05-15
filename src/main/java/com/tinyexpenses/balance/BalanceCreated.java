package com.tinyexpenses.balance;

class BalanceCreated extends BalanceEvent {

	BalanceCreated(long balanceId) {
		super(balanceId);
	}

	protected void apply(Balance balance) {
		balance.handle(this);
	}

}
