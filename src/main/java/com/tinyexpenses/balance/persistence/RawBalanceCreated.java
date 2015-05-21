package com.tinyexpenses.balance.persistence;

import com.tinyexpenses.balance.BalanceCreated;

import java.util.HashMap;
import java.util.Map;
import java.lang.Override;

class RawBalanceCreated extends RawBalanceEvent<BalanceCreated> {

    RawBalanceCreated(Map<String, String> columnValues) {
        super(columnValues);
    }

    @Override
    public BalanceCreated loadedBy(BalanceEventLoadingHandler handler) {
        return handler.load(this);
    }

}
