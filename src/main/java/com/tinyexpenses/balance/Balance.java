package com.tinyexpenses.balance;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Balance {

	private long id;
	private String name;

	public enum EntrySortMethod {
		DESCRIPTION, RECORD_DATE, AMOUNT;
	}

	private static final Map<EntrySortMethod, Comparator<BalanceEntry>> sortMethodMap;

	static {
		sortMethodMap = new HashMap<>();
		sortMethodMap.put(EntrySortMethod.DESCRIPTION,
				BalanceEntry.byDescription);
		sortMethodMap.put(EntrySortMethod.RECORD_DATE,
				BalanceEntry.byRecordedDate);
		sortMethodMap.put(EntrySortMethod.AMOUNT, BalanceEntry.byAmount);
	}

	private List<BalanceEntry> entries;

	private EntrySortMethod sortMethod;

	public Balance() {
		this(EntrySortMethod.RECORD_DATE);
	}

	public Balance(String name) {
		this();
		this.name = name;
	}

	Balance(EntrySortMethod sortMethod) {
		this.entries = new ArrayList<>();
		this.sortMethod = sortMethod;
	}

	void initBalanceEntries(List<BalanceEntry> balanceEntries) {
		for (BalanceEntry balanceEntry : balanceEntries) {
			entries.add(balanceEntry);
		}
	}

	public void assignId(long id) {
		this.id = id;
	}

	public long id() {
		return this.id;
	}

	public String name() {
		return this.name;
	}

	public Balance rename(String newName) {
		this.name = newName;
		return this;
	}

	public BalanceEntry addEntry(String description, Date recordedAt,
			long amountCents) {
		BalanceEntry newEntry = new BalanceEntry(description, recordedAt,
				amountCents);
		this.entries.add(newEntry);

		this.entries = sortEntries(this.sortMethod);

		return newEntry;
	}

	// TODO - deprecated: remove
	public BalanceEntry updateEntry(long entryId, String description,
			Date recordedAt, long amountCents) {
		int pos = findEntryPositionForId(entryId);

		if (pos == -1) {
			return null;
		}

		BalanceEntry balanceEntry = this.entries.get(pos);
		balanceEntry.changeDescription(description);
		balanceEntry.changeRecordedAt(recordedAt);
		balanceEntry.changeAmount(amountCents);
		return balanceEntry;
	}

	public BalanceEntry updateEntry(String entryGuid, String description,
			Date recordedAt, Money amount) {
		int pos = findEntryPositionForGuid(entryGuid);

		if (pos == -1) {
			return null;
		}

		BalanceEntry balanceEntry = this.entries.get(pos);
		balanceEntry.changeDescription(description);
		balanceEntry.changeRecordedAt(recordedAt);
		balanceEntry.changeAmount(amount);
		return balanceEntry;
	}

	// TODO - deprecated: remove
	public boolean deleteEntry(long entryId) {
		int pos = findEntryPositionForId(entryId);

		if (pos == -1) {
			return false;
		}

		this.entries.remove(pos);
		return true;
	}

	public boolean deleteEntry(String entryGuid) {
		int pos = findEntryPositionForGuid(entryGuid);

		if (pos == -1) {
			return false;
		}

		this.entries.remove(pos);
		return true;
	}

	public boolean deleteAllEntries() {
		return this.entries.removeAll(this.entries());
	}

	private int findEntryPositionForId(long entryId) {
		int pos = -1;
		for (int i = 0; i < this.entries.size(); i++) {
			if (this.entries.get(i).id() == entryId) {
				pos = i;
				break;
			}
		}
		return pos;
	}

	private int findEntryPositionForGuid(String entryGuid) {
		int pos = -1;
		for (int i = 0; i < this.entries.size(); i++) {
			if (this.entries.get(i).guid() == entryGuid) {
				pos = i;
				break;
			}
		}
		return pos;
	}

	private List<BalanceEntry> sortEntries(EntrySortMethod sortMethod) {
		List<BalanceEntry> initialBalanceEntryList = new ArrayList<>(entries);
		Collections
				.sort(initialBalanceEntryList, sortMethodMap.get(sortMethod));
		return initialBalanceEntryList;
	}

	public List<BalanceEntry> entries() {
		return sortEntries(this.sortMethod);
	}

	public void changeSortingMethod(EntrySortMethod sortMethod) {
		this.sortMethod = sortMethod;
		this.entries = sortEntries(this.sortMethod);
	}

	public Money balanceAmount() {
		Money acc = Money.ZERO();
		for (BalanceEntry entry : this.entries) {
			acc = acc.plus(entry.amount());
		}
		return acc;
	}

	public List<BalanceEvent> handle(CreateBalance createBalance) {
		List<BalanceEvent> generatedEvents = new ArrayList<>();
		generatedEvents.add(new BalanceCreated(createBalance.balanceId()));
		generatedEvents.add(new BalanceRenamed(createBalance.balanceId(),
				createBalance.balanceName()));
		return generatedEvents;
	}

	public List<BalanceEvent> handle(CreateEntry createEntry) {
		List<BalanceEvent> generatedEvents = new ArrayList<>();
		generatedEvents.add(new BalanceEntryCreated(createEntry.balanceId(),
				createEntry.description(), createEntry.creationDate(),
				createEntry.amount()));
		return generatedEvents;
	}

	public List<BalanceEvent> handle(UpdateBalanceEntry updateEntry) {
		List<BalanceEvent> generatedEvents = new ArrayList<>();
		generatedEvents.add(new BalanceEntryUpdated(updateEntry.balanceId(),
				updateEntry.entryGuid(), updateEntry.entryDescription(),
				updateEntry.createdAt(), updateEntry.amount()));
		return generatedEvents;
	}

	public List<BalanceEvent> handle(DeleteEntry deleteEntry) {
		List<BalanceEvent> generatedEvents = new ArrayList<>();
		generatedEvents.add(new BalanceEntryDeleted(deleteEntry.balanceId(),
				deleteEntry.entryGuid()));
		return generatedEvents;
	}

	public List<BalanceEvent> handle(DeleteAllEntries deleteAllEntries) {
		List<BalanceEvent> generatedEvents = new ArrayList<>();
		for (BalanceEntry entry : this.entries) {
			generatedEvents.add(new BalanceEntryDeleted(id(), entry.guid()));
		}
		return generatedEvents;
	}

	public Balance handle(BalanceCreated balanceCreatedEvent) {
		this.assignId(balanceCreatedEvent.balanceId());
		return this;
	}

	public Balance handle(BalanceRenamed balanceRenamedEvent) {
		this.rename(balanceRenamedEvent.name());
		return this;
	}

	public Balance handle(BalanceEntryCreated balanceEntryCreatedEvent) {
		BalanceEntry newEntry = new BalanceEntry(IdGenerator.generateId(),
				balanceEntryCreatedEvent.entryDescription(),
				balanceEntryCreatedEvent.creationDate(),
				balanceEntryCreatedEvent.amount());
		this.entries.add(newEntry);
		return this;
	}

	public Balance handle(BalanceEntryUpdated balanceEntryUpdatedEvent) {
		updateEntry(balanceEntryUpdatedEvent.entryGuid(),
				balanceEntryUpdatedEvent.entryDescription(),
				balanceEntryUpdatedEvent.creationDate(),
				balanceEntryUpdatedEvent.amount());
		return this;
	}

	public Balance handle(BalanceEntryDeleted balanceEntryDeletedEvent) {
		deleteEntry(balanceEntryDeletedEvent.entryGuid());
		return this;
	}

}
