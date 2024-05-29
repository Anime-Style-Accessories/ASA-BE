package com.miki.animestylebackend.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public abstract class BaseDto<T> {
    private boolean success;
    private int statusCode;
    private String message;
    private T data;
}