package com.interlink.quiz.auth.controller;

import com.interlink.quiz.auth.payload.ApiResponse;
import com.interlink.quiz.auth.payload.JwtAuthenticationResponse;
import com.interlink.quiz.auth.payload.LoginRequest;
import com.interlink.quiz.auth.security.JwtTokenProvider;
import com.interlink.quiz.auth.security.SignUpRequest;
import com.interlink.quiz.object.User;
import com.interlink.quiz.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;

@RestController
public class AuthController {

    private final AuthenticationManager authenticationManager;
    private final UserService userService;
    private final JwtTokenProvider jwtTokenProvider;

    public AuthController(AuthenticationManager authenticationManager,
                          UserService userService,
                          JwtTokenProvider jwtTokenProvider) {
        this.authenticationManager = authenticationManager;
        this.userService = userService;
        this.jwtTokenProvider = jwtTokenProvider;
    }

    @PostMapping("/login")
    public ResponseEntity<?> authenticateUser(@RequestBody LoginRequest loginRequest) {

        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        loginRequest.getEmail(),
                        loginRequest.getPassword()
                )
        );

        SecurityContextHolder.getContext().setAuthentication(authentication);

        String jwt = jwtTokenProvider.generateToken(authentication);
        return ResponseEntity.ok(new JwtAuthenticationResponse(jwt));
    }

    @PostMapping("/registration")
    public ResponseEntity<?> registerUser(@Valid @RequestBody SignUpRequest signUpRequest) {
        if(userService.getUserByEmail(signUpRequest.getEmail()) != null) {

            return new ResponseEntity(HttpStatus.BAD_REQUEST);
        }

        User user = userService.register(signUpRequest);
        URI location = ServletUriComponentsBuilder
                .fromCurrentContextPath().path("/topics")
                .buildAndExpand(user.getEmail()).toUri();

        return ResponseEntity.created(location).body(
                new ApiResponse(true, "User registered successfully"));
    }
}
