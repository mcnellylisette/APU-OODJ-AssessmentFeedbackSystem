/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.apu_oodj_afs.models;

/**
 *
 * @author jamesmcnellylisette
 */
import java.util.regex.Pattern;

public class ValidationUtil {
       // A simple regex to check for a basic email structure (e.g., name@domain.com)
    private static final Pattern EMAIL_PATTERN = Pattern.compile(
        "^[A-Z0-9._%+-]+@[A-Z0-9.-]+\\.[A-Z]{2,6}$",
        Pattern.CASE_INSENSITIVE
    );

    // Ensures this class cannot be instantiated
    private ValidationUtil() {
    }

    /**
     * Checks if a string is not null and not empty after trimming whitespace.
     * @param text The string to check.
     * @return true if the string has content, false otherwise.
     */
    public static boolean isNotEmpty(String text) {
        return text != null && !text.trim().isEmpty();
    }

    /**
     * Validates an email address against a standard email format.
     * @param email The email string to validate.
     * @return true if the email is in a valid format, false otherwise.
     */
    public static boolean isValidEmail(String email) {
        return email != null && EMAIL_PATTERN.matcher(email).matches();
    }

    /**
     * Checks if a password meets the minimum criteria (e.g., at least 8 characters).
     * @param password The password to check.
     * @return true if the password is valid, false otherwise.
     */
    public static boolean isValidPassword(String password) {
        // For this project, we'll just check for a minimum length.
        // A real-world app would have more complex rules (numbers, symbols, etc.)
        return password != null && password.length() >= 8;
    }
 
}
