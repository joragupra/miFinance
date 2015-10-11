package com.tinyexpenses.events;

import com.tinyexpenses.balance.Balance;

public abstract class BalanceEvent {

	protected String balanceGuid;

	protected BalanceEvent(String balanceGuid) {
		this.balanceGuid = balanceGuid;
	}

	public String balanceGuid() {
		return this.balanceGuid;
	}

	public abstract void apply(Balance balance);

}
