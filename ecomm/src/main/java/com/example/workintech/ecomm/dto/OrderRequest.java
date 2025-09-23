package com.example.workintech.ecomm.dto;


import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

public record OrderRequest(
        @NotNull
        Long address_id,

        @NotNull
        Long card_no,

        @NotNull
        Integer card_expire_month,

        @NotNull
        Integer card_expire_year,

        @NotNull
        @NotEmpty
        @NotBlank
        @Size(max = 50)
        String card_name,

        @NotNull
        Double price,

        List<OrderProductsRequest> products,

        LocalDateTime orderDate
) {
}
