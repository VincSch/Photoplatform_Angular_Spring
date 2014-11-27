/*
 *
 * Copyright (C) 2014
 *
 */

package de.htw.sdf.photoplatform.security;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.codec.Hex;
import org.springframework.stereotype.Service;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * @author <a href="mailto:philip@sorst.net">Philip W. Sorst</a>
 * @author <a href="mailto:josh@joshlong.com">Josh Long</a>
 */
@Service
public class TokenUtils {

    /**
     * Magic key.
     */
    public static final String MAGIC_KEY = "obfuscate";

    /**
     * Create token.
     *
     * @param userDetails the user details.
     * @return the token
     */
    public static String createToken(UserDetails userDetails) {
        long expires = System.currentTimeMillis() + 1000L * 60 * 60;
        return userDetails.getUsername() + ":" + expires + ":"
                + computeSignature(userDetails, expires);
    }

    /**
     * Compute signature.
     *
     * @param userDetails the user details
     * @param expires     the expires
     * @return the signature
     */
    public static String computeSignature(UserDetails userDetails, long expires) {
        StringBuilder signatureBuilder = new StringBuilder();
        signatureBuilder.append(userDetails.getUsername()).append(":");
        signatureBuilder.append(expires).append(":");
        signatureBuilder.append(userDetails.getPassword()).append(":");
        signatureBuilder.append(TokenUtils.MAGIC_KEY);

        MessageDigest digest;
        try {
            digest = MessageDigest.getInstance("MD5");
        } catch (NoSuchAlgorithmException e) {
            throw new IllegalStateException("No MD5 algorithm available!");
        }
        return new String(Hex.encode(digest.digest(signatureBuilder.toString()
                .getBytes())));
    }

    /**
     * Get user name from token.
     *
     * @param authToken the auth token
     * @return the user name
     */
    public static String getUserNameFromToken(String authToken) {
        if (null == authToken) {
            return null;
        }
        String[] parts = authToken.split(":");
        return parts[0];
    }

    /**
     * Validate Token.
     *
     * @param authToken   the auth token.
     * @param userDetails the user details
     * @return true if token is valid
     */
    public static boolean validateToken(String authToken,
                                        UserDetails userDetails) {
        String[] parts = authToken.split(":");
        long expires = Long.parseLong(parts[1]);
        String signature = parts[2];
        String signatureToMatch = computeSignature(userDetails, expires);
        return expires >= System.currentTimeMillis()
                && signature.equals(signatureToMatch);
    }
}
