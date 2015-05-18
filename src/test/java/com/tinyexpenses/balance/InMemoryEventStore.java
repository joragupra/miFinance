package com.tinyexpenses.balance;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

class InMemoryEventStore implements EventStore<BalanceEvent> {

	private Map<String, List<BalanceEvent>> eventsForBalance = new HashMap<>();

	public List<BalanceEvent> loadEvents(String balanceGuid) {
		if (!eventsForBalance.containsKey(balanceGuid)) {
			eventsForBalance.put(balanceGuid,
					new java.util.ArrayList<BalanceEvent>());
		}

		return eventsForBalance.get(balanceGuid);
	}

	public void saveEvent(String balanceGuid, BalanceEvent event) {
		this.loadEvents(balanceGuid).add(event);
	}

	public void reset() {
		eventsForBalance = new HashMap<>();
	}

}
