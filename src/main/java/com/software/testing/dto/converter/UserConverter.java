package com.software.testing.dto.converter;

import com.software.testing.dto.UserDTO;
import com.software.testing.model.User;
import org.springframework.stereotype.Component;

@Component
public class UserConverter {

    public UserDTO convertToUserDTO(User user) {
        return new UserDTO()
                .setUserName(user.getUserName())
                .setEmail(user.getEmail())
                .setPassword(user.getPassword());
    }

    public User convertToUser(UserDTO userDTO) {
        return new User()
                .setUserName(userDTO.getUserName())
                .setEmail(userDTO.getEmail())
                .setPassword(userDTO.getPassword());
    }
}
