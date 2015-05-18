package com.tinyexpenses.balance;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class BalanceService {

	private BalanceFactory factory;
	private BalanceEventStream eventStream;
	private BalanceCommandHandler commandHandler;

	public BalanceService(EventStore<BalanceEvent> eventStore) {
		factory = new BalanceFactory();
		eventStream = new BalanceEventStream(eventStore);
		commandHandler = new BalanceCommandHandler(factory, eventStream);
	}

	public Balance retrieveBalance() {
		return factory.createEmptyBalance();
	}

	public Balance retrieveBalance(String balanceGuid) {
		Balance balance = factory.createEmptyBalance();
		balance.loadFromEvents(eventStream.events(balanceGuid));
		return balance;
	}

	public String openNewBalance(String balanceName) {
		final String balanceGuid = IdGenerator.generateId();
		CreateBalance command = new CreateBalance(balanceGuid, balanceName);
		commandHandler.handle(command);
		return balanceGuid;
	}

	public void addEntry(String balanceGuid, String description,
			Date recordedAt, long amountCents) {
		CreateEntry command = new CreateEntry(balanceGuid, description,
				recordedAt, Money.fromCents(amountCents));
		commandHandler.handle(command);
	}

	public void updateEntry(String balanceGuid, String balanceEntryGuid,
			String description, Date recordedAt, long amountCents) {
		UpdateBalanceEntry command = new UpdateBalanceEntry(balanceGuid,
				balanceEntryGuid, description, recordedAt,
				Money.fromCents(amountCents));
		commandHandler.handle(command);
	}

	public void deleteEntry(String balanceGuid, String balanceEntryGuid) {
		DeleteEntry command = new DeleteEntry(balanceGuid, balanceEntryGuid);
		commandHandler.handle(command);
	}

	public void deleteAllEntries(String balanceGuid) {
		DeleteAllEntries command = new DeleteAllEntries(balanceGuid);
		commandHandler.handle(command);
	}

}
