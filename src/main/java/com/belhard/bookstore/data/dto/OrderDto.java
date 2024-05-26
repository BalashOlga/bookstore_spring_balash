package com.belhard.bookstore.data.dto;

import com.belhard.bookstore.data.entity.OrderStatus;
import lombok.Data;

import java.math.BigDecimal;

@Data
public class OrderDto {
    Long id;
    Long userId;
    BigDecimal cost;
    OrderStatus status;
}
