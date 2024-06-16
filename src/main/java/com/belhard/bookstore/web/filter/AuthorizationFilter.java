package com.belhard.bookstore.web.filter;

import com.belhard.bookstore.data.entity.User;
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
        log.debug ("22222222222");
        UserDtoWithoutPassword user = (UserDtoWithoutPassword) req.getSession().getAttribute("user");
        log.debug("111111111111111111111111111" + user.toString());
        if (user.getRole().name() != "MANAGER") {
            res.sendRedirect("/login");
        } else {
            chain.doFilter(req, res);
        }
    }
}