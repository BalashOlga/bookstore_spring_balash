package com.belhard.bookstore.data.repository.Impl;

import com.belhard.bookstore.data.entity.Book;
import com.belhard.bookstore.data.entity.User;
import com.belhard.bookstore.data.repository.BookRepository;
import jakarta.persistence.EntityManager;
import jakarta.persistence.NoResultException;
import jakarta.persistence.PersistenceContext;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
@Slf4j
@Transactional
public class BookRepositoryImpl implements BookRepository {

    private static final String FIND_BY_ID = "from Book where id = :id and deleted = false";
    private static final String FIND_ALL = "from Book where deleted = false";
    private static final String FIND_BY_ISBN = "from Book where isbn = :isbn and deleted = false";
    private static final String FIND_BY_AUTHOR = "from Book where author = :author and deleted = false";
    private static final String COUNT_ALL = "select count(*) from Book";

    @PersistenceContext
    private EntityManager manager;

    @Override
    public Book findById(long id) {
        try {
            return manager.createQuery(FIND_BY_ID, Book.class)
                    .setParameter("id", id)
                    .getSingleResult();
        } catch (NoResultException e) {
            return null;
        }
    }

    @Override
    public Book findByIsbn(String isbn) {
        try {
            return manager.createQuery(FIND_BY_ISBN, Book.class)
                    .setParameter("isbn", isbn)
                    .getSingleResult();
        } catch (NoResultException e) {
            return null;
        }
    }

    @Override
    public List<Book> findAll() {
        try {
            return manager.createQuery(FIND_ALL, Book.class)
                    .getResultList();
        } catch (NoResultException e) {
            return null;
        }
    }

    @Override
    public List<Book> findByAuthor(String author) {
        try {
            return manager.createQuery(FIND_BY_AUTHOR, Book.class)
                    .setParameter("author", author)
                    .getResultList();
        } catch (NoResultException e) {
            return null;
        }
    }

    @Override
    public Book save(Book book) {
        if (book.getId() != null) {
            manager.merge(book);
            manager.flush();
            return findById(book.getId());
        } else {
            manager.persist(book);
            manager.flush();
            manager.refresh(book);
            return book;
        }
    }

    @Override
    public boolean delete(long id) {
        Book book = manager.find(Book.class, id);
        book.setDeleted(true);
        manager.flush();
        return true;
    }

    @Override
    public long countAll() {
        return Long.valueOf(manager.createQuery(COUNT_ALL).getFirstResult());
    }
}
