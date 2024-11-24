package com.javacode.walletapi.web;

import com.javacode.walletapi.errors.WalletApiWalletNotFoundException;
import com.javacode.walletapi.service.WalletService;
import com.javacode.walletapi.web.dto.OperationDTO;
import com.javacode.walletapi.web.dto.WalletDTO;
import org.junit.jupiter.api.*;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.ResponseEntity;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@SpringBootTest
public class WalletControllerTest {
    @Mock
    WalletService walletService;

    @InjectMocks
    WalletController walletController;

    static WalletDTO walletDTO;
    static String uuid="4ea8d54b-a48d-4a27-ae8d-6a0ea6a3fb4c";
    static long amount=1000L;
    static OperationDTO operationDTO;

    @BeforeEach
    void before() {
        walletDTO=new WalletDTO();
        walletDTO.setUuid(uuid);
        walletDTO.setAmount(amount);
        operationDTO=new OperationDTO();
    }

    @Test
    @DisplayName("GetWalletAmount")
    public void getWalletAmountTest() {
        when(walletService.getById(any())).thenReturn(walletDTO);
        Assertions.assertEquals(ResponseEntity.ok(walletDTO), walletController.getByCode(uuid));
    }

    @Test
    @DisplayName("GetNoSuchWallet")
    public void getNoSuchWalletTest() {
        when(walletService.getById(any()))
                .thenThrow(new WalletApiWalletNotFoundException("No data found with specified parameters"));
        Assertions.assertThrows(WalletApiWalletNotFoundException.class, () -> walletController.getByCode(uuid));
    }

    @Test
    @DisplayName("PostDeposit")
    public void postDepositTest() {
        operationDTO.setOperationType("DEPOSITE");
        when(walletService.getById(any())).thenReturn(walletDTO);
        when(walletService.changeWalletAmount(any())).thenReturn(walletDTO);
        Assertions.assertEquals(ResponseEntity.ok(walletDTO), walletController.changeAmount(operationDTO));
    }

    @Test
    @DisplayName("PostDepositEmptyBody")
    public void postDepositEmptyBodyTest() {
        Assertions.assertThrows(WalletApiWalletNotFoundException.class, () -> walletController.changeAmount(null));
    }

    @Test
    @DisplayName("PostDepositNoWallet")
    public void postDepositNoWalletTest() {
        when(walletService.getById(any())).thenReturn(null);
        when(walletService.changeWalletAmount(any())).thenReturn(null);
        Assertions.assertThrows(WalletApiWalletNotFoundException.class, () -> walletController.changeAmount(null));
    }
}
