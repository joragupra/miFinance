package com.tinyexpenses.processing;

import com.tinyexpenses.balance.Balance;
import com.tinyexpenses.balance.BalanceEvent;
import com.tinyexpenses.balance.BalanceFactory;
import com.tinyexpenses.processing.BalanceCommand;
import com.tinyexpenses.processing.BalanceCommandHandler;
import com.tinyexpenses.processing.BalanceEventStream;
import org.junit.*;
import java.util.List;
import java.util.ArrayList;

import static org.mockito.Mockito.*;

public class BalanceCommandHandlerTest {

	private BalanceCommandHandler commandHandler;
	private BalanceEventStream eventStream;
	private Balance mockBalance;

	@Before
	public void setUp() {
		this.eventStream = mock(BalanceEventStream.class);
		this.commandHandler = new BalanceCommandHandler(balanceFactoryStub(),
				eventStream);
	}

	@Test
	public void testHandleCommand_PreviousEventsAreLoaded() {
		final String balanceGuid = "8907";
		final String balanceName = "My new balance";
		List<BalanceEvent> previousEvents = new ArrayList<>();
		when(eventStream.events(balanceGuid)).thenReturn(previousEvents);

		commandHandler.handle(new BalanceCommandStub(balanceGuid));

		verify(mockBalance, times(1)).loadFromEvents(previousEvents);
	}

	@Test
	public void testHandleCommand_NewEventsAreHandledByBalance() {
		final String balanceGuid = "55342";
		final String balanceName = "My new balance";
		List<BalanceEvent> previousEvents = new ArrayList<>();
		when(eventStream.events(balanceGuid)).thenReturn(previousEvents);

		commandHandler.handle(new BalanceCommandStub(balanceGuid));

		verify(mockBalance, times(1)).handle(any(BalanceEventStub.class));
	}

	@Test
	public void testHandleCommand_NewEventsAreRegistered() {
		final String balanceGuid = "55342";
		final String balanceName = "My new balance";
		List<BalanceEvent> previousEvents = new ArrayList<>();
		when(eventStream.events(balanceGuid)).thenReturn(previousEvents);

		commandHandler.handle(new BalanceCommandStub(balanceGuid));

		verify(eventStream, times(1)).registerEvent(eq(balanceGuid),
				any(BalanceEventStub.class));
	}

	private BalanceFactory balanceFactoryStub() {
		BalanceFactory balanceFactory = mock(BalanceFactory.class);
		mockBalance = mock(Balance.class);
		when(balanceFactory.createEmptyBalance()).thenReturn(mockBalance);
		return balanceFactory;
	}

	private class BalanceCommandStub extends BalanceCommand {

		private BalanceCommandStub(String balanceGuid) {
			super(balanceGuid);
		}

		List<BalanceEvent> execute(Balance balance) {
			List<BalanceEvent> generatedEvents = new ArrayList<>();
			generatedEvents.add(new BalanceEventStub(balance.guid()));
			return generatedEvents;
		}

	}

	private class BalanceEventStub extends BalanceEvent {

		BalanceEventStub(String balanceGuid) {
			super(balanceGuid);
		}

		protected void apply(Balance balance) {
			// nothing to do here
		}

	}

}
