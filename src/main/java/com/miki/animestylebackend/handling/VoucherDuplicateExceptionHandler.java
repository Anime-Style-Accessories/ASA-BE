package com.miki.animestylebackend.handling;

import com.miki.animestylebackend.dto.ErrorResponse;
import com.miki.animestylebackend.exception.VoucherDuplicateException;
import com.miki.animestylebackend.exception.VoucherNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;

import java.time.LocalDateTime;

@ControllerAdvice
public class VoucherDuplicateExceptionHandler {
    @ExceptionHandler(VoucherDuplicateException.class)
    public ResponseEntity<ErrorResponse> handleVoucherNotFoundException(VoucherDuplicateException ex, WebRequest request) {
        ErrorResponse errorResponse = new ErrorResponse(
                HttpStatus.CONFLICT.value(),
                ex.getMessage(),
                LocalDateTime.now(),
                request.getDescription(false)
        );
        return new ResponseEntity<>(errorResponse, HttpStatus.CONFLICT);
    }
}
