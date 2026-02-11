/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.apu_oodj_afs;

import com.mycompany.apu_oodj_afs.core.FileManager;
import com.mycompany.apu_oodj_afs.gui.LoginPage;
import java.util.ArrayList;

/**
 * Main class - Entry point for the APU OODJ AFS application
 * Initializes the system and launches the login screen
 * 
 * @author jamesmcnellylisette
 */
public class Main {
    
    // List of all data files used by the system
    private static final String[] DATA_FILES = {
        "users.txt",
        "classes.txt",
        "grading_system.txt",
        "enrollments.txt",
        "assessments.txt",
        "grades.txt"
    };
    
    /**
     * Main entry point of the application
     * @param args Command line arguments (not used)
     */
    public static void main(String[] args) {
        // Initialize all data files
        initializeFiles();
        
        // Launch the login screen
        launchLoginScreen();
    }
    
    /**
     * Initializes all required data files for the system
     * Creates the data directory and empty files if they don't exist
     * Does not overwrite existing files
     */
    public static void initializeFiles() {
        System.out.println("Initializing system files...");
        
        for (String filename : DATA_FILES) {
            // Check if file already exists
            if (!FileManager.fileExists(filename)) {
                // File doesn't exist - create it with empty content
                boolean success = FileManager.writeToFile(filename, new ArrayList<String>());
                
                if (success) {
                    System.out.println("Created: " + filename);
                } else {
                    System.err.println("Failed to create: " + filename);
                }
            } else {
                System.out.println("Already exists: " + filename);
            }
        }
        
        System.out.println("File initialization complete.");
    }
    
    /**
     * Launches the login screen GUI
     * Creates and displays the LoginPage window
     */
    public static void launchLoginScreen() {
        System.out.println("Launching login screen...");
        
        // Use invokeLater to ensure GUI is created on the Event Dispatch Thread
        java.awt.EventQueue.invokeLater(() -> {
            LoginPage loginPage = new LoginPage();
            loginPage.setVisible(true);
        });
    }
}