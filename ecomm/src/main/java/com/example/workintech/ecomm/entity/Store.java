package com.example.workintech.ecomm.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import org.springframework.data.annotation.CreatedDate;

import java.time.LocalDate;

@Entity
@Table(name = "store", schema = "ecommerce")
public class Store {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @NotEmpty
    @NotNull
    @NotBlank
    @Size(max = 150)
    @Column(name = "store_name")
    private String storeName;

    @NotNull
    @NotEmpty
    @NotBlank
    @Size(max = 50)
    @Column(name = "store_phone")
    private String storePhone;

    @NotNull
    @NotEmpty
    @NotBlank
    @Size(max = 50)
    @Column(name = "store_tax_id")
    private String storeTaxId;

    @NotNull
    @NotEmpty
    @NotBlank
    @Size(max = 100)
    @Column(name = "store_bank_account")
    private String storeBankAccount;

    @Column(name = "created_at")
    @CreatedDate
    private LocalDate createdAt;

    @NotNull
    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;
}
