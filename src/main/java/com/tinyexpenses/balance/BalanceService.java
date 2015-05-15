package com.tinyexpenses.balance;

import java.util.Date;

public class BalanceService {

	private BalanceFactory factory;

	public BalanceService() {

		factory = new BalanceFactory();

	}

	public Balance retrieveBalance() {

		return new Balance();

	}

	public void addEntry(Balance balance, String description, Date recordedAt,
			long amountCents) {
		BalanceEntry balanceEntry = balance.addEntry(description, recordedAt,
				amountCents);
	}

	public void updateEntry(Balance balance, long balanceEntryId,
			String description, Date recordedAt, long amountCents) {
		BalanceEntry balanceEntry = balance.updateEntry(balanceEntryId,
				description, recordedAt, amountCents);
	}

	public void deleteEntry(Balance balance, long balanceEntryId) {
		boolean success = balance.deleteEntry(balanceEntryId);
	}

	public void deleteAllEntries(Balance balance) {
		boolean success = balance.deleteAllEntries();
	}

}
