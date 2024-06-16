package com.belhard.bookstore.web.controller;

import com.belhard.bookstore.data.entity.Order;
import com.belhard.bookstore.data.entity.User;
import com.belhard.bookstore.service.OrderService;
import com.belhard.bookstore.service.dto.*;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

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
    public String getByUser(@SessionAttribute UserDtoWithoutPassword user, Model model) {
        List<OrderDto> orders = service.getByUserId(user.getId());
        model.addAttribute("orders", orders);
        return "order/orders";
    }

    @PostMapping
    public String createOrder(@SessionAttribute UserDtoWithoutPassword user, HttpSession session,@SessionAttribute Map<Long, Integer> cart) {
        OrderCreateDto orderCreateDto = new OrderCreateDto(user, (Map<Long, Integer>)session.getAttribute("cart"));
        OrderDto order = service.create(orderCreateDto);
        cart.clear();
        session.setAttribute("cart", cart);

        return "redirect:/orders/" + order.getId();
    }
}
