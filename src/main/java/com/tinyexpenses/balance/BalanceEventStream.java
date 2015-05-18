package com.tinyexpenses.balance;

import java.lang.IllegalStateException;
import java.util.List;

class BalanceEventStream {

	private EventStore<BalanceEvent> eventStore;

	BalanceEventStream(EventStore<BalanceEvent> eventStore) {
		super();
		this.eventStore = eventStore;
	}

	List<BalanceEvent> events(long balanceId) {
		return eventStore.loadEvents(balanceId);
	}

	void registerEvent(long balanceId, BalanceEvent event) {
		eventStore.saveEvent(balanceId, event);
	}

}
