package com.tinyexpenses.balance;

import java.util.List;

abstract class BalanceCommand {

	protected String balanceGuid;

	protected BalanceCommand(String balanceGuid) {
		this.balanceGuid = balanceGuid;
	}

	String balanceGuid() {
		return this.balanceGuid;
	}

	abstract List<BalanceEvent> execute(Balance balance);

}
