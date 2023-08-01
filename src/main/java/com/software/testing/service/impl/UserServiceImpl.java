package com.software.testing.service.impl;

import com.software.testing.model.User;
import com.software.testing.repository.UserRepository;
import com.software.testing.service.UserService;
import org.springframework.stereotype.Service;

import java.util.Objects;
import java.util.Optional;

import static com.software.testing.core.ErrorConstant.*;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public String saveUser(User user) {
        validateUser(user);
        try {
            User savedUser = userRepository.save(user);
            return savedUser.getEmail();
        } catch (Exception exception) {
            throw new IllegalArgumentException(E_GENERAL_SYSTEM);
        }
    }

    private void validateUser(User user) {
        if (Objects.isNull(user.getEmail())) {
            throw new IllegalArgumentException(E_USER_EMAIL_MUST_NOT_BE_NULL);
        }
        Optional<User> existUser = findByEmail(user.getEmail());
        if (existUser.isPresent()) {
            throw new IllegalArgumentException(E_USER_ALREADY_REGISTERED);
        }
    }

    @Override
    public Optional<User> findByEmail(String email) {
        return userRepository.findByEmail(email);
    }
}
