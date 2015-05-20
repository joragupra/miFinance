package com.tinyexpenses.balance;

public class BalanceEntryDeleted extends BalanceEvent {

	private String entryGuid;

	public BalanceEntryDeleted(String balanceGuid, String entryGuid) {
		super(balanceGuid);
		this.entryGuid = entryGuid;
	}

	public String entryGuid() {
		return this.entryGuid;
	}

	protected void apply(Balance balance) {
		balance.handle(this);
	}

}
