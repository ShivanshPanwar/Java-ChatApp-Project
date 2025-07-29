package com.shivansh.chatapp.network;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import javax.swing.DefaultListModel;
import javax.swing.JTextArea;
import javax.swing.JList;

/**
 * Client-side worker thread responsible for continuously listening to
 * incoming messages from the server and updating the client's GUI.
 * <p>
 * Key responsibilities:
 * <ul>
 *     <li>Appends new chat messages to the chat display area.</li>
 *     <li>Updates the active user list when receiving "/users" commands.</li>
 *     <li>Handles message history sent by the server when connecting.</li>
 * </ul>
 */
public class ClientWorker extends Thread {

    private final InputStream in;
    private final JTextArea chatArea;
    private final DefaultListModel<String> userListModel;

    /**
     * Constructs a ClientWorker.
     *
     * @param in             Input stream to receive data from the server
     * @param chatArea       The chat display area to append messages to
     * @param userListModel  Model backing the active users JList
     */
    public ClientWorker(InputStream in, JTextArea chatArea, DefaultListModel<String> userListModel) {
        this.in = in;
        this.chatArea = chatArea;
        this.userListModel = userListModel;
    }

    /**
     * Continuously listens for incoming messages from the server.
     * Updates the chat area or active user list depending on the message type.
     */
    @Override
    public void run() {
        try (BufferedReader br = new BufferedReader(new InputStreamReader(in))) {
            String line;
            while ((line = br.readLine()) != null) {
                // Handle active users update: message starts with "/users"
                if (line.startsWith("/users ")) {
                    updateUserList(line.substring(7));
                } else {
                    // Append chat messages (including history and timestamps) to the chat area
                    chatArea.append(line + "\n");
                    chatArea.setCaretPosition(chatArea.getDocument().getLength()); // auto-scroll
                }
            }
        } catch (IOException e) {
            chatArea.append("Connection to server lost.\n");
        }
    }

    /**
     * Updates the active user list in the GUI based on the server's "/users" message.
     *
     * @param usersCSV Comma-separated string of active usernames
     */
    private void updateUserList(String usersCSV) {
        userListModel.clear();
        if (usersCSV != null && !usersCSV.isEmpty()) {
            String[] users = usersCSV.split(",");
            for (String user : users) {
                userListModel.addElement(user);
            }
        }
    }
}
