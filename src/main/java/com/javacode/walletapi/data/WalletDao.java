package com.javacode.walletapi.data;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class WalletDao {
    @Autowired
    private final WalletRepository repository;

    public Optional<Wallet> getById(int id) {
        return repository.findById(id);
    }

    private Wallet create(Wallet wallet) {
        return repository.save(wallet);
    }

    private Wallet update(Wallet wallet) {
        return repository.save(wallet);
    }
}
