package com.javacode.walletapi.errors;

public class WalletApiInsufficientFundsException extends IllegalArgumentException {
    public WalletApiInsufficientFundsException(String message) {
        super(message);
    }
}
