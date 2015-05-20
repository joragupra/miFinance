package com.tinyexpenses.balance.persistence;

import com.tinyexpenses.balance.BalanceEntryCreated;

import java.lang.Override;

class PersistentBalanceEntryCreated implements PersistentBalanceEvent<BalanceEntryCreated> {

    private BalanceEntryCreated event;

    private static final String EVENT_TYPE = "BALANCE_ENTRY_CREATED";

    PersistentBalanceEntryCreated(BalanceEntryCreated event) {
        this.event = event;
    }

    @Override
    public void toBeSaved(BalanceEventSavingHandler savingHandler) {
        savingHandler.save(this);
    }

    @Override
    public String eventType() {
        return EVENT_TYPE;
    }

    @Override
    public BalanceEntryCreated event() {
        return event;
    }

}
