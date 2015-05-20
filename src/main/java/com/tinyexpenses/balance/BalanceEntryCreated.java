package com.tinyexpenses.balance;

public class BalanceEntryCreated extends BalanceEvent {

	private String entryDescription;
	private String entryGuid;
	private java.util.Date creationDate;
	private Money amount;

	public BalanceEntryCreated(String balanceGuid, String entryGuid,
			String entryDescription, java.util.Date createdAt, Money amount) {
		super(balanceGuid);
		this.entryGuid = entryGuid;
		this.entryDescription = entryDescription;
		this.creationDate = createdAt;
		this.amount = amount;
	}

	public String entryGuid() {
		return this.entryGuid;
	}

	public String entryDescription() {
		return this.entryDescription;
	}

	public java.util.Date creationDate() {
		return this.creationDate;
	}

	public Money amount() {
		return this.amount;
	}

	protected void apply(Balance balance) {
		balance.handle(this);
	}

}
