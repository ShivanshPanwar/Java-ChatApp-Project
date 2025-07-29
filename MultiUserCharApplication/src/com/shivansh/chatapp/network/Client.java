package com.shivansh.chatapp.network;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.net.UnknownHostException;
import java.net.ConnectException;

import javax.swing.DefaultListModel;
import javax.swing.JOptionPane;
import javax.swing.JTextArea;

import com.shivansh.chatapp.utils.ConfigReader;

/**
 * Represents the client-side network handler for the chat application.
 * <p>
 * Handles:
 * <ul>
 *   <li>Establishing and maintaining a connection with the chat server.</li>
 *   <li>Sending messages to the server.</li>
 *   <li>Receiving messages asynchronously via {@link ClientWorker}.</li>
 *   <li>Updating the chat display area and active user list in real time.</li>
 * </ul>
 */
public class Client {

    private Socket socket;                 // TCP connection to the server
    private InputStream in;                // Stream for incoming messages
    private OutputStream out;              // Stream for outgoing messages
    private ClientWorker worker;           // Background thread to handle incoming data

    /**
     * Creates a new client and attempts to connect to the server.
     *
     * @param chatArea      Text area where incoming messages are displayed.
     * @param userListModel List model that holds the active users.
     * @throws IOException if there is an error connecting to the server.
     */
    public Client(JTextArea chatArea, DefaultListModel<String> userListModel) throws IOException {
        try {
            // Read server address and port from configuration file
            String serverAddress = ConfigReader.getValue("ServerAddress");
            int port = Integer.parseInt(ConfigReader.getValue("PORT_NUMBER"));

            // Establish a socket connection to the server
            socket = new Socket(serverAddress, port);
            in = socket.getInputStream();
            out = socket.getOutputStream();

            // Start background thread to read incoming messages
            worker = new ClientWorker(in, chatArea, userListModel);
            worker.start();

            System.out.println("Connected to chat server at " + serverAddress + ":" + port);

        } catch (ConnectException e) {
            // Server not reachable or not running
            JOptionPane.showMessageDialog(null,
                    "Unable to connect to the server.\nPlease make sure the server is running.",
                    "Connection Error", JOptionPane.ERROR_MESSAGE);
            throw e;
        } catch (UnknownHostException e) {
            // Host address not recognized
            JOptionPane.showMessageDialog(null,
                    "Invalid server address. Check your configuration.",
                    "Host Error", JOptionPane.ERROR_MESSAGE);
            throw e;
        } catch (IOException e) {
            // General I/O errors
            JOptionPane.showMessageDialog(null,
                    "I/O error occurred while connecting to the server.",
                    "I/O Error", JOptionPane.ERROR_MESSAGE);
            throw e;
        }
    }

    /**
     * Sends a message to the server.
     *
     * @param message Text message to send.
     * @throws IOException if the output stream cannot be accessed.
     */
    public void sendMessage(String message) throws IOException {
        if (out != null) {
            out.write((message + "\n").getBytes()); // Append newline as a delimiter
            out.flush();
        }
    }

    /**
     * Provides access to the input stream for advanced operations.
     *
     * @return the InputStream connected to the server.
     */
    public InputStream getInputStream() {
        return in;
    }

    /**
     * Closes the client connection and all associated resources.
     */
    public void close() {
        try {
            if (worker != null && worker.isAlive()) {
                worker.interrupt(); // Stop worker thread
            }
            if (in != null) in.close();
            if (out != null) out.close();
            if (socket != null) socket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
