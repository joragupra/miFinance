package com.tinyexpenses.balance;

import java.util.List;

class CreateBalance extends BalanceCommand {

	private String balanceName;

	public CreateBalance(long balanceId, String balanceName) {
		super(balanceId);
		this.balanceName = balanceName;
	}

	public String balanceName() {
		return this.balanceName;
	}

	List<BalanceEvent> execute(Balance balance) {
		return balance.handle(this);
	}

}
