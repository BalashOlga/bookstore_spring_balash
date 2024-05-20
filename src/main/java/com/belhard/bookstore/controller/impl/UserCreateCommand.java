package com.belhard.bookstore.controller.impl;

import com.belhard.bookstore.controller.Command;
import com.belhard.bookstore.service.UserService;
import com.belhard.bookstore.service.dto.UserDtoLogin;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;

@RequiredArgsConstructor
@Controller("user_create")
public class UserCreateCommand implements Command {
    private final UserService service;

    @Override
    public String execute(HttpServletRequest req) {
        UserDtoLogin userDtoLogin = extractUserDtoLogin(req);
        UserDtoLogin created = service.create(userDtoLogin);
        req.setAttribute("user", created);
        return "jsp/user/userLogin.jsp";
    }

    private UserDtoLogin extractUserDtoLogin(HttpServletRequest req) {
        String login = req.getParameter("login");
        String password = req.getParameter("password");
        UserDtoLogin userDtoLogin = new UserDtoLogin();
        userDtoLogin.setLogin(login);
        userDtoLogin.setPassword(password);
        return userDtoLogin ;
    }
}
