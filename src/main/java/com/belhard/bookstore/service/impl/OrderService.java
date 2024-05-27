package com.belhard.bookstore.service.impl;

import com.belhard.bookstore.service.dto.OrderDto;
import com.belhard.bookstore.service.dto.BookDto;

import java.util.List;

public interface OrderService {
    OrderDto getById(long id);

    BookDto getByIsbn(String isbn);

    List<BookDto> getAll();
}
