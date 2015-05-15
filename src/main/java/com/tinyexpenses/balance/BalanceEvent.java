package com.tinyexpenses.balance;

abstract class BalanceEvent {

	protected long balanceId;

	protected BalanceEvent(long balanceId) {
		this.balanceId = balanceId;
	}

	protected long balanceId() {
		return this.balanceId;
	}

	protected abstract void apply(Balance balance);

}
