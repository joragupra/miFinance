package com.tinyexpenses.balance.persistence;

import com.tinyexpenses.events.BalanceEntryCreated;

import java.lang.Override;

class PersistentBalanceEntryCreated
		implements
			PersistentBalanceEvent<BalanceEntryCreated> {

	private BalanceEntryCreated event;

	static final String EVENT_TYPE = "BALANCE_ENTRY_CREATED";

	static final String ENTRY_GUID_COLUMN = "entryGuid";

	static final String DESCRIPTION_COLUMN = "description";

	static final String DATE_COLUMN = "date";

	static final String AMOUNT_COLUMN = "amount";

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
