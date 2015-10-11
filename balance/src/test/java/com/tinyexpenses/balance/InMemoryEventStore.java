package com.tinyexpenses.balance;

import com.tinyexpenses.events.BalanceEvent;
import com.tinyexpenses.events.EventStore;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

class InMemoryEventStore implements EventStore<BalanceEvent> {

	private Map<String, List<BalanceEvent>> eventsForBalance = new HashMap<>();

	public List<BalanceEvent> loadEvents(String balanceGuid) {
		if (EventStore.ALL_EVENTS.equals(balanceGuid)) {
			return loadAllEvents();
		}

		if (!eventsForBalance.containsKey(balanceGuid)) {
			eventsForBalance.put(balanceGuid,
					new java.util.ArrayList<BalanceEvent>());
		}

		return eventsForBalance.get(balanceGuid);
	}

	private List<BalanceEvent> loadAllEvents() {
		List<BalanceEvent> allEvents = new ArrayList<>();
		for (List<BalanceEvent> events : eventsForBalance.values()) {
			allEvents.addAll(events);
		}
		return allEvents;
	}

	public void saveEvent(String balanceGuid, BalanceEvent event) {
		this.loadEvents(balanceGuid).add(event);
	}

	public void reset() {
		eventsForBalance = new HashMap<>();
	}

}
