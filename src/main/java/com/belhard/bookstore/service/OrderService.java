package com.belhard.bookstore.service;

import com.belhard.bookstore.service.dto.BookDto;
import com.belhard.bookstore.service.dto.OrderCreateDto;
import com.belhard.bookstore.service.dto.OrderDto;
import com.belhard.bookstore.service.dto.OrderDtoLazy;

import java.util.List;

public interface OrderService {
    OrderDto getById(long id);

    List<OrderDto> getByUserId(long id);

    List<OrderDtoLazy> getAll();

    OrderDto create(OrderCreateDto orderCreateDto);
}
