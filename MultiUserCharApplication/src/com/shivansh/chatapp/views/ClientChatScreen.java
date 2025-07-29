package com.shivansh.chatapp.views;

import java.awt.BorderLayout;
import java.awt.Font;
import java.io.IOException;

import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;
import javax.swing.border.EmptyBorder;

import com.shivansh.chatapp.network.Client;
import com.shivansh.chatapp.utils.UserInfo;

/**
 * Main chat window for the multi-user chat application.
 * <p>
 * Features:
 * <ul>
 *     <li>Resizable chat interface with auto-adjusting components.</li>
 *     <li>Displays real-time messages with timestamps.</li>
 *     <li>Shows a list of active users on the right.</li>
 *     <li>Supports public and private messages (/w username message).</li>
 * </ul>
 */
public class ClientChatScreen extends JFrame {

    private JPanel contentPane;
    private JTextArea chatArea; // Main chat display area
    private JTextField inputField; // Field to type messages
    private JButton sendButton; // Button to send messages
    private Client client; // Handles server communication

    // Model and view for active user list
    private DefaultListModel<String> userListModel;
    private JList<String> userList;

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            try {
                new ClientChatScreen();
            } catch (IOException e) {
                JOptionPane.showMessageDialog(null,
                        "Could not start chat: " + e.getMessage(),
                        "Startup Error", JOptionPane.ERROR_MESSAGE);
            }
        });
    }

    /**
     * Sends the message typed in the input field to the server.
     */
    private void sendIt() {
        String message = inputField.getText().trim();
        if (message.isEmpty()) {
            return; // Ignore empty messages
        }

        try {
            if (client != null) {
                client.sendMessage(message);
                inputField.setText(""); // Clear input after sending
            } else {
                JOptionPane.showMessageDialog(this,
                        "Not connected to the server.",
                        "Connection Error",
                        JOptionPane.ERROR_MESSAGE);
            }
        } catch (IOException e) {
            JOptionPane.showMessageDialog(this,
                    "Failed to send message. Check your connection.",
                    "Send Error",
                    JOptionPane.ERROR_MESSAGE);
        }
    }

    /**
     * Builds the resizable chat screen and connects to the server.
     */
    public ClientChatScreen() throws IOException {
        setTitle("Chit Chat - Logged in as " + UserInfo.USER_NAME);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBounds(100, 100, 800, 500);
        setResizable(true); // Allow window resizing

        contentPane = new JPanel(new BorderLayout(5, 5));
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
        setContentPane(contentPane);

        // Center: chat area
        chatArea = new JTextArea();
        chatArea.setEditable(false);
        chatArea.setLineWrap(true);
        chatArea.setWrapStyleWord(true);
        JScrollPane chatScrollPane = new JScrollPane(chatArea);
        contentPane.add(chatScrollPane, BorderLayout.CENTER);

        // Right: active users list
        userListModel = new DefaultListModel<>();
        userList = new JList<>(userListModel);
        JScrollPane userScrollPane = new JScrollPane(userList);
        userScrollPane.setPreferredSize(new java.awt.Dimension(180, 0)); // width adjusts with window
        contentPane.add(userScrollPane, BorderLayout.EAST);

        // Bottom: input field and send button
        JPanel bottomPanel = new JPanel(new BorderLayout(5, 5));
        inputField = new JTextField();
        inputField.addActionListener(e -> sendIt());
        bottomPanel.add(inputField, BorderLayout.CENTER);

        sendButton = new JButton("Send");
        sendButton.setFont(new Font("Tahoma", Font.PLAIN, 12));
        sendButton.addActionListener(e -> sendIt());
        bottomPanel.add(sendButton, BorderLayout.EAST);

        contentPane.add(bottomPanel, BorderLayout.SOUTH);

        // Connect to server and register user
        client = new Client(chatArea, userListModel);
        client.sendMessage("/join " + UserInfo.USER_NAME);

        setVisible(true);
    }
}
