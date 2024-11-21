package com.javacode.walletapi.web;

import com.javacode.walletapi.service.WalletService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1")
@Tag(name= "com.javacode.walletapi.walletcontroller", description = "Wallet controller")
public class WalletController {
    private final WalletService walletService;

    @Operation(summary = "Get wallet amount by id")
    @GetMapping(value = "/wallets/{uuid}", produces = MediaType.APPLICATION_JSON_VALUE)
    public WalletDTO getByCode(@PathVariable String uuid) {
        return walletService.getById(uuid);
    }

    @Operation(summary = "Change wallet amount")
    @PostMapping(value = "/wallet", consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public WalletDTO create(@RequestBody OperationDTO dto) {
        return walletService.walletOp(dto);
    }
}
