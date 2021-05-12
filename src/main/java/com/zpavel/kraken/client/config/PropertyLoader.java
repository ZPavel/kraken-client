package com.zpavel.kraken.client.config;

import java.io.IOException;
import java.net.URL;
import java.util.Properties;

public class PropertyLoader {

    private static PropertyLoader instance;
    private Properties properties;

    private PropertyLoader() {
        properties = new Properties();
        URL resourceURL = PropertyLoader.class.getResource("/kraken-client.properties");

        try {
            properties.load(resourceURL.openStream());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static synchronized PropertyLoader getInstance() {
        if (instance == null) {
            instance = new PropertyLoader();
        }
        return instance;
    }

    public String getProperty(String key) {
        return properties.getProperty(key);
    }
}
