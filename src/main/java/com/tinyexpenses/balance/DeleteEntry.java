package com.tinyexpenses.balance;

class DeleteEntry {

	private long balanceId;
	private String entryGuid;

	DeleteEntry(long balanceId, String entryGuid) {
		this.balanceId = balanceId;
		this.entryGuid = entryGuid;
	}

	long balanceId() {
		return this.balanceId;
	}

	String entryGuid() {
		return this.entryGuid;
	}

}
