package com.tinyexpenses.balance.persistence;

import com.tinyexpenses.balance.BalanceEvent;

interface PersistentBalanceEvent<T extends BalanceEvent> {

	void toBeSaved(BalanceEventSavingHandler savingHandler);

	String eventType();

	T event();

}
