package com.tinyexpenses.events;

import com.tinyexpenses.balance.Balance;

public class BalanceEntryDeleted extends BalanceEvent {

	private String entryGuid;

	public BalanceEntryDeleted(String balanceGuid, String entryGuid) {
		super(balanceGuid);
		this.entryGuid = entryGuid;
	}

	public String entryGuid() {
		return this.entryGuid;
	}

	public void apply(Balance balance) {
		balance.handle(this);
	}

}
