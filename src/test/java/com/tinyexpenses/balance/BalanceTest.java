package com.tinyexpenses.balance;

import org.junit.*;
import java.util.List;
import java.util.ArrayList;

import static org.junit.Assert.*;

public class BalanceTest {

	private Balance balance;

	@Before
	public void setUp() {
		this.balance = new Balance();
	}

	@Test
	public void testCreateBalance() {
		final long balanceId = 1245L;
		final String balanceName = "New balance created";
		CreateBalance command = new CreateBalance(balanceId, balanceName);
		List<BalanceEvent> generatedEvents = balance.handle(command);

		checkBalanceEvent(generatedEvents.get(0), BalanceCreated.class);
		checkBalanceEvent(generatedEvents.get(1), BalanceRenamed.class);

		assertEquals(balanceId,
				((BalanceCreated) generatedEvents.get(0)).balanceId());
		assertEquals(balanceId,
				((BalanceRenamed) generatedEvents.get(1)).balanceId());
		assertEquals(balanceName,
				((BalanceRenamed) generatedEvents.get(1)).name());
	}

	private void checkBalanceEvent(BalanceEvent balanceEvent,
			Class balanceEventType) {
		assertTrue(balanceEvent.getClass() == balanceEventType);
	}

	@Test
	public void testBalanceCreated() {
		final long balanceId = 54321L;
		BalanceCreated balanceCreatedEvent = new BalanceCreated(balanceId);

		assertEquals(balanceId, balance.handle(balanceCreatedEvent).id());
	}

	@Test
	public void testBalanceRenamed() {
		final String balanceName = "My balance";
		BalanceRenamed balanceRenamedEvent = new BalanceRenamed(12345L,
				balanceName);

		assertEquals(balanceName, balance.handle(balanceRenamedEvent).name());
	}

	@Test
	public void testCreateEntry() {
		final String description = "Book purchase";
		final java.util.Date createdAt = new java.util.Date();
		final Money amount = Money.fromCents(1250);
		CreateEntry command = new CreateEntry(6789L, description, createdAt,
				amount);

		List<BalanceEvent> generatedEvents = balance.handle(command);

		checkBalanceEvent(generatedEvents.get(0), BalanceEntryCreated.class);
		assertEquals(description,
				((BalanceEntryCreated) generatedEvents.get(0))
						.entryDescription());
		assertEquals(createdAt,
				((BalanceEntryCreated) generatedEvents.get(0)).creationDate());
		assertEquals(amount,
				((BalanceEntryCreated) generatedEvents.get(0)).amount());
	}

	@Test
	public void testBalanceEntryCreated() {
		final String guid = IdGenerator.generateId();
		final String description = "Book purchase";
		final java.util.Date createdAt = new java.util.Date();
		final Money amount = Money.fromCents(1250);
		BalanceEntryCreated balanceEntryCreatedEvent = new BalanceEntryCreated(
				6789L, guid, description, createdAt, amount);

		balance.handle(balanceEntryCreatedEvent);

		assertEquals(1, balance.entries().size());
		assertEquals(guid, ((BalanceEntry) balance.entries().get(0)).guid());
		assertEquals(description,
				((BalanceEntry) balance.entries().get(0)).description());
		assertEquals(createdAt,
				((BalanceEntry) balance.entries().get(0)).recordedAt());
		assertEquals(amount, ((BalanceEntry) balance.entries().get(0)).amount());
	}

	@Test
	public void testUpdateBalanceEntry() {
		final String entryGuid = initializeBalanceWithOneEntry();
		final String description = "Book purchase - modification";
		final java.util.Date createdAt = new java.util.Date();
		final Money amount = Money.fromCents(1550);
		UpdateBalanceEntry command = new UpdateBalanceEntry(9876L, entryGuid,
				description, createdAt, amount);

		List<BalanceEvent> generatedEvents = balance.handle(command);

		checkBalanceEvent(generatedEvents.get(0), BalanceEntryUpdated.class);
		assertEquals(entryGuid,
				((BalanceEntryUpdated) generatedEvents.get(0)).entryGuid());
		assertEquals(description,
				((BalanceEntryUpdated) generatedEvents.get(0))
						.entryDescription());
		assertEquals(createdAt,
				((BalanceEntryUpdated) generatedEvents.get(0)).creationDate());
		assertEquals(amount,
				((BalanceEntryUpdated) generatedEvents.get(0)).amount());
	}

	@Test
	public void testBalanceEntryUpdated() {
		final String entryGuid = initializeBalanceWithOneEntry();
		final String description = "Dinner";
		final java.util.Date createdAt = new java.util.Date();
		final Money amount = Money.fromCents(2400);
		BalanceEntryUpdated balanceEntryUpdatedEvent = new BalanceEntryUpdated(
				1122L, entryGuid, description, createdAt, amount);

		balance.handle(balanceEntryUpdatedEvent);

		assertEquals(1, balance.entries().size());
		assertEquals(description,
				((BalanceEntry) balance.entries().get(0)).description());
		assertEquals(createdAt,
				((BalanceEntry) balance.entries().get(0)).recordedAt());
		assertEquals(amount, ((BalanceEntry) balance.entries().get(0)).amount());
	}

	private String initializeBalanceWithOneEntry() {
		final String description = "Book purchase";
		final java.util.Date createdAt = new java.util.Date();
		final Money amount = Money.fromCents(1250);
		BalanceEntryCreated balanceEntryCreatedEvent = new BalanceEntryCreated(
				6789L, IdGenerator.generateId(), description, createdAt, amount);
		balance.handle(balanceEntryCreatedEvent);
		return ((BalanceEntry) balance.entries().get(0)).guid();
	}

