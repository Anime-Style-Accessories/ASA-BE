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

    @JsonProperty("access_token")
    private String accessToken;
    @JsonProperty("refresh_token")
    private String refreshToken;
    private String tokenType;
    private UserData user;
    private boolean success;
    private HttpStatus status;
    private String message;
    private LocalDateTime timestamp = LocalDateTime.now();
}
