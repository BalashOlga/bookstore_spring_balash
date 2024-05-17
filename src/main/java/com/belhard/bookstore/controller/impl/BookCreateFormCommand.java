package com.belhard.bookstore.controller.impl;

import com.belhard.bookstore.controller.Command;
import jakarta.servlet.http.HttpServletRequest;

public class BookCreateFormCommand implements Command {
    @Override
    public String execute(HttpServletRequest req) {
        return "jsp/book/bookCreateForm.jsp";
    }
}

