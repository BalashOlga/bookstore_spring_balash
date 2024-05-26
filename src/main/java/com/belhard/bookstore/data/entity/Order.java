package com.belhard.bookstore.data.entity;

import lombok.Data;

import java.math.BigDecimal;
import java.util.List;

@Data
public class Order {
    Long id;
    User user;
    List<OrderItems> items;
    BigDecimal cost;
    OrderStatus status;
}
