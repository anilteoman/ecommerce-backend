package com.example.workintech.ecomm.service;



import com.example.workintech.ecomm.dto.BackendResponse;
import com.example.workintech.ecomm.dto.CreditCardRequest;
import com.example.workintech.ecomm.dto.CreditCardResponse;

import java.util.List;

public interface CreditCardService {
    List<CreditCardResponse> getCreditCards();
    CreditCardResponse save(CreditCardRequest creditCardRequest);
    CreditCardResponse update(Long id, CreditCardRequest creditCardRequest);
    BackendResponse delete(Long id);
}
