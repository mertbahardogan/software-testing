package com.software.testing.service;

import com.software.testing.core.exception.ControllerException;
import com.software.testing.model.User;

import java.util.Optional;

public interface UserService {
    String saveUser(User user) throws ControllerException;

    Optional<User> findByEmail(String email);
}