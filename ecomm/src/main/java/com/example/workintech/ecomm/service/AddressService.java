package com.example.workintech.ecomm.service;



import com.example.workintech.ecomm.dto.AddressRequest;
import com.example.workintech.ecomm.dto.AddressResponse;
import com.example.workintech.ecomm.dto.AddressUpdateRequest;
import com.example.workintech.ecomm.dto.BackendResponse;

import java.util.List;

public interface AddressService {
    List<AddressResponse> getAddresses();
    AddressResponse save(AddressRequest addressRequest);
    AddressResponse update(AddressUpdateRequest updateRequest);
    BackendResponse delete(Long id);
}
