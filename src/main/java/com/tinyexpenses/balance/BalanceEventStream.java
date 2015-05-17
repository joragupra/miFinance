package com.tinyexpenses.balance;

import java.lang.IllegalStateException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

class BalanceEventStream {

	private static BalanceEventStream uniqueInstance;

	private Map<Long, List<BalanceEvent>> eventsForBalance = new HashMap<>();

	private BalanceEventStream() {
		super();
	}

	static BalanceEventStream getInstance() {
		if (uniqueInstance == null) {
			uniqueInstance = new BalanceEventStream();
		}

		return uniqueInstance;
	}

	java.util.List<BalanceEvent> events(long balanceId) {
		if (!eventsForBalance.containsKey(balanceId)) {
			eventsForBalance.put(balanceId,
					new java.util.ArrayList<BalanceEvent>());
		}

		return eventsForBalance.get(balanceId);
	}

	void registerEvent(long balanceId, BalanceEvent event) {
		this.events(balanceId).add(event);
	}

	void initialize(List<BalanceEvent> events) {
		if (!this.eventsForBalance.isEmpty()) {
			throw new IllegalStateException(
					"Trying to initialize a non-empty event stream");
		}

		for (BalanceEvent event : events) {
			this.registerEvent(event.balanceId(), event);
		}
	}

	//TODO - remove: this is a dangerous method only for testing purposes
	void forgetAllEvents() {
		eventsForBalance = new HashMap<>();
	}

}
