package com.tinyexpenses.balance.persistence;

import com.tinyexpenses.balance.BalanceEvent;
import com.tinyexpenses.balance.EventStore;
import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class BalanceEventStore implements EventStore<BalanceEvent> {

	private SQLiteDatabase writableDb;

	private SQLiteDatabase readableDb;

	private BalanceEventSavingHandler savingHandler;

	public BalanceEventStore(SQLiteDatabase writableDb, SQLiteDatabase readableDb) {
		this.writableDb = writableDb;
		this.readableDb = readableDb;

		savingHandler = new BalanceEventSavingHandler(writableDb);
	}

	public List<BalanceEvent> loadEvents(String aggregateId) {
		return null;
	}

	public void saveEvent(String aggregateId, BalanceEvent event) {
		PersistentBalanceEvent persistentEvent = PersistentBalanceEventFactory.from(event);
		persistentEvent.toBeSaved(savingHandler);
	}

}
