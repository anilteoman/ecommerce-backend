package com.example.workintech.ecomm.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "category", schema = "ecommerce")
@NoArgsConstructor
@AllArgsConstructor
@Data
public class Category {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @NotEmpty
    @NotBlank
    @NotNull
    @Size(max = 20)
    @Column(name = "code")
    private String code;

    @NotEmpty
    @NotBlank
    @NotNull
    @Size(max = 20)
    @Column(name = "title")
    private String title;

    @NotEmpty
    @NotBlank
    @NotNull
    @Column(name = "img")
    private String img;

    @NotNull
    @Column(name = "rating")
    private Double rating;

    @NotEmpty
    @NotBlank
    @NotNull
    @Size(max = 1)
    @Column(name = "gender")
    private String gender;

    @Column(name = "created_at")
    @CreatedDate
    private LocalDate createdAt;

}
