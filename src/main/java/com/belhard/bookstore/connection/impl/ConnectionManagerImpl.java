package com.belhard.bookstore.connection.impl;

import com.belhard.bookstore.connection.ConfigurationManager;
import com.belhard.bookstore.connection.ConnectionManager;
import com.belhard.bookstore.connection.ConnectionPool;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.sql.Connection;


@Slf4j
public class ConnectionManagerImpl implements ConnectionManager {
    private ConnectionPool connectionPool;
    private final ConfigurationManager configurationManager;
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

    private void setConnectionPool() {
        this.connectionPool = new ConnectionPool(driver, url, user, password, poolsize);
    }

    @Override
    public void close() throws IOException {
       connectionPool.destroyPool();
    }
}
