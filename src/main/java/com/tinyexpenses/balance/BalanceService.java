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
		BalanceEntry balanceEntry = balance.addEntry(IdGenerator.generateId(), description, recordedAt,
				Money.fromCents(amountCents));
	}

	public void updateEntry(Balance balance, String balanceEntryGuid,
			String description, Date recordedAt, long amountCents) {
		BalanceEntry balanceEntry = balance.updateEntry(balanceEntryGuid,
				description, recordedAt, Money.fromCents(amountCents));
	}

	public void deleteEntry(Balance balance, String balanceEntryGuid) {
		boolean success = balance.deleteEntry(balanceEntryGuid);
	}

	public void deleteAllEntries(Balance balance) {
		boolean success = balance.deleteAllEntries();
	}

}
