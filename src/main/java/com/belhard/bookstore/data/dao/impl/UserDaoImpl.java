package com.belhard.bookstore.data.dao.impl;

import com.belhard.bookstore.connection.ConnectionManager;
import com.belhard.bookstore.data.dao.UserDao;
import com.belhard.bookstore.data.entity.Role;
import com.belhard.bookstore.data.entity.User;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

@Slf4j
@RequiredArgsConstructor
public class UserDaoImpl implements UserDao {
    private static final String FIND_BY_ID = "SELECT users.id, users.login, users.password, users.first_name, users.last_name, users.email, roles.name role FROM users JOIN roles ON users.roles_id = roles.id WHERE users.id = ? and users.deleted = false;";
    private static final String FIND_BY_EMAIL = "SELECT users.id, users.login, users.password, users.first_name, users.last_name, users.email, roles.name role FROM users JOIN roles ON users.roles_id = roles.id WHERE users.email = ? and users.deleted = false;";
    private static final String FIND_BY_LAST_NAME = "SELECT users.id, users.login, users.password, users.first_name, users.last_name, users.email, roles.name role FROM users JOIN roles ON users.roles_id = roles.id WHERE users.last_name  = ? and users.deleted = false;";
    private static final String FIND_BY_LOGIN = "SELECT users.id, users.login, users.password, users.first_name, users.last_name, users.email, roles.name role  FROM users JOIN roles ON users.roles_id= roles.id WHERE users.login = ? and users.deleted = false;";
    private static final String FIND_ALL = "SELECT users.id, users.login, users.password, users.first_name, users.last_name, users.email, roles.name role FROM users JOIN roles ON users.roles_id = roles.id WHERE 1 = ? and users.deleted = false;";
    private static final String CREATE = "INSERT INTO users (login, password, first_name, last_name, email, roles_id, deleted) SELECT ?, ?, ?, ?, ?, roles.id, false FROM roles WHERE roles.name = ?;";
    private static final String UPDATE = "UPDATE users SET login = ?, password = ?, first_name = ?,  last_name = ?, email = ?, roles_id = (select roles.id FROM roles WHERE roles.name = ?)  WHERE users.id = ? and users.deleted = false;";
    private static final String DELETE = "UPDATE users SET deleted = true WHERE users.id = ? and users.deleted = false;";
    private static final String COUNT_ALL = "SELECT count(*) FROM users WHERE 1 = ? and users.deleted = false;";
    private static final String FIND_PASSWORD_BY_ID = "SELECT password FROM users WHERE 1 = ? and users.deleted = false;";
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
    public User findById(long id) {
        try {
            ResultSet users = getResultSet(FIND_BY_ID, id);

            if (users.next()) {
                User user = new User();
                user.setId(users.getLong("id"));
                user.setLogin(users.getString("login"));
                user.setPassword(users.getString("password"));
                user.setFirstName(users.getString("first_name"));
                user.setLastName(users.getString("last_name"));
                user.setEmail(users.getString("email"));
                user.setRole(Role.valueOf(users.getString("role")));
                log.debug(user.toString());
                return user;
            }
            log.debug("Select FIND_BY_ID has been completed");
            return null;

        } catch (Exception e) {
            throw new RuntimeException("id = " + id);
        }
    }

    @Override
    public User findByEmail(String email) {
        try {
            ResultSet users = getResultSet(FIND_BY_EMAIL, email);

            if (users.next()) {
                User user = new User();

                user.setId(users.getLong("id"));
                user.setLogin(users.getString("login"));
                user.setPassword(users.getString("password"));
                user.setFirstName(users.getString("first_name"));
                user.setLastName(users.getString("last_name"));
                user.setEmail(users.getString("email"));
                user.setRole(Role.valueOf(users.getString("role")));
                log.debug(user.toString());
                return user;
            }
            log.debug("Select FIND_BY_EMAIL has been completed");
            return null;

        } catch (Exception e) {
            throw new RuntimeException("email = " + email, e);
        }
    }

