package com.belhard.bookstore.connection.impl;

import com.belhard.bookstore.connection.ConnectionManager;
import com.belhard.bookstore.connection.ConnectionPool;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.sql.Connection;


@Slf4j
@RequiredArgsConstructor()
public class ConnectionManagerImpl implements ConnectionManager {
    private ConnectionPool connectionPool;
    private final String driver;
    private final String url;
    private final String user;
    private final String password;
    @Setter
    private final int poolsize;

    @Override
    public Connection getConnection() {
        if (this.connectionPool == null) {
            this.connectionPool = new ConnectionPool(driver, url, user, password, poolsize);
            log.debug("ConnectionPool is created");
            return connectionPool.getConnection();
        } else {
            return connectionPool.getConnection();
        }
    }

    @Override
    public void close() throws IOException {
       connectionPool.destroyPool();
    }
}
