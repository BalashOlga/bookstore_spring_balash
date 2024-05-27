package com.belhard.bookstore.data.dao;

import com.belhard.bookstore.data.dto.OrderItemDto;

import java.util.List;

public interface OrderItemDao {

    OrderItemDto findById(long id);
    List<OrderItemDto> findByOrder(long orderId);

    List<OrderItemDto> findAll();

    OrderItemDto create(OrderItemDto orderItem);

    OrderItemDto update(OrderItemDto OrderItem);

    boolean delete(long id);
}
