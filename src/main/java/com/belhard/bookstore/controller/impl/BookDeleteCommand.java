package com.belhard.bookstore.controller.impl;

import com.belhard.bookstore.controller.Command;
import com.belhard.bookstore.service.BookService;
import com.belhard.bookstore.service.dto.BookDto;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class BookDeleteCommand implements Command {
    private final BookService service;

    @Override
    public String execute(HttpServletRequest req) {
        String idStr = req.getParameter("id");
        Long id = Long.parseLong(idStr);

        BookDto forDelete = service.getById(id);
        service.delete(id);
        req.setAttribute("book", forDelete);
        return "jsp/book/bookDelete.jsp";
    }
}
