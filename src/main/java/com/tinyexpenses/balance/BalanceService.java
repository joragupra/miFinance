package com.tinyexpenses.balance;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class BalanceService {

	private BalanceFactory factory;
	private BalanceEventStream eventStream;
	private BalanceCommandHandler commandHandler;

	private long nextBalanceId = 1;

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

	// TODO - balanceId should be changed to String
	public long openNewBalance(String balanceName) {
		final long balanceId = nextBalanceId++;
		CreateBalance command = new CreateBalance(balanceId, balanceName);
		commandHandler.handle(command);
		return balanceId;
	}

	public void addEntry(long balanceId, String description, Date recordedAt,
			long amountCents) {
		CreateEntry command = new CreateEntry(balanceId, description,
				recordedAt, Money.fromCents(amountCents));
		commandHandler.handle(command);
	}

	public void addEntry(Balance balance, String description, Date recordedAt,
			long amountCents) {
		BalanceEntry balanceEntry = balance.addEntry(IdGenerator.generateId(),
				description, recordedAt, Money.fromCents(amountCents));
	}

	public void updateEntry(long balanceId, String balanceEntryGuid,
			String description, Date recordedAt, long amountCents) {
		UpdateBalanceEntry command = new UpdateBalanceEntry(balanceId,
				balanceEntryGuid, description, recordedAt,
				Money.fromCents(amountCents));
		commandHandler.handle(command);
	}

	public void updateEntry(Balance balance, String balanceEntryGuid,
			String description, Date recordedAt, long amountCents) {
		balance.updateEntry(balanceEntryGuid, description, recordedAt,
				Money.fromCents(amountCents));
	}

	public void deleteEntry(long balanceId, String balanceEntryGuid) {
		DeleteEntry command = new DeleteEntry(balanceId, balanceEntryGuid);
		commandHandler.handle(command);
	}

	public void deleteEntry(Balance balance, String balanceEntryGuid) {
		boolean success = balance.deleteEntry(balanceEntryGuid);
	}

	public void deleteAllEntries(long balanceId) {
		DeleteAllEntries command = new DeleteAllEntries(balanceId);
		commandHandler.handle(command);
	}

	public void deleteAllEntries(Balance balance) {
		boolean success = balance.deleteAllEntries();
	}

}
