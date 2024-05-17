package com.belhard.bookstore.service;

import com.belhard.bookstore.service.dto.BookDto;

import java.math.BigDecimal;
import java.util.List;

public interface BookService {

    BookDto getById(long id);

    BookDto getByIsbn(String isbn);

    List<BookDto> getAll();

    List<BookDto> getByAuthor(String author);

    BookDto create(BookDto book);

    BookDto update(BookDto book);

    void delete(long id);

    long getCountAll();

    BigDecimal getCostByAuthor(String author);
}
