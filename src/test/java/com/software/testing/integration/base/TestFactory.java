package com.software.testing.integration.base;

import com.software.testing.dto.UserDTO;
import com.software.testing.model.User;


public class TestFactory {

    public static final String USER_API_ENDPOINT = "/user";

    public UserDTO generateUserDTO() {
        return new UserDTO()
                .setUserName("userName")
                .setEmail("userEmail")
                .setPassword("userPassword");
    }

    public User generateUser() {
        return new User()
                .setUserName("userName")
                .setEmail("userEmail")
                .setPassword("userPassword");
    }
}