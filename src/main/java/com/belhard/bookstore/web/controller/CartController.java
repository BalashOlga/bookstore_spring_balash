package com.belhard.bookstore.web.controller;

import com.belhard.bookstore.service.BookService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@Controller
@RequiredArgsConstructor
@RequestMapping("/cart")
public class CartController {
    private final BookService service;

    @GetMapping
    public String getCart(@SessionAttribute Map<Long, Integer> cart) {
        return "cart";
    }

    @PostMapping("/add/{bookId}")
    public String addIntoCart(@PathVariable long bookId, @SessionAttribute Map<Long, Integer> cart) {
        cart.merge(bookId, 1, Integer::sum);
        return "redirect:/books/all";
    }

    @PostMapping("/remove/{bookId}")
    public String deleteFromCart(@PathVariable long bookId, @SessionAttribute Map<Long, Integer> cart) {
        cart.merge(bookId, -1, Integer::sum);
        if (cart.get(bookId) <= 0) {
            cart.remove(bookId);
        }
        return "redirect:/books/all";
    }
}
