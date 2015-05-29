package com.tinyexpenses.balance.persistence;

import com.tinyexpenses.balance.BalanceCreated;
import com.tinyexpenses.balance.BalanceRenamed;
import com.tinyexpenses.balance.BalanceEntryCreated;
import com.tinyexpenses.balance.BalanceEntryDeleted;
import com.tinyexpenses.balance.BalanceEntryUpdated;
import com.tinyexpenses.balance.BalanceEvent;

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
