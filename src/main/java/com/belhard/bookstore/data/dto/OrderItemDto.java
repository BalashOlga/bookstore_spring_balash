package com.belhard.bookstore.data.dto;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class OrderItemDto {
    Long id;
    Long orderId;
    Long bookId;
    Integer quantity;
    BigDecimal price;
}
