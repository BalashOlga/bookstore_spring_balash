package com.belhard.bookstore.data.dao;


import com.belhard.bookstore.data.dto.UserDto;

import java.util.List;

public interface UserDao {

    UserDto findById(long id);

    UserDto findByEmail(String email);

    List<UserDto> findByLastName(String lastName);

    UserDto findByLogin(String login);

    List<UserDto> findAll();

    UserDto create(UserDto user);

    UserDto update(UserDto user);

    boolean delete(long id);

    long countAll();

    String findPasswordById(long id);
}
