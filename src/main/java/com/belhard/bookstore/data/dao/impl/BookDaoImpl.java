package com.belhard.bookstore.data.dao.impl;

import com.belhard.bookstore.connection.ConnectionManager;
import com.belhard.bookstore.data.dao.BookDao;
import com.belhard.bookstore.data.entity.Book;
import com.belhard.bookstore.data.entity.CoverType;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

@Slf4j
@RequiredArgsConstructor
@Repository
public class BookDaoImpl implements BookDao {
    private static final String FIND_BY_ID = "SELECT books.id, books.author, books.isbn, books.year, books.cost, covertypes.name covertype  FROM books JOIN covertypes ON covertypes.id = books.covertypes_id WHERE books.id = ?  and books.deleted = false;";
    private static final String FIND_BY_ISBN = "SELECT books.id, books.author, books.isbn, books.year, books.cost, covertypes.name covertype  FROM books JOIN covertypes ON covertypes.id = books.covertypes_id WHERE books.isbn = ? and books.deleted = false;";
    private static final String FIND_BY_AUTHOR = "SELECT books.id, books.author, books.isbn, books.year, books.cost, covertypes.name covertype  FROM books JOIN covertypes ON covertypes.id = books.covertypes_id WHERE books.author = ? and books.deleted = false;";
    private static final String FIND_ALL = "SELECT books.id, books.author, books.isbn, books.year, books.cost, covertypes.name covertype  FROM books JOIN covertypes ON covertypes.id = books.covertypes_id WHERE 1 = ? and books.deleted = false;";
    public static final String CREATE = "INSERT INTO books (author, isbn, year, cost, covertypes_id, deleted)  SELECT ?, ?, ?, ?, covertypes.id, false FROM covertypes WHERE covertypes.name = ?;";
    public static final String UPDATE = "UPDATE books SET author = ?, isbn = ?, year = ?,  cost = ?, covertypes_id = (select covertypes.id from covertypes where covertypes.name = ?) WHERE books.id = ? and books.deleted = false;";
    public static final String DELETE = "UPDATE books SET deleted = true WHERE books.id = ? and books.deleted = false;";
    public static final String COUNT_ALL = "SELECT count(*) FROM books WHERE 1 = ? and books.deleted = false;";
    private final ConnectionManager connectionManager;

