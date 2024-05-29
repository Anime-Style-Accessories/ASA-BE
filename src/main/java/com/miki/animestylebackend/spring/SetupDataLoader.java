package com.miki.animestylebackend.spring;

import com.miki.animestylebackend.model.Role;
import com.miki.animestylebackend.model.User;
import com.miki.animestylebackend.repository.UserRepository;
import com.miki.animestylebackend.service.UserService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Slf4j
public class SetupDataLoader implements ApplicationListener<ContextRefreshedEvent> {

    private final boolean alreadySetup = false;
    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    UserService userService;
    @Autowired
    UserRepository userRepository;
    @Value("${app.password.default}")
    private String defaultPassword;

    @Override
    @Transactional
    public void onApplicationEvent(ContextRefreshedEvent event) {
        if (alreadySetup) {
            return;
        }

        try {
            createUserIfNotFound("admin@gmail.com", Role.ADMIN);

            createUserIfNotFound("staff@gmail.com", Role.STAFF);

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Transactional
    void createUserIfNotFound(String username, Role role) {
        if (!userRepository.existsByEmail(username)) {
            User newUser = userRepository.save(
                    User.builder()
                            .email(username)
                            .role(role)
                            .password(passwordEncoder.encode(defaultPassword))
                            .build()
            );
        }

    }
}
