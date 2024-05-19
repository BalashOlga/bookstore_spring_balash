package com.belhard.bookstore.controller;

import com.belhard.bookstore.AppConfiguration;
import jakarta.servlet.ServletContextEvent;
import jakarta.servlet.ServletContextListener;
import jakarta.servlet.annotation.WebListener;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

@Slf4j
@WebListener
public class AppListner implements ServletContextListener {
    @Getter
    private static AnnotationConfigApplicationContext context;

    @Override
    public void contextInitialized(ServletContextEvent sce) {
        ServletContextListener.super.contextInitialized(sce);
        context = new AnnotationConfigApplicationContext(AppConfiguration.class);
        log.info("The context is raised");
    }

    @Override
    public void contextDestroyed(ServletContextEvent sce) {
        ServletContextListener.super.contextDestroyed(sce);
        if (context != null) {
            context.close();
        }
        log.info("Stopping the application");
    }

}
