package com.belhard.bookstore.service.dto;

import com.belhard.bookstore.data.entity.Role;
import lombok.Data;

@Data
public class UserDtoLogin {
    private Long id;
    private String login;
    private String password;
    private Role role;
}
