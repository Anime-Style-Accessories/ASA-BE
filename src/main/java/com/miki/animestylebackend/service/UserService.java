package com.miki.animestylebackend.service;

import com.miki.animestylebackend.dto.UpdateProfileRequest;
import com.miki.animestylebackend.dto.UserData;
import com.miki.animestylebackend.dto.UserDto;
import com.miki.animestylebackend.dto.page.PageData;
import com.miki.animestylebackend.exception.UnAuthorizedException;
import com.miki.animestylebackend.exception.UserNotFoundException;
import com.miki.animestylebackend.mapper.UserMapper;
import com.miki.animestylebackend.model.Role;
import com.miki.animestylebackend.repository.UserRepository;
import com.miki.animestylebackend.dto.ChangePasswordRequest;
import com.miki.animestylebackend.model.User;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Range;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.security.Principal;
import java.util.Collection;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

@Service
@RequiredArgsConstructor
public class UserService {

    private final PasswordEncoder passwordEncoder;
    private final UserRepository repository;
    private final UserMapper userMapper;
    private static final String HASH_KEY = "User";
    private final RedisTemplate<String, UserDto> redisTemplate;
    private static final long CACHE_TTL = 60;
    private final RedisTemplate<String, Integer> redisTemplateInteger;
    private static final int MAX_REQUESTS = 1;
    private static final int EXPIRE_TIME = 10;

    public void limitAccess(String username) {
        ValueOperations<String, Integer> operations = redisTemplateInteger.opsForValue();
        Integer currentCount = operations.get(username);
        if (currentCount == null) {
            operations.set(username, 1, EXPIRE_TIME, TimeUnit.SECONDS);
        } else if (currentCount < MAX_REQUESTS) {
            operations.increment(username);
        } else {
            throw new IllegalStateException("Exceeded maximum requests");
        }
    }
    public void changePassword(ChangePasswordRequest request, Principal connectedUser) {

        var user = (User) ((UsernamePasswordAuthenticationToken) connectedUser).getPrincipal();

        if (!passwordEncoder.matches(request.getCurrentPassword(), user.getPassword())) {
            throw new IllegalStateException("Wrong password");
        }
        if (!request.getNewPassword().equals(request.getConfirmationPassword())) {
            throw new IllegalStateException("Password are not the same");
        }

        user.setPassword(passwordEncoder.encode(request.getNewPassword()));

        repository.save(user);
    }

    public User getUserByUsername(String username) {
        return repository.findByEmail(username).orElseThrow(() -> new UserNotFoundException("User with email " + username + " not found"));
    }

    public PageData<UserData> getAllUsers(int page, int size) {

        Pageable pageable = Pageable.ofSize(size).withPage(page);

        Page<UserData> userDtoPage = repository.findAll(pageable)
                .map(userMapper::toUserData);

        return new PageData<>(userDtoPage, "Get all users successfully");
    }

    public PageData<UserData> getUsersByEmailContaining(String email, int page, int size, User currentUser) {
        if (!checkIfAdmin(currentUser)) {
            throw new UnAuthorizedException("You do not have permission to do this action");
        }

        Pageable pageable = Pageable.ofSize(size).withPage(page);

        Page<UserData> userDtoPage = repository.findByEmailContaining(email, pageable)
                .map(userMapper::toUserData);

        return new PageData<>(userDtoPage, "Users found by email successfully");
    }

    private boolean checkIfAdmin(User currentUser) {
        return currentUser.getRole() == Role.ADMIN;
    }

    public UserDto getUserProfile(UUID id, User currentUser) {
        limitAccess(currentUser.getUsername());
        User user = (User) redisTemplate.opsForHash().get(HASH_KEY, id);
        if (user != null) {
            redisTemplate.opsForHash().put(HASH_KEY, id, user);
            redisTemplate.expire(HASH_KEY, CACHE_TTL, TimeUnit.SECONDS);
            return userMapper.toUserDto(user, "Get user profile successfully");
        }
        if (id == currentUser.getId() || currentUser.getRole() == Role.ADMIN) {
            return userMapper.toUserDto(currentUser, "Get user profile successfully");
        } else {
            throw new UnAuthorizedException("You do not have permission to do this action");
        }
    }
    @Transactional
    public UserDto save(UpdateProfileRequest profile, User currentUser) {
        currentUser.setFirstname(profile.getFirstName());
        currentUser.setLastname(profile.getLastName());
        currentUser.setEmail(currentUser.getEmail());
        currentUser.setPhone(profile.getPhone());
        currentUser.setAddress(profile.getAddress());
        currentUser.setAvatar(profile.getAvatar());

        User savedUser = repository.save(currentUser);
        redisTemplate.opsForHash().put(HASH_KEY, currentUser.getId(), currentUser);
        redisTemplate.expire(HASH_KEY, CACHE_TTL, TimeUnit.SECONDS);
        return userMapper.toUserDto(savedUser, "Update user profile successfully");
    }
}
