package com.example.workintech.ecomm.controller;

import com.example.workintech.ecomm.entity.User;
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
}