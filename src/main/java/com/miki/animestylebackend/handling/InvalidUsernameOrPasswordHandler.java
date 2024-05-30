package com.miki.animestylebackend.handling;

import com.miki.animestylebackend.exception.ErrorResponse;
import com.miki.animestylebackend.exception.InvalidUsernameOrPassword;
import com.miki.animestylebackend.exception.OrderNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;

import java.time.LocalDateTime;

@ControllerAdvice
public class InvalidUsernameOrPasswordHandler{
    @ExceptionHandler(InvalidUsernameOrPassword.class)
    public ResponseEntity<ErrorResponse> handleInvalidUsernameOrPasswordException(InvalidUsernameOrPassword ex, WebRequest request) {
        ErrorResponse errorResponse = new ErrorResponse(
                HttpStatus.BAD_REQUEST.value(),
                ex.getMessage(),
                LocalDateTime.now(),
                request.getDescription(false),
                false
        );
        return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
    }
}
