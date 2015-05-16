package com.tinyexpenses.balance;

class BalanceEntryDeleted extends BalanceEvent {

	private String entryGuid;

	BalanceEntryDeleted(long balanceId, String entryGuid) {
		super(balanceId);
		this.entryGuid = entryGuid;
	}

	String entryGuid() {
		return this.entryGuid;
	}

	protected void apply(Balance balance) {
		balance.handle(this);
	}

}
