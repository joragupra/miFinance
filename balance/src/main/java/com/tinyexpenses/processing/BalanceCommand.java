package com.tinyexpenses.processing;

import com.tinyexpenses.balance.Balance;
import com.tinyexpenses.balance.BalanceEvent;

import java.util.List;

abstract class BalanceCommand {

	protected String balanceGuid;

	protected BalanceCommand(String balanceGuid) {
		this.balanceGuid = balanceGuid;
	}

	public String balanceGuid() {
		return this.balanceGuid;
	}

	abstract List<BalanceEvent> execute(Balance balance);

}
