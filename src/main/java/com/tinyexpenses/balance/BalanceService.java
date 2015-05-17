package com.tinyexpenses.balance;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class BalanceService {

	private BalanceFactory factory;
	private BalanceEventStream eventStream;
	private BalanceCommandHandler commandHandler;

	public BalanceService() {

		this(new ArrayList<BalanceEvent>());

	}

	public BalanceService(List<BalanceEvent> events) {
		factory = new BalanceFactory();
		initializeEventStream(events);
		commandHandler = new BalanceCommandHandler(factory, eventStream);
	}

	private void initializeEventStream(List<BalanceEvent> events) {
		eventStream = BalanceEventStream.getInstance();
		eventStream.initialize(events);
	}

	public Balance retrieveBalance() {

		return factory.createEmptyBalance();

	}

	public Balance retrieveBalance(long balanceId) {
		Balance balance = factory.createEmptyBalance();
		balance.loadFromEvents(eventStream.events(balanceId));
		return balance;
	}

	public void addEntry(Balance balance, String description, Date recordedAt,
			long amountCents) {
		BalanceEntry balanceEntry = balance.addEntry(IdGenerator.generateId(),
				description, recordedAt, Money.fromCents(amountCents));
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
