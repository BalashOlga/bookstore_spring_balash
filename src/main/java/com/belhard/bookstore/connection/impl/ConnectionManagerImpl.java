package com.belhard.bookstore.connection.impl;

import com.belhard.bookstore.connection.ConfigurationManager;
import com.belhard.bookstore.connection.ConnectionManager;
import com.belhard.bookstore.connection.ConnectionPool;
import jakarta.annotation.PostConstruct;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.sql.Connection;


@Slf4j
@Component
public class ConnectionManagerImpl implements ConnectionManager {
    private final ConfigurationManager configurationManager;
    private ConnectionPool connectionPool;
    private String driver;
    private String url;
    private String user;
    private String password;
    private int poolsize;

    public ConnectionManagerImpl(ConfigurationManager configurationManager) {
        this.configurationManager = configurationManager;
        this.driver = configurationManager.getProperty("db.driver");
        this.url = configurationManager.getProperty("db.url");
        this.user = configurationManager.getProperty("db.user");
        this.password = configurationManager.getProperty("db.password");
        this.poolsize = Integer.valueOf(configurationManager.getProperty("db.poolsize"));
        setConnectionPool();
    }

    @Override
    public Connection getConnection() {
        return connectionPool.getConnection();
    }

    @Override
    public void close() throws IOException {
        connectionPool.destroyPool();
    }

    public void setConnectionPool() {
        this.connectionPool = new ConnectionPool(driver, url, user, password, poolsize);
    }
}
