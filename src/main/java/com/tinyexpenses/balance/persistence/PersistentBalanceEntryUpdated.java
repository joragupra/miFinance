package com.tinyexpenses.balance.persistence;

import com.tinyexpenses.balance.BalanceEntryUpdated;

import java.lang.Override;

class PersistentBalanceEntryUpdated implements PersistentBalanceEvent<BalanceEntryUpdated> {

    private BalanceEntryUpdated event;

    static final String EVENT_TYPE = "BALANCE_ENTRY_UPDATED";

    static final String ENTRY_GUID_COLUMN = "entryGuid";

    static final String DESCRIPTION_COLUMN = "description";

    static final String DATE_COLUMN = "date";

    static final String AMOUNT_COLUMN = "amount";

    PersistentBalanceEntryUpdated(BalanceEntryUpdated event) {
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
    public BalanceEntryUpdated event() {
        return event;
    }

}
