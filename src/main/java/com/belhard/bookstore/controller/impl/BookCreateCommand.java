package com.belhard.bookstore.controller.impl;

import com.belhard.bookstore.controller.Command;
import com.belhard.bookstore.service.BookService;
import com.belhard.bookstore.service.dto.BookDto;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;

import java.math.BigDecimal;

@RequiredArgsConstructor
public class BookCreateCommand implements Command {
    private final BookService service;

    @Override
    public String execute(HttpServletRequest req) {
        BookDto book = extractBookDto(req);
        BookDto created = service.create(book);
        req.setAttribute("book", created);
        return "jsp/book/bookCreate.jsp";
    }

    private BookDto extractBookDto(HttpServletRequest req) {
        String isbn = req.getParameter("isbn");
        String author = req.getParameter("author");
        Integer year = Integer.valueOf(req.getParameter("year"));
        BigDecimal cost = BigDecimal.valueOf(Double.valueOf(req.getParameter("cost")));

        BookDto bookDto = new BookDto();
        bookDto.setIsbn(isbn);
        bookDto.setAuthor(author);
        bookDto.setYear(year);
        bookDto.setCost(cost);

        return bookDto;
    }
}

