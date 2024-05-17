package com.belhard.bookstore.connection;

import lombok.extern.slf4j.Slf4j;

import java.sql.Connection;
import java.sql.DriverManager;
import java.util.ArrayDeque;
import java.util.Queue;
import java.util.concurrent.BlockingDeque;
import java.util.concurrent.LinkedBlockingDeque;

@Slf4j
public class ConnectionPool {
    private int poolsize;
    private BlockingDeque<ProxyConnection> freeConnections;
    private Queue<ProxyConnection> givenConnections;

    public ConnectionPool(String driver, String url, String user, String password, int poolsize){
        this.poolsize = poolsize;
        freeConnections = new LinkedBlockingDeque<>(this.poolsize);
        givenConnections = new ArrayDeque<>();
        try {
            Class.forName(driver);
            log.info ("Database driver loaded");
            for (int i = 0; i < this.poolsize; i++){
                Connection connection = DriverManager.getConnection(url, user, password);
                freeConnections.offer(new ProxyConnection(connection, this));
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public Connection getConnection() {
        ProxyConnection connection = null;
        try {
            connection = freeConnections.take();
            givenConnections.offer(connection);
        }catch (Exception e){
            throw new RuntimeException(e);
        }
        return connection;
    }

    public void releaseConnection (Connection connection) {
        if ((connection instanceof ProxyConnection) /*&& (givenConnections.remove())*/){
            freeConnections.offer((ProxyConnection) connection);
        } else {
            throw new RuntimeException("Trying to get someone else's connection back");
        }
    }

    public void destroyPool() {
        for (int i = 0; i < poolsize; i++) {
            try {
                freeConnections.take().reallyClose();
            } catch (Exception e) {
                throw new RuntimeException("Error closing connections in the pool");
            }
        }

    }
}
