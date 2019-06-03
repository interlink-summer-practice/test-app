package com.interlink.quiz.controller;

import com.interlink.quiz.object.UserDto;
import com.interlink.quiz.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class RegistrationController {

    private final UserService userService;

    @Autowired
    public RegistrationController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/registration")
    public String register(@RequestBody UserDto userDto) {
        userService.register(userDto);

        return "OK";
    }
}