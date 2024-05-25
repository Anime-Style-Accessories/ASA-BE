package com.miki.animestylebackend.handling;

import com.miki.animestylebackend.dto.ErrorResponse;
import com.miki.animestylebackend.exception.OrderNotFoundException;
import com.miki.animestylebackend.exception.VoucherNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;

import java.time.LocalDateTime;

@ControllerAdvice
public class VoucherNotFoundExceptionHandler {
    @ExceptionHandler(VoucherNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleVoucherNotFoundException(VoucherNotFoundException ex, WebRequest request) {
        ErrorResponse errorResponse = new ErrorResponse(
                HttpStatus.NOT_FOUND.value(),
                ex.getMessage(),
                LocalDateTime.now(),
                request.getDescription(false)
        );
        return new ResponseEntity<>(errorResponse, HttpStatus.NOT_FOUND);
    }
}