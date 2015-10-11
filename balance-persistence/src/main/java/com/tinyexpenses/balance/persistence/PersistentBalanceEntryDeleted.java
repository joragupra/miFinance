package com.tinyexpenses.balance.persistence;

import com.tinyexpenses.events.BalanceEntryDeleted;

import java.lang.Override;

class PersistentBalanceEntryDeleted
		implements
			PersistentBalanceEvent<BalanceEntryDeleted> {

	private BalanceEntryDeleted event;

	static final String EVENT_TYPE = "BALANCE_ENTRY_DELETED";

	static final String ENTRY_GUID_COLUMN = "entryGuid";

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
