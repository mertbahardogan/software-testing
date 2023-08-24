package com.software.testing.service;

import com.software.testing.core.exception.ControllerException;
import com.software.testing.model.User;

import com.software.testing.dto.UserDTO;
import java.util.Optional;

public interface UserService {

    UserDTO saveUser(UserDTO userDTO) throws ControllerException;
    Optional<User> findByEmail(String email);
}