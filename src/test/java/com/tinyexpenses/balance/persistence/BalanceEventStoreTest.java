package com.tinyexpenses.balance.persistence;

import com.tinyexpenses.balance.BalanceCreated;
import com.tinyexpenses.balance.BalanceRenamed;
import com.tinyexpenses.balance.BalanceEntryCreated;
import com.tinyexpenses.balance.BalanceEntryDeleted;
import com.tinyexpenses.balance.BalanceEntryUpdated;
import com.tinyexpenses.balance.Money;

import java.util.Date;

import org.junit.*;
import org.mockito.ArgumentMatcher;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

public class BalanceEventStoreTest {

	private BalanceEventStore eventStore;

	private BalanceEventSavingHandler savingHandler;

	@Before
	public void setUp() {
		this.savingHandler = mock(BalanceEventSavingHandler.class);
		this.eventStore = new BalanceEventStore(null, null, savingHandler);
	}

	@Test
	public void testSaveBalanceCreatedEvent() {
		final String newBalanceGuid = "92bfd847daf2a423gaffe43c";
		BalanceCreated event = new BalanceCreated(newBalanceGuid);

		eventStore.saveEvent(newBalanceGuid, event);

		verify(savingHandler, times(1)).save(aPersistentBalanceCreatedEventWithGuid(newBalanceGuid));
	}

	private PersistentBalanceCreated aPersistentBalanceCreatedEventWithGuid(final String guid) {
		return argThat(
			new ArgumentMatcher<PersistentBalanceCreated>() {
				@Override
				public boolean matches(Object arg) {
					PersistentBalanceCreated persistentEvent = (PersistentBalanceCreated) arg;
					return persistentEvent.event().balanceGuid().equals(guid);
				}
			}
		);
	}

	@Test
	public void testSaveBalanceRenamedEvent() {
		final String balanceGuid = "92bfd847daf2a423gaffe43c";
		final String newBalanceName = "This is the new name";
		BalanceRenamed event = new BalanceRenamed(balanceGuid, newBalanceName);

		eventStore.saveEvent(balanceGuid, event);

		verify(savingHandler, times(1)).save(aPersistentBalanceRenamedEventWith(balanceGuid, newBalanceName));
	}

	private PersistentBalanceRenamed aPersistentBalanceRenamedEventWith(final String guid, final String newName) {
		return argThat(
			new ArgumentMatcher<PersistentBalanceRenamed>() {
				@Override
				public boolean matches(Object arg) {
					PersistentBalanceRenamed persistentEvent = (PersistentBalanceRenamed) arg;
					return persistentEvent.event().balanceGuid().equals(guid) && persistentEvent.event().name().equals(newName);
				}
			}
		);
	}

	@Test
	public void testSaveBalanceEntryCreatedEvent() {
		final String balanceGuid = "81f9d7f8c7c8a92fg";
		final String balanceEntryGuid = "7ccf234fg124ed23";
		final String balanceEntryDescription = "Lunch at Maxim's";
		final Date balanceEntryDate = new Date();
		final Money balanceEntryAmount = Money.fromCents(12845);
		BalanceEntryCreated event = new BalanceEntryCreated(balanceGuid, balanceEntryGuid, balanceEntryDescription, balanceEntryDate, balanceEntryAmount);

		eventStore.saveEvent(balanceGuid, event);

		verify(savingHandler, times(1)).save(aPersistentBalanceEntryCreatedEventWith(balanceGuid, balanceEntryGuid, balanceEntryDescription, balanceEntryDate, balanceEntryAmount));
	}

	private PersistentBalanceEntryCreated aPersistentBalanceEntryCreatedEventWith(final String balanceGuid, final String balanceEntryGuid, final String entryDescription, final Date entryDate, final Money entryAmount) {
		return argThat(
			new ArgumentMatcher<PersistentBalanceEntryCreated>() {
				@Override
				public boolean matches(Object arg) {
					PersistentBalanceEntryCreated persistentEvent = (PersistentBalanceEntryCreated) arg;
					return persistentEvent.event().balanceGuid().equals(balanceGuid) && persistentEvent.event().entryGuid().equals(balanceEntryGuid) && persistentEvent.event().entryDescription().equals(entryDescription) && persistentEvent.event().creationDate().equals(entryDate) && persistentEvent.event().amount().equals(entryAmount);
				}
			}
		);
	}

	@Test
	public void testSaveBalanceEntryUpdatedEvent() {
		final String balanceGuid = "81f9d7f8c7c8a92fg";
		final String balanceEntryGuid = "7ccf234fg124ed23";
		final String balanceEntryDescription = "Lunch at Maxim's";
		final Date balanceEntryDate = new Date();
		final Money balanceEntryAmount = Money.fromCents(12845);
		BalanceEntryUpdated event = new BalanceEntryUpdated(balanceGuid, balanceEntryGuid, balanceEntryDescription, balanceEntryDate, balanceEntryAmount);

		eventStore.saveEvent(balanceGuid, event);

		verify(savingHandler, times(1)).save(aPersistentBalanceEntryUpdatedEventWith(balanceGuid, balanceEntryGuid, balanceEntryDescription, balanceEntryDate, balanceEntryAmount));
	}

	private PersistentBalanceEntryUpdated aPersistentBalanceEntryUpdatedEventWith(final String balanceGuid, final String balanceEntryGuid, final String entryDescription, final Date entryDate, final Money entryAmount) {
		return argThat(
			new ArgumentMatcher<PersistentBalanceEntryUpdated>() {
				@Override
				public boolean matches(Object arg) {
					PersistentBalanceEntryUpdated persistentEvent = (PersistentBalanceEntryUpdated) arg;
					return persistentEvent.event().balanceGuid().equals(balanceGuid) && persistentEvent.event().entryGuid().equals(balanceEntryGuid) && persistentEvent.event().entryDescription().equals(entryDescription) && persistentEvent.event().creationDate().equals(entryDate) && persistentEvent.event().amount().equals(entryAmount);
				}
			}
		);
	}

	@Test
	public void testSaveBalanceEntryDeletedEvent() {
		final String balanceGuid = "81f9d7f8c7c8a92fg";
		final String balanceEntryGuid = "7ccf234fg124ed23";
		BalanceEntryDeleted event = new BalanceEntryDeleted(balanceGuid, balanceEntryGuid);

		eventStore.saveEvent(balanceGuid, event);

		verify(savingHandler, times(1)).save(aPersistentBalanceEntryDeletedEventWith(balanceGuid, balanceEntryGuid));
	}

	private PersistentBalanceEntryDeleted aPersistentBalanceEntryDeletedEventWith(final String balanceGuid, final String balanceEntryGuid) {
		return argThat(
		new ArgumentMatcher<PersistentBalanceEntryDeleted>() {
			@Override
			public boolean matches(Object arg) {
				PersistentBalanceEntryDeleted persistentEvent = (PersistentBalanceEntryDeleted) arg;
				return persistentEvent.event().balanceGuid().equals(balanceGuid) && persistentEvent.event().entryGuid().equals(balanceEntryGuid);
			}
		}
		);
	}

}
