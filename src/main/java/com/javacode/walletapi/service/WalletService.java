package com.javacode.walletapi.service;

import com.javacode.walletapi.web.OperationDTO;
import com.javacode.walletapi.web.WalletDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class WalletService {
    private static final long wallet = 0L;

    public WalletDTO getById(String uuid) {
        WalletDTO dto = new WalletDTO();
        dto.setBalance(wallet);
        dto.setUuid(uuid);
        return dto;
    }

    public WalletDTO walletOp(OperationDTO dto) {
        String op = dto.getOperationType();
        String uuid = dto.getWalletId();
        WalletDTO walletDTO = new WalletDTO();
        walletDTO.setBalance(wallet);
        walletDTO.setUuid(uuid);
        return walletDTO;
    }
}