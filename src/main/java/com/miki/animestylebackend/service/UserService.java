package com.miki.animestylebackend.service;

import com.miki.animestylebackend.dto.UpdateProfileRequest;
import com.miki.animestylebackend.dto.UserDto;
import com.miki.animestylebackend.dto.page.PageData;
import com.miki.animestylebackend.exception.UnAuthorizedException;
import com.miki.animestylebackend.exception.UserNotFoundException;
import com.miki.animestylebackend.mapper.UserMapper;
import com.miki.animestylebackend.model.Role;
import com.miki.animestylebackend.repository.UserRepository;
import com.miki.animestylebackend.dto.ChangePasswordRequest;
import com.miki.animestylebackend.model.User;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Range;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.security.Principal;
import java.util.Collection;
import java.util.List;
import java.util.UUID;

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

    public PageData<UserDto> getAllUsers(int page, int size) {

        Pageable pageable = Pageable.ofSize(size).withPage(page);

        Page<UserDto> userDtoPage = repository.findAll(pageable)
                .map(userMapper::toUserDto);

        return new PageData<>(userDtoPage);
    }

    public PageData<UserDto> getUsersByEmailContaining(String email, int page, int size, User currentUser) {
        if (!checkIfAdmin(currentUser)) {
            throw new UnAuthorizedException("You do not have permission to do this action");
        }

        Pageable pageable = Pageable.ofSize(size).withPage(page);

        Page<UserDto> userDtoPage = repository.findByEmailContaining(email, pageable)
                .map(userMapper::toUserDto);

        return new PageData<>(userDtoPage);
    }

    private boolean checkIfAdmin(User currentUser) {
        return currentUser.getRole() == Role.ADMIN;
    }

    public UserDto getUserProfile(UUID id, User currentUser) {
        if (id == currentUser.getId() || currentUser.getRole() == Role.ADMIN) {
            return userMapper.toUserDto(currentUser);
        } else {
            throw new UnAuthorizedException("You do not have permission to do this action");
        }
    }

    public UserDto save(UpdateProfileRequest profile, User currentUser) {
        currentUser.setFirstname(profile.getFirstName());
        currentUser.setLastname(profile.getLastName());
        currentUser.setEmail(profile.getEmail());
        currentUser.setPhone(profile.getPhone());
        currentUser.setAddress(profile.getAddress());
        currentUser.setAvatar(profile.getAvatar());

        User savedUser = repository.save(currentUser);
        return userMapper.toUserDto(savedUser);
    }
}