	@Test
	public void testDeleteEntry() {
		final String entryGuid = initializeBalanceWithOneEntry();
		DeleteEntry command = new DeleteEntry(112233L, entryGuid);

		List<BalanceEvent> generatedEvents = balance.handle(command);

		checkBalanceEvent(generatedEvents.get(0), BalanceEntryDeleted.class);
		assertEquals(entryGuid,
				((BalanceEntryDeleted) generatedEvents.get(0)).entryGuid());
	}

	@Test
	public void testBalanceEntryDeleted() {
		final String entryGuid = initializeBalanceWithOneEntry();
		BalanceEntryDeleted balanceEntryDeletedEvent = new BalanceEntryDeleted(
				5544L, entryGuid);

		balance.handle(balanceEntryDeletedEvent);

		assertEquals(0, balance.entries().size());
	}

	@Test
	public void testDeleteAllEntries() {
		initializeBalanceWithOneEntry();
		initializeBalanceWithOneEntry();
		initializeBalanceWithOneEntry();
		DeleteAllEntries command = new DeleteAllEntries(1234567L);

		List<BalanceEvent> generatedEvents = balance.handle(command);

		assertEquals(3, generatedEvents.size());
		checkBalanceEvent(generatedEvents.get(0), BalanceEntryDeleted.class);
		checkBalanceEvent(generatedEvents.get(1), BalanceEntryDeleted.class);
		checkBalanceEvent(generatedEvents.get(2), BalanceEntryDeleted.class);
	}

	@Test
	public void testLoadFromEvents_WhenBalanceIsJustCreated() {
		final long balanceId = 123456789L;
		final String balanceName = "General ledger";
		List<BalanceEvent> events = new ArrayList<>();
		BalanceCreated created = new BalanceCreated(balanceId);
		BalanceRenamed renamed = new BalanceRenamed(balanceId, balanceName);
		events.add(created);
		events.add(renamed);

		balance.loadFromEvents(events);

		assertEquals(balanceId, balance.id());
		assertEquals(balanceName, balance.name());
	}

	@Test
	public void testLoadFromEvents_WhenTwoEntriesAreAdded() {
		final long balanceId = 123456789L;
		final String balanceName = "General ledger";
		final String firstEntryDescription = "Dinner";
		final java.util.Date firstEntryCreationDate = new java.util.Date();
		final Money firstEntryAmount = Money.fromCents(3400);
		final String secondEntryDescription = "Book purchase";
		final java.util.Date secondEntryCreationDate = new java.util.Date();
		final Money secondEntryAmount = Money.fromCents(2780);
		List<BalanceEvent> events = new ArrayList<>();
		BalanceCreated created = new BalanceCreated(balanceId);
		BalanceRenamed renamed = new BalanceRenamed(balanceId, balanceName);
		BalanceEntryCreated firstEntryCreated = new BalanceEntryCreated(balanceId, IdGenerator.generateId(), firstEntryDescription, firstEntryCreationDate, firstEntryAmount);
		BalanceEntryCreated secondCreated = new BalanceEntryCreated(balanceId, IdGenerator.generateId(), secondEntryDescription, secondEntryCreationDate, secondEntryAmount);
		events.add(created);
		events.add(renamed);
		events.add(firstEntryCreated);
		events.add(secondCreated);

		balance.loadFromEvents(events);

		assertEquals(2, balance.entries().size());
		assertEquals(firstEntryDescription, ((BalanceEntry) balance.entries().get(0)).description());
		assertEquals(firstEntryCreationDate, ((BalanceEntry) balance.entries().get(0)).recordedAt());
		assertEquals(firstEntryAmount, ((BalanceEntry) balance.entries().get(0)).amount());
		assertEquals(secondEntryDescription, ((BalanceEntry) balance.entries().get(1)).description());
		assertEquals(secondEntryCreationDate, ((BalanceEntry) balance.entries().get(1)).recordedAt());
		assertEquals(secondEntryAmount, ((BalanceEntry) balance.entries().get(1)).amount());
	}

	@Test
	public void testLoadFromEvents_WhenOneEntryIsDeleted() {
		final long balanceId = 123456789L;
		final String balanceName = "General ledger";
		final String firstEntryGuid = IdGenerator.generateId();
		final String secondEntryDescription = "Book purchase";
		final java.util.Date secondEntryCreationDate = new java.util.Date();
		final Money secondEntryAmount = Money.fromCents(2780);
		List<BalanceEvent> events = new ArrayList<>();
		BalanceCreated created = new BalanceCreated(balanceId);
		BalanceRenamed renamed = new BalanceRenamed(balanceId, balanceName);
		BalanceEntryCreated firstEntryCreated = new BalanceEntryCreated(balanceId, firstEntryGuid, "Dinner", new java.util.Date(), Money.fromCents(3400));
		BalanceEntryCreated secondCreated = new BalanceEntryCreated(balanceId, IdGenerator.generateId(), secondEntryDescription, secondEntryCreationDate, secondEntryAmount);
		BalanceEntryDeleted firstEntryDeleted = new BalanceEntryDeleted(balanceId, firstEntryGuid);
		events.add(created);
		events.add(renamed);
		events.add(firstEntryCreated);
		events.add(secondCreated);
		events.add(firstEntryDeleted);

		balance.loadFromEvents(events);

		assertEquals(1, balance.entries().size());
		assertEquals(secondEntryDescription, ((BalanceEntry) balance.entries().get(0)).description());
		assertEquals(secondEntryCreationDate, ((BalanceEntry) balance.entries().get(0)).recordedAt());
		assertEquals(secondEntryAmount, ((BalanceEntry) balance.entries().get(0)).amount());
	}

}
