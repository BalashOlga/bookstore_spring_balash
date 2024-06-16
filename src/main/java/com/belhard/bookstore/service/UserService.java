package com.belhard.bookstore.service;

import com.belhard.bookstore.service.dto.UserDto;
import com.belhard.bookstore.service.dto.UserDtoLogin;
import com.belhard.bookstore.service.dto.UserDtoWithoutPassword;

import java.util.List;

public interface UserService {
    UserDtoWithoutPassword getById(long id);

    List<UserDtoWithoutPassword> getByEmail(String email);

    List<UserDtoWithoutPassword> getByLastName(String lastName);

    UserDtoWithoutPassword getByLogin(String login);

    List<UserDtoWithoutPassword> getAll();

    UserDtoLogin create(UserDtoLogin userDtoLogin);

    UserDto update(UserDto userDto);

    void delete(long id);

    long getCountAll();

    String getPassword(long id);

    UserDtoWithoutPassword login(String login, String password);
}
