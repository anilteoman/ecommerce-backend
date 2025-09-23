package com.example.workintech.ecomm.controller;


import com.example.workintech.ecomm.dto.BackendResponse;
import com.example.workintech.ecomm.dto.LoginRequest;
import com.example.workintech.ecomm.dto.UserResponse;
import com.example.workintech.ecomm.dto.UserSignupRequest;
import com.example.workintech.ecomm.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
public class AuthController {
    @Autowired
    private final UserService userService;

    @PostMapping("/signup")
    public BackendResponse signup(@Validated @RequestBody UserSignupRequest signupRequest) {
        return userService.save(signupRequest);
    }

    @PostMapping("/login")
    public UserResponse login(@Validated @RequestBody LoginRequest loginRequest) {
        return userService.login(loginRequest);
    }

    @GetMapping("/verify")
    public UserResponse verify(@RequestHeader("Authorization") String token) {
        return userService.verify(token);
    }


}
