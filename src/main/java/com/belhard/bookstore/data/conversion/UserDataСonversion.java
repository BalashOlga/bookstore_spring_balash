package com.belhard.bookstore.data.conversion;

import com.belhard.bookstore.data.dto.UserDto;
import com.belhard.bookstore.data.entity.User;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class UserDataСonversion {

    public static UserDto toDto(User user) {

        UserDto userDto = new UserDto();

        userDto.setId(user.getId());
        userDto.setLogin(user.getLogin());
        userDto.setPassword(user.getPassword());
        userDto.setFirstName(user.getFirstName());
        userDto.setLastName(user.getLastName());
        userDto.setEmail(user.getEmail());
        userDto.setRole(user.getRole());

        return userDto;
    }

    public static User toUser(UserDto userDto) {
        log.debug("444444444444");
        User user = new User();

        user.setId(userDto.getId());
        user.setLogin(userDto.getLogin());
        user.setPassword(userDto.getPassword());
        user.setFirstName(userDto.getFirstName());
        user.setLastName(userDto.getLastName());
        user.setEmail(userDto.getEmail());
        user.setRole(userDto.getRole());

        return user;
    }
}
