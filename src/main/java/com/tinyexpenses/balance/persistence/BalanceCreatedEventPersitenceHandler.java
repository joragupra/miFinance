package com.tinyexpenses.balance.persistence;

import com.tinyexpenses.balance.BalanceCreated;
import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;

import static com.tinyexpenses.balance.persistence.BalanceEventStoreContract.DBEventStore.*;

class BalanceCreatedEventPersistenceHandler {

    private SQLiteDatabase writableDb;

    private SQLiteDatabase readableDb;

    private static final String EVENT_TYPE = "BALANCE_CREATED";

    BalanceCreatedEventPersistenceHandler(SQLiteDatabase writableDb, SQLiteDatabase readableDb) {
        this.writableDb = writableDb;
        this.readableDb = readableDb;
    }

    void addBalanceEvent(BalanceCreated event) {
        ContentValues values = new ContentValues();
        values.put(COLUMN_NAME_AGGREGATE_ID, event.balanceGuid());
        values.put(COLUMN_NAME_EVENT_TYPE, EVENT_TYPE);
        writableDb.insert(TABLE_NAME, null, values);
    }

}
