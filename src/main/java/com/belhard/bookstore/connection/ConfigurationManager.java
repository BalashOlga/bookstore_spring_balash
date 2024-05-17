package com.belhard.bookstore.connection;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class ConfigurationManager {
    private final String source;
    private Properties properties;

    public ConfigurationManager(String source) {
        try (InputStream in = ConfigurationManager.class.getResourceAsStream(source);) {
            Properties properties = new Properties();
            properties.load(in);
            this.source = source;
            this.properties = properties;
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public String getProperty(String property) {
        return properties.getProperty(property);
    }
}


