package com.example.workintech.ecomm.controller;

import com.example.workintech.ecomm.entity.User;
import com.example.workintech.ecomm.jwt.JwtUtil;
import com.example.workintech.ecomm.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
public class VerificationDebugController {
    
    private final UserRepository userRepository;
    private final JwtUtil jwtUtil;
    
    @GetMapping("/debug/verification-tokens")
    public List<Map<String, Object>> getVerificationTokens() {
        System.out.println("=== DEBUG: Fetching all verification tokens ===");
        
        List<User> users = userRepository.findAll();
        List<Map<String, Object>> tokenInfo = users.stream()
                .filter(user -> user.getVerificationToken() != null)
                .map(user -> {
                    Map<String, Object> info = new HashMap<>();
                    info.put("email", user.getEmail());
                    info.put("fullName", user.getFullName());
                    info.put("verificationToken", user.getVerificationToken());
                    info.put("isVerified", user.getIsVerified());
                    info.put("verificationUrl", "http://localhost:9000/ecommerce/verify?token=" + user.getVerificationToken());
                    return info;
                })
                .collect(Collectors.toList());
        
        System.out.println("Found " + tokenInfo.size() + " users with verification tokens");
        return tokenInfo;
    }
    
    @GetMapping("/debug/user/{email}/token")
    public Map<String, Object> getUserToken(@PathVariable String email) {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found: " + email));
        
        Map<String, Object> info = new HashMap<>();
        info.put("email", user.getEmail());
        info.put("fullName", user.getFullName());
        info.put("verificationToken", user.getVerificationToken());
        info.put("isVerified", user.getIsVerified());
        
        if (user.getVerificationToken() != null) {
            info.put("verificationUrl", "http://localhost:9000/ecommerce/verify?token=" + user.getVerificationToken());
        }
        
        return info;
    }
    
    @GetMapping("/debug/jwt-tokens")
    public List<Map<String, Object>> getAllJwtTokens() {
        System.out.println("=== DEBUG: Generating JWT tokens for all verified users ===");
        
        List<User> verifiedUsers = userRepository.findAll().stream()
                .filter(user -> user.getIsVerified() != null && user.getIsVerified())
                .toList();
        
        List<Map<String, Object>> tokenInfo = verifiedUsers.stream()
                .map(user -> {
                    String jwtToken = jwtUtil.generateToken(user.getEmail());
                    
                    Map<String, Object> info = new HashMap<>();
                    info.put("email", user.getEmail());
                    info.put("fullName", user.getFullName());
                    info.put("isVerified", user.getIsVerified());
                    info.put("jwtToken", jwtToken);
                    info.put("authorizationHeader", "Bearer " + jwtToken);
                    
                    // Also print to console
                    System.out.println("\n👤 " + user.getFullName() + " (" + user.getEmail() + ")");
                    System.out.println("🔑 JWT: " + jwtToken);
                    System.out.println("📋 Auth Header: Bearer " + jwtToken);
                    
                    return info;
                })
                .collect(Collectors.toList());
        
        System.out.println("\nTotal verified users: " + tokenInfo.size());
        System.out.println("===================================================\n");
        
        return tokenInfo;
    }
    
    @GetMapping("/debug/user/{email}/jwt")
    public Map<String, Object> getUserJwtToken(@PathVariable String email) {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found: " + email));
        
        if (!user.getIsVerified()) {
            throw new RuntimeException("User is not verified: " + email);
        }
        
        String jwtToken = jwtUtil.generateToken(user.getEmail());
        
        Map<String, Object> info = new HashMap<>();
        info.put("email", user.getEmail());
        info.put("fullName", user.getFullName());
        info.put("isVerified", user.getIsVerified());
        info.put("jwtToken", jwtToken);
        info.put("authorizationHeader", "Bearer " + jwtToken);
        
        // Print to console
        System.out.println("\n" + "=" .repeat(60));
        System.out.println("🔍 JWT TOKEN GENERATED FOR: " + user.getEmail());
        System.out.println("👤 Name: " + user.getFullName());
        System.out.println("\n🔑 JWT TOKEN:");
        System.out.println(jwtToken);
        System.out.println("\n📋 Authorization Header:");
        System.out.println("Bearer " + jwtToken);
        System.out.println("=" .repeat(60) + "\n");
        
        return info;
    }
}
