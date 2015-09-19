package com.tinyexpenses.balance.persistence;

import org.junit.*;

import static org.junit.Assert.*;

public class PersistentBalanceEventTest {

	@Test
	public void testPersistentBalanceCreatedType() {
		assertEquals("BALANCE_CREATED",
				new PersistentBalanceCreated(null).eventType());
	}

	@Test
	public void testPersistentBalanceRenamedType() {
		assertEquals("BALANCE_RENAMED",
				new PersistentBalanceRenamed(null).eventType());
	}

	@Test
	public void testPersistentBalanceEntryCreatedType() {
		assertEquals("BALANCE_ENTRY_CREATED",
				new PersistentBalanceEntryCreated(null).eventType());
	}

	@Test
	public void testPersistentBalanceEntryUpdatedType() {
		assertEquals("BALANCE_ENTRY_UPDATED",
				new PersistentBalanceEntryUpdated(null).eventType());
	}

	@Test
	public void testPersistentBalanceEntryDeletedType() {
		assertEquals("BALANCE_ENTRY_DELETED",
				new PersistentBalanceEntryDeleted(null).eventType());
	}

}
