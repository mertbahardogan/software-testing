package com.software.testing.service;

import com.software.testing.model.User;

import java.util.Optional;

public interface UserService {
    String saveUser(User user);

    Optional<User> findByEmail(String email);
}