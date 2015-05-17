package com.tinyexpenses.balance;

import java.util.List;

class DeleteEntry extends BalanceCommand {

	private String entryGuid;

	DeleteEntry(long balanceId, String entryGuid) {
		super(balanceId);
		this.entryGuid = entryGuid;
	}

	String entryGuid() {
		return this.entryGuid;
	}

	List<BalanceEvent> execute(Balance balance) {
		return balance.handle(this);
	}

}
