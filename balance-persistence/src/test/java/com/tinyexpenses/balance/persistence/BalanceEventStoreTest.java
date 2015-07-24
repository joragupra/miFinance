package com.tinyexpenses.balance.persistence;

import com.tinyexpenses.balance.BalanceEvent;
import com.tinyexpenses.balance.BalanceCreated;
import com.tinyexpenses.balance.BalanceRenamed;
import com.tinyexpenses.balance.BalanceEntryCreated;
import com.tinyexpenses.balance.BalanceEntryDeleted;
import com.tinyexpenses.balance.BalanceEntryUpdated;
import com.tinyexpenses.balance.Money;

import java.util.Date;
import java.util.HashMap;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.text.SimpleDateFormat;
import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;
import android.database.Cursor;

import org.junit.runner.*;
import org.junit.*;
import org.mockito.ArgumentMatcher;
import org.powermock.modules.junit4.PowerMockRunner;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.api.mockito.PowerMockito;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

@RunWith(PowerMockRunner.class)
@PrepareForTest({SQLiteDatabase.class})
public class BalanceEventStoreTest {

	private BalanceEventStore eventStore;

	private static final String TEST_BALANCE_ID = "565fd67e99c007d";
	private SQLiteDatabase mockDatabase;
	private Cursor singleResultCursor;

	@Before
	public void setUp() {
		this.mockDatabase = PowerMockito.mock(SQLiteDatabase.class);
		this.singleResultCursor = prepareDatabaseToReturnCursor();
		this.eventStore = new BalanceEventStore(mockDatabase, mockDatabase);
	}

	private Cursor prepareDatabaseToReturnCursor() {
		final String query = "select * from "
				+ BalanceEventStoreContract.DBEventStore.TABLE_NAME
				+ " where "
				+ BalanceEventStoreContract.DBEventStore.COLUMN_NAME_AGGREGATE_ID
				+ " = '" + TEST_BALANCE_ID + "'";
		Cursor cursor = mock(Cursor.class);
		when(cursor.getCount()).thenReturn(1);
		when(cursor.move(1)).thenReturn(true);
		when(mockDatabase.rawQuery(query, null)).thenReturn(cursor);
		return cursor;
	}

	@Test
	public void testSaveBalanceCreatedEvent() {
		final String newBalanceGuid = "92bfd847daf2a423gaffe43c";
		BalanceCreated event = new BalanceCreated(newBalanceGuid);

		eventStore.saveEvent(newBalanceGuid, event);

		verify(mockDatabase, times(1))
				.insert(eq(BalanceEventStoreContract.DBEventStore.TABLE_NAME),
						isNull(String.class),
						contentWithValues(new KeyValuePair[]{
								new KeyValuePair(
										BalanceEventStoreContract.DBEventStore.COLUMN_NAME_AGGREGATE_ID,
										newBalanceGuid),
								new KeyValuePair(
										BalanceEventStoreContract.DBEventStore.COLUMN_NAME_EVENT_TYPE,
										PersistentBalanceCreated.EVENT_TYPE)}));
	}

	@Test
	public void testSaveBalanceRenamedEvent() {
		final String balanceGuid = "92bfd847daf2a423gaffe43c";
		final String newBalanceName = "This is the new name";
		BalanceRenamed event = new BalanceRenamed(balanceGuid, newBalanceName);

		eventStore.saveEvent(balanceGuid, event);

		verify(mockDatabase, times(1))
				.insert(eq(BalanceEventStoreContract.DBEventStore.TABLE_NAME),
						isNull(String.class),
						contentWithValues(new KeyValuePair[]{
								new KeyValuePair(
										BalanceEventStoreContract.DBEventStore.COLUMN_NAME_AGGREGATE_ID,
										balanceGuid),
								new KeyValuePair(
										BalanceEventStoreContract.DBEventStore.COLUMN_NAME_EVENT_TYPE,
										PersistentBalanceRenamed.EVENT_TYPE),
								new KeyValuePair(
										BalanceEventStoreContract.DBEventStore.COLUMN_NAME_KEY_01,
										PersistentBalanceRenamed.NAME_COLUMN),
								new KeyValuePair(
										BalanceEventStoreContract.DBEventStore.COLUMN_NAME_DATA_01,
										newBalanceName)}));
	}

