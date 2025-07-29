package com.shivansh.chatapp.views;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

import com.shivansh.chatapp.dao.UserDAO;
import com.shivansh.chatapp.dto.UserDTO;
import com.shivansh.chatapp.utils.UserInfo;

/**
 * Simple login and registration screen for the multi-user chat app.
 * Connects to the database via UserDAO and navigates to the dashboard on successful login.
 */
public class UserScreen extends JFrame {

    private JTextField useridtxt;
    private JPasswordField passwordField;

    // Data access object for user authentication and registration
    private final UserDAO userDAO = new UserDAO();

    /**
     * Entry point for testing this screen independently.
     */
    public static void main(String[] args) {
        new UserScreen();
    }

    /**
     * Attempts to log the user in using credentials from the text fields.
     * Shows an appropriate message and navigates to the dashboard on success.
     */
    private void doLogin() {
        String userid = useridtxt.getText();
        char[] password = passwordField.getPassword();

        UserDTO userDTO = new UserDTO(userid, password);

        try {
            if (userDAO.doLogin(userDTO)) {
                // Login successful
                UserInfo.USER_NAME = userid;
                JOptionPane.showMessageDialog(this, "Welcome " + userid);

                // Open dashboard and close login screen
                DashBoard dashboard = new DashBoard("Welcome " + userid);
                setVisible(false);
                dispose();
                dashboard.setVisible(true);
            } else {
                // Invalid credentials
                JOptionPane.showMessageDialog(this, "Invalid Userid or Password");
            }
        } catch (ClassNotFoundException | SQLException ex) {
            System.out.println("Database error during login");
            ex.printStackTrace();
        } catch (Exception ex) {
            System.out.println("Unexpected error during login");
            ex.printStackTrace();
        }
    }

    /**
     * Registers a new user with the entered ID and password.
     * Displays a message dialog based on whether the operation succeeded.
     */
    private void register() {
        String userid = useridtxt.getText();
        char[] password = passwordField.getPassword();

        UserDTO userDTO = new UserDTO(userid, password);

        try {
            int result = userDAO.add(userDTO);
            if (result > 0) {
                JOptionPane.showMessageDialog(this, "Registered successfully!");
            } else {
                JOptionPane.showMessageDialog(this, "Registration failed. Try again.");
            }
        } catch (ClassNotFoundException | SQLException ex) {
            System.out.println("Database error during registration");
            ex.printStackTrace();
        } catch (Exception ex) {
            System.out.println("Unexpected error during registration");
            ex.printStackTrace();
        }

        System.out.println("Registered user: " + userid);
    }

    // Getters and setters for testability if needed
    public JTextField getUseridtxt() {
        return useridtxt;
    }
    public void setUseridtxt(JTextField useridtxt) {
        this.useridtxt = useridtxt;
    }
    public JPasswordField getPasswordField() {
        return passwordField;
    }
    public void setPasswordField(JPasswordField passwordField) {
        this.passwordField = passwordField;
    }

    /**
     * Sets up the UI components and event listeners.
     */
    public UserScreen() {
        setTitle("Login");
        setResizable(false);
        getContentPane().setLayout(null);

        // Title label
        JLabel lblNewLabel = new JLabel("Login");
        lblNewLabel.setFont(new Font("Tahoma", Font.BOLD, 20));
        lblNewLabel.setHorizontalAlignment(SwingConstants.CENTER);
        lblNewLabel.setBounds(215, 43, 148, 42);
        getContentPane().add(lblNewLabel);

        // User ID input
        useridtxt = new JTextField();
        useridtxt.setBounds(237, 124, 164, 25);
        getContentPane().add(useridtxt);

        JLabel lblUser = new JLabel("User-id");
        lblUser.setFont(new Font("Tahoma", Font.PLAIN, 16));
        lblUser.setHorizontalAlignment(SwingConstants.CENTER);
        lblUser.setBounds(128, 122, 81, 25);
        getContentPane().add(lblUser);

        // Password input
        JLabel lblPass = new JLabel("Password");
        lblPass.setFont(new Font("Tahoma", Font.PLAIN, 16));
        lblPass.setHorizontalAlignment(SwingConstants.CENTER);
        lblPass.setBounds(128, 170, 81, 25);
        getContentPane().add(lblPass);

        passwordField = new JPasswordField();
        passwordField.setBounds(233, 172, 168, 25);
        getContentPane().add(passwordField);

        // Login button
        JButton loginButton = new JButton("Login");
        loginButton.setFont(new Font("Tahoma", Font.PLAIN, 16));
        loginButton.setBounds(167, 255, 97, 23);
        loginButton.addActionListener(e -> doLogin());
        getContentPane().add(loginButton);

        // Register button
        JButton registerButton = new JButton("Register");
        registerButton.setFont(new Font("Tahoma", Font.PLAIN, 16));
        registerButton.setBounds(316, 255, 103, 23);
        registerButton.addActionListener(e -> register());
        getContentPane().add(registerButton);

        // Window properties
        setBackground(new Color(255, 255, 255));
        setBounds(100, 100, 560, 364);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setVisible(true);
    }
}
