package com.example.workintech.ecomm.dto;

import jakarta.persistence.Column;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record CreditCardRequest(
        @NotNull
        Long card_no,

        @NotNull
        Integer expire_month,

        @NotNull
        Integer expire_year,

        @NotNull
        @NotEmpty
        @NotBlank
        @Size(max = 50)
        String name_on_card
) {
}
