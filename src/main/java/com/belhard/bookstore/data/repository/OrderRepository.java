package com.belhard.bookstore.data.repository;

import com.belhard.bookstore.data.entity.Book;
import com.belhard.bookstore.data.entity.Order;

import java.util.List;

public interface OrderRepository {
    Order findById(long id);

    Order findOrderItemsByOrderId(long id);

    List<Order> findAll();

    Order save(Order order);

    boolean delete(long orderId);
}
