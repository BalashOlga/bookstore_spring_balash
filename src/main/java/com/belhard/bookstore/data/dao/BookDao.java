package com.belhard.bookstore.data.dao;


import com.belhard.bookstore.data.dto.BookDto;

import java.util.List;

public interface BookDao {

    BookDto findById(long id);

    BookDto findByIsbn(String isbn);

    List<BookDto> findAll();

    List<BookDto> findByAuthor(String author);

    BookDto create(BookDto book);

    BookDto update(BookDto book);

    boolean delete(long id);

    long countAll();
}
