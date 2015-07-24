package com.tinyexpenses.balance.persistence;

import android.database.sqlite.SQLiteDatabase;

public final class EventStoreDatabaseHelper {

	private static final String TEXT_TYPE = " TEXT";

	private static final String INTEGER_TYPE = " INTEGER";

	private static final String PRIMARY_KEY = " PRIMARY KEY";

	private static final String DEFAULT = " DEFAULT";

	private static final String COMMA_SEP = ",";

	private static final String SQL_CREATE_EVENT_TABLE = "CREATE TABLE "
			+ BalanceEventStoreContract.DBEventStore.TABLE_NAME + " ("
			+ BalanceEventStoreContract.DBEventStore.COLUMN_NAME_ID
			+ INTEGER_TYPE + PRIMARY_KEY + COMMA_SEP
			+ BalanceEventStoreContract.DBEventStore.COLUMN_NAME_AGGREGATE_ID
			+ TEXT_TYPE + COMMA_SEP
			+ BalanceEventStoreContract.DBEventStore.COLUMN_NAME_EVENT_TYPE
			+ TEXT_TYPE + COMMA_SEP
			+ BalanceEventStoreContract.DBEventStore.COLUMN_NAME_KEY_01
			+ TEXT_TYPE + COMMA_SEP
			+ BalanceEventStoreContract.DBEventStore.COLUMN_NAME_DATA_01
			+ TEXT_TYPE + COMMA_SEP
			+ BalanceEventStoreContract.DBEventStore.COLUMN_NAME_KEY_02
			+ TEXT_TYPE + COMMA_SEP
			+ BalanceEventStoreContract.DBEventStore.COLUMN_NAME_DATA_02
			+ TEXT_TYPE + COMMA_SEP
			+ BalanceEventStoreContract.DBEventStore.COLUMN_NAME_KEY_03
			+ TEXT_TYPE + COMMA_SEP
			+ BalanceEventStoreContract.DBEventStore.COLUMN_NAME_DATA_03
			+ TEXT_TYPE + COMMA_SEP
			+ BalanceEventStoreContract.DBEventStore.COLUMN_NAME_KEY_04
			+ TEXT_TYPE + COMMA_SEP
			+ BalanceEventStoreContract.DBEventStore.COLUMN_NAME_DATA_04
			+ TEXT_TYPE + COMMA_SEP
			+ BalanceEventStoreContract.DBEventStore.COLUMN_NAME_KEY_05
			+ TEXT_TYPE + COMMA_SEP
			+ BalanceEventStoreContract.DBEventStore.COLUMN_NAME_DATA_05
			+ TEXT_TYPE + COMMA_SEP
			+ BalanceEventStoreContract.DBEventStore.COLUMN_NAME_KEY_06
			+ TEXT_TYPE + COMMA_SEP
			+ BalanceEventStoreContract.DBEventStore.COLUMN_NAME_DATA_06
			+ TEXT_TYPE + COMMA_SEP
			+ BalanceEventStoreContract.DBEventStore.COLUMN_NAME_KEY_07
			+ TEXT_TYPE + COMMA_SEP
			+ BalanceEventStoreContract.DBEventStore.COLUMN_NAME_DATA_07
			+ TEXT_TYPE + " )";

	public static void createDatabaseFromScratch(SQLiteDatabase db) {
		db.execSQL(SQL_CREATE_EVENT_TABLE);
	}

}
