package com.belhard.bookstore.controller.impl;

import com.belhard.bookstore.controller.Command;
import com.belhard.bookstore.service.BookService;
import com.belhard.bookstore.service.OrderService;
import com.belhard.bookstore.service.dto.BookDto;
import com.belhard.bookstore.service.dto.OrderDto;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;

import java.util.List;


@RequiredArgsConstructor
@Controller("orders")
public class OrdersCommand implements Command {

    private final OrderService service;

    @Override
    public String execute(HttpServletRequest req) {
        List<OrderDto> orders = service.getAll();
        req.setAttribute("orders", orders);
        return "jsp/order/orders.jsp";
    }
}

