package com.software.testing.controller;

import com.software.testing.core.exception.ControllerException;
import com.software.testing.dto.UserDTO;
import com.software.testing.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/user")
public class UserController {

    private final UserService userService;

    @PostMapping
    public ResponseEntity<UserDTO> saveUser(@RequestBody UserDTO userDTO) throws ControllerException {
        return new ResponseEntity<>(userService.saveUser(userDTO), HttpStatus.CREATED);
    }
}
