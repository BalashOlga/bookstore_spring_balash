package com.belhard.bookstore.controller;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class ErrorController {
    @RequestMapping("/error")
    public String handleError (HttpServletRequest request, Model model) {
        model.addAttribute("status", request.getAttribute("javax.servlet.error.status_code"));
        model.addAttribute("reason", request.getAttribute("javax.servlet.error.status_code"));
        return "error";
    }
}
