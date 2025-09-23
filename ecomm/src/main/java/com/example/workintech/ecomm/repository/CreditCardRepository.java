package com.example.workintech.ecomm.repository;


import com.example.workintech.ecomm.entity.CreditCard;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface CreditCardRepository extends JpaRepository<CreditCard, Long> {
    Optional<CreditCard> findByCardNo(Long cardNo);
    List<CreditCard> findByUserId(Long userId);
}
