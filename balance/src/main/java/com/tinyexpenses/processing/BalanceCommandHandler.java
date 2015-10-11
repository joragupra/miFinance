package com.tinyexpenses.processing;

import com.tinyexpenses.balance.*;
import com.tinyexpenses.events.BalanceEvent;

import java.util.List;

public class BalanceCommandHandler {

	private BalanceFactory factory;
	private BalanceEventStream eventStream;

	public BalanceCommandHandler(BalanceFactory balanceFactory,
			BalanceEventStream eventStream) {
		this.factory = balanceFactory;
		this.eventStream = eventStream;
	}

	public void handle(BalanceCommand command) {
		final String balanceGuid = command.balanceGuid();

		List<BalanceEvent> previousEvents = eventStream.events(balanceGuid);

		Balance balance = factory.createEmptyBalance();
		balance.loadFromEvents(previousEvents);

		List<BalanceEvent> newEvents = command.execute(balance);

		for (BalanceEvent event : newEvents) {
			balance.handle(event);
			eventStream.registerEvent(balanceGuid, event);
		}
	}

}
