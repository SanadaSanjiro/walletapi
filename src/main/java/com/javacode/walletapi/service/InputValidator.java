package com.javacode.walletapi.service;

import com.javacode.walletapi.web.dto.OperationDTO;
import org.springframework.stereotype.Service;

import java.util.regex.Pattern;

/**
 * Provides methods to check incoming objects
 */
@Service
public class InputValidator {
    private final Pattern UUID_REGEX =
            Pattern.compile("^[0-9a-fA-F]{8}-[0-9a-fA-F]{4}-[0-9a-fA-F]{4}" +
                    "-[0-9a-fA-F]{4}-[0-9a-fA-F]{12}$");

    /**
     * Checks uuid
     * @param input uuid should be in hexadecimal digits, meaning it uses the numbers 0 through 9
     *              and letters A through F. The hexadecimal digits are grouped as 32 hexadecimal characters with
     *              four hyphens: XXXXXXXX-XXXX-XXXX-XXXX-XXXXXXXXXXXX.
     * @return true, if the input meets the requirements
     */
    public boolean isValidUUID(String input) {
        return UUID_REGEX.matcher(input).matches();
    }

    /**
     * Checks incoming DTO with operations
     * @param dto A valid dto must contain a valid uuid string, one of the operations in string format,
     *            and a positive, non-zero, amount
     * @return true if all conditions are met
     */
    public boolean isValidWalletDTO(OperationDTO dto) {
        return isValidUUID(dto.getWalletId()) &&
               Operation.fromString(dto.getOperationType()).isPresent() && dto.getAmount() > 0;
    }
}
