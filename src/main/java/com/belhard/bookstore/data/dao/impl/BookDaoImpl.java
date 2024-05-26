package com.belhard.bookstore.data.dao.impl;

import com.belhard.bookstore.data.dao.BookDao;
import com.belhard.bookstore.data.dto.BookDto;
import com.belhard.bookstore.data.entity.CoverType;
import jakarta.annotation.Nullable;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.List;

@Slf4j
@RequiredArgsConstructor
@Repository
public class BookDaoImpl implements BookDao {
    private static final String FIND_BY_ID = "SELECT books.id, books.author, books.isbn, books.year, books.cost, covertypes.name covertype  FROM books JOIN covertypes ON covertypes.id = books.covertypes_id WHERE books.id = ?  and books.deleted = false";
    private static final String FIND_BY_ISBN = "SELECT books.id, books.author, books.isbn, books.year, books.cost, covertypes.name covertype  FROM books JOIN covertypes ON covertypes.id = books.covertypes_id WHERE books.isbn = ? and books.deleted = false";
    private static final String FIND_BY_AUTHOR = "SELECT books.id, books.author, books.isbn, books.year, books.cost, covertypes.name covertype  FROM books JOIN covertypes ON covertypes.id = books.covertypes_id WHERE books.author = ? and books.deleted = false";
    private static final String FIND_ALL = "SELECT books.id, books.author, books.isbn, books.year, books.cost, covertypes.name covertype  FROM books JOIN covertypes ON covertypes.id = books.covertypes_id WHERE books.deleted = false";
    public static final String CREATE = "INSERT INTO books (author, isbn, year, cost, covertypes_id, deleted)  SELECT :author, :isbn, :year, :cost, covertypes.id, false FROM covertypes WHERE covertypes.name = :coverType";
    public static final String UPDATE = "UPDATE books SET author = :author, isbn = :isbn, year = :year,  cost = :cost, covertypes_id = (select covertypes.id from covertypes where covertypes.name = :coverType) WHERE books.id = :id and books.deleted = false";
    public static final String DELETE = "UPDATE books SET deleted = true WHERE books.id = ? and books.deleted = false";
    public static final String COUNT_ALL = "SELECT count(*) FROM books books.deleted = false";
    private final JdbcTemplate jdbcTemplate;
    private final NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    @Nullable
    private BookDto mapRow(ResultSet rs, int rowNum) throws SQLException {
        BookDto book = new BookDto();
        book.setId(rs.getLong("id"));
        book.setAuthor(rs.getString("author"));
        book.setIsbn(rs.getString("isbn"));
        book.setYear(rs.getInt("year"));
        book.setCost(rs.getBigDecimal("cost"));
        book.setCoverType(CoverType.valueOf(rs.getString("covertype")));
        log.debug(book.toString());
        return book;
    }

    private BeanPropertySqlParameterSource getBeanPropertySql(BookDto book) {
        BeanPropertySqlParameterSource namedParameters = new BeanPropertySqlParameterSource(book);
        namedParameters.registerSqlType("isbn", Types.VARCHAR);
        namedParameters.registerSqlType("coverType", Types.VARCHAR);
        return namedParameters;
    }

    @Override
    public BookDto findById(long id) {
            BookDto book = jdbcTemplate.queryForObject(FIND_BY_ID, this::mapRow, id);
            log.debug("Select FIND_BY_ID has been completed");
            return book;
    }

    @Override
    public BookDto findByIsbn(String isbn) {
            BookDto book = jdbcTemplate.queryForObject(FIND_BY_ISBN, this::mapRow, isbn);
            log.debug("Select FIND_BY_ISBN has been completed");
            return book;
    }

    @Override
    public List<BookDto> findAll() {
        List<BookDto> books = jdbcTemplate.query(FIND_ALL, this::mapRow);
        log.debug("Select FIND_ALL has been completed");
        return books;
    }

    @Override
    public List<BookDto> findByAuthor(String author) {
        List<BookDto> books = jdbcTemplate.query(FIND_BY_AUTHOR, this::mapRow, author);
        log.debug("Select FIND_BY_AUTHOR has been completed");
        return books;
    }

    @Override
    public BookDto create(BookDto book) {
        KeyHolder keyHolder = new GeneratedKeyHolder();
        BeanPropertySqlParameterSource namedParameters = getBeanPropertySql(book);
        namedParameterJdbcTemplate.update(CREATE, namedParameters, keyHolder, new String[]{"id"});
        log.debug("Update CREATE has been completed");
        return findById(keyHolder.getKey().longValue());
    }

    @Override
    public BookDto update(BookDto book) {
        BeanPropertySqlParameterSource namedParameters = getBeanPropertySql(book);
        namedParameterJdbcTemplate.update(UPDATE, namedParameters);
        log.debug("Update UPDATE has been completed");
        return findById(book.getId());
    }

    @Override
    public boolean delete(long id) {
        int i = jdbcTemplate.update(DELETE, id);
        log.debug("Update DELETE has been completed");
        return i == 1;
    }

    @Override
    public long countAll() {
        return jdbcTemplate.queryForObject(COUNT_ALL, Integer.class);
    }

}
