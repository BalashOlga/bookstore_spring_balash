package com.belhard.bookstore.controller;

import com.belhard.bookstore.connection.ConfigurationManager;
import com.belhard.bookstore.connection.ConnectionManager;
import com.belhard.bookstore.connection.impl.ConnectionManagerImpl;
import com.belhard.bookstore.controller.impl.*;
import com.belhard.bookstore.data.dao.BookDao;
import com.belhard.bookstore.data.dao.UserDao;
import com.belhard.bookstore.data.dao.impl.BookDaoImpl;
import com.belhard.bookstore.data.dao.impl.UserDaoImpl;
import com.belhard.bookstore.service.BookService;
import com.belhard.bookstore.service.UserService;
import com.belhard.bookstore.service.impl.BookServiceImpl;
import com.belhard.bookstore.service.impl.UserServiceImpl;
import lombok.extern.slf4j.Slf4j;

import java.io.Closeable;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
public class CommandFactory {
    private static final CommandFactory INSTANCE = new CommandFactory();
    private static final String DB_URL_KEY = "db.url";
    private static final String DB_PASSWORD_KEY = "db.password";
    private static final String DB_USER_KEY = "db.user";
    private static final String DB_DRIVER_KEY = "db.driver";
    private static final String DB_POOL_SIZE = "db.poolsize";
    private final List<Closeable> resources;
    private final Map<String, Command> commands;

    public static CommandFactory getInstance() {return INSTANCE;}

    private CommandFactory() {
        resources = new ArrayList<>();
        commands = new HashMap<>();

        //ConnectionManager
        ConfigurationManager configurationManager = new ConfigurationManager("/application.properties");
        String url = configurationManager.getProperty(DB_URL_KEY);
        String password = configurationManager.getProperty(DB_PASSWORD_KEY);
        String user = configurationManager.getProperty(DB_USER_KEY);
        String driver = configurationManager.getProperty( DB_DRIVER_KEY);
        int poolsize = Integer.parseInt(configurationManager.getProperty(DB_POOL_SIZE));

        ConnectionManager connectionManager = new ConnectionManagerImpl(driver, url, user, password, poolsize);
        resources.add(connectionManager);

        // DAO
        BookDao bookDao = new BookDaoImpl(connectionManager);
        UserDao userDao = new UserDaoImpl(connectionManager);

        //service
        BookService bookService = new BookServiceImpl(bookDao);
        UserService userService = new UserServiceImpl(userDao);

        //book
        commands.put("book_delete", new BookDeleteCommand(bookService));
        commands.put("book_edit", new BookEditCommand(bookService));
        commands.put("book_edit_form", new BookEditFormCommand(bookService));
        commands.put("book_create", new BookCreateCommand(bookService));
        commands.put("book_create_form", new BookCreateFormCommand());
        commands.put("book", new BookCommand(bookService));
        commands.put("books", new BooksCommand(bookService));

        // user
        commands.put("user_delete", new UserDeleteCommand(userService));
        commands.put("user_edit_form", new UserEditFormCommand(userService));
        commands.put("user_edit", new UserEditCommand(userService));
        commands.put("user_create_form", new UserCreateFormCommand());
        commands.put("user_create", new UserCreateCommand(userService));
        commands.put("user", new UserCommand(userService));
        commands.put("users", new UsersCommand(userService));

        // home
        commands.put("home", new HomeCommand());
    }

    public Command getCommand(String command){
        return INSTANCE.commands.get(command);
    }

    public void shutdown() {
        resources.forEach(resource -> {
            try {
                resource.close();
            } catch (IOException e) {
                log.error("Unable to close {}", resource);
            }});
    }
}
