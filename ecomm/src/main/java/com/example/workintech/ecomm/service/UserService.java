package com.example.workintech.ecomm.service;


import com.example.workintech.ecomm.dto.BackendResponse;
import com.example.workintech.ecomm.dto.LoginRequest;
import com.example.workintech.ecomm.dto.UserResponse;
import com.example.workintech.ecomm.dto.UserSignupRequest;

public interface UserService {
    UserResponse getByEmail(String email);
    BackendResponse save(UserSignupRequest signupRequest);
    UserResponse verify(String token);
    UserResponse login(LoginRequest loginRequest);
}