	@Test
	public void testSaveBalanceEntryCreatedEvent() {
		final String balanceGuid = "81f9d7f8c7c8a92fg";
		final String balanceEntryGuid = "7ccf234fg124ed23";
		final String balanceEntryDescription = "Lunch at Maxim's";
		final Date balanceEntryDate = new Date();
		final Money balanceEntryAmount = Money.fromCents(12845);
		BalanceEntryCreated event = new BalanceEntryCreated(balanceGuid,
				balanceEntryGuid, balanceEntryDescription, balanceEntryDate,
				balanceEntryAmount);

		eventStore.saveEvent(balanceGuid, event);

		verify(mockDatabase, times(1))
				.insert(eq(BalanceEventStoreContract.DBEventStore.TABLE_NAME),
						isNull(String.class),
						contentWithValues(new KeyValuePair[]{
								new KeyValuePair(
										BalanceEventStoreContract.DBEventStore.COLUMN_NAME_AGGREGATE_ID,
										balanceGuid),
								new KeyValuePair(
										BalanceEventStoreContract.DBEventStore.COLUMN_NAME_EVENT_TYPE,
										PersistentBalanceEntryCreated.EVENT_TYPE),
								new KeyValuePair(
										BalanceEventStoreContract.DBEventStore.COLUMN_NAME_KEY_01,
										PersistentBalanceEntryCreated.ENTRY_GUID_COLUMN),
								new KeyValuePair(
										BalanceEventStoreContract.DBEventStore.COLUMN_NAME_DATA_01,
										balanceEntryGuid),
								new KeyValuePair(
										BalanceEventStoreContract.DBEventStore.COLUMN_NAME_KEY_02,
										PersistentBalanceEntryCreated.DESCRIPTION_COLUMN),
								new KeyValuePair(
										BalanceEventStoreContract.DBEventStore.COLUMN_NAME_DATA_02,
										balanceEntryDescription),
								new KeyValuePair(
										BalanceEventStoreContract.DBEventStore.COLUMN_NAME_KEY_03,
										PersistentBalanceEntryCreated.DATE_COLUMN),
								new KeyValuePair(
										BalanceEventStoreContract.DBEventStore.COLUMN_NAME_DATA_03,
										new SimpleDateFormat("yyyy.MM.dd")
												.format(balanceEntryDate)),
								new KeyValuePair(
										BalanceEventStoreContract.DBEventStore.COLUMN_NAME_KEY_04,
										PersistentBalanceEntryCreated.AMOUNT_COLUMN),
								new KeyValuePair(
										BalanceEventStoreContract.DBEventStore.COLUMN_NAME_DATA_04,
										"" + balanceEntryAmount.cents())}));
	}

