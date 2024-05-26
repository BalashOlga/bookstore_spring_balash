package com.belhard.bookstore.data.repository.Impl;

import com.belhard.bookstore.data.conversion.BookDataConversion;
import com.belhard.bookstore.data.dao.BookDao;
import com.belhard.bookstore.data.entity.Book;
import com.belhard.bookstore.data.repository.BookRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@Slf4j
@RequiredArgsConstructor
public class BookRepositoryImpl implements BookRepository {
    private final BookDao bookDao;

    @Override
    public Book findById(long id) {
        try {
            return BookDataConversion.toBook(bookDao.findById(id));
        } catch (EmptyResultDataAccessException e) {
            return null;
        }
    }

    @Override
    public Book findByIsbn(String isbn) {
        try {
            return BookDataConversion.toBook(bookDao.findByIsbn(isbn));
        } catch (EmptyResultDataAccessException e) {
            return null;
        }
    }

    @Override
    public List<Book> findAll() {
        return
                bookDao.findAll()
                .stream()
                .map(BookDataConversion::toBook)
                .toList();
    }

    @Override
    public List<Book> findByAuthor(String author) {
        return  bookDao.findAll()
                .stream()
                .map(BookDataConversion::toBook)
                .toList();
    }

    @Override
    public Book create(Book book) {
        try {
            return BookDataConversion.toBook(bookDao.create(BookDataConversion.toDto(book)));
        } catch (EmptyResultDataAccessException e) {
            return null;
        }
    }

    @Override
    public Book update(Book book) {
        try {
            return BookDataConversion.toBook(bookDao.update(BookDataConversion.toDto(book)));
        } catch (EmptyResultDataAccessException e) {
            return null;
        }
    }

    @Override
    public boolean delete(long id) {
        return bookDao.delete(id);
    }

    @Override
    public long countAll() {
        return bookDao.countAll();
    }
}
