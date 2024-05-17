package com.belhard.bookstore.data.dao;

import com.belhard.bookstore.data.entity.Book;

import java.util.List;

public interface BookDao {

    Book findById(long id);

    Book findByIsbn(String isbn);

    List<Book> findAll();

    List<Book> findByAuthor(String author);

    Book create(Book book);

    Book update(Book book);

    boolean delete(long id);

    long countAll();
}
