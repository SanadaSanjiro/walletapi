package com.javacode.walletapi.service;

import com.javacode.walletapi.data.Wallet;
import com.javacode.walletapi.data.WalletDAO;
import com.javacode.walletapi.errors.WalletApiBadParameterException;
import com.javacode.walletapi.errors.WalletApiInsufficientFundsException;
import com.javacode.walletapi.errors.WalletApiWalletNotFoundException;
import com.javacode.walletapi.web.OperationDTO;
import com.javacode.walletapi.web.WalletDTO;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

import java.util.Objects;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Log4j2
public class WalletService {
    private final WalletDAO dao;
    private final InputValidator validator;

    public WalletDTO getById(String uuid) {
        if (Objects.isNull(uuid) || !validator.isValidUUID(uuid)) {
            log.warn("Get: Not valid wallet UUID");
            throw new WalletApiBadParameterException("Not valid wallet UUID");
        }
        Optional<Wallet> optional = dao.getByUuid(uuid);
        if (optional.isEmpty()) {
            log.warn("Get: No data found with specified parameters");
            throw new WalletApiWalletNotFoundException("No data found with specified parameters");
        }
        Wallet wallet = optional.get();
        log.info("Get: Wallet id={} found with specified parameters", uuid);
        return new WalletDTO(wallet);
    }

    public WalletDTO changeWalletAmount(OperationDTO dto) {
        if (Objects.isNull(dto)) {
            log.warn("Post: Empty request");
            throw new WalletApiBadParameterException("Empty request");
        }
        if (!validator.isValidWalletDTO(dto)) {
            log.warn("Post: Not valid request");
            throw new WalletApiBadParameterException("Not valid request");
        }
        Optional<Wallet> optionalWallet = dao.getByUuid(dto.getWalletId());
        if (optionalWallet.isEmpty()) {
            log.warn("Post: No data found with specified parameters");
            throw new WalletApiWalletNotFoundException("No data found with specified parameters");
        }
        Optional<Operation> optionalOperation = Operation.fromString(dto.getOperationType());
        if (optionalOperation.isEmpty()) {
            log.warn("Post: Wrong operation type");
            throw new WalletApiWalletNotFoundException("Wrong operation type");
        }
        Wallet wallet = optionalWallet.get();
        Operation operation = optionalOperation.get();
        long newAmount = operation.apply(wallet.getAmount(), dto.getAmount());
        if (newAmount < 0 ) {
            log.warn("Post: Insufficient funds. Wallet id={}, amount={}", dto.getWalletId(), dto.getAmount());
            throw new WalletApiInsufficientFundsException("Insufficient funds");
        }
        wallet.setAmount(newAmount);
        log.info("Post: Operation {} successfully processed with the wallet id={}",
                optionalOperation.get(), dto.getWalletId());
        return new WalletDTO(dao.update(wallet));
    }
}