package com.javacode.walletapi.web;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

/**
 * Data for incoming requests to change funds in the wallet
 */
@Data
public class OperationDTO {
    @Schema(description = "UUID of existing wallet", example = "4ea8d54b-a48d-4a27-ae8d-6a0ea6a3fb4c")
    private String walletId;
    @Schema(description = "Operation type. Supported DEPOSIT and WITHDRAW", example = "WITHDRAW")
    private String operationType;
    @Schema(description = "Change amount (int). Must be greater than zero", example = "1000")
    private int amount;
}
