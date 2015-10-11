package com.tinyexpenses.events;

import com.tinyexpenses.balance.Balance;

public class BalanceEmptied extends BalanceEvent {

    public BalanceEmptied(String balanceGuid) {
        super(balanceGuid);
    }

    @Override
    public void apply(Balance balance) {
        balance.handle(this);
    }

}
