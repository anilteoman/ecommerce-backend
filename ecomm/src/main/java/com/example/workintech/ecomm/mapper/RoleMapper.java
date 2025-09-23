package com.example.workintech.ecomm.mapper;


import com.example.workintech.ecomm.dto.RoleResponse;
import com.example.workintech.ecomm.entity.Role;
import org.springframework.stereotype.Component;

@Component
public class RoleMapper {
    public RoleResponse toResponse(Role role) {
        return new RoleResponse(role.getId(), role.getName(), role.getCode());
    }
}
