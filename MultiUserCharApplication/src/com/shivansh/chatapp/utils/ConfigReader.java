package com.shivansh.chatapp.utils;

import java.util.ResourceBundle;

/**
 * Utility class for reading configuration values from a properties file.
 * <p>
 * This class uses {@link ResourceBundle} to load key-value pairs from
 * <code>config.properties</code> (located on the classpath) and
 * provides a simple static method to retrieve configuration values
 * by key.
 * </p>
 */
public class ConfigReader {

    // Private constructor to prevent instantiation (utility class pattern)
    private ConfigReader() {}

    /** Holds the loaded properties */
    private static ResourceBundle rb;

    // Static initializer to load the resource bundle once at class loading time
    static {
        try {
            // Load properties file named 'config.properties'
            rb = ResourceBundle.getBundle("config");
        } catch (Exception e) {
            // Log error if the file cannot be loaded
            System.err.println("Could not load config.properties. Make sure it is in your classpath.");
            e.printStackTrace();
        }
    }

    /**
     * Retrieves the value associated with the specified key from the configuration file.
     *
     * @param key the property key
     * @return the value corresponding to the given key
     * @throws NullPointerException if the resource bundle failed to load
     */
    public static String getValue(String key) {
        return rb.getString(key);
    }
}
