package com.interlink.quiz.controller;

import com.interlink.quiz.object.dto.UserDto;
import com.interlink.quiz.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
public class RegistrationController {

    private final UserService userService;

    @Autowired
    public RegistrationController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/registration")
    public ResponseEntity<String> register(@Valid @RequestBody UserDto userDto) {
        if (userService.getUserByEmail(userDto.getEmail()) == null) {
            userService.register(userDto);

            return ResponseEntity.ok("User is valid");
        }

        return ResponseEntity.ok("This email already in use");
    }
}