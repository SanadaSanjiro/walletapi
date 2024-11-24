package com.javacode.walletapi.service;

import com.javacode.walletapi.data.Wallet;
import com.javacode.walletapi.data.WalletDAO;
import com.javacode.walletapi.errors.WalletApiBadParameterException;
import com.javacode.walletapi.errors.WalletApiInsufficientFundsException;
import com.javacode.walletapi.errors.WalletApiWalletNotFoundException;
import com.javacode.walletapi.web.dto.OperationDTO;
import com.javacode.walletapi.web.dto.WalletDTO;
import org.junit.jupiter.api.*;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@SpringBootTest
public class WalletServiceTest {
    @Mock
    WalletDAO dao;

    @Mock
    InputValidator inputValidator;

    @InjectMocks
    WalletService service;

    static String uuid="4ea8d54b-a48d-4a27-ae8d-6a0ea6a3fb4c";
    static long amount=1000L;
    static Wallet wallet;
    static OperationDTO operation;

    @BeforeEach
    void before() {
        wallet=new Wallet();
        operation=new OperationDTO();
        operation.setWalletId(uuid);
        operation.setAmount(1000);
    }

    @Test
    @DisplayName("GetById")
    public void getByIdTest() {
        when(dao.getByUuid(any())).thenReturn(Optional.of(wallet));
        when(inputValidator.isValidUUID(uuid)).thenReturn(true);
        Assertions.assertEquals(new WalletDTO(wallet), service.getById(uuid));
    }

    @Test
    @DisplayName("GetEmptyUUID")
    public void getEmptyUUIDTest() {
       Assertions.assertThrows(WalletApiBadParameterException.class, () -> service.getById(null));
    }

    @Test
    @DisplayName("GetBadUUID")
    public void getBadUUIDTest() {
        when(inputValidator.isValidUUID(any())).thenReturn(false);
        Assertions.assertThrows(WalletApiBadParameterException.class, () -> service.getById(uuid));
    }

    @Test
    @DisplayName("GetNoSuchWallet")
    public void getNoSuchWalletTest() {
        when(dao.getByUuid(any())).thenReturn(Optional.empty());
        when(inputValidator.isValidUUID(uuid)).thenReturn(true);
        Assertions.assertThrows(WalletApiWalletNotFoundException.class, () -> service.getById(uuid));
    }

    @Test
    @DisplayName("ChangeAmount")
    public void changeAmountTest() {
        wallet.setAmount(amount);
        wallet.setId(1);
        wallet.setUuid(uuid);
        operation.setOperationType(Operation.DEPOSIT.toString());
        when(inputValidator.isValidWalletDTO(operation)).thenReturn(true);
        when(dao.getByUuid(uuid)).thenReturn(Optional.of(wallet));
        when(dao.update(any())).thenReturn(wallet);
        Assertions.assertEquals((amount*2), service.changeWalletAmount(operation).getAmount());
    }

    @Test
    @DisplayName("ChangeEmptyDto")
    public void changeEmptyDtoTest() {
        Assertions.assertThrows(WalletApiBadParameterException.class, () -> service.changeWalletAmount(null));
    }

    @Test
    @DisplayName("ChangeBadUUID")
    public void changeBadUUIDTest() {
        operation.setOperationType(Operation.WITHDRAW.toString());
        when(inputValidator.isValidWalletDTO(operation)).thenReturn(false);
        Assertions.assertThrows(WalletApiBadParameterException.class, () -> service.changeWalletAmount(operation));
    }

    @Test
    @DisplayName("ChangeNotEnoughFunds")
    public void changeNotEnoughFundsTest() {
        wallet.setAmount(0);
        wallet.setId(1);
        wallet.setUuid(uuid);
        operation.setOperationType(Operation.WITHDRAW.toString());
        when(inputValidator.isValidWalletDTO(operation)).thenReturn(true);
        when(dao.getByUuid(uuid)).thenReturn(Optional.of(wallet));
        when(dao.update(any())).thenReturn(wallet);
        Assertions.assertThrows(WalletApiInsufficientFundsException.class, () -> service.changeWalletAmount(operation));
    }
}
