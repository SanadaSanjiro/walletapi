package com.javacode.walletapi.service;

import com.javacode.walletapi.web.dto.OperationDTO;
import org.junit.jupiter.api.*;

public class InputValidatorTest {
    static String uuid="4ea8d54b-a48d-4a27-ae8d-6a0ea6a3fb4c";
    static OperationDTO operation;

    InputValidator validator=new InputValidator();

    @BeforeEach
    void before() {
        operation=new OperationDTO();
        operation.setWalletId(uuid);
        operation.setAmount(1000);
        operation.setOperationType(Operation.DEPOSIT.toString());
    }

    @Test
    @DisplayName("ValidUuidCheck")
    public void testValidUuidCheck() {
        Assertions.assertTrue(validator.isValidUUID(uuid));
    }

    @Test
    @DisplayName("BadUuidCheck")
    public void testBadUuidCheck() {
        Assertions.assertFalse(validator.isValidUUID(uuid.substring(0, 8)));
    }

    @Test
    @DisplayName("ValidDtoCheck")
    public void testValidDtoCheck() {
        Assertions.assertTrue(validator.isValidWalletDTO(operation));
    }

    @Test
    @DisplayName("DtoCheckBadUuid")
    public void testDtoCheckBadUuid() {
        operation.setWalletId(uuid.substring(0, 8));
        Assertions.assertFalse(validator.isValidWalletDTO(operation));
    }

    @Test
    @DisplayName("DtoCheckBadUuid")
    public void testDtoCheckBadAmount() {
        operation.setAmount(-1000);
        Assertions.assertFalse(validator.isValidWalletDTO(operation));
    }

    @Test
    @DisplayName("DtoCheckBadOperation")
    public void testDtoCheckBadOperation() {
        operation.setOperationType("BAD");
        Assertions.assertFalse(validator.isValidWalletDTO(operation));
    }
}
