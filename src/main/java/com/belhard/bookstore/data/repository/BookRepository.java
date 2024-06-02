package com.belhard.bookstore.data.repository;

import com.belhard.bookstore.data.entity.Book;

import java.util.List;

public interface BookRepository {

    Book findById(long id);

    Book findByIsbn(String isbn);

    List<Book> findAll();

    List<Book> findByAuthor(String author);

    Book save(Book book);

    boolean delete(long id);

    long countAll();
}
