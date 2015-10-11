package com.tinyexpenses.balance.persistence;

import com.tinyexpenses.events.BalanceEvent;

import java.util.HashMap;
import java.util.Map;

abstract class RawBalanceEvent<T extends BalanceEvent> {

	protected Map<String, String> columnValues = new HashMap<>();

	RawBalanceEvent(Map<String, String> columnValues) {
		this.columnValues = columnValues;
	}

	String valueInColumn(String column) {
		return columnValues.get(column);
	}

	abstract T loadedBy(BalanceEventLoadingHandler handler);

}
