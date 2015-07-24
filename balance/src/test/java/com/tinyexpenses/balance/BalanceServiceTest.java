package com.tinyexpenses.balance;

import org.junit.*;
import java.util.Date;
import java.util.List;

import static org.junit.Assert.*;

public class BalanceServiceTest {

	private BalanceService service;
	private InMemoryEventStore eventStore = new InMemoryEventStore();

	@Before
	public void setUp() {
		service = new BalanceService(eventStore);
	}

	@Test
	public void testRetrieveBalance_WhenNoBalanceExists() {
		Balance balance = service.retrieveBalance();

		assertNotNull(balance);
	}

	@Test
	public void testRetrieveBalance_WhenOneBalanceAlreadyExists() {
		final String balanceName = "Test balance";
		final String balanceGuid = service.openNewBalance(balanceName);

		Balance balance = service.retrieveBalance();

		assertNotNull(balance);
		assertEquals(balanceGuid, balance.guid());
		assertEquals(balanceName, balance.name());
	}

	@Test
	public void testOpenNewBalance() {
		final String balanceName = "General ledger";

		final String balanceGuid = service.openNewBalance(balanceName);

		Balance balance = service.retrieveBalance(balanceGuid);

		assertEquals(balanceGuid, balance.guid());
		assertEquals(balanceName, balance.name());
	}

	@Test
	public void testAddEntry() {
		final String entryDescription = "Dinner with friends";
		final Date entryDate = new Date();
		final long entryAmountCents = 2500L;
		final String balanceGuid = service.openNewBalance("General ledger");

		service.addEntry(balanceGuid, entryDescription, entryDate,
				entryAmountCents);

		Balance balance = service.retrieveBalance(balanceGuid);

		assertEquals(1, balance.entries().size());
		assertEquals(entryDescription, balance.entries().get(0).description());
		assertEquals(entryDate, balance.entries().get(0).recordedAt());
		assertEquals(entryAmountCents, balance.entries().get(0).amount()
				.cents());
	}

	@Test
	public void testUpdateEntry() {
		final String modifiedEntryDescription = "Lunch with friends";
		final long modifiedAmountCents = 1895L;
		final String balanceGuid = service.openNewBalance("General ledger");
		service.addEntry(balanceGuid, "Dinner with friends", new Date(), 2500L);
		final String entryGuid = service.retrieveBalance(balanceGuid).entries()
				.get(0).guid();

		service.updateEntry(balanceGuid, entryGuid, modifiedEntryDescription,
				new Date(), modifiedAmountCents);

		Balance balance = service.retrieveBalance(balanceGuid);

		assertEquals(1, balance.entries().size());
		assertEquals(modifiedEntryDescription, balance.entries().get(0)
				.description());
		assertEquals(modifiedAmountCents, balance.entries().get(0).amount()
				.cents());
	}

	@Test
	public void testDeleteEntry() {
		final String balanceGuid = service.openNewBalance("General ledger");
		service.addEntry(balanceGuid, "Dinner with friends", new Date(), 2500L);
		final String entryGuid = service.retrieveBalance(balanceGuid).entries()
				.get(0).guid();

		service.deleteEntry(balanceGuid, entryGuid);

		Balance balance = service.retrieveBalance(balanceGuid);

		assertEquals(0, balance.entries().size());
	}

	@Test
	public void testDeleteAllEntries() {
		final String balanceGuid = service.openNewBalance("General ledger");
		service.addEntry(balanceGuid, "Dinner with friends", new Date(), 2500L);
		service.addEntry(balanceGuid, "Lunch at work", new Date(), 1250L);
		service.addEntry(balanceGuid, "Magazine", new Date(), 490L);

		service.deleteAllEntries(balanceGuid);

		Balance balance = service.retrieveBalance(balanceGuid);

		assertEquals(0, balance.entries().size());
	}

}
