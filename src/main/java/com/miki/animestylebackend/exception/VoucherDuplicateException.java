package com.miki.animestylebackend.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.CONFLICT)
public class VoucherDuplicateException extends RuntimeException {
    public VoucherDuplicateException(String message) {
        super(message);
    }
}
