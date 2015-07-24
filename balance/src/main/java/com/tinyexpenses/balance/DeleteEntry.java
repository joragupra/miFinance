package com.tinyexpenses.balance;

import java.util.List;

class DeleteEntry extends BalanceCommand {

	private String entryGuid;

	DeleteEntry(String balanceGuid, String entryGuid) {
		super(balanceGuid);
		this.entryGuid = entryGuid;
	}

	String entryGuid() {
		return this.entryGuid;
	}

	List<BalanceEvent> execute(Balance balance) {
		return balance.handle(this);
	}

}
