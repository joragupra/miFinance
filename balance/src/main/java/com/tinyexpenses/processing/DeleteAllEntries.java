package com.tinyexpenses.processing;

import com.tinyexpenses.balance.Balance;
import com.tinyexpenses.events.BalanceEvent;

import java.util.List;

public class DeleteAllEntries extends BalanceCommand {

	public DeleteAllEntries(String balanceGuid) {
		super(balanceGuid);
	}

	List<BalanceEvent> execute(Balance balance) {
		return balance.handle(this);
	}

}
