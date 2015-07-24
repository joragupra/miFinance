package com.tinyexpenses.balance.persistence;

import android.provider.BaseColumns;

public final class BalanceEventStoreContract {

	public BalanceEventStoreContract() {
		super();
	}

	public static abstract class DBEventStore implements BaseColumns {

		public static final String TABLE_NAME = "event_store";

		public static final String COLUMN_NAME_ID = "_id";

		public static final String COLUMN_NAME_AGGREGATE_ID = "aggregate_id";

		public static final String COLUMN_NAME_EVENT_TYPE = "event_type";

		public static final String COLUMN_NAME_KEY_01 = "key_01";

		public static final String COLUMN_NAME_DATA_01 = "data_01";

		public static final String COLUMN_NAME_KEY_02 = "key_02";

		public static final String COLUMN_NAME_DATA_02 = "data_02";

		public static final String COLUMN_NAME_KEY_03 = "key_03";

		public static final String COLUMN_NAME_DATA_03 = "data_03";

		public static final String COLUMN_NAME_KEY_04 = "key_04";

		public static final String COLUMN_NAME_DATA_04 = "data_04";

		public static final String COLUMN_NAME_KEY_05 = "key_05";

		public static final String COLUMN_NAME_DATA_05 = "data_05";

		public static final String COLUMN_NAME_KEY_06 = "key_06";

		public static final String COLUMN_NAME_DATA_06 = "data_06";

		public static final String COLUMN_NAME_KEY_07 = "key_07";

		public static final String COLUMN_NAME_DATA_07 = "data_07";

	}

}
