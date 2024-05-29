package com.miki.animestylebackend.controller;


import com.miki.animestylebackend.exception.BadRequestException;
import com.miki.animestylebackend.model.User;
import com.miki.animestylebackend.repository.UserRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;


@Slf4j
public abstract class BaseController {
    @Autowired
    private UserRepository appUserRepository;

    protected User getCurrentUser() {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        log.info("Current user: " + username);
        User currentUser = appUserRepository.findByEmail(username).get();
        if (currentUser == null) {
            throw new BadRequestException("You aren't authorized to perform this operation.");
        }
        return currentUser;
    }

}
