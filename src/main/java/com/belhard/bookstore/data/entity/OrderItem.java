package com.belhard.bookstore.data.entity;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class OrderItem {
    Book book;
    Integer quantity;
    BigDecimal price;
}
