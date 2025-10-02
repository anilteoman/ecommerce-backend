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
        
        // Auto-verify user for manual testing (no email service)
        user.setIsVerified(true);
        user.setVerificationToken(null);
        
        userRepository.save(user);
        
        // Generate JWT token immediately
        String jwtToken = jwtUtil.generateToken(user.getEmail());
        
        // Display registration success and JWT token in console
        System.out.println("\n" + "=" .repeat(60));
        System.out.println("🎉 USER REGISTERED SUCCESSFULLY!");
        System.out.println("📧 Email: " + user.getEmail());
        System.out.println("👤 Name: " + user.getFullName());
        System.out.println("🏷️ Role: " + role.getName());
        System.out.println("\n🔑 JWT TOKEN FOR AUTHENTICATION:");
        System.out.println(jwtToken);
        System.out.println("\n📋 Use this token in Authorization header:");
        System.out.println("Authorization: Bearer " + jwtToken);
        System.out.println("=" .repeat(60) + "\n");

        return new BackendResponse("User registered successfully! Check console for JWT token.");
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
        
        // Display verification success and JWT token in console
        System.out.println("\n" + "=" .repeat(60));
        System.out.println("✓ USER VERIFIED SUCCESSFULLY!");
        System.out.println("📧 Email: " + user.getEmail());
        System.out.println("👤 Name: " + user.getFullName());
        System.out.println("\n🔑 JWT TOKEN FOR AUTHENTICATION:");
        System.out.println(jwtToken);
        System.out.println("\n📋 Use this token in Authorization header:");
        System.out.println("Authorization: Bearer " + jwtToken);
        System.out.println("=" .repeat(60) + "\n");
        
        return new UserResponse(jwtToken, user.getFullName(), user.getEmail(), user.getRoles().get(0).getId());
    }

    @Override
    public UserResponse login(LoginRequest loginRequest) {
        User user = userRepository.findByEmail(loginRequest.email()).orElseThrow(() -> new UserNotFoundException("User not found."));
        if(!passwordEncoder.matches(loginRequest.password(), user.getPassword())) {
            throw new InvalidPasswordException("Wrong login information.");
        }
        
        // Check if user is verified
        if (!user.getIsVerified()) {
            throw new TokenNotValidException("User is not verified. Please verify your account first.");
        }
        
        String token = jwtUtil.generateToken(user.getEmail());
        
        // Display login success and JWT token in console
        System.out.println("\n" + "=" .repeat(60));
        System.out.println("🔓 USER LOGGED IN SUCCESSFULLY!");
        System.out.println("📧 Email: " + user.getEmail());
        System.out.println("👤 Name: " + user.getFullName());
        System.out.println("\n🔑 JWT TOKEN FOR AUTHENTICATION:");
        System.out.println(token);
        System.out.println("\n📋 Use this token in Authorization header:");
        System.out.println("Authorization: Bearer " + token);
        System.out.println("=" .repeat(60) + "\n");
        
        return new UserResponse(token, user.getFullName(), user.getEmail(), user.getRoles().get(0).getId());
    }
}
