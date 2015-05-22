package com.tinyexpenses.balance.persistence;

import com.tinyexpenses.balance.BalanceCreated;
import com.tinyexpenses.balance.BalanceRenamed;

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
}
