package com.tinyexpenses.balance.persistence;

import com.tinyexpenses.balance.BalanceCreated;

import java.lang.Override;

class PersistentBalanceCreated
		implements
			PersistentBalanceEvent<BalanceCreated> {

	private BalanceCreated event;

	static final String EVENT_TYPE = "BALANCE_CREATED";

	PersistentBalanceCreated(BalanceCreated event) {
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
	public BalanceCreated event() {
		return event;
	}

}
