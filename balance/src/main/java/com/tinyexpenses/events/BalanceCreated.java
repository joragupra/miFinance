package com.tinyexpenses.events;

import com.tinyexpenses.balance.Balance;

public class BalanceCreated extends BalanceEvent {

	public BalanceCreated(String balanceGuid) {
		super(balanceGuid);
	}

	public void apply(Balance balance) {
		balance.handle(this);
	}

}
