package com.tinyexpenses.balance;

import org.junit.*;
import java.util.Date;
import java.util.List;

import static org.junit.Assert.*;

public class BalanceServiceTest {

	private BalanceService service;

	@Before
	public void setUp() {
		service = new BalanceService();
	}

	@After
	public void tearDown() {
		BalanceEventStream.getInstance().forgetAllEvents();
	}

	@Test
	public void testOpenNewBalance() {
		final String balanceName = "General ledger";

		final long balanceId = service.openNewBalance(balanceName);

		Balance balance = service.retrieveBalance(balanceId);

		assertEquals(balanceId, balance.id());
		assertEquals(balanceName, balance.name());
	}

	@Test
	public void testAddEntry() {
		final String entryDescription = "Dinner with friends";
		final Date entryDate = new Date();
		final long entryAmountCents = 2500L;
		final long balanceId = service.openNewBalance("General ledger");

		service.addEntry(balanceId, entryDescription, entryDate,
				entryAmountCents);

		Balance balance = service.retrieveBalance(balanceId);

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
		final long balanceId = service.openNewBalance("General ledger");
		service.addEntry(balanceId, "Dinner with friends", new Date(), 2500L);
		final String entryGuid = service.retrieveBalance(balanceId).entries()
				.get(0).guid();

		service.updateEntry(balanceId, entryGuid, modifiedEntryDescription,
				new Date(), modifiedAmountCents);

		Balance balance = service.retrieveBalance(balanceId);

		assertEquals(1, balance.entries().size());
		assertEquals(modifiedEntryDescription, balance.entries().get(0)
				.description());
		assertEquals(modifiedAmountCents, balance.entries().get(0).amount()
				.cents());
	}

	@Test
	public void testDeleteEntry() {
		final long balanceId = service.openNewBalance("General ledger");
		service.addEntry(balanceId, "Dinner with friends", new Date(), 2500L);
		final String entryGuid = service.retrieveBalance(balanceId).entries()
				.get(0).guid();

		service.deleteEntry(balanceId, entryGuid);

		Balance balance = service.retrieveBalance(balanceId);

		assertEquals(0, balance.entries().size());
	}

	@Test
	public void testDeleteAllEntries() {
		final long balanceId = service.openNewBalance("General ledger");
		service.addEntry(balanceId, "Dinner with friends", new Date(), 2500L);
		service.addEntry(balanceId, "Lunch at work", new Date(), 1250L);
		service.addEntry(balanceId, "Magazine", new Date(), 490L);

		service.deleteAllEntries(balanceId);

		Balance balance = service.retrieveBalance(balanceId);

		assertEquals(0, balance.entries().size());
	}

}
