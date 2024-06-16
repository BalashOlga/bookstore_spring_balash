package com.belhard.bookstore.web.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@Controller
@RequiredArgsConstructor
@RequestMapping("/cart")
public class CartController {


    @GetMapping
    public String getCart() {
        return "cart";
    }

    @PostMapping("/book/{bookId}")
    public String addToCart(@PathVariable long bookId, @SessionAttribute Map<Long, Integer> cart) {
        cart.merge(bookId, 1, (a, b) -> a + b);
        return "redirect:/books/all";
    }
}
