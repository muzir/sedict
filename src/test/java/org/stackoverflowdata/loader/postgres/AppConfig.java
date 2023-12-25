package org.stackoverflowdata.loader.postgres;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

public class AppConfig {

    String loadProperties(String environment) {
        Properties properties = new Properties();
        ClassLoader classLoader = getClass().getClassLoader();
        File file = new File(classLoader.getResource(environment + ".properties").getFile());
        try (FileInputStream input = new FileInputStream(file)) {
            properties.load(input);
        } catch (IOException e) {
            e.printStackTrace();
            System.exit(1); // Terminate the application on error
        }
        return properties.getProperty("DATABASE_URL");
    }
}
