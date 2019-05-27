package com.interlink.quiz.controller;

import com.interlink.quiz.object.UserRegistrationForm;
import com.interlink.quiz.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class RegistrationController {

    private final UserService userService;

    @Autowired
    public RegistrationController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/registration")
    @ResponseBody
    public String register(@RequestBody UserRegistrationForm userRegistrationForm) {
        userService.register(userRegistrationForm);

        return "OK";
    }
}