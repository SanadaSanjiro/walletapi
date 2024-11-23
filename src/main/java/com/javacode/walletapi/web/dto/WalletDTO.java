package com.javacode.walletapi.web.dto;

import com.javacode.walletapi.data.Wallet;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

/**
 * Wallet data for requests
 */
@Data
public class WalletDTO {
    @Schema(description = "Wallet UUID", example = "4ea8d54b-a48d-4a27-ae8d-6a0ea6a3fb4c")
    private String uuid;
    @Schema(description = "Amount on the wallet (long)", example = "1000")
    private long amount;

    public WalletDTO(Wallet wallet) {
        this.uuid = wallet.getUuid();
        this.amount = wallet.getAmount();
    }
}
