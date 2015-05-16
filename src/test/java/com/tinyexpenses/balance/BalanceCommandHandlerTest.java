package com.tinyexpenses.balance;

import org.junit.*;
import java.util.List;
import java.util.ArrayList;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

public class BalanceCommandHandlerTest {

	private BalanceCommandHandler commandHandler;
	private BalanceEventStream eventStream;

	@Before
	public void setUp() {
		this.eventStream = mock(BalanceEventStream.class);
		this.commandHandler = new BalanceCommandHandler(eventStream);
	}

	@Test
	public void testHandleCommand_PreviousEventsAreLoaded() {
		final long balanceId = 8907L;
		final String balanceName = "My new balance";
		List<BalanceEvent> previousEvents = new ArrayList<>();
		when(eventStream.events(balanceId)).thenReturn(previousEvents);

		commandHandler.handle(new BalanceCommandStub(balanceId));

		verify(eventStream, times(1)).events(eq(balanceId));
	}

	@Test
	public void testHandleCommand_NewEventsAreRegistered() {
		final long balanceId = 55342L;
		final String balanceName = "My new balance";
		List<BalanceEvent> previousEvents = new ArrayList<>();
		when(eventStream.events(balanceId)).thenReturn(previousEvents);

		commandHandler.handle(new BalanceCommandStub(balanceId));

		verify(eventStream, times(1)).registerEvent(eq(balanceId),
				any(BalanceEventStub.class));
	}

	private class BalanceCommandStub extends BalanceCommand {

		private BalanceCommandStub(long balanceId) {
			super(balanceId);
		}

		List<BalanceEvent> execute(Balance balance) {
			List<BalanceEvent> generatedEvents = new ArrayList<>();
			generatedEvents.add(new BalanceEventStub(balance.id()));
			return generatedEvents;
		}

	}

	private class BalanceEventStub extends BalanceEvent {

		BalanceEventStub(long balanceId) {
			super(balanceId);
		}

		protected void apply(Balance balance) {
			// nothing to do here
		}

	}

}
