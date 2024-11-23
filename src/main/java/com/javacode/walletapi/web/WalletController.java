package com.javacode.walletapi.web;

import com.javacode.walletapi.data.ErrorResponse;
import com.javacode.walletapi.errors.WalletApiBadParameterException;
import com.javacode.walletapi.errors.WalletApiInsufficientFundsException;
import com.javacode.walletapi.errors.WalletApiWalletNotFoundException;
import com.javacode.walletapi.service.WalletService;
import com.javacode.walletapi.web.dto.OperationDTO;
import com.javacode.walletapi.web.dto.WalletDTO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Objects;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1")
@Log4j2
@Tag(name= "com.javacode.walletapi.walletcontroller", description = "Wallet controller")
public class WalletController {
    private final WalletService walletService;

    @Operation(summary = "Get wallet amount by id")
    @GetMapping(value = "/wallets/{uuid}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<WalletDTO> getByCode(@PathVariable
                                                   @Parameter(description = "Wallet UUID",
                                                   example = "4ea8d54b-a48d-4a27-ae8d-6a0ea6a3fb4c")
                                                   String uuid) {
        WalletDTO response = walletService.getById(uuid);
        if (Objects.isNull(response)) {
            throw new WalletApiWalletNotFoundException("No data found");
        }
        log.info("Get request for wallet uuid={} successfully processed", uuid);
        return ResponseEntity.ok(response);
    }

    @Operation(summary = "Change wallet amount")
    @PostMapping(value = "/wallet", consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<WalletDTO> changeAmount(@RequestBody(required = false) OperationDTO dto) {
        if (Objects.isNull(dto)) {
            throw new WalletApiWalletNotFoundException("Request without body");
        }
        if (Objects.isNull(walletService.getById(dto.getWalletId()))) {
            throw new WalletApiWalletNotFoundException("No data found with specified parameters");
        }
        WalletDTO response = walletService.changeWalletAmount(dto);
        log.info("Post request for wallet uuid={} successfully processed", response.getUuid());
        return ResponseEntity.ok(response);
    }

    @ExceptionHandler(WalletApiBadParameterException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseEntity<ErrorResponse> handleBadRequest(WalletApiBadParameterException ex) {
        log.warn("Bad request exception registered. {}", ex.getMessage());
        return new ResponseEntity<>(new ErrorResponse(HttpStatus.BAD_REQUEST, ex), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(WalletApiWalletNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ResponseEntity<ErrorResponse> handleBadRequest(WalletApiWalletNotFoundException ex) {
        log.warn("NotFound exception registered. {}", ex.getMessage());
        return new ResponseEntity<>(new ErrorResponse(HttpStatus.NOT_FOUND, ex), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(WalletApiInsufficientFundsException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseEntity<ErrorResponse> handleInsufficientFunds(WalletApiInsufficientFundsException ex) {
        log.warn("Insufficient funds exception registered. {}", ex.getMessage());
        return new ResponseEntity<>(new ErrorResponse(HttpStatus.BAD_REQUEST, ex), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(RuntimeException.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ResponseEntity<ErrorResponse> handleInternalServerError(RuntimeException ex) {
        log.error("Exception: {}", ex.getMessage());
        return new ResponseEntity<>(new ErrorResponse(HttpStatus.INTERNAL_SERVER_ERROR, ex),
                HttpStatus.INTERNAL_SERVER_ERROR);
    }
}