package com.tinyexpenses.events;

import com.tinyexpenses.balance.BalanceEntry;
import com.tinyexpenses.balance.IdGenerator;
import com.tinyexpenses.processing.*;

import java.util.ArrayList;
import java.util.List;

/**
 * Process commands generating events that can be consumed by Balance.
 */
public class EventGenerator {

    public static List<BalanceEvent> generateEvents(CreateBalance createBalance) {
        List<BalanceEvent> generatedEvents = new ArrayList<>();
        generatedEvents.add(new BalanceCreated(createBalance.balanceGuid()));
        generatedEvents.add(new BalanceRenamed(createBalance.balanceGuid(),
                createBalance.balanceName()));
        return generatedEvents;
    }

    public static List<BalanceEvent> generateEvents(CreateEntry createEntry) {
        List<BalanceEvent> generatedEvents = new ArrayList<>();
        generatedEvents.add(new BalanceEntryCreated(createEntry.balanceGuid(),
                IdGenerator.generateId(), createEntry.description(),
                createEntry.creationDate(), createEntry.amount()));
        return generatedEvents;
    }

    public static List<BalanceEvent> generateEvents(UpdateBalanceEntry updateEntry) {
        List<BalanceEvent> generatedEvents = new ArrayList<>();
        generatedEvents.add(new BalanceEntryUpdated(updateEntry.balanceGuid(),
                updateEntry.entryGuid(), updateEntry.entryDescription(),
                updateEntry.createdAt(), updateEntry.amount()));
        return generatedEvents;
    }

    public static List<BalanceEvent> generateEvents(DeleteEntry deleteEntry) {
        List<BalanceEvent> generatedEvents = new ArrayList<>();
        generatedEvents.add(new BalanceEntryDeleted(deleteEntry.balanceGuid(),
                deleteEntry.entryGuid()));
        return generatedEvents;
    }

    public static List<BalanceEvent> generateEvents(DeleteAllEntries deleteAllEntries) {
        List<BalanceEvent> generatedEvents = new ArrayList<>();
        generatedEvents.add(new BalanceEmptied(deleteAllEntries.balanceGuid()));
        return generatedEvents;
    }

}
