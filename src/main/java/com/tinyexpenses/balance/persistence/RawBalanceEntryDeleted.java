package com.tinyexpenses.balance.persistence;

import com.tinyexpenses.balance.BalanceEntryDeleted;

import java.util.HashMap;
import java.util.Map;
import java.lang.Override;

class RawBalanceEntryDeleted extends RawBalanceEvent<BalanceEntryDeleted> {

	RawBalanceEntryDeleted(Map<String, String> columnValues) {
		super(columnValues);
	}

	@Override
	public BalanceEntryDeleted loadedBy(BalanceEventLoadingHandler handler) {
		return handler.load(this);
	}

}
