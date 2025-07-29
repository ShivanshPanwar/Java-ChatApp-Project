package com.shivansh.chatapp.dto;

/**
 * Data Transfer Object (DTO) for carrying user credentials between
 * different layers of the application.
 * <p>
 * This object encapsulates the user's ID and password without
 * containing any business logic. It’s primarily used to pass
 * login or registration data from the UI layer to the DAO layer.
 * <p/>
 */
public class UserDTO {

    /** Unique identifier for the user (e.g., username or email) */
    private String userid;

    /** User's password as a character array for better security handling */
    private char[] password;

    /**
     * Constructs a new UserDTO with the provided user ID and password.
     *
     * @param userid   the user ID
     * @param password the user's password as a char array
     */
    public UserDTO(String userid, char[] password) {
        this.userid = userid;
        this.password = password;
    }

    /** @return the user ID */
    public String getUserid() {
        return userid;
    }

    /** @param userid sets a new user ID */
    public void setUserid(String userid) {
        this.userid = userid;
    }

    /** @return the user's password as a char array */
    public char[] getPassword() {
        return password;
    }

    /** @param password sets a new password */
    public void setPassword(char[] password) {
        this.password = password;
    }
}
