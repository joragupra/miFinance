package com.tinyexpenses.balance;

import org.junit.*;
import java.util.List;

import static org.junit.Assert.*;

public class BalanceTest {

  private Balance balance;

  @Before
  public void setUp() {
      this.balance = new Balance();
  }

  @Test
  public void testCreateBalance() {
      final long balanceId = 1245L;
      final String balanceName = "New balance created";
      CreateBalance command = new CreateBalance(balanceId, balanceName);
      List<BalanceEvent> generatedEvents = balance.handle(command);

      checkBalanceEvent(generatedEvents.get(0), BalanceCreated.class);
      checkBalanceEvent(generatedEvents.get(1), BalanceRenamed.class);

      assertEquals(balanceId, ((BalanceCreated) generatedEvents.get(0)).balanceId());
      assertEquals(balanceId, ((BalanceRenamed) generatedEvents.get(1)).balanceId());
      assertEquals(balanceName, ((BalanceRenamed) generatedEvents.get(1)).name());
  }

  private void checkBalanceEvent(BalanceEvent balanceEvent, Class balanceEventType) {
      assertTrue(balanceEvent.getClass() == balanceEventType);
  }

  @Test
  public void testBalanceCreated() {
      final long balanceId = 54321L;
      BalanceCreated balanceCreatedEvent = new BalanceCreated(balanceId);

      assertEquals(balanceId, balance.handle(balanceCreatedEvent).id());
  }

  @Test
  public void testBalanceRenamed() {
      final String balanceName = "My balance";
      BalanceRenamed balanceRenamedEvent = new BalanceRenamed(12345L, balanceName);

      assertEquals(balanceName, balance.handle(balanceRenamedEvent).name());
  }

  @Test
  public void testCreateEntry() {
      final String description = "Book purchase";
      final java.util.Date createdAt = new java.util.Date();
      final Money amount = Money.fromCents(1250);
      CreateEntry command = new CreateEntry(6789L, description, createdAt, amount);

      List<BalanceEvent> generatedEvents = balance.handle(command);

      checkBalanceEvent(generatedEvents.get(0), BalanceEntryCreated.class);
      assertEquals(description, ((BalanceEntryCreated) generatedEvents.get(0)).entryDescription());
      assertEquals(createdAt, ((BalanceEntryCreated) generatedEvents.get(0)).creationDate());
      assertEquals(amount, ((BalanceEntryCreated) generatedEvents.get(0)).amount());
  }

  @Test
  public void testBalanceEntryCreated() {
      final String description = "Book purchase";
      final java.util.Date createdAt = new java.util.Date();
      final Money amount = Money.fromCents(1250);
      BalanceEntryCreated balanceEntryCreatedEvent = new BalanceEntryCreated(6789L, description, createdAt, amount);

      balance.handle(balanceEntryCreatedEvent);

      assertEquals(1, balance.entries().size());
      assertEquals(description, ((BalanceEntry) balance.entries().get(0)).description());
      assertEquals(createdAt, ((BalanceEntry) balance.entries().get(0)).recordedAt());
      assertEquals(amount, ((BalanceEntry) balance.entries().get(0)).amount());
  }

  @Test
  public void testUpdateBalanceEntry() {
      final String entryGuid = initializeBalanceWithOneEntry();
      final String description = "Book purchase - modification";
      final java.util.Date createdAt = new java.util.Date();
      final Money amount = Money.fromCents(1550);
      UpdateBalanceEntry command = new UpdateBalanceEntry(9876L, entryGuid, description, createdAt, amount);

      List<BalanceEvent> generatedEvents = balance.handle(command);

      checkBalanceEvent(generatedEvents.get(0), BalanceEntryUpdated.class);
      assertEquals(entryGuid, ((BalanceEntryUpdated) generatedEvents.get(0)).entryGuid());
      assertEquals(description, ((BalanceEntryUpdated) generatedEvents.get(0)).entryDescription());
      assertEquals(createdAt, ((BalanceEntryUpdated) generatedEvents.get(0)).creationDate());
      assertEquals(amount, ((BalanceEntryUpdated) generatedEvents.get(0)).amount());
  }

  private String initializeBalanceWithOneEntry() {
      final String description = "Book purchase";
      final java.util.Date createdAt = new java.util.Date();
      final Money amount = Money.fromCents(1250);
      BalanceEntryCreated balanceEntryCreatedEvent = new BalanceEntryCreated(6789L, description, createdAt, amount);
      balance.handle(balanceEntryCreatedEvent);
      return ((BalanceEntry) balance.entries().get(0)).guid();
  }

}
