package com.software.testing.integration;

import static com.software.testing.core.constant.ErrorConstant.E_USER_ALREADY_REGISTERED;
import static com.software.testing.core.constant.ErrorConstant.E_USER_EMAIL_MUST_NOT_BE_NULL;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import com.software.testing.dto.UserDTO;
import com.software.testing.integration.base.AbstractIntegrationTest;
import com.software.testing.model.User;
import com.software.testing.repository.UserRepository;
import jakarta.annotation.PostConstruct;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ProblemDetail;


class UserControllerIntegrationTest extends AbstractIntegrationTest {

    @Autowired
    private UserRepository userRepository;
    private UserDTO userDTO;

    @PostConstruct
    public void init() {
        userDTO = generateUserDTO();
    }

    @Test
    @DisplayName("Happy Path Test: save user and return user dto")
    void givenCorrectUserDTO_whenSaveUser_thenReturnUserDTO() throws Exception {
        // when
        UserDTO savedUserDTO = performPostRequestExpectedSuccess(USER_API_ENDPOINT, userDTO, UserDTO.class);

        //then
        assertNotNull(savedUserDTO);
        assertEquals("userEmail", savedUserDTO.getEmail());
        assertEquals("userName", savedUserDTO.getUserName());
        assertEquals("userPassword", savedUserDTO.getPassword());
    }

    @Test
    @DisplayName("Exception Test: user email must not be null case")
    void givenMissingEmail_whenSaveUser_thenThrowControllerException() throws Exception {
        // given
        userDTO.setEmail(null);

        // when
        ProblemDetail responseWithException = performPostRequestExpectedServerError(
                USER_API_ENDPOINT, userDTO, ProblemDetail.class);

        //then
        assertNotNull(responseWithException);
        assertEquals(E_USER_EMAIL_MUST_NOT_BE_NULL, responseWithException.getDetail());
    }

    @Test
    @DisplayName("Exception Test: user is already registered case")
    void givenRegisteredUserDTO_whenSaveUser_thenThrowControllerException() throws Exception {
        // given
        User user = generateUser();
        userRepository.save(user);

        // when
        ProblemDetail responseWithException = performPostRequestExpectedServerError(
                USER_API_ENDPOINT, userDTO, ProblemDetail.class);

        //then
        assertNotNull(responseWithException);
        assertEquals(E_USER_ALREADY_REGISTERED, responseWithException.getDetail());
    }
}