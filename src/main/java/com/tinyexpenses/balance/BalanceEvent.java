package com.tinyexpenses.balance;

class BalanceEvent {

    protected long balanceId;

    protected BalanceEvent(long balanceId) {
        this.balanceId = balanceId;
    }

    protected long balanceId() {
        return this.balanceId;
    }

}
