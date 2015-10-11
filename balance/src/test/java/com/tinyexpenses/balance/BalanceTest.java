package com.tinyexpenses.balance;

import com.tinyexpenses.common.Money;
import com.tinyexpenses.events.*;
import com.tinyexpenses.processing.*;
import org.junit.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static org.junit.Assert.*;

public class BalanceTest {

	private Balance balance;

	@Before
	public void setUp() {
		this.balance = new Balance();
	}

	@Test
	public void testAddEntry() {
		final String entryGuid = "727217ad271b34cc";
		final String entryDescription = "Book purchase";
		final Date entryDate = new Date();
		final Money entryAmount = Money.fromCents(2499);

		balance.addEntry(entryGuid, entryDescription, entryDate, entryAmount);

		assertEquals(1, balance.entries().size());
		assertEquals(entryGuid, balance.entries().get(0).guid());
		assertEquals(entryDescription, balance.entries().get(0).description());
		assertEquals(entryDate, balance.entries().get(0).recordedAt());
		assertEquals(entryAmount.cents(), balance.entries().get(0).amount()
				.cents());
	}

	@Test
	public void testUpdateEntry() {
		final String entryGuid = "727217ad271b34cc";
		balance.addEntry(entryGuid, "Dinner", new Date(), Money.fromCents(1499));
		final String entryDescription = "Book purchase";
		final Date entryDate = new Date();
		final Money entryAmount = Money.fromCents(2499);

		balance.updateEntry(entryGuid, entryDescription, entryDate, entryAmount);

		assertEquals(1, balance.entries().size());
		assertEquals(entryGuid, balance.entries().get(0).guid());
		assertEquals(entryDescription, balance.entries().get(0).description());
		assertEquals(entryDate, balance.entries().get(0).recordedAt());
		assertEquals(entryAmount.cents(), balance.entries().get(0).amount()
				.cents());
	}

	@Test
	public void testUpdateEntry_WhenEntryNotFound_DoesNothing() {
		final String entryGuid = "727217ad271b34cc";
		final String entryDescription = "Book purchase";
		final Date entryDate = new Date();
		final Money entryAmount = Money.fromCents(2499);
		balance.addEntry(entryGuid, entryDescription, entryDate, entryAmount);

		balance.updateEntry("f52767c4d5de766", "Dinner", new Date(),
				Money.fromCents(1499));

		assertEquals(1, balance.entries().size());
		assertEquals(entryGuid, balance.entries().get(0).guid());
		assertEquals(entryDescription, balance.entries().get(0).description());
		assertEquals(entryDate, balance.entries().get(0).recordedAt());
		assertEquals(entryAmount.cents(), balance.entries().get(0).amount()
				.cents());
	}

	@Test
	public void testDeleteEntry() {
		final String entryGuid = "727217ad271b34cc";
		balance.addEntry(entryGuid, "Dinner", new Date(), Money.fromCents(1499));

		balance.deleteEntry(entryGuid);

		assertEquals(0, balance.entries().size());
	}

	@Test
	public void testDeleteEntry_WhenEntryNotFound_DoesNothing() {
		balance.addEntry("727217ad271b34cc", "Dinner", new Date(),
				Money.fromCents(1499));

		balance.deleteEntry("ab672c726d2f152");

		assertEquals(1, balance.entries().size());
	}

	@Test
	public void testDeleteAllEntries() {
		balance.addEntry("727217ad271b34cc", "Dinner", new Date(),
				Money.fromCents(1499));
		balance.addEntry("33fed237ad271b378f", "Book purchase", new Date(),
				Money.fromCents(2499));

		balance.deleteAllEntries();

		assertEquals(0, balance.entries().size());
	}

	@Test
	public void testBalanceAmount() {
		balance.addEntry("727217ad271b34cc", "Dinner", new Date(),
				Money.fromCents(1499));
		balance.addEntry("33fed237ad271b378f", "Book purchase", new Date(),
				Money.fromCents(2499));

		assertEquals(3998, balance.balanceAmount().cents());
	}

	@Test
	public void testChangeSortingMethod_ByAmount() {
		final String entryGuid1 = "727217ad271b34ccd2";
		final String entryDescription1 = "Book purchase";
		final Date entryDate1 = new Date();
		final Money entryAmount1 = Money.fromCents(499);

		final String entryGuid2 = "787ef917ad271b34cc";
		final String entryDescription2 = "Another book purchase";
		final Date entryDate2 = new Date();
		final Money entryAmount2 = Money.fromCents(2499);

		balance.addEntry(entryGuid1, entryDescription1, entryDate1,
				entryAmount1);
		balance.addEntry(entryGuid2, entryDescription2, entryDate2,
				entryAmount2);

		balance.changeSortingMethod(Balance.EntrySortMethod.AMOUNT);

		assertEquals(entryGuid1, balance.entries().get(0).guid());
		assertEquals(entryDescription1, balance.entries().get(0).description());
		assertEquals(entryDate1, balance.entries().get(0).recordedAt());
		assertEquals(entryAmount1.cents(), balance.entries().get(0).amount()
				.cents());
		assertEquals(entryGuid2, balance.entries().get(1).guid());
		assertEquals(entryDescription2, balance.entries().get(1).description());
		assertEquals(entryDate2, balance.entries().get(1).recordedAt());
		assertEquals(entryAmount2.cents(), balance.entries().get(1).amount()
				.cents());
	}

