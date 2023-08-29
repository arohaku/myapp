package com.noah.backend.domain.entity;

import com.noah.backend.commons.advice.exception.CategoryNotFoundException;
import jakarta.persistence.*;
import lombok.Getter;

import java.util.Arrays;

@Getter
@Entity
public class Category {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "CATEGORY_ID")
    private Long id;

    private String categoryName;


}