package com.belhard.bookstore.data.repository;

import com.belhard.bookstore.data.entity.User;

import java.util.List;

public interface UserRepository {

    User findById(long id);

    User findByEmail(String email);

    List<User> findByLastName(String lastName);

    User findByLogin(String login);

    List<User> findAll();

    User save(User user);

    boolean delete(long id);

    public long countAll();

    String findPasswordById(long id);
}
