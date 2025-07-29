package com.shivansh.chatapp.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import com.shivansh.chatapp.utils.ConfigReader;

/**
 * Provides a common utility method to create database connections.
 * <p>
 * Uses configuration values such as driver class name, connection URL,
 * user ID, and password loaded from the {@link ConfigReader}.
 * Keeping this logic centralized avoids duplication in multiple DAO classes.
 * </p>
 */
public interface CommonDAO {

    /**
     * Establishes and returns a new connection to the database.
     *
     * @return a valid JDBC {@link Connection} object
     * @throws ClassNotFoundException if the JDBC driver class cannot be found
     * @throws SQLException if the connection could not be established
     */
    static Connection createConnection() throws ClassNotFoundException, SQLException {
        // Load the JDBC driver class dynamically
        Class.forName(ConfigReader.getValue("DRIVER"));

        // Retrieve DB connection details from the configuration file
        String url = ConfigReader.getValue("CONNECTION_URL");
        String user = ConfigReader.getValue("USER_ID");
        String password = ConfigReader.getValue("PASSWORD");

        // Attempt to establish a connection
        Connection con = DriverManager.getConnection(url, user, password);

        // Log confirmation for successful connection (useful for debugging)
        if (con != null) {
            System.out.println("Connection created...");
        }

        return con;
    }
}
