package com.example.workintech.ecomm.mapper;


import com.example.workintech.ecomm.dto.UserSignupRequest;
import com.example.workintech.ecomm.entity.User;
import org.springframework.stereotype.Component;

@Component
public class UserMapper {
    public User toEntity(UserSignupRequest signupRequest) {
        User user = new User();
        user.setFullName(signupRequest.name());
        user.setEmail(signupRequest.email());
        user.setPassword(signupRequest.password());
        if(signupRequest.store_name() != null)
            user.setStoreName(signupRequest.store_name());
        if(signupRequest.store_phone() != null)
            user.setStorePhone(signupRequest.store_phone());
        if(signupRequest.store_tax_id() != null)
            user.setStoreTaxId(signupRequest.store_tax_id());
        if(signupRequest.store_bank_account() != null)
            user.setStoreBankAccount(signupRequest.store_bank_account());

        return user;
    }
}