	@Test
	public void testChangeSortingMethod_ByDescription() {
		final String entryGuid1 = "727217ad271b34ccd2";
		final String entryDescription1 = "Book purchase";
		final Date entryDate1 = new Date();
		final Money entryAmount1 = Money.fromCents(499);

		final String entryGuid2 = "787ef917ad271b34cc";
		final String entryDescription2 = "Another purchase";
		final Date entryDate2 = new Date();
		final Money entryAmount2 = Money.fromCents(2499);

		balance.addEntry(entryGuid1, entryDescription1, entryDate1,
				entryAmount1);
		balance.addEntry(entryGuid2, entryDescription2, entryDate2,
				entryAmount2);

		balance.changeSortingMethod(Balance.EntrySortMethod.DESCRIPTION);

		assertEquals(entryGuid2, balance.entries().get(0).guid());
		assertEquals(entryDescription2, balance.entries().get(0).description());
		assertEquals(entryDate2, balance.entries().get(0).recordedAt());
		assertEquals(entryAmount2.cents(), balance.entries().get(0).amount()
				.cents());
		assertEquals(entryGuid1, balance.entries().get(1).guid());
		assertEquals(entryDescription1, balance.entries().get(1).description());
		assertEquals(entryDate1, balance.entries().get(1).recordedAt());
		assertEquals(entryAmount1.cents(), balance.entries().get(1).amount()
				.cents());
	}

	@Test
	public void testCreateBalanceCommand() {
		final String balanceGuid = "48388432423";
		final String balanceName = "New balance created";
		CreateBalance command = new CreateBalance(balanceGuid, balanceName);
		List<BalanceEvent> generatedEvents = EventGenerator.generateEvents(command);

		checkBalanceEvent(generatedEvents.get(0), BalanceCreated.class);
		checkBalanceEvent(generatedEvents.get(1), BalanceRenamed.class);

		assertEquals(balanceGuid,
				((BalanceCreated) generatedEvents.get(0)).balanceGuid());
		assertEquals(balanceGuid,
				((BalanceRenamed) generatedEvents.get(1)).balanceGuid());
		assertEquals(balanceName,
				((BalanceRenamed) generatedEvents.get(1)).name());
	}

	private void checkBalanceEvent(BalanceEvent balanceEvent,
			Class balanceEventType) {
		assertTrue(balanceEvent.getClass() == balanceEventType);
	}

	@Test
	public void testBalanceCreated() {
		final String balanceGuid = "54321982738232";
		BalanceCreated balanceCreatedEvent = new BalanceCreated(balanceGuid);

		assertEquals(balanceGuid, balance.handle(balanceCreatedEvent).guid());
	}

	@Test
	public void testBalanceRenamed() {
		final String balanceName = "My balance";
		BalanceRenamed balanceRenamedEvent = new BalanceRenamed("12345",
				balanceName);

		assertEquals(balanceName, balance.handle(balanceRenamedEvent).name());
	}

