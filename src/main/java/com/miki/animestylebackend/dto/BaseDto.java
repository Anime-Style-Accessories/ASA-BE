package com.miki.animestylebackend.dto;

import lombok.Getter;
import lombok.Setter;
import org.springframework.http.HttpStatus;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
public abstract class BaseDto<T> {
    private boolean success;
    private int status;
    private String message;
    private LocalDateTime timestamp;
    private T data;
}