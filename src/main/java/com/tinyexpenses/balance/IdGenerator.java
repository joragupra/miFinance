package com.tinyexpenses.balance;

public class IdGenerator {

    public static String generateId() {
        return java.util.UUID.randomUUID().toString();
    }

}
