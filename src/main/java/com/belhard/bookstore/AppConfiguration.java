package com.belhard.bookstore;

import com.belhard.bookstore.connection.ConfigurationManager;
import com.belhard.bookstore.connection.ConnectionManager;
import com.belhard.bookstore.connection.impl.ConfigurationManagerImpl;
import com.belhard.bookstore.connection.impl.ConnectionManagerImpl;
import com.belhard.bookstore.controller.Command;
import com.belhard.bookstore.controller.impl.*;
import com.belhard.bookstore.data.dao.BookDao;
import com.belhard.bookstore.data.dao.UserDao;
import com.belhard.bookstore.data.dao.impl.BookDaoImpl;
import com.belhard.bookstore.data.dao.impl.UserDaoImpl;
import com.belhard.bookstore.service.BookService;
import com.belhard.bookstore.service.UserService;
import com.belhard.bookstore.service.impl.BookServiceImpl;
import com.belhard.bookstore.service.impl.UserServiceImpl;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

@Configuration
public class AppConfiguration {
//connection
    @Bean
    public ConfigurationManager configurationManagerImpl() {
        return new ConfigurationManagerImpl("/application.properties");
    }

    @Bean
    public ConnectionManager connectionManagerImpl(@Qualifier("configurationManagerImpl") ConfigurationManager configurationManager) {
        return new ConnectionManagerImpl(configurationManager);
    }

//DAO
    @Bean
    public BookDao bookDaoImpl(ConnectionManager connectionManager) {
        return new BookDaoImpl(connectionManager);
    }

    @Bean
    public UserDao userDaoImpl(ConnectionManager connectionManager) {
        return new UserDaoImpl(connectionManager);
    }

//service
    @Bean
    public BookService bookServiceImpl(@Qualifier("bookDaoImpl") BookDao bookDao) {
        return new BookServiceImpl(bookDao);
    }

    @Bean
    public UserService userServiceImpl(@Qualifier("userDaoImpl") UserDao userDao) {
        return new UserServiceImpl(userDao);
    }


//book
    @Bean
    public Command book_delete(@Qualifier("bookServiceImpl") BookService bookService) {
        return new BookDeleteCommand(bookService);
    }

    @Bean
    public Command book_edit(@Qualifier("bookServiceImpl") BookService bookService) {
        return new BookEditCommand(bookService);
    }

    @Bean
    public Command book_edit_form(@Qualifier("bookServiceImpl") BookService bookService) {
        return new BookEditFormCommand(bookService);
    }

    @Bean
    public Command book_create(@Qualifier("bookServiceImpl") BookService bookService) {
        return new BookCreateCommand(bookService);
    }

    @Bean
    public Command book_create_form() {
        return new BookCreateFormCommand();
    }

    @Bean
    public Command book(@Qualifier("bookServiceImpl") BookService bookService) {
        return new BookCommand(bookService);
    }

    @Bean
    public Command books(@Qualifier("bookServiceImpl") BookService bookService) {
        return new BooksCommand(bookService);
    }

// user

    @Bean
    public Command user_delete(@Qualifier("userServiceImpl") UserService userService) {
        return new UserDeleteCommand(userService);
    }

    @Bean
    public Command user_edit_form(@Qualifier("userServiceImpl") UserService userService) {
        return new UserEditFormCommand(userService);
    }

    @Bean
    public Command user_edit(@Qualifier("userServiceImpl") UserService userService) {
        return new UserEditCommand(userService);
    }

    @Bean
    public Command user_create_form() {
        return new UserCreateFormCommand();
    }

    @Bean
    public Command user_create(@Qualifier("userServiceImpl") UserService userService) {
        return new UserCreateCommand(userService);
    }

    @Bean
    public Command user(@Qualifier("userServiceImpl") UserService userService) {
        return new UserCommand(userService);
    }

    @Bean
    public Command users(@Qualifier("userServiceImpl") UserService userService) {
        return new UsersCommand(userService);
    }

//home
    @Bean
    public Command home() {
        return new HomeCommand();
    }
}
