package com.belhard.bookstore.web.controller;

import com.belhard.bookstore.service.BookService;
import com.belhard.bookstore.service.dto.BookDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequiredArgsConstructor
@RequestMapping("/books")
public class BookController {

    private final BookService service;

    @GetMapping("/{id}")
    public String getBook(@PathVariable Long id, Model model) {
        BookDto book = service.getById(id);
        model.addAttribute("book", book);
        return "book/book";
    }

    @GetMapping("/isbn/{isbn}")
    public String getBook(@PathVariable String isbn, Model model) {
        BookDto book = service.getByIsbn(isbn);
        model.addAttribute("book", book);
        return "book/book";
    }

    @GetMapping("/all")
    public String getBooks(Model model) {
        List<BookDto> books = service.getAll();
        model.addAttribute("books", books);
        return "book/books";
    }

    @GetMapping("/delete/{id}")
    public String deleteById(@PathVariable Long id) {
        service.delete(id);
        return "book/bookDelete";
    }

    @GetMapping("/create")
    public String createBookForm() {
        return "book/bookCreateForm";
    }

    @PostMapping("/create")
    public String createBook(@ModelAttribute BookDto bookDto) {
        BookDto book = service.create(bookDto);
        return "redirect:/books/" + book.getId();
    }

    @GetMapping("/edit/{id}")
    public String editBookForm(@PathVariable Long id, Model model) {
        BookDto book = service.getById(id);
        model.addAttribute("book", book);
        return "book/bookEditForm";
    }

    @PostMapping("/edit/{id}")
    public String edit(@ModelAttribute BookDto bookDto) {
        service.update(bookDto);
        return "redirect:/books/" + bookDto.getId();
    }
}
