package com.tinyexpenses.balance;

import java.util.List;

public interface EventStore<T> {

    List<T> loadEvents(long aggregateId);

    void saveEvent(long aggregateId, T event);

}
