package com.miki.animestylebackend.controller;

import com.miki.animestylebackend.dto.ChangePasswordRequest;
import com.miki.animestylebackend.dto.UserDto;
import com.miki.animestylebackend.dto.page.PageData;
import com.miki.animestylebackend.mapper.UserMapper;
import com.miki.animestylebackend.model.User;
import com.miki.animestylebackend.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
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

    @GetMapping("/getUserByUsername")
    public ResponseEntity<UserDto> getUserByUsername(@RequestParam("username") String username) {
        return ResponseEntity.ok(mapper.toUserDto(service.getUserByUsername(username)));
    }

    @GetMapping("/getAllUsers")
//    @PreAuthorize("hasAuthority('admin:read')")
    public ResponseEntity<PageData<UserDto>> getUsers(
            @RequestParam(value = "email", required = false) String email,
            @RequestParam(value = "page", required = false, defaultValue = "0") int page,
            @RequestParam(value = "size", required = false, defaultValue = "10") int size) {
        if(email != null) {
            return ResponseEntity.ok(service.getUsersByEmailContaining(email, page, size));
        }
        return ResponseEntity.ok(
                service.getAllUsers(
                        page, size
                )
        );
    }


}
