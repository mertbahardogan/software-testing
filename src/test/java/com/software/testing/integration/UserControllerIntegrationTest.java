package com.software.testing.integration;

import static com.software.testing.core.constant.ErrorConstant.E_USER_ALREADY_REGISTERED;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import com.software.testing.core.exception.ControllerException;
import com.software.testing.dto.UserDTO;
import com.software.testing.integration.base.AbstractIntegrationTest;
import com.software.testing.model.User;
import com.software.testing.repository.UserRepository;
import com.software.testing.service.UserService;
import jakarta.annotation.PostConstruct;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;


class UserControllerIntegrationTest extends AbstractIntegrationTest {

    @Autowired
    private UserService userService;
    @Autowired
    private UserRepository userRepository;

    @PostConstruct
    public void init() {
        User user = generateUser();
        userRepository.save(user);
    }

    @Test
    void givenCorrectUserDTO_whenSaveUser_thenReturnUserDTO() throws Exception {
        // given
        UserDTO userDTO = generateUserDTO();

        // when
        UserDTO savedUserDTO = performPostRequestExpectedSuccess(USER_API_ENDPOINT, userDTO, UserDTO.class);

        //then
        assertNotNull(savedUserDTO);
        assertEquals("userEmail", savedUserDTO.getEmail());
        assertEquals("userName", savedUserDTO.getUserName());
        assertEquals("userPassword", savedUserDTO.getPassword());
    }

    @Test
    void givenCorrectUserDTO_whenSaveUser_thenReturnControllerException() throws Exception {
        // given
        UserDTO userDTO = generateUserDTO();

        // when
        ControllerException exception = performPostRequestExpectedControllerException(USER_API_ENDPOINT, userDTO, ControllerException.class);

        //then
        assertNotNull(exception);
        assertEquals(E_USER_ALREADY_REGISTERED, exception.getErrorMessage());
    }

}