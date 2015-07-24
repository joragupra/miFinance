package com.tinyexpenses.balance;

import java.util.List;

class DeleteAllEntries extends BalanceCommand {

	DeleteAllEntries(String balanceGuid) {
		super(balanceGuid);
	}

	List<BalanceEvent> execute(Balance balance) {
		return balance.handle(this);
	}

}
