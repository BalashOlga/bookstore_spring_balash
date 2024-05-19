package com.belhard.bookstore.controller.impl;

import com.belhard.bookstore.controller.Command;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.stereotype.Controller;

@Controller("book_create_form")
public class BookCreateFormCommand implements Command {
    @Override
    public String execute(HttpServletRequest req) {
        return "jsp/book/bookCreateForm.jsp";
    }
}

