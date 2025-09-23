package com.example.workintech.ecomm.controller;


import com.example.workintech.ecomm.dto.BackendResponse;
import com.example.workintech.ecomm.dto.CreditCardRequest;
import com.example.workintech.ecomm.dto.CreditCardResponse;
import com.example.workintech.ecomm.service.CreditCardService;
import jakarta.validation.constraints.Positive;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/user")
@RequiredArgsConstructor
public class CreditCardController {
    private final CreditCardService creditCardService;

    @GetMapping("/card")
    public List<CreditCardResponse> getCreditCards() {
        return creditCardService.getCreditCards();
    }

    @PostMapping("/card")
    public CreditCardResponse save(@Validated @RequestBody CreditCardRequest cardRequest) {
        return creditCardService.save(cardRequest);
    }

    @PutMapping("/card")
    public CreditCardResponse update(@Positive Long id, @Validated @RequestBody CreditCardRequest cardRequest) {
        return creditCardService.update(id, cardRequest);
    }

    @DeleteMapping("/card/{id}")
    @ResponseStatus(HttpStatus.OK)
    public BackendResponse delete(@Positive @PathVariable Long id) {
        return creditCardService.delete(id);
    }
}
