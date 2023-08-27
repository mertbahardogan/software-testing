package com.software.testing.service.impl;

import static com.software.testing.core.constant.ErrorConstant.E_GENERAL_SYSTEM;
import static com.software.testing.core.constant.ErrorConstant.E_USER_ALREADY_REGISTERED;
import static com.software.testing.core.constant.ErrorConstant.E_USER_EMAIL_MUST_NOT_BE_NULL;

import com.software.testing.core.exception.ControllerException;
import com.software.testing.core.util.Validate;
import com.software.testing.dto.UserDTO;
import com.software.testing.dto.converter.UserConverter;
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
    private final UserConverter userConverter;

    @Override
    public UserDTO saveUser(UserDTO userDTO) throws ControllerException {
        validateUserDTO(userDTO);
        User user = userConverter.convertToUser(userDTO);
        try {
            return userConverter.convertToUserDTO(userRepository.save(user));
        } catch (Exception exception) {
            throw new ControllerException(E_GENERAL_SYSTEM);
        }
    }

    private void validateUserDTO(UserDTO userDTO) throws ControllerException {
        Validate.stateNot(Objects.isNull(userDTO.getEmail()), E_USER_EMAIL_MUST_NOT_BE_NULL);
        Validate.stateNot(findByEmail(userDTO.getEmail()).isPresent(), E_USER_ALREADY_REGISTERED);
    }

    @Override
    public Optional<User> findByEmail(String email) {
        return userRepository.findByEmail(email);
    }
}