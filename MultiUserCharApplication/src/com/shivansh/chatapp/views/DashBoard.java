package com.shivansh.chatapp.views;

import java.awt.*;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.net.UnknownHostException;
import java.awt.event.ActionEvent;

/**
 * Dashboard screen shown after successful login.
 * Provides a central hub for the multi-user chat application
 * with a welcome message, background image, and menu options.
 */
public class DashBoard extends JFrame {

    private static final long serialVersionUID = 1L;

    private JPanel contentPane;       // Main container for layout
    private JLabel lblImage;          // Displays a background/center image
    private JMenuBar menuBar;         // Menu bar at the top
    private JMenu ChatMenu;           // "Chat" menu
    private JMenuItem startChat;      // Option to open the chat window

    /**
     * Creates the dashboard UI.
     *
     * @param message Greeting or user-specific text to display at the top.
     */
    public DashBoard(String message) {
        // Use full-screen mode
        setExtendedState(JFrame.MAXIMIZED_BOTH);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBounds(100, 100, 800, 600);
        setLocationRelativeTo(null); // Center window
        setTitle("Multi-User Chat Dashboard");

        // Build menu bar with "Chat" menu
        menuBar = new JMenuBar();
        setJMenuBar(menuBar);

        ChatMenu = new JMenu("Chat");
        menuBar.add(ChatMenu);

        // Menu item to start a chat session
        startChat = new JMenuItem("Start Chat");
        startChat.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
//                
        		try {
					new ClientChatScreen();   // Opens the chat screen
				} catch (UnknownHostException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
        	}
        });
        menuBar.add(startChat);

        // Main content panel with padding
        contentPane = new JPanel(new BorderLayout(10, 10));
        contentPane.setBorder(new EmptyBorder(20, 20, 20, 20));
        setContentPane(contentPane);

        // Top section: dynamic welcome message
        JLabel lblMessage = new JLabel(message, SwingConstants.CENTER);
        lblMessage.setFont(new Font("Segoe UI", Font.BOLD, 24));
        lblMessage.setForeground(new Color(50, 50, 50));
        contentPane.add(lblMessage, BorderLayout.NORTH);

        // Center section: scalable image
        lblImage = new JLabel();
        lblImage.setHorizontalAlignment(SwingConstants.CENTER);
        lblImage.setVerticalAlignment(SwingConstants.CENTER);
        contentPane.add(lblImage, BorderLayout.CENTER);

        // Load and set initial image
        ImageIcon icon = new ImageIcon(DashBoard.class.getResource("/Images/ChitChatimg2.png"));
        setScaledImage(icon);

        // Re-scale image whenever the window is resized
        addComponentListener(new java.awt.event.ComponentAdapter() {
            public void componentResized(java.awt.event.ComponentEvent evt) {
                setScaledImage(icon);
            }
        });
    }

    /**
     * Scales the provided image to fit the center label while maintaining aspect ratio.
     *
     * @param icon The original image icon to scale.
     */
    private void setScaledImage(ImageIcon icon) {
        int width = lblImage.getWidth();
        int height = lblImage.getHeight();

        if (width > 0 && height > 0) {
            Image img = icon.getImage();
            Image scaledImg = img.getScaledInstance(width, height, Image.SCALE_SMOOTH);
            lblImage.setIcon(new ImageIcon(scaledImg));
        }
    }
}
