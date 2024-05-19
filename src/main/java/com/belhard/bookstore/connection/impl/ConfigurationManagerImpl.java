package com.belhard.bookstore.connection.impl;

import com.belhard.bookstore.connection.ConfigurationManager;
import org.springframework.context.annotation.PropertySource;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class ConfigurationManagerImpl implements ConfigurationManager {
    private final String source;
    private Properties properties;

    public ConfigurationManagerImpl(String source) {
        try (InputStream in = ConfigurationManagerImpl.class.getResourceAsStream(source);) {
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