    @Override
    public List<User> findAll() {
        try {
            ResultSet users = getResultSet(FIND_ALL, 1);

            List<User> listUser = new ArrayList<>();

            while (users.next()) {

                User user = new User();
                user.setId(users.getLong("id"));
                user.setLogin(users.getString("login"));
                user.setPassword(users.getString("password"));
                user.setFirstName(users.getString("first_name"));
                user.setLastName(users.getString("last_name"));
                user.setEmail(users.getString("email"));
                user.setRole(Role.valueOf(users.getString("role")));

                listUser.add(user);

                log.debug(user.toString());
            }
            if (listUser.isEmpty()) {
                log.debug("Select FIND_ALL has been completed");
                return null;
            } else {
                return listUser;
            }

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

//    @Override
//    public List<User> findAll() {
//        try (Connection connection = connectionManager.getConnection();
//             PreparedStatement preparedStatement = connection.prepareStatement(FIND_ALL);){
//            log.info("Connection get successfully");
//            preparedStatement.setInt(1, 1);
//            preparedStatement.executeQuery();
//            ResultSet users = preparedStatement.executeQuery();
//            log.debug("Select FIND_ALL has been comleted");
//
//            List<User> listUser = new ArrayList<>();
//
//            while (users.next()) {
//
//                User user = new User();
//                user.setId(users.getLong("id"));
//                user.setLogin(users.getString("login"));
//                user.setPassword(users.getString("password"));
//                user.setFirstName(users.getString("first_name"));
//                user.setLastName(users.getString("last_name"));
//                user.setEmail(users.getString("email"));
//                user.setRole(Role.valueOf(users.getString("role")));
//
//                listUser.add(user);
//
//                log.debug(user.toString());
//            }
//            if (listUser.isEmpty()) {
//                return null;
//            } else {
//                return listUser;
//            }
//
//        } catch (Exception e) {
//            throw new RuntimeException(e);
//        }
//    }


    @Override
    public User findByLogin(String login) {
        try {
            ResultSet users = getResultSet(FIND_BY_LOGIN, login);
            log.debug("Select FIND_BY_LOGIN has been completed");

            if (users.next()) {
                User user = new User();

                user.setId(users.getLong("id"));
                user.setLogin(users.getString("login"));
                user.setPassword(users.getString("password"));
                user.setFirstName(users.getString("first_name"));
                user.setLastName(users.getString("last_name"));
                user.setEmail(users.getString("email"));
                user.setRole(Role.valueOf(users.getString("role")));

                log.debug(user.toString());
                return user;
            }
            return null;

        } catch (SQLException e) {
            throw new RuntimeException("login = " + login, e);
        }
    }

    @Override
    public User create(User user) {
        try (Connection connection = connectionManager.getConnection()) {
            log.info("Connection get successfully");
            log.debug("create.."+ user.toString());

            PreparedStatement statement = connection.prepareStatement(CREATE, Statement.RETURN_GENERATED_KEYS);
            statement.setString(1, user.getLogin());
            statement.setString(2, user.getPassword());
            statement.setString(3, user.getFirstName());
            statement.setString(4, user.getLastName());
            statement.setString(5, user.getEmail());
            statement.setString(6, user.getRole().name());

            statement.executeUpdate();

            ResultSet resultSet = statement.getGeneratedKeys();

            if (resultSet.next()) {
                long id = resultSet.getLong(1);
                log.debug("CREATE has been completed");
                return findById(id);
            } else {
                throw new RuntimeException("Everything is bad");
            }

        } catch (Exception e) {
            throw new RuntimeException(user.toString(), e);
        }
    }

    @Override
    public User update(User user) {
        try (Connection connection = connectionManager.getConnection()) {
            log.info("Connection get successfully");

            PreparedStatement statement = connection.prepareStatement(UPDATE);
            statement.setString(1, user.getLogin());
            statement.setString(2, user.getPassword());
            statement.setString(3, user.getFirstName());
            statement.setString(4, user.getLastName());
            statement.setString(5, user.getEmail());
            statement.setString(6, user.getRole().name());
            statement.setLong(7, user.getId());

            statement.executeUpdate();
            log.debug("Update UPDATE has been completed");

            return findById(user.getId());

        } catch (Exception e) {
            throw new RuntimeException(user.toString(), e);
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
            ResultSet users = getResultSet(COUNT_ALL, 1);
            users.next();
            long count = users.getLong(1);
            log.debug("Select COUNT_ALL has been completed ");

            return count;

        } catch (Exception e) {
            throw new RuntimeException("UserDaoImpl:public long countAll()");
        }
    }

    @Override
    public String findPasswordById(long id) {
        try {
            ResultSet users = getResultSet(FIND_PASSWORD_BY_ID, id);

            if (users.next()) {
                String password = users.getString("password");
                log.debug("The password has been received from DB ");
                return password;
            }
            log.debug("Select FIND_PASSWORD_BY_ID has been completed");
            return null;

        } catch (Exception e) {
            throw new RuntimeException("id = " + id);
        }
    }

    @Override
    public List<User> findByLastName(String lastName) {
        try {
            ResultSet users = getResultSet(FIND_BY_LAST_NAME, lastName);
            log.debug("Select FIND_BY_LAST_NAME has been completed");

            List<User> listUser = new ArrayList<>();

            while (users.next()) {
                User user = new User();
                user.setId(users.getLong("id"));
                user.setLogin(users.getString("login"));
                user.setPassword(users.getString("password"));
                user.setFirstName(users.getString("first_name"));
                user.setLastName(users.getString("last_name"));
                user.setEmail(users.getString("email"));
                user.setRole(Role.valueOf(users.getString("role")));

                listUser.add(user);

                log.debug(user.toString());
            }

            if (listUser.isEmpty()) {
                log.debug("Select FIND_BY_LAST_NAME has been completed");
                return null;
            } else {
                return listUser;
            }

        } catch (Exception e) {
            throw new RuntimeException("lastName = " + lastName, e);
        }

    }
}
