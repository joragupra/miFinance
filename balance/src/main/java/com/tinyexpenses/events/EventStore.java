package com.tinyexpenses.events;

import java.util.List;

public interface EventStore<T> {

	String ALL_EVENTS = "ALL";

	List<T> loadEvents(String aggregateId);

	void saveEvent(String aggregateId, T event);

}
