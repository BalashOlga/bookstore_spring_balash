package com.belhard.bookstore.data.dao;

import com.belhard.bookstore.data.dto.OrderDto;

import java.util.List;

public interface OrderDao {

    OrderDto findById(long id);

    List<OrderDto> findAll();

    List<OrderDto> findByUser(long userId);

    OrderDto create(OrderDto order);

    OrderDto update(OrderDto order);

    boolean delete(long orderId);
}
