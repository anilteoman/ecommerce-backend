package com.example.workintech.ecomm.dto;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

public record OrderResponse(Long id, Long user_id, Long address_id, Long card_no, Integer card_expire_month, Integer card_expire_year, String card_name, Double price, LocalDateTime order_date, List<OrderProductsResponse> products) {
}
