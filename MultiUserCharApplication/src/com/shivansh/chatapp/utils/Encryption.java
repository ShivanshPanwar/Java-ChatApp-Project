package com.shivansh.chatapp.utils;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * Utility interface for password encryption.
 * <p>
 * Provides a static method to convert a plain-text password
 * into an encrypted representation using the MD5 hashing algorithm.
 * Although MD5 is used here for demonstration purposes,
 * it is not recommended for production systems due to known vulnerabilities.
 * </p>
 */
public interface Encryption {

    /**
     * Converts the given plain-text password into an MD5-hashed string.
     *
     * @param plainPassword the raw password to be encrypted
     * @return the hashed password as a string of byte values
     * @throws NoSuchAlgorithmException if the MD5 algorithm is not available in the environment
     */
    static String passwordEncrypt(String plainPassword) throws NoSuchAlgorithmException {
        String encryptedPassword = null;

        // Create a MessageDigest instance for MD5 hashing
        MessageDigest messageDigest = MessageDigest.getInstance("MD5");

        // Feed the plain password bytes into the digest
        messageDigest.update(plainPassword.getBytes());

        // Perform the hashing and get the encrypted byte array
        byte[] encrypt = messageDigest.digest();

        // Convert the encrypted byte array into a string of byte values
        StringBuilder sb = new StringBuilder();
        for (byte b : encrypt) {
            sb.append(b);
        }
        encryptedPassword = sb.toString();

        return encryptedPassword;
    }
}
