/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.apu_oodj_afs.core;

import java.util.regex.Pattern;

/**
 * ValidationUtil - Utility class for input validation
 * Provides static methods for validating user inputs
 * 
 * @author jamesmcnellylisette
 */
public class ValidationUtil {
    
    // Email pattern: name@domain.com
    private static final Pattern EMAIL_PATTERN = Pattern.compile(
        "^[A-Z0-9._%+-]+@[A-Z0-9.-]+\\.[A-Z]{2,6}$",
        Pattern.CASE_INSENSITIVE
    );
    
    // Username pattern: 3-20 alphanumeric characters, underscores allowed
    private static final Pattern USERNAME_PATTERN = Pattern.compile(
        "^[a-zA-Z0-9_]{3,20}$"
    );
    
    // ID pattern: Alphanumeric, 3-15 characters
    private static final Pattern ID_PATTERN = Pattern.compile(
        "^[A-Z0-9]{3,15}$",
        Pattern.CASE_INSENSITIVE
    );
    
    // Minimum password length
    private static final int MIN_PASSWORD_LENGTH = 8;
    
    /**
     * Private constructor to prevent instantiation
     * This is a utility class with only static methods
     */
    private ValidationUtil() {
        throw new UnsupportedOperationException("Utility class cannot be instantiated");
    }
    
    /**
     * Checks if a string is not null and not empty after trimming whitespace.
     * @param text The string to check
     * @return true if the string has content, false otherwise
     */
    public static boolean isNotEmpty(String text) {
        return text != null && !text.trim().isEmpty();
    }
    
    /**
     * Validates an email address against a standard email format.
     * @param email The email string to validate
     * @return true if the email is in a valid format, false otherwise
     */
    public static boolean isValidEmail(String email) {
        return email != null && EMAIL_PATTERN.matcher(email).matches();
    }
    
    /**
     * Checks if a password meets the minimum criteria (at least 8 characters).
     * @param password The password to check
     * @return true if the password is valid, false otherwise
     */
    public static boolean isValidPassword(String password) {
        // For this project, we check for minimum length.
        // A real-world app would have more complex rules (numbers, symbols, etc.)
        return password != null && password.length() >= MIN_PASSWORD_LENGTH;
    }
    
    /**
     * Validates a username format (3-20 alphanumeric characters, underscores allowed)
     * @param username The username to validate
     * @return true if username is valid, false otherwise
     */
    public static boolean isValidUsername(String username) {
        return username != null && USERNAME_PATTERN.matcher(username).matches();
    }
    
    /**
     * Validates an ID format (3-15 alphanumeric characters)
     * @param id The ID to validate
     * @return true if ID is valid, false otherwise
     */
    public static boolean isValidID(String id) {
        return id != null && ID_PATTERN.matcher(id).matches();
    }
    
    /**
     * Validates if a string is a valid positive integer
     * @param text The string to check
     * @return true if the string represents a positive integer, false otherwise
     */
    public static boolean isPositiveInteger(String text) {
        if (text == null || text.trim().isEmpty()) {
            return false;
        }
        
        try {
            int value = Integer.parseInt(text.trim());
            return value > 0;
        } catch (NumberFormatException e) {
            return false;
        }
    }
    
    /**
     * Validates if a string is a valid integer within a range
     * @param text The string to check
     * @param min Minimum value (inclusive)
     * @param max Maximum value (inclusive)
     * @return true if the string represents an integer within range, false otherwise
     */
    public static boolean isIntegerInRange(String text, int min, int max) {
        if (text == null || text.trim().isEmpty()) {
            return false;
        }
        
        try {
            int value = Integer.parseInt(text.trim());
            return value >= min && value <= max;
        } catch (NumberFormatException e) {
            return false;
        }
    }
    
    /**
     * Validates if a string is a valid double/decimal number
     * @param text The string to check
     * @return true if the string represents a valid double, false otherwise
     */
    public static boolean isValidDouble(String text) {
        if (text == null || text.trim().isEmpty()) {
            return false;
        }
        
        try {
            Double.parseDouble(text.trim());
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }
    
    /**
     * Validates if a name contains only letters and spaces (for full names)
     * @param name The name to validate
     * @return true if name is valid, false otherwise
     */
    public static boolean isValidName(String name) {
        if (name == null || name.trim().isEmpty()) {
            return false;
        }
        
        // Allow letters, spaces, hyphens, and apostrophes (e.g., "Mary-Jane O'Brien")
        return name.matches("^[a-zA-Z\\s'-]+$") && name.trim().length() >= 2;
    }
    
    /**
     * Checks if a string length is within specified bounds
     * @param text The string to check
     * @param minLength Minimum length (inclusive)
     * @param maxLength Maximum length (inclusive)
     * @return true if length is within bounds, false otherwise
     */
    public static boolean isLengthValid(String text, int minLength, int maxLength) {
        if (text == null) {
            return false;
        }
        
        int length = text.trim().length();
        return length >= minLength && length <= maxLength;
        
        
        /**
 * Validates if a grade is in valid format (e.g., "A", "A+", "B-")
 */
    }       
    public static boolean isValidGrade(String grade) {
    return grade != null && grade.matches("^[A-F][+-]?$");
    }

/**
 * Validates if a semester number is valid (1-8)
 */
    public static boolean isValidSemester(String semester) {
    return isIntegerInRange(semester, 1, 8);
    }
        
        
    
}   