package com.javacode.walletapi.data;

import lombok.Data;
import org.springframework.http.HttpStatus;

@Data
public class ErrorResponse {
    private final HttpStatus status;
    private final String error;

    public ErrorResponse(HttpStatus status, Exception e) {
        this.status = status;
        this.error = e.getMessage();
    }
}
