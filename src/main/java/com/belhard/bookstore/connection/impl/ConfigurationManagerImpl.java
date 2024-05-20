package com.belhard.bookstore.connection.impl;

import com.belhard.bookstore.connection.ConfigurationManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

@Component
@PropertySource("classpath:application.properties")
public class ConfigurationManagerImpl implements ConfigurationManager {
    @Autowired
    Environment env;

    @Override
    public String getProperty(String property) {
        return env.getProperty(property);
    }

}


