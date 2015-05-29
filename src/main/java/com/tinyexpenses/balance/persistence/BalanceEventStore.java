package com.tinyexpenses.balance.persistence;

import com.tinyexpenses.balance.BalanceEvent;
import com.tinyexpenses.balance.EventStore;
import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.HashMap;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class BalanceEventStore implements EventStore<BalanceEvent> {

	private SQLiteDatabase writableDb;

	private SQLiteDatabase readableDb;

	private BalanceEventLoadingHandler loadingHandler;
	private BalanceEventSavingHandler savingHandler;

	public BalanceEventStore(SQLiteDatabase writableDb,
			SQLiteDatabase readableDb) {
		this.writableDb = writableDb;
		this.readableDb = readableDb;

		loadingHandler = new BalanceEventLoadingHandler(readableDb);
		savingHandler = new BalanceEventSavingHandler(writableDb);
	}

	void setSavingHandler(BalanceEventSavingHandler savingHandler) {
		this.savingHandler = savingHandler;
	}

	public List<BalanceEvent> loadEvents(String aggregateId) {
		List<BalanceEvent> events = new ArrayList<>();
		Cursor c = readableDb.rawQuery("select * from "
				+ BalanceEventStoreContract.DBEventStore.TABLE_NAME, null);
		for (int i = 0; i < c.getCount(); i++) {
			boolean moved = c.move(1);
			if (moved) {
				String aggregateGuid = c.getString(1);
				String eventType = c.getString(2);

				int offset = 3;
				Map<String, String> keyValue = new HashMap();
				keyValue.put(
						BalanceEventStoreContract.DBEventStore.COLUMN_NAME_AGGREGATE_ID,
						aggregateGuid);
				// TODO - make this beautiful
				for (int j = 0; j < 7; j += 2) {
					String key = c.getString(offset + j);
					String val = c.getString(offset + j + 1);
					keyValue.put(key, val);
				}
				RawBalanceEvent rawEvent = create(eventType, keyValue);
				events.add(rawEvent.loadedBy(loadingHandler));
			}
		}
		return events;
	}

	// TODO - move this factory method to an external class
	private RawBalanceEvent create(String eventType,
			Map<String, String> keyValue) {
		if (eventType.equals(PersistentBalanceCreated.EVENT_TYPE)) {
			return new RawBalanceCreated(keyValue);
		}
		if (eventType.equals(PersistentBalanceRenamed.EVENT_TYPE)) {
			return new RawBalanceRenamed(keyValue);
		}
		if (eventType.equals(PersistentBalanceEntryCreated.EVENT_TYPE)) {
			return new RawBalanceEntryCreated(keyValue);
		}
		if (eventType.equals(PersistentBalanceEntryUpdated.EVENT_TYPE)) {
			return new RawBalanceEntryUpdated(keyValue);
		}
		if (eventType.equals(PersistentBalanceEntryDeleted.EVENT_TYPE)) {
			return new RawBalanceEntryDeleted(keyValue);
		}
		return null;
	}

	public void saveEvent(String aggregateId, BalanceEvent event) {
		PersistentBalanceEvent persistentEvent = PersistentBalanceEventFactory
				.from(event);
		persistentEvent.toBeSaved(savingHandler);
	}

}
