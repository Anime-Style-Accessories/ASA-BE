package com.miki.animestylebackend.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.miki.animestylebackend.model.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;

import java.time.LocalDateTime;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AuthenticationResponse {
    private AuthenticationData data;
    private boolean success;
    private HttpStatus status;
    private String message;
    private LocalDateTime timestamp;
}
