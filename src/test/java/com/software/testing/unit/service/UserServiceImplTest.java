package com.software.testing.unit.service;

import com.software.testing.model.User;
import com.software.testing.repository.UserRepository;
import com.software.testing.service.impl.UserServiceImpl;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static com.software.testing.core.ErrorConstant.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UserServiceImplTest {

    @InjectMocks
    private UserServiceImpl userService;

    @Mock
    private UserRepository userRepository;

    private User user;
    public static final String MOCK_EMAIL = "mert@bahardogan.com";

    @BeforeEach
    void setUp() {
        user = new User();
        System.out.println("init");
    }

    @AfterEach
    void teardown() {
        System.out.println("teardown");
    }

    @ParameterizedTest
    @ValueSource(strings = {"mert@bahardogan.com", "info@gmail.com"})
    @DisplayName("Happy Path: save user use cases")
    void givenCorrectUser_whenSaveUser_thenReturnUserEmail(String email) {
        // given
        user.setUserName("mertbahardogan").setEmail(email).setPassword("pass");
        User savedUser = new User().setEmail(email);
        doReturn(savedUser).when(userRepository).save(any());

        // when
        String savedUserEmail = userService.saveUser(user);

        // then
        verify(userRepository,times(1)).findByEmail(anyString());
        verify(userRepository,times(1)).save(any());
        assertEquals(email, savedUserEmail);
    }

    @Test
    @DisplayName("Exception Test: user email must not be null case")
    void givenNullUserEmail_whenSaveUser_thenThrowsEmailMustNotNullEx() {
        // when
        Exception exception = assertThrows(IllegalArgumentException.class, () -> userService.saveUser(user));

        // then
        assertNotNull(exception);
        assertEquals(E_USER_EMAIL_MUST_NOT_BE_NULL, exception.getMessage());
    }

    @Test
    @DisplayName("Exception Test: user is already registered case")
    void givenRegisteredUser_whenSaveUser_thenThrowsUserAlreadyRegisteredEx() {
        // given
        user.setEmail(MOCK_EMAIL);
        Optional<User> savedUser = Optional.of(new User().setEmail(MOCK_EMAIL));
        doReturn(savedUser).when(userRepository).findByEmail(anyString());

        // when
        Exception exception = assertThrows(IllegalArgumentException.class, () -> userService.saveUser(user));

        // then
        assertNotNull(exception);
        assertEquals(E_USER_ALREADY_REGISTERED, exception.getMessage());
    }

    @Test
    @DisplayName("Exception Test: catch case")
    void givenIncorrectDependencies_whenSaveUser_thenThrowsGeneralSystemEx() {
        // given
        user.setEmail(MOCK_EMAIL);

        // when
        Exception exception = assertThrows(IllegalArgumentException.class, () -> userService.saveUser(user));

        // then
        assertNotNull(exception);
        assertEquals(E_GENERAL_SYSTEM, exception.getMessage());
    }

    @Test
    @DisplayName("Happy Path: find user by email")
    void givenCorrectUser_whenFindByEmail_thenReturnUserEmail() {
        // given
        Optional<User> savedUser = Optional.of(new User().setEmail(MOCK_EMAIL));
        doReturn(savedUser).when(userRepository).findByEmail(anyString());

        // when
        Optional<User> user = userService.findByEmail(MOCK_EMAIL);

        // then
        verify(userRepository,times(1)).findByEmail(anyString());
        assertEquals(savedUser, user);
    }
}