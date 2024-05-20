package com.belhard.bookstore.controller.impl;

import com.belhard.bookstore.controller.Command;
import com.belhard.bookstore.service.UserService;
import com.belhard.bookstore.service.dto.UserDto;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;

@RequiredArgsConstructor
@Controller("user_edit")
public class UserEditCommand implements Command {
    private final UserService service;


    @Override
    public String execute(HttpServletRequest req) {
        String idStr = req.getParameter("id");
        Long id = Long.parseLong(idStr);

        String login = req.getParameter("login");
        String firstName = req.getParameter("firstName");
        String lastName = req.getParameter("lastName");
        String email = req.getParameter("email");

        UserDto userDto = new UserDto();
        userDto.setId(id);
        userDto.setLogin(login);
        userDto.setFirstName(firstName);
        userDto.setLastName(lastName);
        userDto.setEmail(email);

        UserDto user = service.update(userDto);
        req.setAttribute("user", user);

        return "jsp/user/user.jsp";
    }
}
