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
		System.out.println("Executing command for balance " + balanceId);

		List<BalanceEvent> previousEvents = eventStream.events(balanceId);
		System.out.println(previousEvents.size()
				+ " previous events found for balance " + balanceId);

		Balance balance = factory.createEmptyBalance();
		balance.loadFromEvents(previousEvents);

		List<BalanceEvent> newEvents = command.execute(balance);
		System.out.println(newEvents.size()
				+ " new events generated for balance " + balanceId);

		for (BalanceEvent event : newEvents) {
			balance.handle(event);
			eventStream.registerEvent(balanceId, event);
		}
	}

}
