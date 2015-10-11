package com.tinyexpenses.processing;

import com.tinyexpenses.balance.Balance;
import com.tinyexpenses.balance.BalanceEvent;
import com.tinyexpenses.processing.BalanceCommand;

import java.util.List;

public class DeleteEntry extends BalanceCommand {

	private String entryGuid;

	public DeleteEntry(String balanceGuid, String entryGuid) {
		super(balanceGuid);
		this.entryGuid = entryGuid;
	}

	public String entryGuid() {
		return this.entryGuid;
	}

	List<BalanceEvent> execute(Balance balance) {
		return balance.handle(this);
	}

}
