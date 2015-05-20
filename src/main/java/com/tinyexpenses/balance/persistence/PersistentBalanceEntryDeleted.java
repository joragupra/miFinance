package com.tinyexpenses.balance.persistence;

import com.tinyexpenses.balance.BalanceEntryDeleted;

import java.lang.Override;

class PersistentBalanceEntryDeleted implements PersistentBalanceEvent<BalanceEntryDeleted> {

    private BalanceEntryDeleted event;

    private static final String EVENT_TYPE = "BALANCE_ENTRY_DELETED";

    PersistentBalanceEntryDeleted(BalanceEntryDeleted event) {
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
    public BalanceEntryDeleted event() {
        return event;
    }

}
