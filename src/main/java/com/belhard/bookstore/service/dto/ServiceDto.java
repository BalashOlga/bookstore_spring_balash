package com.belhard.bookstore.service.dto;

import com.belhard.bookstore.data.entity.OrderItem;
import com.belhard.bookstore.data.entity.OrderStatus;
import com.belhard.bookstore.data.entity.User;
import lombok.Data;

import java.math.BigDecimal;
import java.util.List;

@Data
public class ServiceDto {
    Long id;
    User user;
    List<OrderItem> items;
    BigDecimal cost;
    OrderStatus status;
}
