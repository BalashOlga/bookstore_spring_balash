package com.belhard.bookstore.controller.impl;

import com.belhard.bookstore.controller.Command;
import com.belhard.bookstore.service.BookService;
import com.belhard.bookstore.service.dto.BookDto;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;

import java.math.BigDecimal;

@RequiredArgsConstructor
public class BookEditCommand implements Command {

    private final BookService service;

    @Override
    public String execute(HttpServletRequest req) {
        String idStr = req.getParameter("id");
        Long id = Long.parseLong(idStr);

        String author = req.getParameter("author");
        String isbn = req.getParameter("isbn");
        Integer year = Integer.valueOf(req.getParameter("year"));
        BigDecimal cost = BigDecimal.valueOf(Double.valueOf(req.getParameter("cost")));

        BookDto bookDto = new BookDto();
        bookDto.setId(id);
        bookDto.setAuthor(author);
        bookDto.setIsbn(isbn);
        bookDto.setYear(year);
        bookDto.setCost(cost);

        BookDto book = service.update(bookDto);
        req.setAttribute("book", book);

        return "jsp/book/book.jsp";
    }

}
