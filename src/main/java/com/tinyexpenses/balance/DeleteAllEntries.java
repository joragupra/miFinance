package com.tinyexpenses.balance;

import java.util.List;

class DeleteAllEntries extends BalanceCommand {

	DeleteAllEntries(long balanceId) {
		super(balanceId);
	}

	List<BalanceEvent> execute(Balance balance) {
		return balance.handle(this);
	}

}
