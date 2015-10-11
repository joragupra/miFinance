package com.tinyexpenses.balance.persistence;

import com.tinyexpenses.events.BalanceCreated;
import com.tinyexpenses.events.BalanceRenamed;
import com.tinyexpenses.events.BalanceEntryCreated;
import com.tinyexpenses.events.BalanceEntryDeleted;
import com.tinyexpenses.events.BalanceEntryUpdated;
import com.tinyexpenses.events.BalanceEvent;

class PersistentBalanceEventFactory {

	static PersistentBalanceEvent from(BalanceEvent balanceEvent) {
		if (BalanceCreated.class == balanceEvent.getClass()) {
			return new PersistentBalanceCreated((BalanceCreated) balanceEvent);
		} else if (BalanceRenamed.class == balanceEvent.getClass()) {
			return new PersistentBalanceRenamed((BalanceRenamed) balanceEvent);
		} else if (BalanceEntryCreated.class == balanceEvent.getClass()) {
			return new PersistentBalanceEntryCreated(
					(BalanceEntryCreated) balanceEvent);
		} else if (BalanceEntryDeleted.class == balanceEvent.getClass()) {
			return new PersistentBalanceEntryDeleted(
					(BalanceEntryDeleted) balanceEvent);
		} else if (BalanceEntryUpdated.class == balanceEvent.getClass()) {
			return new PersistentBalanceEntryUpdated(
					(BalanceEntryUpdated) balanceEvent);
		}
		return null;
	}

}