	@Test
	public void testSaveBalanceEntryUpdatedEvent() {
		final String balanceGuid = "81f9d7f8c7c8a92fg";
		final String balanceEntryGuid = "7ccf234fg124ed23";
		final String balanceEntryDescription = "Lunch at Maxim's";
		final Date balanceEntryDate = new Date();
		final Money balanceEntryAmount = Money.fromCents(12845);
		BalanceEntryUpdated event = new BalanceEntryUpdated(balanceGuid,
				balanceEntryGuid, balanceEntryDescription, balanceEntryDate,
				balanceEntryAmount);

		eventStore.saveEvent(balanceGuid, event);

		verify(mockDatabase, times(1))
				.insert(eq(BalanceEventStoreContract.DBEventStore.TABLE_NAME),
						isNull(String.class),
						contentWithValues(new KeyValuePair[]{
								new KeyValuePair(
										BalanceEventStoreContract.DBEventStore.COLUMN_NAME_AGGREGATE_ID,
										balanceGuid),
								new KeyValuePair(
										BalanceEventStoreContract.DBEventStore.COLUMN_NAME_EVENT_TYPE,
										PersistentBalanceEntryUpdated.EVENT_TYPE),
								new KeyValuePair(
										BalanceEventStoreContract.DBEventStore.COLUMN_NAME_KEY_01,
										PersistentBalanceEntryUpdated.ENTRY_GUID_COLUMN),
								new KeyValuePair(
										BalanceEventStoreContract.DBEventStore.COLUMN_NAME_DATA_01,
										balanceEntryGuid),
								new KeyValuePair(
										BalanceEventStoreContract.DBEventStore.COLUMN_NAME_KEY_02,
										PersistentBalanceEntryUpdated.DESCRIPTION_COLUMN),
								new KeyValuePair(
										BalanceEventStoreContract.DBEventStore.COLUMN_NAME_DATA_02,
										balanceEntryDescription),
								new KeyValuePair(
										BalanceEventStoreContract.DBEventStore.COLUMN_NAME_KEY_03,
										PersistentBalanceEntryUpdated.DATE_COLUMN),
								new KeyValuePair(
										BalanceEventStoreContract.DBEventStore.COLUMN_NAME_DATA_03,
										new SimpleDateFormat("yyyy.MM.dd")
												.format(balanceEntryDate)),
								new KeyValuePair(
										BalanceEventStoreContract.DBEventStore.COLUMN_NAME_KEY_04,
										PersistentBalanceEntryUpdated.AMOUNT_COLUMN),
								new KeyValuePair(
										BalanceEventStoreContract.DBEventStore.COLUMN_NAME_DATA_04,
										"" + balanceEntryAmount.cents())}));
	}

	@Test
	public void testSaveBalanceEntryDeletedEvent() {
		final String balanceGuid = "81f9d7f8c7c8a92fg";
		final String balanceEntryGuid = "7ccf234fg124ed23";
		BalanceEntryDeleted event = new BalanceEntryDeleted(balanceGuid,
				balanceEntryGuid);

		eventStore.saveEvent(balanceGuid, event);

		verify(mockDatabase, times(1))
				.insert(eq(BalanceEventStoreContract.DBEventStore.TABLE_NAME),
						isNull(String.class),
						contentWithValues(new KeyValuePair[]{
								new KeyValuePair(
										BalanceEventStoreContract.DBEventStore.COLUMN_NAME_AGGREGATE_ID,
										balanceGuid),
								new KeyValuePair(
										BalanceEventStoreContract.DBEventStore.COLUMN_NAME_EVENT_TYPE,
										PersistentBalanceEntryDeleted.EVENT_TYPE),
								new KeyValuePair(
										BalanceEventStoreContract.DBEventStore.COLUMN_NAME_KEY_01,
										PersistentBalanceEntryDeleted.ENTRY_GUID_COLUMN),
								new KeyValuePair(
										BalanceEventStoreContract.DBEventStore.COLUMN_NAME_DATA_01,
										balanceEntryGuid)}));
	}

	private class KeyValuePair {

		private String key;
		private String value;

		private KeyValuePair(String key, String value) {
			this.key = key;
			this.value = value;
		}

		private String key() {
			return this.key;
		}

		private String value() {
			return this.value;
		}

	}

	private ContentValues contentWithValues(final KeyValuePair[] keyValues) {
		return argThat(new ArgumentMatcher<ContentValues>() {
			@Override
			public boolean matches(Object arg) {
				ContentValues contentValues = (ContentValues) arg;
				for (KeyValuePair keyValue : keyValues) {
					if (!keyValue.value().equals(
							contentValues.get(keyValue.key()).toString())) {
						return false;
					}
				}
				return true;
			}
		});
	}

	@Test
	public void testLoadBalanceCreatedEvent() {
		prepareBalanceCreatedRowReturned(singleResultCursor);

		List<BalanceEvent> events = eventStore.loadEvents(TEST_BALANCE_ID);

		assertEquals(1, events.size());
		assertEquals(BalanceCreated.class, events.get(0).getClass());
		assertEquals(TEST_BALANCE_ID, events.get(0).balanceGuid());
	}

