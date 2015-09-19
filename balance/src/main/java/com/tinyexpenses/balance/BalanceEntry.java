package com.tinyexpenses.balance;

import java.util.Comparator;
import java.util.Date;

public class BalanceEntry {

	private String guid;

	private String description;

	private Date recordedAt;

	private Money amount;

	BalanceEntry(String guid, String description, Date recordedAt, Money amount) {
		this.guid = guid;
		this.description = description;
		this.recordedAt = recordedAt;
		this.amount = amount;
	}

	public String guid() {
		return guid;
	}

	public String description() {
		return description;
	}

	void changeDescription(String description) {
		this.description = description;
	}

	public Date recordedAt() {
		return recordedAt;
	}

	void changeRecordedAt(Date recordedAt) {
		this.recordedAt = recordedAt;
	}

	public Money amount() {
		return amount;
	}

	void changeAmount(Money amount) {
		this.amount = amount;
	}

	static final Comparator<BalanceEntry> byRecordedDate = new Comparator<BalanceEntry>() {
		@Override
		public int compare(BalanceEntry entry1, BalanceEntry entry2) {
			return entry1.recordedAt.compareTo(entry2.recordedAt);
		}
	};

	static final Comparator<BalanceEntry> byAmount = new Comparator<BalanceEntry>() {
		@Override
		public int compare(BalanceEntry entry1, BalanceEntry entry2) {
			return entry1.amount.compareTo(entry2.amount);
		}
	};

	static final Comparator<BalanceEntry> byDescription = new Comparator<BalanceEntry>() {
		@Override
		public int compare(BalanceEntry entry1, BalanceEntry entry2) {
			return entry1.description.compareTo(entry2.description);
		}
	};

}
