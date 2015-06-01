package com.tinyexpenses.balance;

import java.lang.IllegalStateException;
import java.util.List;

class BalanceEventStream {

	private EventStore<BalanceEvent> eventStore;

	BalanceEventStream(EventStore<BalanceEvent> eventStore) {
		super();
		this.eventStore = eventStore;
	}

	List<BalanceEvent> events() {
		return events(EventStore.ALL_EVENTS);
	}

	List<BalanceEvent> events(String balanceGuid) {
		return eventStore.loadEvents(balanceGuid);
	}

	void registerEvent(String balanceGuid, BalanceEvent event) {
		eventStore.saveEvent(balanceGuid, event);
	}

}
