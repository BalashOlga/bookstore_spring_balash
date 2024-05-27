package com.belhard.bookstore.data.dto;

import com.belhard.bookstore.data.entity.Role;
import lombok.Data;

@Data
public class UserDto {
    private Long id;
    private String login;
    private String password;
    private String firstName;
    private String lastName;
    private String email;
    private Role role;
}