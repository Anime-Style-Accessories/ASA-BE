package com.miki.animestylebackend.service;

import com.miki.animestylebackend.dto.UserDto;
import com.miki.animestylebackend.exception.UserNotFoundException;
import com.miki.animestylebackend.mapper.UserMapper;
import com.miki.animestylebackend.repository.UserRepository;
import com.miki.animestylebackend.dto.ChangePasswordRequest;
import com.miki.animestylebackend.model.User;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.security.Principal;
import java.util.Collection;
import java.util.List;

@Service
@RequiredArgsConstructor
public class UserService {

    private final PasswordEncoder passwordEncoder;
    private final UserRepository repository;
    private final UserMapper userMapper;
    public void changePassword(ChangePasswordRequest request, Principal connectedUser) {

        var user = (User) ((UsernamePasswordAuthenticationToken) connectedUser).getPrincipal();

        // check if the current password is correct
        if (!passwordEncoder.matches(request.getCurrentPassword(), user.getPassword())) {
            throw new IllegalStateException("Wrong password");
        }
        // check if the two new passwords are the same
        if (!request.getNewPassword().equals(request.getConfirmationPassword())) {
            throw new IllegalStateException("Password are not the same");
        }

        // update the password
        user.setPassword(passwordEncoder.encode(request.getNewPassword()));

        // save the new password
        repository.save(user);
    }
    public User getUserByUsername(String username) {
        return repository.findByEmail(username).orElseThrow(() -> new UserNotFoundException(username));
    }

    public List<User> getAllUsers() {
        return repository.findAll();
    }
}
