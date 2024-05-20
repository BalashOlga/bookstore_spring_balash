package com.belhard.bookstore.controller.impl;

import com.belhard.bookstore.controller.Command;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.stereotype.Controller;

@Controller("home")
public class HomeCommand implements Command {
    @Override
    public String execute(HttpServletRequest req) {
        return "jsp/home.jsp";
    }
}
