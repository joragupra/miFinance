package com.tinyexpenses.balance;

import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import static com.tinyexpenses.balance.Money.*;

public class MoneyTest {

    @Test
    public void testGetAmount_Double() {
        assertEquals(10.00, fromCents(1000).amount(), 0.0);
    }

    @Test
    public void testGetCents_Long_Positive() {
        assertEquals(123123, fromCents(123123).cents());
    }

    @Test
    public void testGetCents_Long_Zero() {
        assertEquals(0, fromCents(0).cents());
    }

    @Test
    public void testGetCents_Long_Negative() {
        assertEquals(-4322234, fromCents(-4322234).cents());
    }

    @Test
    public void testMinus_Same() {
        assertEquals(0, fromCents(68576).minus(fromCents(68576)).cents());
    }

    @Test
    public void testMinus_Greater() {
        assertEquals(53, fromCents(94853).minus(fromCents(94800)).cents());
    }

    @Test
    public void testMinus_Less() {
        assertEquals(-5199, fromCents(1234).minus(fromCents(6433)).cents());
    }

    @Test
    public void testMinus_MinusNegative() {
        assertEquals(1466, fromCents(1234).minus(fromCents(-232)).cents());
    }

    @Test
    public void testMinus_NegativeMinusNegative() {
        assertEquals(-220, fromCents(-541).minus(fromCents(-321)).cents());
    }

    @Test
    public void testPlus_PositivePlusPositive() {
        assertEquals(444, fromCents(123).plus(fromCents(321)).cents());
    }

    @Test
    public void testPlus_PositivePlusLesserNegative() {
        assertEquals(222, fromCents(333).plus(fromCents(-111)).cents());
    }

    @Test
    public void testPlus_PositivePlusGreaterNegative() {
        assertEquals(-1111, fromCents(1234).plus(fromCents(-2345)).cents());
    }

    @Test
    public void testPlus_NegativePlusLesserPositive() {
        assertEquals(-3333, fromCents(-5555).plus(fromCents(2222)).cents());
    }

    @Test
    public void testPlus_NegativePlusGreaterPositive() {
        assertEquals(2222, fromCents(-5432).plus(fromCents(7654)).cents());
    }

    @Test
    public void testPlus_NegativePlusNegative() {
        assertEquals(-2468, fromCents(-1234).plus(fromCents(-1234)).cents());
    }

    @Test
    public void testEquals_DifferentClass() {
        assertFalse(fromCents(100).equals("Hello world"));
    }

    @Test
    public void testEquals_Null() {
        assertFalse(fromCents(100).equals(null));
    }

    @Test
    public void testEquals_SameObject() {
        Money money = fromCents(43223);
        assertTrue(money.equals(money));
    }

    @Test
    public void testEquals_EqualObjects() {
        assertTrue(fromCents(654233).equals(fromCents(654233)));
    }

    @Test
    public void testEquals_Different_Cents() {
        assertFalse(fromCents(5541).equals(fromCents(4321)));
    }

    @Test
    public void testHashCode_SameObject() {
        Money money = fromCents(43223);
        assertEquals(money.hashCode(), money.hashCode());
    }

    @Test
    public void testHashCode_EqualObjects() {
        assertEquals(fromCents(654233).hashCode(), fromCents(654233).hashCode());
    }

    @Test
    public void testHashCode_Different_Cents() {
        assertTrue(fromCents(1234).hashCode() != fromCents(4233).hashCode());
    }

    @Test
    public void testCompareTo() {
        assertEquals(-1, fromCents(100).compareTo(fromCents(200)));
        assertEquals(1, fromCents(200).compareTo(fromCents(100)));
        assertEquals(0, fromCents(200).compareTo(fromCents(200)));
    }

    @Test
    public void testIsGreaterThan_Greater() {
        assertTrue(fromCents(54314).gt(fromCents(8531)));
    }

    @Test
    public void testIsGreaterThan_Equal() {
        assertFalse(fromCents(4312).gt(fromCents(4312)));
    }

    @Test
    public void testIsGreaterThan_Less() {
        assertFalse(fromCents(4312).gt(fromCents(7312)));
    }

    @Test
    public void testIsLessThan_Greater() {
        assertFalse(fromCents(54314).lt(fromCents(8531)));
    }

    @Test
    public void testIsLessThan_Equal() {
        assertFalse(fromCents(4312).lt(fromCents(4312)));
    }

    @Test
    public void testIsLessThan_Less() {
        assertTrue(fromCents(4312).lt(fromCents(7312)));
    }

    @Test
    public void testIsGreaterEqualsThan_Greater() {
        assertTrue(fromCents(54314).ge(fromCents(8531)));
    }

    @Test
    public void testIsGreaterEqualsThan_Equal() {
        assertTrue(fromCents(4312).ge(fromCents(4312)));
    }

    @Test
    public void testIsGreaterEqualsThan_Less() {
        assertFalse(fromCents(4312).ge(fromCents(7312)));
    }

    @Test
    public void testIsLessEqualsThan_Greater() {
        assertFalse(fromCents(54314).le(fromCents(8531)));
    }

    @Test
    public void testIsLessEqualsThan_Equal() {
        assertTrue(fromCents(4312).le(fromCents(4312)));
    }

    @Test
    public void testIsLessEqualsThan_Less() {
        assertTrue(fromCents(4312).le(fromCents(7312)));
    }


}
