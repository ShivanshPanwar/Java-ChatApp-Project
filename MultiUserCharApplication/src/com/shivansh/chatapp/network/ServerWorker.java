package com.shivansh.chatapp.network;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.Socket;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * Handles communication between the server and a single connected client.
 * <p>
 * Each connected client runs on its own thread. This class:
 * <ul>
 *     <li>Receives messages from its client.</li>
 *     <li>Broadcasts messages to all clients or sends private messages.</li>
 *     <li>Sends stored message history when a client connects.</li>
 *     <li>Updates and notifies the server about user join/leave events.</li>
 * </ul>
 */
public class ServerWorker extends Thread {

    private final Socket clientSocket;
    private final Server server;
    private final InputStream in;
    private final OutputStream out;

    private String clientName;

    /**
     * Creates a new ServerWorker for an incoming client connection.
     *
     * @param clientSocket the client's socket
     * @param server reference to the server for broadcasting messages
     * @throws IOException if an I/O error occurs when creating input/output streams
     */
    public ServerWorker(Socket clientSocket, Server server) throws IOException {
        this.clientSocket = clientSocket;
        this.server = server;
        this.in = clientSocket.getInputStream();
        this.out = clientSocket.getOutputStream();
        System.out.println("New client connected from " + clientSocket.getInetAddress());
    }

    /**
     * Main thread execution.
     * Reads incoming messages, processes commands, and broadcasts messages.
     */
    @Override
    public void run() {
        try (BufferedReader br = new BufferedReader(new InputStreamReader(in))) {
            // First message received is treated as the client username
            this.clientName = br.readLine();
            System.out.println(clientName + " has joined the chat.");

            // Send message history to the newly connected client
            sendHistory(server.getMessageHistory());

            // Notify all users about the new join
            server.broadcastMessage(getTimestamp() + " - " + clientName + " joined the chat.");
            server.updateUserList();

            String line;
            while ((line = br.readLine()) != null) {
                // Handle client quit
                if (line.equalsIgnoreCase("quit")) {
                    break;
                }

                // Handle private messaging: /w username message
                if (line.startsWith("/w ")) {
                    String[] parts = line.split(" ", 3);
                    if (parts.length >= 3) {
                        String targetUser = parts[1];
                        String privateMsg = getTimestamp() + " [Private] " + clientName + ": " + parts[2];
                        sendPrivateMessage(targetUser, privateMsg);
                    }
                } else {
                    // Broadcast public message to all connected clients
                    String broadcastMsg = getTimestamp() + " - " + clientName + ": " + line;
                    server.broadcastMessage(broadcastMsg);
                }
            }
        } catch (IOException e) {
            System.err.println("Connection error with client " + clientName);
            e.printStackTrace();
        } finally {
            cleanup();
        }
    }

    /**
     * Sends the stored chat history to this client upon connection.
     *
     * @param history list of recent messages stored on the server
     */
    public void sendHistory(List<String> history) {
        for (String msg : history) {
            sendMessage(msg);
        }
    }

    /**
     * Sends a message to this client.
     *
     * @param message the message text
     */
    public void sendMessage(String message) {
        try {
            out.write((message + "\n").getBytes());
            out.flush();
        } catch (IOException e) {
            System.err.println("Failed to send message to " + clientName);
        }
    }

    /**
     * Sends a private message to a specified user.
     *
     * @param targetUser recipient's username
     * @param message the private message content
     */
    private void sendPrivateMessage(String targetUser, String message) {
        synchronized (server.workers) {
            for (ServerWorker worker : server.workers) {
                if (worker.getClientName() != null &&
                        worker.getClientName().equalsIgnoreCase(targetUser)) {
                    worker.sendMessage(message);
                    this.sendMessage("To " + targetUser + ": " + message); // sender copy
                    return;
                }
            }
        }
        sendMessage("User " + targetUser + " not found.");
    }

    /**
     * Cleans up resources and notifies the server when the client disconnects.
     */
    private void cleanup() {
        try {
            server.removeWorker(this);
            server.broadcastMessage(getTimestamp() + " - " + clientName + " left the chat.");
            clientSocket.close();
            System.out.println(clientName + " disconnected.");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Returns the username of the connected client.
     */
    public String getClientName() {
        return clientName;
    }

    /**
     * Returns the current timestamp in HH:mm format.
     */
    private String getTimestamp() {
        return new SimpleDateFormat("HH:mm").format(new Date());
    }
}

