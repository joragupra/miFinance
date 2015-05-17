package com.tinyexpenses.balance;

import java.util.List;

class BalanceCommandHandler {

	private BalanceFactory factory;
	private BalanceEventStream eventStream;

	BalanceCommandHandler(BalanceFactory balanceFactory,
			BalanceEventStream eventStream) {
		this.factory = balanceFactory;
		this.eventStream = eventStream;
	}

	void handle(BalanceCommand command) {
		final long balanceId = command.balanceId();

		List<BalanceEvent> previousEvents = eventStream.events(balanceId);

		Balance balance = factory.createEmptyBalance();
		balance.loadFromEvents(previousEvents);

		List<BalanceEvent> newEvents = command.execute(balance);

		for (BalanceEvent event : newEvents) {
			balance.handle(event);
			eventStream.registerEvent(balanceId, event);
		}
	}

}
