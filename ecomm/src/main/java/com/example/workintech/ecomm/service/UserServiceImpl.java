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
import java.util.UUID;

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
        
        // Generate verification token
        String verificationToken = UUID.randomUUID().toString();
        user.setVerificationToken(verificationToken);
        user.setIsVerified(false);
        
        userRepository.save(user);
        
        // In a real application, you would send this token via email
        System.out.println("=== VERIFICATION TOKEN FOR " + user.getEmail() + " ===");
        System.out.println("Token: " + verificationToken);
        System.out.println("Verification URL: http://localhost:9000/ecommerce/verify?token=" + verificationToken);
        System.out.println("================================================");

        return new BackendResponse("Please check your email to confirm your signup!");
    }

    @Override
    public UserResponse verify(String token) {
        User user = userRepository.findByVerificationToken(token)
                .orElseThrow(() -> new TokenNotValidException("Invalid verification token."));

        if (user.getIsVerified()) {
            throw new TokenNotValidException("User is already verified.");
        }

        user.setIsVerified(true);
        user.setVerificationToken(null); // Clear the token after successful verification
        userRepository.save(user);

        // Generate JWT token for the verified user
        String jwtToken = jwtUtil.generateToken(user.getEmail());
        
        return new UserResponse(jwtToken, user.getFullName(), user.getEmail(), user.getRoles().get(0).getId());
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
