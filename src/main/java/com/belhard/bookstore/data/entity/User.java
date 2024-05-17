package com.belhard.bookstore.data.entity;

import lombok.Data;

@Data
public class User {
    private Long id;
    private String login;
    private String password;
    private String firstName;
    private String lastName;
    private String email;
    private Role role;
}