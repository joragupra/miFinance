package com.tinyexpenses.balance;

import java.util.List;

abstract class BalanceCommand {

    protected long balanceId;

    protected BalanceCommand(long balanceId) {
        this.balanceId = balanceId;
    }

    long balanceId() {
        return this.balanceId;
    }

    abstract List<BalanceEvent> execute(Balance balance);

}
