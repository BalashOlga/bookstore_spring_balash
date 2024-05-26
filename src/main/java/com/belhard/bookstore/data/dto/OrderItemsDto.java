package com.belhard.bookstore.data.dto;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class OrderItemsDto {
    Long bookId;
    Integer quantity;
    BigDecimal price;
    Long orderId;
}
