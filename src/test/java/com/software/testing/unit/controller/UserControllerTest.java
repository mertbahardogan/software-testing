package com.software.testing.unit.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import com.software.testing.controller.UserController;
import com.software.testing.core.exception.ControllerException;
import com.software.testing.dto.UserDTO;
import com.software.testing.service.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

@ExtendWith(MockitoExtension.class)
class UserControllerTest {

    @InjectMocks
    private UserController userController;
    @Mock
    private UserService userService;
    public static final String MOCK_EMAIL = "mert@bahardogan.com";
    private UserDTO userDTO;

    @BeforeEach
    void setUp() {
        userDTO = new UserDTO();
    }

    @Test
    @DisplayName("Happy Path: save user use case")
    void givenCorrectUser_whenSaveUser_thenReturnUserEmail() throws ControllerException {
        // given
        userDTO = new UserDTO().setUserName("mertbahardogan").setEmail(MOCK_EMAIL).setPassword("pass");
        doReturn(new UserDTO().setEmail(MOCK_EMAIL)).when(userService).saveUser(any());

        // when
        ResponseEntity<UserDTO> savedUserEmail = userController.saveUser(userDTO);

        // then
        verify(userService, times(1)).saveUser(any());
        assertEquals(HttpStatus.CREATED, savedUserEmail.getStatusCode());
    }
}