package com.tinyexpenses.balance.persistence;

import com.tinyexpenses.balance.BalanceEntryUpdated;

import java.util.HashMap;
import java.util.Map;
import java.lang.Override;

class RawBalanceEntryUpdated extends RawBalanceEvent<BalanceEntryUpdated> {

	RawBalanceEntryUpdated(Map<String, String> columnValues) {
		super(columnValues);
	}

	@Override
	public BalanceEntryUpdated loadedBy(BalanceEventLoadingHandler handler) {
		return handler.load(this);
	}

}
