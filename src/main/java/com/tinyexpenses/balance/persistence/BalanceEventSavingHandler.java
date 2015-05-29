package com.tinyexpenses.balance.persistence;

import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;
import java.text.SimpleDateFormat;

import static com.tinyexpenses.balance.persistence.BalanceEventStoreContract.DBEventStore.*;

/**
 * Visitor for balance event saving operation.
 */
class BalanceEventSavingHandler {

    private SQLiteDatabase writableDb;

    BalanceEventSavingHandler(SQLiteDatabase writableDb) {
        this.writableDb = writableDb;
    }

    void save(PersistentBalanceCreated persistentEvent) {
        ContentValues values = new ContentValues();
        values.put(COLUMN_NAME_AGGREGATE_ID, persistentEvent.event().balanceGuid());
        values.put(COLUMN_NAME_EVENT_TYPE, persistentEvent.eventType());
        writableDb.insert(TABLE_NAME, null, values);
    }

    void save(PersistentBalanceRenamed persistentEvent) {
        ContentValues values = new ContentValues();
        values.put(COLUMN_NAME_AGGREGATE_ID, persistentEvent.event().balanceGuid());
        values.put(COLUMN_NAME_EVENT_TYPE, persistentEvent.eventType());
        values.put(COLUMN_NAME_KEY_01, PersistentBalanceRenamed.NAME_COLUMN);
        values.put(COLUMN_NAME_DATA_01, persistentEvent.event().name());
        writableDb.insert(TABLE_NAME, null, values);
    }

    void save(PersistentBalanceEntryCreated persistentEvent) {
        ContentValues values = new ContentValues();
        values.put(COLUMN_NAME_AGGREGATE_ID, persistentEvent.event().balanceGuid());
        values.put(COLUMN_NAME_EVENT_TYPE, persistentEvent.eventType());
        values.put(COLUMN_NAME_KEY_01, PersistentBalanceEntryCreated.ENTRY_GUID_COLUMN);
        values.put(COLUMN_NAME_DATA_01, persistentEvent.event().entryGuid());
        values.put(COLUMN_NAME_KEY_02, PersistentBalanceEntryCreated.DESCRIPTION_COLUMN);
        values.put(COLUMN_NAME_DATA_02, persistentEvent.event().entryDescription());
        values.put(COLUMN_NAME_KEY_03, PersistentBalanceEntryCreated.DATE_COLUMN);
        values.put(COLUMN_NAME_DATA_03, new SimpleDateFormat("yyyy.MM.dd").format(persistentEvent.event().creationDate()));
        values.put(COLUMN_NAME_KEY_04, PersistentBalanceEntryCreated.AMOUNT_COLUMN);
        values.put(COLUMN_NAME_DATA_04, persistentEvent.event().amount().cents());
        writableDb.insert(TABLE_NAME, null, values);
    }

    void save(PersistentBalanceEntryDeleted persistentEvent) {
        ContentValues values = new ContentValues();
        values.put(COLUMN_NAME_AGGREGATE_ID, persistentEvent.event().balanceGuid());
        values.put(COLUMN_NAME_EVENT_TYPE, persistentEvent.eventType());
        values.put(COLUMN_NAME_KEY_01, "entryGuid");
        values.put(COLUMN_NAME_DATA_01, persistentEvent.event().entryGuid());
        writableDb.insert(TABLE_NAME, null, values);
    }

    void save(PersistentBalanceEntryUpdated persistentEvent) {
        ContentValues values = new ContentValues();
        values.put(COLUMN_NAME_AGGREGATE_ID, persistentEvent.event().balanceGuid());
        values.put(COLUMN_NAME_EVENT_TYPE, persistentEvent.eventType());
        values.put(COLUMN_NAME_KEY_01, PersistentBalanceEntryUpdated.ENTRY_GUID_COLUMN);
        values.put(COLUMN_NAME_DATA_01, persistentEvent.event().entryGuid());

        values.put(COLUMN_NAME_KEY_02, PersistentBalanceEntryUpdated.DESCRIPTION_COLUMN);
        values.put(COLUMN_NAME_DATA_02, persistentEvent.event().entryDescription());

        values.put(COLUMN_NAME_KEY_03, PersistentBalanceEntryUpdated.DATE_COLUMN);
        values.put(COLUMN_NAME_DATA_03, new SimpleDateFormat("yyyy.MM.dd").format(persistentEvent.event().creationDate()));

        values.put(COLUMN_NAME_KEY_04, PersistentBalanceEntryUpdated.AMOUNT_COLUMN);
        values.put(COLUMN_NAME_DATA_04, persistentEvent.event().amount().cents());


        writableDb.insert(TABLE_NAME, null, values);
    }

}
