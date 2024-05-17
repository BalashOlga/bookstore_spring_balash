package com.belhard.bookstore.service.dto;

import lombok.Data;

@Data
public class UserDtoLogin {
    private Long id;
    private String login;
    private String password;
}
