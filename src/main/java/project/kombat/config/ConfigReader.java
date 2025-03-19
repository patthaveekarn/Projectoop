package project.kombat.config;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

public class ConfigReader {
    private Properties properties;

    public ConfigReader(String filename) throws IOException {
        properties = new Properties();
        try (FileInputStream input = new FileInputStream("scr/project/kombat/config.properties")) {
            properties.load(input);
        }
    }

    public int getInt(String key) {
        return Integer.parseInt(properties.getProperty(key));
    }

    public double getDouble(String key) {
        return Double.parseDouble(properties.getProperty(key));
    }

    public String getString(String key) {
        return properties.getProperty(key);
    }
}
