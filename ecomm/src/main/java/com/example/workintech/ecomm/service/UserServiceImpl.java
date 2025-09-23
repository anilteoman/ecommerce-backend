package com.example.workintech.ecomm.service;


import com.example.workintech.ecomm.dto.BackendResponse;
import com.example.workintech.ecomm.dto.LoginRequest;
import com.example.workintech.ecomm.dto.UserResponse;
import com.example.workintech.ecomm.dto.UserSignupRequest;
import com.example.workintech.ecomm.entity.Role;
import com.example.workintech.ecomm.entity.User;
import com.example.workintech.ecomm.exceptions.*;
import com.example.workintech.ecomm.jwt.JwtUtil;
import com.example.workintech.ecomm.mapper.UserMapper;
import com.example.workintech.ecomm.repository.RoleRepository;
import com.example.workintech.ecomm.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService{

    @Autowired
    private final JwtUtil jwtUtil;

    @Autowired
    private final PasswordEncoder passwordEncoder;

    @Autowired
    private final UserRepository userRepository;

    @Autowired
    private final UserMapper userMapper;

    @Autowired
    private final RoleRepository roleRepository;

    @Override
    public UserResponse getByEmail(String email) {
        User user = userRepository.findByEmail(email).orElseThrow(() -> new UsernameNotFoundException("User not found."));
        String token = jwtUtil.generateToken(user.getEmail());
        Long roleId = user.getRoles().get(0).getId();
        return new UserResponse(token, user.getFullName(), user.getEmail(), roleId);
    }

    @Override
    public BackendResponse save(UserSignupRequest signupRequest) {
        if(userRepository.findByEmail(signupRequest.email()).isPresent()) {
            throw new EmailAlreadyExistsException("Email already registered.");
        }

        Role role = roleRepository.findById(signupRequest.role_id()).orElseThrow(() -> new RoleNotFoundException("Role not found."));

        User user = userMapper.toEntity(signupRequest);
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setRoles(List.of(role));
        userRepository.save(user);

        return new BackendResponse("Please check your email to confirm your signup!");
    }

    @Override
    public UserResponse verify(String token) {
        String email = jwtUtil.extractEmail(token);

        User user = userRepository.findByEmail(email).orElseThrow(() -> new UserNotFoundException("User not found."));

        if(!jwtUtil.isTokenValid(token)) {
            throw new TokenNotValidException("Token is not valid or expired.");
        }

        return new UserResponse(token, user.getFullName(), user.getEmail(), user.getRoles().get(0).getId());
    }

    @Override
    public UserResponse login(LoginRequest loginRequest) {
        User user = userRepository.findByEmail(loginRequest.email()).orElseThrow(() -> new UserNotFoundException("User not found."));
        if(!passwordEncoder.matches(loginRequest.password(), user.getPassword())) {
            throw new InvalidPasswordException("Wrong login information.");
        }
        String token = jwtUtil.generateToken(user.getEmail());
        return new UserResponse(token, user.getFullName(), user.getEmail(), user.getRoles().get(0).getId());
    }
}
