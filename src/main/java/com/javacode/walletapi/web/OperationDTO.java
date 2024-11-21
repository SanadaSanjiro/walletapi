package com.javacode.walletapi.web;

import lombok.Data;

@Data
public class OperationDTO {
    private String walletId;
    private String operationType;
    private long amount;
}
