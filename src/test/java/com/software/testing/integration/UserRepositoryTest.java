package com.software.testing.integration;

import com.software.testing.model.User;
import com.software.testing.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
class UserRepositoryTest {

    @Autowired
    private UserRepository userRepository;


    @Test
    void givenUserEntity_whenSaveUser_thenUserIsPersisted() {
        // given
        User user = new User()
                .setId(1L)
                .setUserName("user")
                .setPassword("password")
                .setEmail("email");

        // when
        userRepository.save(user);

        // then
        Optional<User> retrievedUser = userRepository.findById(1L);
        assertTrue(retrievedUser.isPresent());
        assertEquals("user", retrievedUser.get().getUserName());
    }
}