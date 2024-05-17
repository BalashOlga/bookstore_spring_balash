package com.belhard.bookstore.data.entity;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class Book {
    private Long id;
    private String author;
    private String isbn;
    private Integer year;
    private BigDecimal cost;
    private CoverType coverType;
}

