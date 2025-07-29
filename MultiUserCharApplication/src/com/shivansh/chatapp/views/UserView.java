package com.shivansh.chatapp.views;

import java.awt.Container;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;

/**
 * Simple demonstration window for the chat application.
 * Shows a welcome message and a button that increments a counter each time it's pressed.
 * 
 * Mainly used to test basic Swing UI components and event handling.
 */
public class UserView extends JFrame {

    // Keeps track of button clicks
    private int counter;

    /**
     * Constructor sets up the UI layout, components, and click handling.
     */
    UserView() {
        counter = 0;

        // Basic frame settings
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(500, 500);
        setResizable(false);  // Prevents resizing
        setLocationRelativeTo(null); // Centers the window on screen
        setTitle("Login");

        // Welcome label with large, bold font
        JLabel welcome = new JLabel("Welcome");
        welcome.setFont(new Font("Arial", Font.BOLD, 40));

        // Add components to the frame's container
        Container container = this.getContentPane();
        container.setLayout(null); // Using absolute positioning
        welcome.setBounds(100, 70, 250, 60); 
        container.add(welcome);

        // Button that increments the counter and updates the label
        JButton button = new JButton("Count");
        button.setBounds(100, 300, 100, 40);
        button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent event) {
                counter++;
                welcome.setText("Count " + counter);
            }
        });
        container.add(button);

        // Finally make the window visible
        setVisible(true);
    }

    /**
     * Entry point for running this UI independently.
     */
    public static void main(String[] args) {
        new UserView();
    }
}
