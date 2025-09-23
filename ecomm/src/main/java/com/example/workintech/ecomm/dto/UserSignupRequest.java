package com.example.workintech.ecomm.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record UserSignupRequest(
        @NotBlank
        String name,
        @NotBlank
        String email,
        @NotBlank
        String password,
        @NotNull
        Long role_id,
        String store_name,
        String store_phone,
        String store_tax_id,
        String store_bank_account
) {
}
