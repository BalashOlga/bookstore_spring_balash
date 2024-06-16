package com.belhard.bookstore.web.interceptor;

import com.belhard.bookstore.service.dto.UserDtoWithoutPassword;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import java.io.IOException;

@Component
@Slf4j
public class SecondInterceptor implements HandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws IOException {
        log.debug("Second Pre-Handler " + request.getRequestURI() + "  " + request.getMethod());
        return true;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws IOException {
        log.debug("Second Post-Handler " + request.getRequestURI() + "  " + request.getMethod());

        UserDtoWithoutPassword user = (UserDtoWithoutPassword)request.getSession().getAttribute("user");

        if (!splitQuery(request.getRequestURI())[3].equals(String.valueOf(user.getId()))) {
            log.debug("The user requested someone else's data: session Id = " +  user.getId() + " rquest ID " + splitQuery(request.getRequestURI())[3]);
            response.sendRedirect("/login");
        }
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws IOException {
        log.debug("Second After-Handler " + request.getRequestURI() + "  " + request.getMethod());
    }

    public static String[] splitQuery(String url) {
        return url.split("/");
    }

}
