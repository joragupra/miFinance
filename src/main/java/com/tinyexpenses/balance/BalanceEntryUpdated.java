package com.tinyexpenses.balance;

class BalanceEntryUpdated extends BalanceEvent {

    private String entryGuid;
    private String entryDescription;
    private java.util.Date creationDate;
    private Money amount;

    BalanceEntryUpdated(long balanceId, String entryGuid, String entryDescription, java.util.Date createdAt, Money amount) {
        super(balanceId);
        this.entryGuid = entryGuid;
        this.entryDescription = entryDescription;
        this.creationDate = createdAt;
        this.amount = amount;
    }

    String entryGuid() {
        return this.entryGuid;
    }

    String entryDescription() {
        return this.entryDescription;
    }

    java.util.Date creationDate() {
        return this.creationDate;
    }

    Money amount() {
        return this.amount;
    }

}
