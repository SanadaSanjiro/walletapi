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

    /**
     * Takes a wallet from repository by it uuid
     * @param uuid should be in hexadecimal digits, meaning it uses the numbers 0 through 9
     *             and letters A through F. The hexadecimal digits are grouped as 32 hexadecimal characters with
     *             four hyphens: XXXXXXXX-XXXX-XXXX-XXXX-XXXXXXXXXXXX.
     * @return Optional Wallet
     */
    public Optional<Wallet> getByUuid(String uuid) { return repository.getByUuid(uuid); }

    public Wallet create(Wallet wallet) {
        return repository.save(wallet);
    }

    public Wallet update(Wallet wallet) {
        return repository.save(wallet);
    }
}
