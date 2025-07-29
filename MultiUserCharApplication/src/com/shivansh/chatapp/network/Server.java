package com.shivansh.chatapp.network;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import com.shivansh.chatapp.utils.ConfigReader;

/**
 * Main server class for the multi-user chat application.
 * <p>
 * Responsibilities:
 * <ul>
 *   <li>Listens for incoming client connections.</li>
 *   <li>Maintains a list of connected clients (workers).</li>
 *   <li>Stores a rolling history of the last 100 messages for new clients.</li>
 *   <li>Provides broadcast utilities to send messages and user list updates to all clients.</li>
 * </ul>
 */
public class Server {

    private ServerSocket serverSocket;

    /**
     * List of all currently connected client handlers.
     * Each client runs in its own ServerWorker thread.
     */
    protected final ArrayList<ServerWorker> workers = new ArrayList<>();

    /**
     * Stores the last N messages for history replay.
     * LinkedList chosen for efficient removal of oldest messages.
     */
    private final List<String> messageHistory = new LinkedList<>();

    /**
     * Maximum number of messages stored in history.
     */
    private static final int MAX_HISTORY = 100;

    /**
     * Initializes the server on the configured port.
     * Reads port number from config.properties.
     */
    public Server() throws IOException {
        int PORT = Integer.parseInt(ConfigReader.getValue("PORT_NUMBER"));
        serverSocket = new ServerSocket(PORT);
        System.out.println("Server started on port " + PORT + " and waiting for clients...");
        handleClientRequest();
    }

    /**
     * Continuously accepts new client connections and assigns each one a dedicated ServerWorker thread.
     */
    private void handleClientRequest() throws IOException {
        while (true) {
            Socket clientSocket = serverSocket.accept();
            ServerWorker serverWorker = new ServerWorker(clientSocket, this);

            synchronized (workers) {
                workers.add(serverWorker);
            }

            serverWorker.start();
        }
    }

    /**
     * Broadcasts a message to all connected clients and stores it in the server's history.
     * 
     * @param message the message to broadcast
     */
    protected void broadcastMessage(String message) {
        synchronized (messageHistory) {
            if (messageHistory.size() >= MAX_HISTORY) {
                messageHistory.remove(0); // remove oldest
            }
            messageHistory.add(message);
        }

        synchronized (workers) {
            for (ServerWorker worker : workers) {
                worker.sendMessage(message);
            }
        }
    }

    /**
     * Sends the current user list to all connected clients.
     */
    protected void updateUserList() {
        StringBuilder userList = new StringBuilder("/users ");
        synchronized (workers) {
            for (ServerWorker worker : workers) {
                userList.append(worker.getClientName()).append(",");
            }
        }

        // Remove trailing comma
        if (userList.charAt(userList.length() - 1) == ',') {
            userList.deleteCharAt(userList.length() - 1);
        }

        synchronized (workers) {
            for (ServerWorker worker : workers) {
                worker.sendMessage(userList.toString());
            }
        }
    }

    /**
     * Returns a copy of the stored message history.
     */
    protected List<String> getMessageHistory() {
        synchronized (messageHistory) {
            return new LinkedList<>(messageHistory);
        }
    }

    /**
     * Removes a worker when the client disconnects.
     */
    protected void removeWorker(ServerWorker worker) {
        synchronized (workers) {
            workers.remove(worker);
        }
        updateUserList();
    }

    /**
     * Entry point to start the server.
     */
    public static void main(String[] args) {
        try {
            new Server();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
