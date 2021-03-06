package com.tinyexpenses.common;

public class Money implements Comparable<Money> {

	private long amountCents;

	private Money(long amountCents) {
		this.amountCents = amountCents;
	}

	public long cents() {
		return amountCents;
	}

	public double amount() {
		return amountCents / 100f;
	}

	public static Money fromCents(long amountCents) {
		return new Money(amountCents);
	}

	public Money plus(Money anotherMoney) {
		return fromCents(this.amountCents + anotherMoney.amountCents);
	}

	public Money minus(Money anotherMoney) {
		return fromCents(this.amountCents - anotherMoney.amountCents);
	}

	public boolean lt(Money anotherMoney) {
		return this.amountCents < anotherMoney.amountCents;
	}

	public boolean le(Money anotherMoney) {
		return this.amountCents <= anotherMoney.amountCents;
	}

	public boolean gt(Money anotherMoney) {
		return this.amountCents > anotherMoney.amountCents;
	}

	public boolean ge(Money anotherMoney) {
		return this.amountCents >= anotherMoney.amountCents;
	}

	public static Money ZERO() {
		return fromCents(0);
	}

	@Override
	public int compareTo(Money another) {
		return new Long(this.amountCents).compareTo(new Long(
				another.amountCents));
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) {
			return true;
		}
		if (o == null || getClass() != o.getClass()) {
			return false;
		}

		Money money = (Money) o;

		if (amountCents != money.amountCents) {
			return false;
		}

		return true;
	}

	@Override
	public int hashCode() {
		return (int) (amountCents ^ (amountCents >>> 32));
	}

}
