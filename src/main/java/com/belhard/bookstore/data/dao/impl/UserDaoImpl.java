package com.belhard.bookstore.data.dao.impl;

import com.belhard.bookstore.data.dao.UserDao;
import com.belhard.bookstore.data.entity.Role;
import com.belhard.bookstore.data.entity.User;
import jakarta.annotation.Nullable;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
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
public class UserDaoImpl implements UserDao {
    private static final String FIND_BY_ID = "SELECT users.id, users.login, users.password, users.first_name, users.last_name, users.email, roles.name role FROM users JOIN roles ON users.roles_id = roles.id WHERE users.id = ? and users.deleted = false";
    private static final String FIND_BY_EMAIL = "SELECT users.id, users.login, users.password, users.first_name, users.last_name, users.email, roles.name role FROM users JOIN roles ON users.roles_id = roles.id WHERE users.email = ? and users.deleted = false";
    private static final String FIND_BY_LAST_NAME = "SELECT users.id, users.login, users.password, users.first_name, users.last_name, users.email, roles.name role FROM users JOIN roles ON users.roles_id = roles.id WHERE users.last_name  = ? and users.deleted = false";
    private static final String FIND_BY_LOGIN = "SELECT users.id, users.login, users.password, users.first_name, users.last_name, users.email, roles.name role  FROM users JOIN roles ON users.roles_id= roles.id WHERE users.login = ? and users.deleted = false";
    private static final String FIND_ALL = "SELECT users.id, users.login, users.password, users.first_name, users.last_name, users.email, roles.name role FROM users JOIN roles ON users.roles_id = roles.id WHERE users.deleted = false";
    private static final String CREATE = "INSERT INTO users (login, password, first_name, last_name, email, roles_id, deleted) SELECT :login, :password, :firstName, :lastName, :email, roles.id, false FROM roles WHERE roles.name = :role";
    private static final String UPDATE = "UPDATE users SET login = :login, password = :password, first_name = :firstName,  last_name = :lastName, email = :email, roles_id = (select roles.id FROM roles WHERE roles.name = :role)  WHERE users.id = :id and users.deleted = false";
    private static final String DELETE = "UPDATE users SET deleted = true WHERE users.id = ? and users.deleted = false";
    private static final String COUNT_ALL = "SELECT count(*) FROM users WHERE users.deleted = false";
    private static final String FIND_PASSWORD_BY_ID = "SELECT password FROM users WHERE users.deleted = false";
    private final JdbcTemplate jdbcTemplate;
    private final NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    @Nullable
    private User mapRow(ResultSet rs, int rowNum) throws SQLException {
        User user = new User();
        user.setId(rs.getLong("id"));
        user.setLogin(rs.getString("login"));
        user.setPassword(rs.getString("password"));
        user.setFirstName(rs.getString("first_name"));
        user.setLastName(rs.getString("last_name"));
        user.setEmail(rs.getString("email"));
        user.setRole(Role.valueOf(rs.getString("role")));
        log.debug(user.toString());
        return user;
    }

    private BeanPropertySqlParameterSource getBeanPropertySql(User user) {
        BeanPropertySqlParameterSource namedParameters = new BeanPropertySqlParameterSource(user);
        namedParameters.registerSqlType("role", Types.VARCHAR);
        return namedParameters;
    }

    @Override
    public User findById(long id) {
        User user = jdbcTemplate.queryForObject(FIND_BY_ID, this::mapRow, id);
        log.debug("Select FIND_BY_ID has been completed");
        return user;
    }

    @Override
    public User findByEmail(String email) {
        User user = jdbcTemplate.queryForObject(FIND_BY_EMAIL, this::mapRow, email);
        log.debug("Select FIND_BY_EMAIL has been completed");
        return user;
    }

    @Override
    public List<User> findAll() {
        List<User> users = jdbcTemplate.query(FIND_ALL, this::mapRow);
        log.debug("Select FIND_ALL has been completed");
        return users;
    }

    @Override
    public User findByLogin(String login) {
        User user = jdbcTemplate.queryForObject(FIND_BY_LOGIN, this::mapRow, login);
        log.debug("Select FIND_BY_LOGIN has been completed");
        return user;
    }

    @Override
    public User create(User user) {
        KeyHolder keyHolder = new GeneratedKeyHolder();
        BeanPropertySqlParameterSource namedParameters = getBeanPropertySql(user);
        namedParameterJdbcTemplate.update(CREATE, namedParameters, keyHolder, new String[]{"id"});
        log.debug("Update CREATE has been completed");
        return findById(keyHolder.getKey().longValue());
    }

    @Override
    public User update(User user) {
        BeanPropertySqlParameterSource namedParameters = getBeanPropertySql(user);
        namedParameterJdbcTemplate.update(UPDATE, namedParameters);
        log.debug("Update UPDATE has been completed");
        return findById(user.getId());
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

    @Override
    public String findPasswordById(long id) {
        String password = jdbcTemplate.queryForObject(FIND_PASSWORD_BY_ID, String.class, id);
        log.debug("Select FIND_PASSWORD_BY_ID has been completed");
        return password;
    }

    @Override
    public List<User> findByLastName(String lastName) {
        List<User> users = jdbcTemplate.query(FIND_BY_LAST_NAME, this::mapRow, lastName);
        log.debug("Select FIND_BY_LAST_NAME has been completed");
        return users;
    }
}
