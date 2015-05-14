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

}
