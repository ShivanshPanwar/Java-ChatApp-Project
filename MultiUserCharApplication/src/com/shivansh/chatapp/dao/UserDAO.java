package com.shivansh.chatapp.dao;

import java.security.NoSuchAlgorithmException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.shivansh.chatapp.dto.UserDTO;
import com.shivansh.chatapp.utils.Encryption;

/**
 * Data Access Object (DAO) for user-related operations.
 * <p>
 * Handles communication with the database for user login validation
 * and new user registration. Passwords are stored securely after
 * encryption.
 * </p>
 */
public class UserDAO {

    /**
     * Validates user credentials against the database.
     *
     * @param userDTO contains the user ID and password entered by the user
     * @return true if a matching user record is found; false otherwise
     * @throws ClassNotFoundException if the database driver is missing
     * @throws SQLException if a database access error occurs
     * @throws Exception for encryption-related errors
     */
    public boolean doLogin(UserDTO userDTO) throws ClassNotFoundException, SQLException, Exception {
        Connection connection = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        final String SQL = "SELECT userid FROM users WHERE userid = ? AND password = ?";

        try {
            // Establish a connection to the database
            connection = CommonDAO.createConnection();
            ps = connection.prepareStatement(SQL);

            // Set query parameters: user ID and encrypted password
            ps.setString(1, userDTO.getUserid());
            String encryptPass = Encryption.passwordEncrypt(new String(userDTO.getPassword()));
            ps.setString(2, encryptPass);

            // Execute the query to check if the user exists
            rs = ps.executeQuery();
            return rs.next(); // returns true if at least one record matches
        } finally {
            // Clean up database resources
            if (rs != null) rs.close();
            if (ps != null) ps.close();
            if (connection != null) connection.close();
        }
    }

    /**
     * Registers a new user in the database.
     *
     * @param userDTO contains the new user's ID and password
     * @return number of rows affected (1 if successful, 0 otherwise)
     * @throws ClassNotFoundException if the database driver is missing
     * @throws SQLException if a database access error occurs
     * @throws NoSuchAlgorithmException if the encryption algorithm is not found
     */
    public int add(UserDTO userDTO) throws ClassNotFoundException, SQLException, NoSuchAlgorithmException {
        Connection connection = null;
        PreparedStatement ps = null;

        try {
            // Establish a connection to the database
            connection = CommonDAO.createConnection();
            String query = "INSERT INTO users(userid, password) VALUES(?, ?)";
            ps = connection.prepareStatement(query);

            // Encrypt the password before storing it
            String encryptedPassword = Encryption.passwordEncrypt(new String(userDTO.getPassword()));

            ps.setString(1, userDTO.getUserid());
            ps.setString(2, encryptedPassword);

            // Execute the insert operation
            return ps.executeUpdate();
        } finally {
            // Close resources to avoid memory leaks
            if (ps != null) ps.close();
            if (connection != null) connection.close();
        }
    }
}
