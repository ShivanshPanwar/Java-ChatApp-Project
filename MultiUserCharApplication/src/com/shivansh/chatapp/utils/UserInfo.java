package com.shivansh.chatapp.utils;

/**
 * Holds information about the currently logged-in user.
 * <p>
 * This class acts as a simple in-memory store for the active user's data,
 * primarily their username, which needs to be accessed across different
 * parts of the application (e.g., for sending messages or displaying in the UI).
 * </p>
 * <p>
 * It uses a static field for convenience, so the username can be read or
 * updated without creating an object. In a production-grade system, this
 * might be replaced by a more secure session management mechanism.
 * </p>
 */
public class UserInfo {

    // Private constructor to prevent instantiation (utility holder class)
    private UserInfo() {}

    /**
     * The username of the currently authenticated user.
     * <p>
     * This field is set once after a successful login and used throughout
     * the application lifecycle.
     * </p>
     */
    public static String USER_NAME;
}
