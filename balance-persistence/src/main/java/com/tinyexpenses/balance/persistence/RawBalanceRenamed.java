package com.tinyexpenses.balance.persistence;

import com.tinyexpenses.events.BalanceRenamed;

import java.util.Map;

class RawBalanceRenamed extends RawBalanceEvent<BalanceRenamed> {

	RawBalanceRenamed(Map<String, String> columnValues) {
		super(columnValues);
	}

	@Override
	public BalanceRenamed loadedBy(BalanceEventLoadingHandler handler) {
		return handler.load(this);
	}

}
