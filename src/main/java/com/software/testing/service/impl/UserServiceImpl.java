package com.software.testing.service.impl;

import static com.software.testing.core.constant.ErrorConstant.E_GENERAL_SYSTEM;
import static com.software.testing.core.constant.ErrorConstant.E_USER_ALREADY_REGISTERED;
import static com.software.testing.core.constant.ErrorConstant.E_USER_EMAIL_MUST_NOT_BE_NULL;

import com.software.testing.core.exception.ControllerException;
import com.software.testing.model.User;
import com.software.testing.repository.UserRepository;
import com.software.testing.service.UserService;
import java.util.Objects;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    @Override
    public String saveUser(User user) throws ControllerException {
        validateUser(user);
        try {
            User savedUser = userRepository.save(user);
            return savedUser.getEmail();
        } catch (Exception exception) {
            throw new ControllerException(E_GENERAL_SYSTEM);
        }
    }

    private void validateUser(User user) throws ControllerException {
        if (Objects.isNull(user.getEmail())) {
            throw new ControllerException(E_USER_EMAIL_MUST_NOT_BE_NULL);
        }
        if (findByEmail(user.getEmail()).isPresent()) {
            throw new ControllerException(E_USER_ALREADY_REGISTERED);
        }
    }

    @Override
    public Optional<User> findByEmail(String email) {
        return userRepository.findByEmail(email);
    }
}
