package com.tinyexpenses.processing;

import com.tinyexpenses.balance.Balance;
import com.tinyexpenses.balance.BalanceEvent;
import com.tinyexpenses.processing.BalanceCommand;

import java.util.List;

public class CreateBalance extends BalanceCommand {

	private String balanceName;

	public CreateBalance(String balanceGuid, String balanceName) {
		super(balanceGuid);
		this.balanceName = balanceName;
	}

	public String balanceName() {
		return this.balanceName;
	}

	List<BalanceEvent> execute(Balance balance) {
		return balance.handle(this);
	}

}
