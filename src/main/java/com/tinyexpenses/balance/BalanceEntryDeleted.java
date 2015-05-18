package com.tinyexpenses.balance;

class BalanceEntryDeleted extends BalanceEvent {

	private String entryGuid;

	BalanceEntryDeleted(String balanceGuid, String entryGuid) {
		super(balanceGuid);
		this.entryGuid = entryGuid;
	}

	String entryGuid() {
		return this.entryGuid;
	}

	protected void apply(Balance balance) {
		balance.handle(this);
	}

}
