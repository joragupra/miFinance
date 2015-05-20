package com.tinyexpenses.balance.persistence;

import com.tinyexpenses.balance.BalanceCreated;
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

	private Map<Class, BalanceCreatedEventPersistenceHandler> eventPersistenceHandlers;

	public BalanceEventStore(SQLiteDatabase writableDb, SQLiteDatabase readableDb) {
		this.writableDb = writableDb;
		this.readableDb = readableDb;

		eventPersistenceHandlers = new HashMap<>();
		eventPersistenceHandlers.put(BalanceCreated.class, new BalanceCreatedEventPersistenceHandler(writableDb, readableDb));
	}

	public List<BalanceEvent> loadEvents(String aggregateId) {
		return null;
	}

	public void saveEvent(String aggregateId, BalanceEvent event) {
		BalanceCreatedEventPersistenceHandler handler = retrievePersistenceHandler(event.getClass());

		//TODO - find a better way to do this
		handler.addBalanceEvent((BalanceCreated) event);
	}

	private BalanceCreatedEventPersistenceHandler retrievePersistenceHandler(Class eventClass) {
		BalanceCreatedEventPersistenceHandler handler = eventPersistenceHandlers.get(eventClass);

		return handler;
	}

}
