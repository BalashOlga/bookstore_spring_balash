package com.belhard.bookstore.data.repository.Impl;

import com.belhard.bookstore.data.conversion.DataConversion;
import com.belhard.bookstore.data.dao.BookDao;
import com.belhard.bookstore.data.entity.Book;
import com.belhard.bookstore.data.repository.BookRepository;
import jakarta.persistence.EntityManager;
import jakarta.persistence.NoResultException;
import jakarta.persistence.PersistenceContext;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
@Slf4j
@RequiredArgsConstructor
@Transactional
public class BookRepositoryImpl implements BookRepository {
    private final BookDao bookDao;
    private final DataConversion dataConversion;

    private static final String FIND_ALL = "from Book where deleted = false";
    private static final String FIND_BY_ISBN = "from Book where isbn = :isbn and deleted = false";
    private static final String FIND_BY_AUTHOR = "from Book where author = :author and deleted = false";
    private static final String COUNT_ALL = "select count(*) from Book";



    @PersistenceContext
    private EntityManager manager;

    @Override
    public Book findById(long id) {
        return manager.find(Book.class, id);
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
            return manager.find(Book.class, book.getId());
        } else {
            manager.persist(book);
            manager.flush();
            return manager.find(Book.class, book.getId());
        }
    }

    @Override
    public boolean delete(long id) {
        Book book = manager.find(Book.class, id);
        book.setDeleted(true);
        manager.flush();
        manager.detach(book);
        return true;
    }

    @Override
    public long countAll() {
        return Long.valueOf(manager.createQuery(COUNT_ALL).getFirstResult());
    }
}
