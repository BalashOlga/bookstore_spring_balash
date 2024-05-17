package com.belhard.bookstore.controller.impl;

import com.belhard.bookstore.controller.Command;
import com.belhard.bookstore.service.UserService;
import com.belhard.bookstore.service.dto.UserDtoWithoutPassword;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.util.List;

@Slf4j
@RequiredArgsConstructor
public class UsersCommand implements Command {
    private final UserService service;

    @Override
    public String execute(HttpServletRequest req) {
        List<UserDtoWithoutPassword> users = service.getAll();
        req.setAttribute("users", users);
        return "jsp/user/users.jsp";
    }
}