	@Test
	public void testLoadBalanceRenamedEvent() {
		final String newBalanceName = "General ledger";
		prepareBalanceRenamedRowReturned(singleResultCursor, newBalanceName);

		List<BalanceEvent> events = eventStore.loadEvents(TEST_BALANCE_ID);

		assertEquals(1, events.size());
		assertEquals(BalanceRenamed.class, events.get(0).getClass());
		assertEquals(TEST_BALANCE_ID, events.get(0).balanceGuid());
		assertEquals(newBalanceName, ((BalanceRenamed) events.get(0)).name());
	}

	@Test
	public void testLoadBalanceEntryCreatedEvent() {
		final String balanceEntryGuid = "5cdf114fg14efd23";
		final String balanceEntryDescription = "Dinner at Maxim's";
		final Date balanceEntryDate = new Date();
		final Money balanceEntryAmount = Money.fromCents(12845);
		prepareBalanceEntryCreatedRowReturned(singleResultCursor,
				balanceEntryGuid, balanceEntryDescription, balanceEntryDate,
				balanceEntryAmount);

		List<BalanceEvent> events = eventStore.loadEvents(TEST_BALANCE_ID);

		assertEquals(1, events.size());
		assertEquals(BalanceEntryCreated.class, events.get(0).getClass());
		assertEquals(TEST_BALANCE_ID, events.get(0).balanceGuid());
		assertEquals(balanceEntryGuid,
				((BalanceEntryCreated) events.get(0)).entryGuid());
		assertEquals(balanceEntryDescription,
				((BalanceEntryCreated) events.get(0)).entryDescription());
		assertEquals(balanceEntryDate.getYear(),
				((BalanceEntryCreated) events.get(0)).creationDate().getYear());
		assertEquals(balanceEntryDate.getMonth(),
				((BalanceEntryCreated) events.get(0)).creationDate().getMonth());
		assertEquals(balanceEntryDate.getDay(),
				((BalanceEntryCreated) events.get(0)).creationDate().getDay());
		assertEquals(balanceEntryAmount.cents(),
				((BalanceEntryCreated) events.get(0)).amount().cents());
	}

	@Test
	public void testLoadBalanceEntryDeletedEvent() {
		final String balanceEntryGuid = "5cdf114fg14efd23";
		prepareBalanceEntryDeletedRowReturned(singleResultCursor,
				balanceEntryGuid);

		List<BalanceEvent> events = eventStore.loadEvents(TEST_BALANCE_ID);

		assertEquals(1, events.size());
		assertEquals(BalanceEntryDeleted.class, events.get(0).getClass());
		assertEquals(TEST_BALANCE_ID, events.get(0).balanceGuid());
		assertEquals(balanceEntryGuid,
				((BalanceEntryDeleted) events.get(0)).entryGuid());
	}

	@Test
	public void testLoadBalanceEntryUpdatedEvent() {
		final String balanceEntryGuid = "5cdf114fg14efd23";
		final String balanceEntryDescription = "Dinner at Maxim's modified";
		final Date balanceEntryDate = new Date();
		final Money balanceEntryAmount = Money.fromCents(12845);
		prepareBalanceEntryUpdatedRowReturned(singleResultCursor,
				balanceEntryGuid, balanceEntryDescription, balanceEntryDate,
				balanceEntryAmount);

		List<BalanceEvent> events = eventStore.loadEvents(TEST_BALANCE_ID);

		assertEquals(1, events.size());
		assertEquals(BalanceEntryUpdated.class, events.get(0).getClass());
		assertEquals(TEST_BALANCE_ID, events.get(0).balanceGuid());
		assertEquals(balanceEntryGuid,
				((BalanceEntryUpdated) events.get(0)).entryGuid());
	}

