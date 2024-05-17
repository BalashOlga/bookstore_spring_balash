package com.belhard.bookstore.service.dto;

import com.belhard.bookstore.data.entity.CoverType;
import lombok.Data;

import java.math.BigDecimal;

@Data
public class BookDto {
    private Long id;
    private String author;
    private String isbn;
    private Integer year;
    private BigDecimal cost;
    private CoverType coverType;
}
