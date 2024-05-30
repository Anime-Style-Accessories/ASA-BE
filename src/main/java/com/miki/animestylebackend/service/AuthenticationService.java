package com.miki.animestylebackend.service;

import com.miki.animestylebackend.dto.AuthenticationData;
import com.miki.animestylebackend.dto.AuthenticationRequest;
import com.miki.animestylebackend.dto.AuthenticationResponse;
import com.miki.animestylebackend.dto.RegisterRequest;
import com.miki.animestylebackend.exception.BadRequestException;
import com.miki.animestylebackend.exception.InvalidUsernameOrPassword;
import com.miki.animestylebackend.mapper.UserMapper;
import com.miki.animestylebackend.model.Role;
import com.miki.animestylebackend.model.Token;
import com.miki.animestylebackend.repository.TokenRepository;
import com.miki.animestylebackend.model.TokenType;
import com.miki.animestylebackend.model.User;
import com.miki.animestylebackend.repository.UserRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class AuthenticationService {
    private final UserRepository repository;
    private final TokenRepository tokenRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;
    private final UserMapper userMapper;

    public AuthenticationResponse register(RegisterRequest request) {
        if (repository.existsByEmail(request.getEmail())) {
            throw new BadRequestException("Username is already taken!");
        }
        var user = User.builder()
                .firstname(request.getFirstname())
                .lastname(request.getLastname())
                .email(request.getEmail())
                .password(passwordEncoder.encode(request.getPassword()))
                .role(Role.CUSTOMER)
                .build();
        var savedUser = repository.save(user);
        var jwtToken = jwtService.generateToken(user);
        var refreshToken = jwtService.generateRefreshToken(user);
        saveUserToken(savedUser, jwtToken);
        AuthenticationData authenticationData = AuthenticationData.builder()
                .accessToken(jwtToken)
                .refreshToken(refreshToken)
                .tokenType("Bearer")
                .user(userMapper.toUserData(user))
                .build();
        return AuthenticationResponse.builder()
                .data(authenticationData)
                .status(HttpStatus.OK)
                .message("User registered successfully")
                .success(true)
                .timestamp(LocalDateTime.now())
                .build();
    }

    public AuthenticationResponse authenticate(AuthenticationRequest request) {
        try {
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            request.getEmail(),
                            request.getPassword()
                    )
            );
            var user = repository.findByEmail(request.getEmail())
                    .orElseThrow(() -> new BadRequestException("User not found"));
            var jwtToken = jwtService.generateToken(user);
            var refreshToken = jwtService.generateRefreshToken(user);
            revokeAllUserTokens(user);
            saveUserToken(user, jwtToken);
            AuthenticationData authenticationData = AuthenticationData.builder()
                    .accessToken(jwtToken)
                    .refreshToken(refreshToken)
                    .tokenType("Bearer")
                    .user(userMapper.toUserData(user))
                    .build();
            return AuthenticationResponse.builder()
                    .data(authenticationData)
                    .status(HttpStatus.OK)
                    .message("User authenticated successfully")
                    .success(true)
                    .timestamp(LocalDateTime.now())
                    .build();
        } catch (AuthenticationException e) {
            throw new InvalidUsernameOrPassword("Incorrect email or password");
        }
    }

    private void saveUserToken(User user, String jwtToken) {
        var token = Token.builder()
                .user(user)
                .token(jwtToken)
                .tokenType(TokenType.BEARER)
                .expired(false)
                .revoked(false)
                .build();
        tokenRepository.save(token);
    }

    private void revokeAllUserTokens(User user) {
        var validUserTokens = tokenRepository.findAllValidTokenByUser(user.getId());
        if (validUserTokens.isEmpty())
            return;
        validUserTokens.forEach(token -> {
            token.setExpired(true);
            token.setRevoked(true);
        });
        tokenRepository.saveAll(validUserTokens);
    }

    public void refreshToken(
            HttpServletRequest request,
            HttpServletResponse response
    ) throws IOException {
        final String authHeader = request.getHeader(HttpHeaders.AUTHORIZATION);
        final String refreshToken;
        final String userEmail;
        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            return;
        }
        refreshToken = authHeader.substring(7);
        userEmail = jwtService.extractUsername(refreshToken);
        if (userEmail != null) {
            var user = this.repository.findByEmail(userEmail)
                    .orElseThrow();
            if (jwtService.isTokenValid(refreshToken, user)) {
                var accessToken = jwtService.generateToken(user);
                revokeAllUserTokens(user);
                saveUserToken(user, accessToken);
                AuthenticationData authenticationData = AuthenticationData.builder()
                        .accessToken(accessToken)
                        .refreshToken(refreshToken)
                        .tokenType("Bearer")
                        .user(userMapper.toUserData(user))
                        .build();
                var authResponse = AuthenticationResponse.builder()
                        .data(authenticationData)
                        .status(HttpStatus.OK)
                        .message("Token refreshed successfully")
                        .success(true)
                        .timestamp(LocalDateTime.now())
                        .build();
                new ObjectMapper().writeValue(response.getOutputStream(), authResponse);
            }
        }
    }
}
