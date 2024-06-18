package com.belhard.bookstore.cofiguration;

import com.belhard.bookstore.web.filter.AuthorizationFilter;
import com.belhard.bookstore.web.filter.LogFilter;
import com.belhard.bookstore.web.filter.LoginFilter;
import com.belhard.bookstore.web.interceptor.FirstInterceptor;
import com.belhard.bookstore.web.interceptor.SecondInterceptor;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;


@Configuration
@RequiredArgsConstructor
public class WebConfiguration implements WebMvcConfigurer {

    private final FirstInterceptor firstInterceptor;
    private final SecondInterceptor secondInterceptor;

    @Bean
    @Order(1)
    public FilterRegistrationBean<LogFilter> logFilterBean(LogFilter logFilter) {
        FilterRegistrationBean<LogFilter> logBean = new FilterRegistrationBean<>();
        logBean.setFilter(logFilter);
        logBean.addUrlPatterns("/*");
        return logBean;
    }

    @Bean
    @Order(2)
    public FilterRegistrationBean<LoginFilter> loginFilterBean(LoginFilter loginFilter) {
        FilterRegistrationBean<LoginFilter> loginBean = new FilterRegistrationBean<>();
        loginBean.setFilter(loginFilter);
        loginBean.addUrlPatterns("/users/all", "/books/create", "/orders/all", "/orders/user/*", "/logout");
        return loginBean;
    }

    @Bean
    @Order(3)
    public FilterRegistrationBean<AuthorizationFilter> authorizationFilterBean(AuthorizationFilter authorizationFilter) {
        FilterRegistrationBean<AuthorizationFilter> authorizationBean = new FilterRegistrationBean<>();
        authorizationBean.setFilter(authorizationFilter);
        authorizationBean.addUrlPatterns("/users/all", "/books/create", "/orders/all");
        return authorizationBean;
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(firstInterceptor).order(1).addPathPatterns("/**");
        registry.addInterceptor(secondInterceptor).order(10).addPathPatterns("/orders/user/{userId}");
    }
}

