package com.tinyexpenses.balance.persistence;

import com.tinyexpenses.events.BalanceEntryCreated;

import java.util.Map;
import java.lang.Override;

class RawBalanceEntryCreated extends RawBalanceEvent<BalanceEntryCreated> {

	RawBalanceEntryCreated(Map<String, String> columnValues) {
		super(columnValues);
	}

	@Override
	public BalanceEntryCreated loadedBy(BalanceEventLoadingHandler handler) {
		return handler.load(this);
	}

}
