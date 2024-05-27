package com.belhard.bookstore.data.repository.Impl;

import com.belhard.bookstore.data.conversion.DataConversion;
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
    private final DataConversion dataConversion;

    @Override
    public Book findById(long id) {
        try {
            return dataConversion.toEntity(bookDao.findById(id));
        } catch (EmptyResultDataAccessException e) {
            return null;
        }
    }

    @Override
    public Book findByIsbn(String isbn) {
        try {
            return dataConversion.toEntity(bookDao.findByIsbn(isbn));
        } catch (EmptyResultDataAccessException e) {
            return null;
        }
    }

    @Override
    public List<Book> findAll() {
        return
                bookDao.findAll()
                .stream()
                .map(dataConversion::toEntity)
                .toList();
    }

    @Override
    public List<Book> findByAuthor(String author) {
        return  bookDao.findAll()
                .stream()
                .map(dataConversion::toEntity)
                .toList();
    }

    @Override
    public Book create(Book book) {
        try {
            return dataConversion.toEntity(bookDao.create(dataConversion.toDto(book)));
        } catch (EmptyResultDataAccessException e) {
            return null;
        }
    }

    @Override
    public Book update(Book book) {
        try {
            return dataConversion.toEntity(bookDao.update(dataConversion.toDto(book)));
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
