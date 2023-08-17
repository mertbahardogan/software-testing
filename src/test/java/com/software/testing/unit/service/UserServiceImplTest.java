package com.software.testing.unit.service;

import static com.software.testing.core.constant.ErrorConstant.E_GENERAL_SYSTEM;
import static com.software.testing.core.constant.ErrorConstant.E_USER_ALREADY_REGISTERED;
import static com.software.testing.core.constant.ErrorConstant.E_USER_EMAIL_MUST_NOT_BE_NULL;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import com.software.testing.core.exception.ControllerException;
import com.software.testing.model.User;
import com.software.testing.repository.UserRepository;
import com.software.testing.service.impl.UserServiceImpl;
import java.util.Optional;
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
        System.out.println("setUp");
    }

    @AfterEach
    void tearDown() {
        System.out.println("tearDown");
    }

    @ParameterizedTest
    @ValueSource(strings = {"mert@bahardogan.com", "info@gmail.com"})
    @DisplayName("Happy Path: save user use cases")
    void givenCorrectUser_whenSaveUser_thenReturnUserEmail(String email) throws ControllerException {
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
        ControllerException exception = assertThrows(ControllerException.class, () -> userService.saveUser(user));

        // then
        assertNotNull(exception);
        assertEquals(E_USER_EMAIL_MUST_NOT_BE_NULL, exception.getErrorMessage());
    }

    @Test
    @DisplayName("Exception Test: user is already registered case")
    void givenRegisteredUser_whenSaveUser_thenThrowsUserAlreadyRegisteredEx() {
        // given
        user.setEmail(MOCK_EMAIL);
        Optional<User> savedUser = Optional.of(new User().setEmail(MOCK_EMAIL));
        doReturn(savedUser).when(userRepository).findByEmail(anyString());

        // when
        ControllerException exception = assertThrows(ControllerException.class, () -> userService.saveUser(user));

        // then
        assertNotNull(exception);
        assertEquals(E_USER_ALREADY_REGISTERED, exception.getErrorMessage());
    }

    @Test
    @DisplayName("Exception Test: catch case")
    void givenIncorrectDependencies_whenSaveUser_thenThrowsGeneralSystemEx() {
        // given
        user.setEmail(MOCK_EMAIL);

        // when
        ControllerException exception = assertThrows(ControllerException.class, () -> userService.saveUser(user));

        // then
        assertNotNull(exception);
        assertEquals(E_GENERAL_SYSTEM, exception.getErrorMessage());
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