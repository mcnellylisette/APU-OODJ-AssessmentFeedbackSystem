/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.apu_oodj_afs.core;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Utility class for centralized file management operations.
 * Handles all file I/O operations for the application.
 * 
 * @author jamesmcnellylisette
 */
public class FileManager {
    
    private static final String DATA_DIR = "data/";
    private static final java.util.logging.Logger logger = 
            java.util.logging.Logger.getLogger(FileManager.class.getName());
    
    /**
     * Private constructor to prevent instantiation.
     * This is a utility class with only static methods.
     */
    private FileManager() {
        throw new UnsupportedOperationException("Utility class cannot be instantiated");
    }
    
    /**
     * Reads all lines from a file and returns them as an ArrayList.
     * 
     * @param filename The name of the file to read (without path)
     * @return ArrayList containing all lines from the file, or empty list if file doesn't exist or error occurs
     */
    public static ArrayList<String> readFromFile(String filename) {
        ArrayList<String> lines = new ArrayList<>();
        String filepath = DATA_DIR + filename;
        
        File file = new File(filepath);
        if (!file.exists()) {
            logger.warning("File does not exist: " + filepath);
            return lines; // Return empty list
        }
        
        try (BufferedReader br = new BufferedReader(new FileReader(filepath))) {
            String line;
            while ((line = br.readLine()) != null) {
                lines.add(line);
            }
        } catch (IOException e) {
            logger.log(java.util.logging.Level.SEVERE, "Error reading file: " + filepath, e);
        }
        
        return lines;
    }
    
    /**
     * Writes data to a file, overwriting existing content.
     * Creates the data directory if it doesn't exist.
     * 
     * @param filename The name of the file to write (without path)
     * @param data List of strings to write to the file
     * @return true if successful, false otherwise
     */
    public static boolean writeToFile(String filename, List<String> data) {
        String filepath = DATA_DIR + filename;
        
        try {
            // Create data directory if it doesn't exist
            File directory = new File(DATA_DIR);
            if (!directory.exists()) {
                directory.mkdirs();
            }
            
            try (BufferedWriter bw = new BufferedWriter(new FileWriter(filepath, false))) {
                for (String line : data) {
                    bw.write(line);
                    bw.newLine();
                }
                return true;
            }
        } catch (IOException e) {
            logger.log(java.util.logging.Level.SEVERE, "Error writing to file: " + filepath, e);
            return false;
        }
    }
    
    /**
     * Appends a single line of data to a file.
     * Creates the file and directory if they don't exist.
     * 
     * @param filename The name of the file to append to (without path)
     * @param data The string to append to the file
     * @return true if successful, false otherwise
     */
    public static boolean appendToFile(String filename, String data) {
        String filepath = DATA_DIR + filename;
        
        try {
            // Create data directory if it doesn't exist
            File directory = new File(DATA_DIR);
            if (!directory.exists()) {
                directory.mkdirs();
            }
            
            try (BufferedWriter bw = new BufferedWriter(new FileWriter(filepath, true))) {
                bw.write(data);
                bw.newLine();
                return true;
            }
        } catch (IOException e) {
            logger.log(java.util.logging.Level.SEVERE, "Error appending to file: " + filepath, e);
            return false;
        }
    }
    
    /**
     * Deletes a line from a file that starts with the specified identifier.
     * Reads the file, removes matching line, and overwrites the file.
     * 
     * @param filename The name of the file to modify (without path)
     * @param identifier The identifier to search for at the start of lines (e.g., "S001")
     * @return true if line was found and deleted, false otherwise
     */
    public static boolean deleteFromFile(String filename, String identifier) {
        ArrayList<String> lines = readFromFile(filename);
        
        if (lines.isEmpty()) {
            logger.warning("File is empty or does not exist: " + filename);
            return false;
        }
        
        boolean found = false;
        ArrayList<String> filteredLines = new ArrayList<>();
        
        for (String line : lines) {
            // Check if line starts with the identifier (before first delimiter)
            if (line.startsWith(identifier + "|") || line.equals(identifier)) {
                found = true;
                // Skip this line (don't add to filteredLines)
                continue;
            }
            filteredLines.add(line);
        }
        
        if (!found) {
            logger.warning("Identifier not found in file: " + identifier);
            return false;
        }
        
        // Overwrite file with filtered content
        return writeToFile(filename, filteredLines);
    }
    
    /**
     * Checks if a file exists in the data directory.
     * 
     * @param filename The name of the file to check (without path)
     * @return true if file exists, false otherwise
     */
    public static boolean fileExists(String filename) {
        String filepath = DATA_DIR + filename;
        File file = new File(filepath);
        return file.exists() && file.isFile();
    }
}