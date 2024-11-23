package com.javacode.walletapi.errors;

import java.security.InvalidParameterException;

public class WalletApiBadParameterException extends InvalidParameterException {
    public WalletApiBadParameterException(String message) {
        super(message);
    }
}
