package com.belhard.bookstore.controller;

import com.belhard.bookstore.service.OrderService;
import com.belhard.bookstore.service.dto.BookDto;
import com.belhard.bookstore.service.dto.OrderDto;
import com.belhard.bookstore.service.dto.OrderDtoLazy;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequiredArgsConstructor
@RequestMapping("/orders")
public class OrderController {

    private final OrderService service;

    @GetMapping("/{id}")
    public String getById(@PathVariable Long id, Model model) {
        OrderDto order = service.getById(id);
        model.addAttribute("order", order);
        return "order/order";
    }

    @GetMapping("/all")
    public String getAll(Model model) {
        List<OrderDtoLazy> orders = service.getAll();
        model.addAttribute("orders", orders);
        return "order/orders";
    }

    @GetMapping("/user/{userId}")
    public String getByUser(@PathVariable Long userId, Model model) {
        List<OrderDto> orders = service.getByUserId(userId);
        model.addAttribute("orders", orders);
        return "order/orders";
    }

}
