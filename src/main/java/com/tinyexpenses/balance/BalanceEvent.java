package com.tinyexpenses.balance;

public abstract class BalanceEvent {

	protected String balanceGuid;

	protected BalanceEvent(String balanceGuid) {
		this.balanceGuid = balanceGuid;
	}

	protected String balanceGuid() {
		return this.balanceGuid;
	}

	protected abstract void apply(Balance balance);

}
