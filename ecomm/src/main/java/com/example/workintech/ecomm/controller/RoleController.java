package com.example.workintech.ecomm.controller;


import com.example.workintech.ecomm.dto.RoleResponse;
import com.example.workintech.ecomm.service.RoleService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class RoleController {
    private final RoleService roleService;

    @GetMapping("/roles")
    public List<RoleResponse> getRoles() {
        return roleService.getRoles();
    }
}
