package com.tinyexpenses.balance;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

class InMemoryEventStore implements EventStore<BalanceEvent> {

    private Map<Long, List<BalanceEvent>> eventsForBalance = new HashMap<>();

    public List<BalanceEvent> loadEvents(long balanceId) {
        if (!eventsForBalance.containsKey(balanceId)) {
            eventsForBalance.put(balanceId,
            new java.util.ArrayList<BalanceEvent>());
        }

        return eventsForBalance.get(balanceId);
    }

    public void saveEvent(long balanceId, BalanceEvent event) {
        this.loadEvents(balanceId).add(event);
    }

    public void reset() {
        eventsForBalance = new HashMap<>();
    }

}
