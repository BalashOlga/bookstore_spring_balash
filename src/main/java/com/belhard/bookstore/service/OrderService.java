package com.belhard.bookstore.service;

import com.belhard.bookstore.service.dto.BookDto;
import com.belhard.bookstore.service.dto.OrderDto;

import java.util.List;

public interface OrderService {
    OrderDto getById(long id);

    List<OrderDto> getAll();
}