    private ResultSet getResultSet(String sql, String value) {
        try (Connection connection = connectionManager.getConnection();) {
            log.info("Connection get successfully");

            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, value);

            return preparedStatement.executeQuery();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private ResultSet getResultSet(String sql, long value) {
        try (Connection connection = connectionManager.getConnection();) {
            log.info("Connection get successfully");

            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setLong(1, value);

            return preparedStatement.executeQuery();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public Book findById(long id) {
        try {
            ResultSet books = getResultSet(FIND_BY_ID, id);

            if (books.next()) {
                Book book = new Book();

                book.setId(books.getLong("id"));
                book.setAuthor(books.getString("author"));
                book.setIsbn(books.getString("isbn"));
                book.setYear(books.getInt("year"));
                book.setCost(books.getBigDecimal("cost"));
                book.setCoverType(CoverType.valueOf(books.getString("covertype")));
                log.debug(book.toString());
                return book;
            }
            log.debug("Select FIND_BY_ID has been completed");
            return null;

        } catch (Exception e) {
            throw new RuntimeException("bookId= " + id, e);
        }
    }

    @Override
    public Book findByIsbn(String isbn) {
        try {
            ResultSet books = getResultSet(FIND_BY_ISBN, isbn);

            if (books.next()) {
                Book book = new Book();
                book.setId(books.getLong("id"));
                book.setAuthor(books.getString("author"));
                book.setIsbn(books.getString("isbn"));
                book.setYear(books.getInt("year"));
                book.setCost(books.getBigDecimal("cost"));
                book.setCoverType(CoverType.valueOf(books.getString("covertype")));
                log.debug(book.toString());

                return book;
            }
            log.debug("Select FIND_BY_ISBN has been completed");
            return null;

        } catch (SQLException e) {
            throw new RuntimeException("isbn = " + isbn, e);
        }
    }

    @Override
    public List<Book> findAll() {
        try {
            ResultSet books = getResultSet(FIND_ALL, 1);

            List<Book> listBook = new ArrayList<>();

            while (books.next()) {

                Book book = new Book();
                book.setId(books.getLong("id"));
                book.setAuthor(books.getString("author"));
                book.setIsbn(books.getString("isbn"));
                book.setYear(books.getInt("year"));
                book.setCost(books.getBigDecimal("cost"));
                book.setCoverType(CoverType.valueOf(books.getString("covertype")));

                listBook.add(book);
                log.debug(book.toString());

            }
            if (listBook.isEmpty()) {
                log.debug("Select FIND_ALL has been completed");
                return null;
            } else {
                return listBook;
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public List<Book> findByAuthor(String author) {

        try {
            ResultSet books = getResultSet(FIND_BY_AUTHOR, author);

            while (books.next()) {

                List<Book> listBook = new ArrayList<>();
                Book book = new Book();

                book.setId(books.getLong("id"));
                book.setAuthor(books.getString("author"));
                book.setIsbn(books.getString("isbn"));
                book.setYear(books.getInt("year"));
                book.setCost(books.getBigDecimal("cost"));
                book.setCoverType(CoverType.valueOf(books.getString("covertype")));

                listBook.add(book);
                log.debug(book.toString());

                return listBook;
            }
            log.debug("Select FIND_BY_AUTHOR has been completed");
            return null;

        } catch (SQLException e) {
            throw new RuntimeException("author = " + author, e);
        }
    }

    @Override
    public Book create(Book book) {
        try (Connection connection = connectionManager.getConnection()) {
            log.info("Connection get successfully");
            PreparedStatement praparestatement = connection.prepareStatement(CREATE, Statement.RETURN_GENERATED_KEYS);
            praparestatement.setString(1, book.getAuthor());
            praparestatement.setString(2, book.getIsbn());
            praparestatement.setInt(3, book.getYear());
            praparestatement.setBigDecimal(4, book.getCost());
            praparestatement.setString(5, CoverType.HARD.name());
            praparestatement.executeUpdate();
            ResultSet resultSet = praparestatement.getGeneratedKeys();

            if (resultSet.next()) {

                long id = resultSet.getLong(1);
                System.out.println("id= " + id);
                log.debug("Update CREATE has been completed");
                return findById(id);
            } else {
                throw new RuntimeException("Everything is bad");
            }

        } catch (Exception e) {
            throw new RuntimeException(book.toString(), e);
        }
    }

    @Override
    public Book update(Book book) {
        try (Connection connection = connectionManager.getConnection()) {
            log.info("Connection get successfully");

            PreparedStatement statement = connection.prepareStatement(UPDATE);
            statement.setString(1, book.getAuthor());
            statement.setString(2, book.getIsbn());
            statement.setInt(3, book.getYear());
            statement.setBigDecimal(4, book.getCost());
            statement.setString(5, book.getCoverType().name());
            statement.setLong(6, book.getId());

            statement.executeUpdate();
            log.debug("Update UPDATE has been completed");

            return findById(book.getId());

        } catch (SQLException e) {
            throw new RuntimeException(book.toString(), e);
        }
    }

    @Override
    public boolean delete(long id) {
        try (Connection connection = connectionManager.getConnection()) {
            log.info("Connection get successfully");

            PreparedStatement statement = connection.prepareStatement(DELETE);
            statement.setLong(1, id);
            int i = statement.executeUpdate();
            log.debug("Update DELETE has been completed");

            return (i > 0);

        } catch (Exception e) {
            throw new RuntimeException("id = " + id, e);
        }
    }

    @Override
    public long countAll() {
        try {
            ResultSet books = getResultSet(COUNT_ALL, 1);

            books.next();
            long count = books.getLong(1);
            log.debug("Select COUNT_ALL has been completed");

            return count;

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
