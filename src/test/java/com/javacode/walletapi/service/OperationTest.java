package com.javacode.walletapi.service;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class OperationTest {
    @Test
    @DisplayName("FromStringWithdrawCheck")
    public void testFromStringWithdraw() {
        Assertions.assertTrue(Operation.fromString("WITHDRAW").isPresent());
    }
    @Test
    @DisplayName("FromStringDepositCheck")
    public void testFromStringDeposit() {
        Assertions.assertTrue(Operation.fromString("deposit").isPresent());
    }
    @Test
    @DisplayName("FromStringBadInputCheck")
    public void testFromStringBadInput() {
        Assertions.assertTrue(Operation.fromString("bad").isEmpty());
    }
    @Test
    @DisplayName("WithdrawCheck")
    public void testWithdraw() {
        Assertions.assertEquals(Operation.WITHDRAW.apply(1,1),0L);
    }
    @Test
    @DisplayName("DepositCheck")
    public void testDeposit() {
        Assertions.assertEquals(Operation.DEPOSIT.apply(1,1),2L);
    }
}
