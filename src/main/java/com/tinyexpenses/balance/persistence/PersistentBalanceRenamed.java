package com.tinyexpenses.balance.persistence;

import com.tinyexpenses.balance.BalanceRenamed;

import java.lang.Override;

class PersistentBalanceRenamed implements PersistentBalanceEvent<BalanceRenamed> {

    private BalanceRenamed event;

    private static final String EVENT_TYPE = "BALANCE_RENAMED";

    PersistentBalanceRenamed(BalanceRenamed event) {
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
    public BalanceRenamed event() {
        return event;
    }

}