	@Test
	public void testCreateEntryCommand() {
		final String description = "Book purchase";
		final java.util.Date createdAt = new java.util.Date();
		final Money amount = Money.fromCents(1250);
		CreateEntry command = new CreateEntry("6789", description, createdAt,
				amount);

		List<BalanceEvent> generatedEvents = EventGenerator.generateEvents(command);

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
				"6789", guid, description, createdAt, amount);

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
	public void testUpdateBalanceEntryCommand() {
		final String entryGuid = initializeBalanceWithOneEntry();
		final String description = "Book purchase - modification";
		final java.util.Date createdAt = new java.util.Date();
		final Money amount = Money.fromCents(1550);
		UpdateBalanceEntry command = new UpdateBalanceEntry("9876", entryGuid,
				description, createdAt, amount);

		List<BalanceEvent> generatedEvents = EventGenerator.generateEvents(command);

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
				"1122", entryGuid, description, createdAt, amount);

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
				"6789", IdGenerator.generateId(), description, createdAt,
				amount);
		balance.handle(balanceEntryCreatedEvent);
		return ((BalanceEntry) balance.entries().get(0)).guid();
	}

	@Test
	public void testDeleteEntryCommand() {
		final String entryGuid = initializeBalanceWithOneEntry();
		DeleteEntry command = new DeleteEntry("112233", entryGuid);

		List<BalanceEvent> generatedEvents = EventGenerator.generateEvents(command);

		checkBalanceEvent(generatedEvents.get(0), BalanceEntryDeleted.class);
		assertEquals(entryGuid,
				((BalanceEntryDeleted) generatedEvents.get(0)).entryGuid());
	}

	@Test
	public void testBalanceEntryDeleted() {
		final String entryGuid = initializeBalanceWithOneEntry();
		BalanceEntryDeleted balanceEntryDeletedEvent = new BalanceEntryDeleted(
				"5544", entryGuid);

		balance.handle(balanceEntryDeletedEvent);

		assertEquals(0, balance.entries().size());
	}

	@Test
	public void testDeleteAllEntriesCommand() {
		initializeBalanceWithOneEntry();
		initializeBalanceWithOneEntry();
		initializeBalanceWithOneEntry();
		DeleteAllEntries command = new DeleteAllEntries("1234567");

		List<BalanceEvent> generatedEvents = EventGenerator.generateEvents(command);

		assertEquals(1, generatedEvents.size());
		checkBalanceEvent(generatedEvents.get(0), BalanceEmptied.class);
	}

	@Test
	public void testLoadFromEvents_WhenBalanceIsJustCreated() {
		final String balanceGuid = "123456789";
		final String balanceName = "General ledger";
		List<BalanceEvent> events = new ArrayList<>();
		BalanceCreated created = new BalanceCreated(balanceGuid);
		BalanceRenamed renamed = new BalanceRenamed(balanceGuid, balanceName);
		events.add(created);
		events.add(renamed);

		balance.loadFromEvents(events);

		assertEquals(balanceGuid, balance.guid());
		assertEquals(balanceName, balance.name());
	}

	@Test
	public void testLoadFromEvents_WhenTwoEntriesAreAdded() {
		final String balanceGuid = "123456789";
		final String balanceName = "General ledger";
		final String firstEntryDescription = "Dinner";
		final java.util.Date firstEntryCreationDate = new java.util.Date();
		final Money firstEntryAmount = Money.fromCents(3400);
		final String secondEntryDescription = "Book purchase";
		final java.util.Date secondEntryCreationDate = new java.util.Date();
		final Money secondEntryAmount = Money.fromCents(2780);
		List<BalanceEvent> events = new ArrayList<>();
		BalanceCreated created = new BalanceCreated(balanceGuid);
		BalanceRenamed renamed = new BalanceRenamed(balanceGuid, balanceName);
		BalanceEntryCreated firstEntryCreated = new BalanceEntryCreated(
				balanceGuid, IdGenerator.generateId(), firstEntryDescription,
				firstEntryCreationDate, firstEntryAmount);
		BalanceEntryCreated secondCreated = new BalanceEntryCreated(
				balanceGuid, IdGenerator.generateId(), secondEntryDescription,
				secondEntryCreationDate, secondEntryAmount);
		events.add(created);
		events.add(renamed);
		events.add(firstEntryCreated);
		events.add(secondCreated);

		balance.loadFromEvents(events);

		assertEquals(2, balance.entries().size());
		assertEquals(firstEntryDescription, ((BalanceEntry) balance.entries()
				.get(0)).description());
		assertEquals(firstEntryCreationDate, ((BalanceEntry) balance.entries()
				.get(0)).recordedAt());
		assertEquals(firstEntryAmount,
				((BalanceEntry) balance.entries().get(0)).amount());
		assertEquals(secondEntryDescription, ((BalanceEntry) balance.entries()
				.get(1)).description());
		assertEquals(secondEntryCreationDate, ((BalanceEntry) balance.entries()
				.get(1)).recordedAt());
		assertEquals(secondEntryAmount, ((BalanceEntry) balance.entries()
				.get(1)).amount());
	}

	@Test
	public void testLoadFromEvents_WhenOneEntryIsDeleted() {
		final String balanceGuid = "123456789";
		final String balanceName = "General ledger";
		final String firstEntryGuid = IdGenerator.generateId();
		final String secondEntryDescription = "Book purchase";
		final java.util.Date secondEntryCreationDate = new java.util.Date();
		final Money secondEntryAmount = Money.fromCents(2780);
		List<BalanceEvent> events = new ArrayList<>();
		BalanceCreated created = new BalanceCreated(balanceGuid);
		BalanceRenamed renamed = new BalanceRenamed(balanceGuid, balanceName);
		BalanceEntryCreated firstEntryCreated = new BalanceEntryCreated(
				balanceGuid, firstEntryGuid, "Dinner", new java.util.Date(),
				Money.fromCents(3400));
		BalanceEntryCreated secondCreated = new BalanceEntryCreated(
				balanceGuid, IdGenerator.generateId(), secondEntryDescription,
				secondEntryCreationDate, secondEntryAmount);
		BalanceEntryDeleted firstEntryDeleted = new BalanceEntryDeleted(
				balanceGuid, firstEntryGuid);
		events.add(created);
		events.add(renamed);
		events.add(firstEntryCreated);
		events.add(secondCreated);
		events.add(firstEntryDeleted);

		balance.loadFromEvents(events);

		assertEquals(1, balance.entries().size());
		assertEquals(secondEntryDescription, ((BalanceEntry) balance.entries()
				.get(0)).description());
		assertEquals(secondEntryCreationDate, ((BalanceEntry) balance.entries()
				.get(0)).recordedAt());
		assertEquals(secondEntryAmount, ((BalanceEntry) balance.entries()
				.get(0)).amount());
	}

}
