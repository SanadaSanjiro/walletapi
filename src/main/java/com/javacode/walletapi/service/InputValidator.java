package com.javacode.walletapi.service;

import com.javacode.walletapi.web.OperationDTO;
import org.springframework.stereotype.Service;

import java.util.Objects;
import java.util.regex.Pattern;

@Service
public class InputValidator {
    private final Pattern UUID_REGEX =
            Pattern.compile("^[0-9a-fA-F]{8}-[0-9a-fA-F]{4}-[0-9a-fA-F]{4}" +
                    "-[0-9a-fA-F]{4}-[0-9a-fA-F]{12}$");

    public boolean isValidUUID(String input) {
        return UUID_REGEX.matcher(input).matches();
    }

    public boolean isValidWalletDTO(OperationDTO dto) {
        return isValidUUID(dto.getWalletId()) &&
                !Objects.isNull(Operation.fromString(dto.getOperationType())) &&
                dto.getAmount() > 0L;
    }
}