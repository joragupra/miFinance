package com.tinyexpenses.balance;

import java.util.List;

class BalanceFactory {

	Balance createEmptyBalance() {

		return new Balance();

	}

	Balance createBalance(List<BalanceEntry> entries) {

		Balance balance = new Balance();
		balance.initBalanceEntries(entries);
		return balance;

	}

}
