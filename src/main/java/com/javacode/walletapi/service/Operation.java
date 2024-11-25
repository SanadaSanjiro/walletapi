package com.javacode.walletapi.service;

import java.util.Map;
import java.util.Optional;
import java.util.stream.Stream;

import static java.util.stream.Collectors.toMap;

/**
 * Provides wallet operations
 */

public enum Operation {
    DEPOSIT {
        @Override
        public long apply(long wallet, long amount) {return wallet + amount;}
    },
    WITHDRAW {
        @Override
        public long apply(long wallet, long amount) {return wallet - amount;}
    };

    public abstract long apply(long wallet, long amount);

    private static final Map<String, Operation> stringToEnum = Stream.of(values()).collect(
            toMap(Object::toString, e->e));

    /**
     * Turns string into enum
     * @param s String with operation type. Not case-sensitive
     * @return Optional with one of the operations, if string is correct, or empty optional in other case
     */
    static Optional<Operation> fromString(String s) {
        return Optional.ofNullable(stringToEnum.get(s.toUpperCase()));
    }
}