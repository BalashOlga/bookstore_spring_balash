package com.belhard.bookstore.web.filter;

import com.belhard.bookstore.service.dto.UserDtoWithoutPassword;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;

@Slf4j
public class AuthorizationFilter extends HttpFilter {
    @Override
    public void doFilter(HttpServletRequest req, HttpServletResponse res, FilterChain chain) throws IOException, ServletException {
        log.debug(" AuthorizationFilter " + req.getRequestURI() + "  " + req.getMethod());

        Object user = req.getSession().getAttribute("user");

        if (user == null ) {
            res.sendRedirect("/login");
        } else {
            chain.doFilter(req,res);
        }
    }
}
