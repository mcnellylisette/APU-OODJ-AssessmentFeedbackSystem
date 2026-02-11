/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.apu_oodj_afs.models;

import com.mycompany.apu_oodj_afs.core.FileManager;
import java.util.ArrayList;

/**
 * Admin class representing administrative users with enhanced privileges
 * @author jamesmcnellylisette
 */
public class Admin extends User {
    
    private static final String USERS_FILE = "users.txt";
    
    /**
     * Constructor: Passes data to Parent (User)
     */
    public Admin(String userID, String username, String password, String name, String role, String email) {
        super(userID, username, password, name, role, email);
    }
    
    @Override
    public void displayDashboard() {
        System.out.println("Opening Admin Dashboard for: " + getName());
        // new AdminDashboard(this).setVisible(true);
    }
    
    /**
     * Creates a new user and saves to file
     * @param user The user object to create
     * @return true if successful, false otherwise
     */
    public boolean createUser(User user) {
        String data = user.getuserID() + "|" + user.getUsername() + "|" + user.getPassword() + "|"
                    + user.getName() + "|" + user.getEmail() + "|" + user.getRole();
        
        return FileManager.appendToFile(USERS_FILE, data);
    }
    
    /**
     * Retrieves all users from the file
     * @return ArrayList of User objects
     */
    public ArrayList<User> viewAllUsers() {
        ArrayList<User> userList = new ArrayList<>();
        ArrayList<String> lines = FileManager.readFromFile(USERS_FILE);
        
        if (lines == null) {
            System.err.println("Error reading users file");
            return userList;
        }
        
        for (String line : lines) {
            String[] p = line.split("\\|");
            
            // Validate line length to prevent crashes
            if (p.length >= 6) {
                if (p[5].equalsIgnoreCase("Admin")) {
                    userList.add(new Admin(p[0], p[1], p[2], p[3], p[5], p[4]));
                } else if (p[5].equalsIgnoreCase("Student")) {
                    userList.add(new Student(p[0], p[1], p[2], p[3], p[5], p[4]));
                }
                // Add Lecturer/AcademicLeader logic here later
            }
        }
        
        return userList;
    }
    
    /**
     * Updates an existing user in the file
     * @param user The user object with updated information
     * @return true if successful, false otherwise
     */
    public boolean updateUser(User user) {
        ArrayList<String> lines = FileManager.readFromFile(USERS_FILE);
        
        if (lines == null) {
            System.err.println("Error reading users file for update");
            return false;
        }
        
        boolean userFound = false;
        String updatedData = user.getuserID() + "|" + user.getUsername() + "|" + user.getPassword() + "|"
                           + user.getName() + "|" + user.getEmail() + "|" + user.getRole();
        
        // Find and replace the matching user line
        for (int i = 0; i < lines.size(); i++) {
            String line = lines.get(i);
            String[] parts = line.split("\\|");
            
            if (parts.length >= 1 && parts[0].equals(user.getuserID())) {
                lines.set(i, updatedData);
                userFound = true;
                break;
            }
        }
        
        if (!userFound) {
            System.err.println("User ID not found: " + user.getuserID());
            return false;
        }
        
        // Overwrite the file with updated data
        return FileManager.writeToFile(USERS_FILE, lines);
    }
    
    /**
     * Deletes a user from the file
     * @param userID The ID of the user to delete
     * @return true if successful, false otherwise
     */
    public boolean deleteUser(String userID) {
        ArrayList<String> lines = FileManager.readFromFile(USERS_FILE);
        
        if (lines == null) {
            System.err.println("Error reading users file for deletion");
            return false;
        }
        
        ArrayList<String> filteredLines = new ArrayList<>();
        boolean userFound = false;
        
        // Filter out the user with matching ID
        for (String line : lines) {
            String[] parts = line.split("\\|");
            
            if (parts.length >= 1 && parts[0].equals(userID)) {
                userFound = true;
                // Skip this line (don't add to filteredLines)
                continue;
            }
            
            filteredLines.add(line);
        }
        
        if (!userFound) {
            System.err.println("User ID not found for deletion: " + userID);
            return false;
        }
        
        // Overwrite the file with filtered data
        return FileManager.writeToFile(USERS_FILE, filteredLines);
    }
    
    /**
     * Placeholder for viewing grading system
     * @return ArrayList of GradingSystem objects
     */
    public ArrayList<GradingSystem> viewGradingSystem() {
        return new ArrayList<>();
    }
}