package com.miki.animestylebackend.controller;

import com.miki.animestylebackend.dto.ChangePasswordRequest;
import com.miki.animestylebackend.dto.UserDto;
import com.miki.animestylebackend.mapper.UserMapper;
import com.miki.animestylebackend.model.User;
import com.miki.animestylebackend.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/v1/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService service;
    private final UserMapper mapper;

    @PatchMapping
    public ResponseEntity<?> changePassword(
          @RequestBody ChangePasswordRequest request,
          Principal connectedUser
    ) {
        service.changePassword(request, connectedUser);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/get-user")
    public UserDto getUserByUsername(@RequestParam("username") String username) {
        return mapper.toUserDto(service.getUserByUsername(username));
    }

    @GetMapping("/getAllUsers")
    @PreAuthorize("hasAuthority('admin:read')")
    public List<UserDto> getUsers() {
        return service.getAllUsers().stream()
                .map(mapper::toUserDto)
                .collect(Collectors.toList());
    }
}
