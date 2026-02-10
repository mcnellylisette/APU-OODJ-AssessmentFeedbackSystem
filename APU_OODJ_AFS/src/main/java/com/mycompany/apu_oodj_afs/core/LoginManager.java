/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.apu_oodj_afs.core;

import com.mycompany.apu_oodj_afs.models.*;
import java.util.ArrayList;

/**
 * LoginManager - Handles user authentication and session management
 * This is an instance-based class that maintains the current logged-in user
 * 
 * @author jamesmcnellylisette
 */
public class LoginManager {
    
    private static final String USERS_FILE = "users.txt";
    private User currentUser;  // Stores the currently logged-in user
    
    /**
     * Default constructor
     */
    public LoginManager() {
        this.currentUser = null;
    }
    
    /**
     * Authenticates a user with username and password
     * Sets currentUser if authentication succeeds
     * 
     * @param username The username to authenticate
     * @param password The password to verify
     * @return The authenticated User object, or null if authentication fails
     */
    public User authenticate(String username, String password) {
        // Validate input
        if (username == null || username.trim().isEmpty() || 
            password == null || password.trim().isEmpty()) {
            System.err.println("Username and password cannot be empty");
            return null;
        }
        
        // Read all users from file using FileManager
        ArrayList<String> lines = FileManager.readFromFile(USERS_FILE);
        
        if (lines == null || lines.isEmpty()) {
            System.err.println("No users found in the system");
            return null;
        }
        
        // Search for matching user
        for (String line : lines) {
            String[] data = line.split("\\|");
            
            // Validate data format: ID|Username|Password|Name|Email|Role
            if (data.length < 6) {
                continue; // Skip malformed lines
            }
            
            // Check if username and password match
            if (data[1].equals(username) && data[2].equals(password)) {
                String role = data[5].trim();
                
                // Create appropriate User subclass based on role
                User user = createUserFromRole(data, role);
                
                if (user != null) {
                    // Set as current user
                    this.currentUser = user;
                    System.out.println("Login successful: " + username + " (" + role + ")");
                    return user;
                }
            }
        }
        
        // Authentication failed
        System.err.println("Authentication failed: Invalid username or password");
        return null;
    }
    
    /**
     * Logs out the current user
     * Clears the currentUser field
     */
    public void logout() {
        if (currentUser != null) {
            System.out.println("User logged out: " + currentUser.getUsername());
            this.currentUser = null;
        } else {
            System.out.println("No user is currently logged in");
        }
    }
    
    /**
     * Gets the currently logged-in user
     * 
     * @return The current User object, or null if no user is logged in
     */
    public User getCurrentUser() {
        return this.currentUser;
    }
    
    /**
     * Checks if a user is currently logged in
     * 
     * @return true if a user is logged in, false otherwise
     */
    public boolean isLoggedIn() {
        return this.currentUser != null;
    }
    
    /**
     * Helper method to create the appropriate User subclass based on role
     * 
     * @param data The user data array [ID, Username, Password, Name, Email, Role]
     * @param role The user's role
     * @return The created User object, or null if role is unknown
     */
    private User createUserFromRole(String[] data, String role) {
        // Data format: ID|Username|Password|Name|Email|Role
        // User constructor: (userID, username, password, name, role, email)
        
        switch (role) {
            case "Admin":
                return new Admin(data[0], data[1], data[2], data[3], data[5], data[4]);
                
            case "Student":
                return new Student(data[0], data[1], data[2], data[3], data[5], data[4]);
                
            case "Lecturer":
                return new Lecturer(data[0], data[1], data[2], data[3], data[5], data[4]);
                
            case "Academic Leader":
                // TODO: Create AcademicLeader class
                // For now, return as Lecturer
                return new Lecturer(data[0], data[1], data[2], data[3], data[5], data[4]);
                
            default:
                System.err.println("Unknown role: " + role);
                return null;
        }
    }
}