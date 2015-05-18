package com.tinyexpenses.balance;

import java.util.List;

public interface EventStore<T> {

	List<T> loadEvents(String aggregateId);

	void saveEvent(String aggregateId, T event);

}
