package com.example.workintech.ecomm.controller;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
public class TestAuthController {
    
    @GetMapping("/test/protected")
    public Map<String, Object> testProtectedEndpoint() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        
        Map<String, Object> response = new HashMap<>();
        
        if (auth != null && auth.isAuthenticated()) {
            response.put("status", "success");
            response.put("message", "JWT Authentication successful!");
            response.put("user", auth.getName());
            response.put("authorities", auth.getAuthorities());
            
            // Log successful authentication
            System.out.println("\n✅ PROTECTED ENDPOINT ACCESS SUCCESSFUL!");
            System.out.println("👤 User: " + auth.getName());
            System.out.println("🔐 Authorities: " + auth.getAuthorities());
            System.out.println("===============================\n");
            
        } else {
            response.put("status", "error");
            response.put("message", "Authentication failed");
        }
        
        return response;
    }
    
    @GetMapping("/test/public")
    public Map<String, Object> testPublicEndpoint() {
        Map<String, Object> response = new HashMap<>();
        response.put("status", "success");
        response.put("message", "This is a public endpoint - no authentication required");
        
        System.out.println("🌍 PUBLIC ENDPOINT ACCESSED");
        
        return response;
    }
}