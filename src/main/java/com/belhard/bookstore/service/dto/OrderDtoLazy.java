package com.belhard.bookstore.service.dto;

import com.belhard.bookstore.data.entity.OrderStatus;
import com.belhard.bookstore.data.entity.User;
import lombok.Data;

import java.math.BigDecimal;

@Data
public class OrderDtoLazy {
    private Long id;
    private User user;
    private BigDecimal cost;
    private OrderStatus status;
}
