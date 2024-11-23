package com.javacode.walletapi.errors;

public class WalletApiWalletNotFoundException extends IllegalArgumentException {
    public WalletApiWalletNotFoundException(String message) {
        super(message);
    }
}
