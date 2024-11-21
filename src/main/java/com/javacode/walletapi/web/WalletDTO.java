package com.javacode.walletapi.web;

import lombok.Data;

@Data
public class WalletDTO {
    private String uuid;
    private long balance;
}
