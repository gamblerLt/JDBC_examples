package lt.code.academy.H2ConnectionExample;

import java.io.InputStream;
import java.util.Properties;

public class ApplicationProperties {
    private static final String PROPERTIES_FILE_NAME = "connection.properties";

    private static ApplicationProperties instance;
    private final Properties properties;

    private ApplicationProperties() {
        properties = new Properties();

        try (InputStream stream = this.getClass().getClassLoader().getResourceAsStream(PROPERTIES_FILE_NAME)){
            properties.load(stream);
        } catch(Exception e) {
            System.out.println("Nepavyko nuskaityti failo " + e.getMessage());
        }
    }

    public static ApplicationProperties getInstance() {
        if(instance == null) {
            instance = new ApplicationProperties();
        }

        return instance;
    }

    public String getValue(String key) {
        return properties.getProperty(key);
    }
}