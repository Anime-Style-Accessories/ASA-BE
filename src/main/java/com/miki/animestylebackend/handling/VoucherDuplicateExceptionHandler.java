package com.miki.animestylebackend.handling;

import com.miki.animestylebackend.exception.ErrorResponse;
import com.miki.animestylebackend.exception.VoucherDuplicateException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;

import java.time.LocalDateTime;

@ControllerAdvice
public class VoucherDuplicateExceptionHandler {
    @ExceptionHandler(VoucherDuplicateException.class)
    public ResponseEntity<ErrorResponse> handleVoucherNotFoundException(VoucherDuplicateException ex) {
        ErrorResponse errorResponse = new ErrorResponse(
                HttpStatus.CONFLICT,
                ex.getMessage(),
                LocalDateTime.now(),
                "Voucher already exists",
                false
        );
        return new ResponseEntity<>(errorResponse, HttpStatus.CONFLICT);
    }
}
