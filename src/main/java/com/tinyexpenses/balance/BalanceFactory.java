package com.tinyexpenses.balance;

import java.util.List;

class BalanceFactory {

	Balance createEmptyBalance() {

		return new Balance();

	}

	//TODO - remove as it is useless from now on
	Balance createBalance(List<BalanceEntry> entries) {

		Balance balance = new Balance();
		balance.initBalanceEntries(entries);
		return balance;

	}

}
