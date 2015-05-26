package com.tinyexpenses.balance.persistence;

import com.tinyexpenses.balance.BalanceCreated;
import com.tinyexpenses.balance.BalanceEntryCreated;
import com.tinyexpenses.balance.BalanceEntryDeleted;
import com.tinyexpenses.balance.BalanceEntryUpdated;
import com.tinyexpenses.balance.BalanceRenamed;
import com.tinyexpenses.balance.Money;

import android.database.sqlite.SQLiteDatabase;
import java.text.ParseException;
import java.text.SimpleDateFormat;

import static com.tinyexpenses.balance.persistence.BalanceEventStoreContract.DBEventStore.*;

/**
* Visitor for balance event loading operation.
*/
class BalanceEventLoadingHandler {

    private SQLiteDatabase readableDb;

    BalanceEventLoadingHandler(SQLiteDatabase readableDb) {
        this.readableDb = readableDb;
    }

    BalanceCreated load(RawBalanceCreated rawEvent) {
        return new BalanceCreated(rawEvent.valueInColumn(COLUMN_NAME_AGGREGATE_ID));
    }

    BalanceRenamed load(RawBalanceRenamed rawEvent) {
        return new BalanceRenamed(rawEvent.valueInColumn(COLUMN_NAME_AGGREGATE_ID), rawEvent.valueInColumn(PersistentBalanceRenamed.NAME_COLUMN));
    }

    BalanceEntryCreated load(RawBalanceEntryCreated rawEvent) {
        try {
            return new BalanceEntryCreated(rawEvent.valueInColumn(COLUMN_NAME_AGGREGATE_ID), rawEvent.valueInColumn(COLUMN_NAME_DATA_01), rawEvent.valueInColumn(COLUMN_NAME_DATA_02), new SimpleDateFormat("yyyy.MM.dd").parse(rawEvent.valueInColumn(COLUMN_NAME_DATA_03)), Money.fromCents(Long.parseLong(rawEvent.valueInColumn(COLUMN_NAME_DATA_04))));
        } catch(ParseException e) {
            return null;
        }
    }

    BalanceEntryDeleted load(RawBalanceEntryDeleted rawEvent) {
        return new BalanceEntryDeleted(rawEvent.valueInColumn(COLUMN_NAME_AGGREGATE_ID), rawEvent.valueInColumn(COLUMN_NAME_DATA_01));
    }

    BalanceEntryUpdated load(RawBalanceEntryUpdated rawEvent) {
        try {
            return new BalanceEntryUpdated(rawEvent.valueInColumn(COLUMN_NAME_AGGREGATE_ID), rawEvent.valueInColumn(COLUMN_NAME_DATA_01), rawEvent.valueInColumn(COLUMN_NAME_DATA_02), new SimpleDateFormat("yyyy.MM.dd").parse(rawEvent.valueInColumn(COLUMN_NAME_DATA_03)), Money.fromCents(Long.parseLong(rawEvent.valueInColumn(COLUMN_NAME_DATA_04))));
        } catch(ParseException e) {
            return null;
        }
    }

}
