package com.belhard.bookstore.controller.impl;

import com.belhard.bookstore.controller.Command;
import jakarta.servlet.http.HttpServletRequest;

public class UserCreateFormCommand implements Command {
    @Override
    public String execute(HttpServletRequest req) {

        return "jsp/user/userCreateForm.jsp";
    }
}
