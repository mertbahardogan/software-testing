package com.software.testing.unit.service;

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
import com.software.testing.dto.UserDTO;
import com.software.testing.dto.converter.UserConverter;
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
    @Mock
    private UserConverter userConverter;


    private UserDTO userDTO;
    public static final String MOCK_EMAIL = "mert@bahardogan.com";

    @BeforeEach
    void setUp() {
        System.out.println("setUp");
        userDTO = new UserDTO();
    }

    @AfterEach
    void tearDown() {
        System.out.println("tearDown");
    }

    @ParameterizedTest
    @ValueSource(strings = {"mert@bahardogan.com", "info@gmail.com"})
    @DisplayName("Happy Path Test: save user use cases")
    void givenCorrectUserDTO_whenSaveUser_thenReturnUserDTO(String email) throws ControllerException {
        // given
        userDTO.setUserName("mertbahardogan").setEmail(email).setPassword("pass");
        User savedUser = new User().setEmail(email);
        doReturn(savedUser).when(userRepository).save(any());
        doReturn(userDTO).when(userConverter).convertToUserDTO(any());

        // when
        UserDTO saveUser = userService.saveUser(userDTO);

        // then
        verify(userRepository, times(1)).findByEmail(anyString());
        verify(userRepository, times(1)).save(any());
        assertEquals(email, saveUser.getEmail());
    }

    @Test
    @DisplayName("Exception Test: user email must not be null case")
    void givenMissingUserDTO_whenSaveUser_thenThrowEmailMustNotNullEx() {
        // when
        ControllerException exception = assertThrows(ControllerException.class, () -> userService.saveUser(userDTO));

        // then
        assertNotNull(exception);
        assertEquals(E_USER_EMAIL_MUST_NOT_BE_NULL, exception.getErrorMessage());
    }

    @Test
    @DisplayName("Exception Test: user is already registered case")
    void givenRegisteredUserDTO_whenSaveUser_thenThrowUserAlreadyRegisteredEx() {
        // given
        userDTO.setEmail(MOCK_EMAIL);
        Optional<User> savedUser = Optional.of(new User().setEmail(MOCK_EMAIL));
        doReturn(savedUser).when(userRepository).findByEmail(anyString());

        // when
        ControllerException exception = assertThrows(ControllerException.class, () -> userService.saveUser(userDTO));

        // then
        assertNotNull(exception);
        assertEquals(E_USER_ALREADY_REGISTERED, exception.getErrorMessage());
    }

    @Test
    @DisplayName("Happy Path Test: find user by email")
    void givenCorrectUserDTO_whenFindByEmail_thenReturnUserEmail() {
        // given
        Optional<User> savedUser = Optional.of(new User().setEmail(MOCK_EMAIL));
        doReturn(savedUser).when(userRepository).findByEmail(anyString());

        // when
        Optional<User> user = userService.findByEmail(MOCK_EMAIL);

        // then
        verify(userRepository, times(1)).findByEmail(anyString());
        assertEquals(savedUser, user);
    }
}