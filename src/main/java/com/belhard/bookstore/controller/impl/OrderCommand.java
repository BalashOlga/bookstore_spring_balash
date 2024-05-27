package com.belhard.bookstore.controller.impl;

import com.belhard.bookstore.controller.Command;
import com.belhard.bookstore.service.OrderService;
import com.belhard.bookstore.service.dto.OrderDto;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;

@Slf4j
@RequiredArgsConstructor
@Controller("order")
public class OrderCommand implements Command {

    private final OrderService service;

    @Override
    public String execute(HttpServletRequest req) {
        String idStr = req.getParameter("id");
        Long id = Long.parseLong(idStr);
        OrderDto order = service.getById(id);
        req.setAttribute("order", order);
        return "jsp/order/order.jsp";
    }
}

