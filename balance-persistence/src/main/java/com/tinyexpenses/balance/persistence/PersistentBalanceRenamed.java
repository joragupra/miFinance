package com.tinyexpenses.balance.persistence;

import com.tinyexpenses.events.BalanceRenamed;

import java.lang.Override;

class PersistentBalanceRenamed
		implements
			PersistentBalanceEvent<BalanceRenamed> {

	private BalanceRenamed event;

	static final String EVENT_TYPE = "BALANCE_RENAMED";

	static final String NAME_COLUMN = "name";

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