	private void prepareBalanceCreatedRowReturned(Cursor mockedCursor) {
		when(mockedCursor.getString(1)).thenReturn(TEST_BALANCE_ID);
		when(mockedCursor.getString(2)).thenReturn("BALANCE_CREATED");
	}

	private void prepareBalanceRenamedRowReturned(Cursor mockedCursor,
			String newBalanceName) {
		when(mockedCursor.getString(1)).thenReturn(TEST_BALANCE_ID);
		when(mockedCursor.getString(2)).thenReturn(
				PersistentBalanceRenamed.EVENT_TYPE);
		when(mockedCursor.getString(3)).thenReturn(
				PersistentBalanceRenamed.NAME_COLUMN);
		when(mockedCursor.getString(4)).thenReturn(newBalanceName);
	}

	private void prepareBalanceEntryCreatedRowReturned(Cursor mockedCursor,
			String newEntryGuid, String newEntryDescription, Date newEntryDate,
			Money newEntryAmount) {
		when(mockedCursor.getString(1)).thenReturn(TEST_BALANCE_ID);
		when(mockedCursor.getString(2)).thenReturn(
				PersistentBalanceEntryCreated.EVENT_TYPE);
		when(mockedCursor.getString(3)).thenReturn(
				PersistentBalanceEntryCreated.ENTRY_GUID_COLUMN);
		when(mockedCursor.getString(4)).thenReturn(newEntryGuid);
		when(mockedCursor.getString(5)).thenReturn(
				PersistentBalanceEntryCreated.DESCRIPTION_COLUMN);
		when(mockedCursor.getString(6)).thenReturn(newEntryDescription);
		when(mockedCursor.getString(7)).thenReturn(
				PersistentBalanceEntryCreated.DATE_COLUMN);
		when(mockedCursor.getString(8)).thenReturn(
				new SimpleDateFormat("yyyy.MM.dd").format(newEntryDate));
		when(mockedCursor.getString(9)).thenReturn(
				PersistentBalanceEntryCreated.AMOUNT_COLUMN);
		when(mockedCursor.getString(10)).thenReturn(
				new Long(newEntryAmount.cents()).toString());
	}

	private void prepareBalanceEntryDeletedRowReturned(Cursor mockedCursor,
			String entryGuid) {
		when(mockedCursor.getString(1)).thenReturn(TEST_BALANCE_ID);
		when(mockedCursor.getString(2)).thenReturn(
				PersistentBalanceEntryDeleted.EVENT_TYPE);
		when(mockedCursor.getString(3)).thenReturn(
				PersistentBalanceEntryDeleted.ENTRY_GUID_COLUMN);
		when(mockedCursor.getString(4)).thenReturn(entryGuid);
	}

	private void prepareBalanceEntryUpdatedRowReturned(Cursor mockedCursor,
			String entryGuid, String entryDescription, Date entryDate,
			Money entryAmount) {
		when(mockedCursor.getString(1)).thenReturn(TEST_BALANCE_ID);
		when(mockedCursor.getString(2)).thenReturn(
				PersistentBalanceEntryUpdated.EVENT_TYPE);
		when(mockedCursor.getString(3)).thenReturn(
				PersistentBalanceEntryUpdated.ENTRY_GUID_COLUMN);
		when(mockedCursor.getString(4)).thenReturn(entryGuid);
		when(mockedCursor.getString(5)).thenReturn(
				PersistentBalanceEntryUpdated.DESCRIPTION_COLUMN);
		when(mockedCursor.getString(6)).thenReturn(entryDescription);
		when(mockedCursor.getString(7)).thenReturn(
				PersistentBalanceEntryUpdated.DATE_COLUMN);
		when(mockedCursor.getString(8)).thenReturn(
				new SimpleDateFormat("yyyy.MM.dd").format(entryDate));
		when(mockedCursor.getString(9)).thenReturn(
				PersistentBalanceEntryUpdated.AMOUNT_COLUMN);
		when(mockedCursor.getString(10)).thenReturn(
				new Long(entryAmount.cents()).toString());
	}

}
