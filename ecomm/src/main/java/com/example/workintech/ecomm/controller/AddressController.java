package com.example.workintech.ecomm.controller;


import com.example.workintech.ecomm.dto.AddressRequest;
import com.example.workintech.ecomm.dto.AddressResponse;
import com.example.workintech.ecomm.dto.AddressUpdateRequest;
import com.example.workintech.ecomm.dto.BackendResponse;
import com.example.workintech.ecomm.service.AddressService;
import jakarta.validation.constraints.Positive;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/user")
@RequiredArgsConstructor
public class AddressController {
    @Autowired
    private final AddressService addressService;

    @GetMapping("/address")
    public List<AddressResponse> getAddresses() {
        return addressService.getAddresses();
    }

    @PostMapping("/address")
    public AddressResponse save(@Validated @RequestBody AddressRequest addressRequest) {
        return addressService.save(addressRequest);
    }

    @PutMapping("/address")
    public AddressResponse update(@Validated @RequestBody AddressUpdateRequest updateRequest) {
        return addressService.update(updateRequest);
    }

    @DeleteMapping("/address/{id}")
    @ResponseStatus(HttpStatus.OK)
    public BackendResponse delete(@Positive @PathVariable("id") Long id) {
        return addressService.delete(id);
    }
}
