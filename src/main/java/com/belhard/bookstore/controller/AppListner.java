package com.belhard.bookstore.controller;

import jakarta.servlet.ServletContextEvent;
import jakarta.servlet.ServletContextListener;
import jakarta.servlet.annotation.WebListener;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@WebListener
public class AppListner implements ServletContextListener {
    private static CommandFactory commandFactory;

    @Override
    public void contextInitialized(ServletContextEvent sce) {
        ServletContextListener.super.contextInitialized(sce);
        commandFactory = CommandFactory.getInstance();
        log.debug("CommandFactory has been initialized");
    }

    @Override
    public void contextDestroyed(ServletContextEvent sce) {
        ServletContextListener.super.contextDestroyed(sce);
        if (commandFactory != null) {
            commandFactory.shutdown();
        }
        log.debug("Stopping the application");
    }

    public static CommandFactory getCommandFactory() {
        return commandFactory;
    }
}
