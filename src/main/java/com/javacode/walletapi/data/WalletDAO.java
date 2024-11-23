package com.javacode.walletapi.data;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class WalletDAO {
    @Autowired
    private final WalletRepository repository;

    public Optional<Wallet> getById(int id) {
        return repository.findById(id);
    }

    public Optional<Wallet> getByUuid(String uuid) { return repository.getByUuid(uuid); }

    public Wallet create(Wallet wallet) {
        return repository.save(wallet);
    }

    public Wallet update(Wallet wallet) {
        return repository.save(wallet);
    }
}
