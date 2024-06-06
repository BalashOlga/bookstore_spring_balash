package com.belhard.bookstore.data.repository;

import com.belhard.bookstore.data.entity.Order;

import java.util.List;

public interface OrderRepository {
    Order findById(long id);

    List<Order> findAll();

    List<Order> findByUserId(long id);

    Order save(Order order);

    boolean delete(long orderId);
}
