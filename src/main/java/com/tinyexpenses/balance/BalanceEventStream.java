package com.tinyexpenses.balance;

class BalanceEventStream {

    private static BalanceEventStream uniqueInstance;

    private java.util.Map<Long, java.util.List<BalanceEvent>> eventsForBalance = new java.util.HashMap<>();

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
            eventsForBalance.put(balanceId, new java.util.ArrayList<BalanceEvent>());
        }

        return eventsForBalance.get(balanceId);
    }

    void registerEvent(long balanceId, BalanceEvent event) {
        this.events(balanceId).add(event);
    }

}
