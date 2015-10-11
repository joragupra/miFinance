package com.tinyexpenses.processing;

import com.tinyexpenses.balance.BalanceEvent;
import com.tinyexpenses.balance.EventStore;

import java.lang.IllegalStateException;
import java.util.List;

public class BalanceEventStream {

	private EventStore<BalanceEvent> eventStore;

	public BalanceEventStream(EventStore<BalanceEvent> eventStore) {
		super();
		this.eventStore = eventStore;
	}

	public List<BalanceEvent> events() {
		return events(EventStore.ALL_EVENTS);
	}

	public List<BalanceEvent> events(String balanceGuid) {
		return eventStore.loadEvents(balanceGuid);
	}

	void registerEvent(String balanceGuid, BalanceEvent event) {
		eventStore.saveEvent(balanceGuid, event);
	}

}
