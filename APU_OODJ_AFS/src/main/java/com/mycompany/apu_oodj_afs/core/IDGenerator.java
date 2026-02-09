/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.apu_oodj_afs.core;

/**
 *
 * @author jamesmcnellylisette
 */

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;


public class IDGenerator {
    // No longer a single constant file. The filename will be generated.

    // Ensures this class cannot be instantiated
    private IDGenerator() {
    }

    /**
     * Generates a new, unique user ID based on a prefix.
     * For example, calling with "Student" will produce "Student0001", "Student0002", etc.
     * It manages a separate counter file for each prefix (e.g., "student_id_counter.text").
     * @param prefix The prefix for the user role (e.g., "Student", "Lecturer").
     * @return A new, formatted user ID string.
     */
    public static synchronized String generateNewUserID(String prefix) {
        String counterFileName = prefix.toLowerCase() + "_id_counter.txt";
        int nextId = readLastId(counterFileName) + 1;
        writeLastId(nextId, counterFileName);

        // Format the ID with the prefix and leading zeros
        return prefix + String.format("%04d", nextId);
    }

    private static int readLastId(String filename) {
        File file = new File(filename);
        if (!file.exists()) {
            return 0; // If the file doesn't exist for this prefix, start from 0
        }

        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String lastIdStr = reader.readLine();
            if (lastIdStr != null && !lastIdStr.trim().isEmpty()) {
                return Integer.parseInt(lastIdStr.trim());
            }
        } catch (IOException | NumberFormatException e) {
            System.err.println("Error reading ID counter file '" + filename + "', resetting to 0. Error: " + e.getMessage());
            return 0;
        }
        return 0;
    }

    private static void writeLastId(int id, String filename) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filename))) {
            writer.write(String.valueOf(id));
        } catch (IOException e) {
            System.err.println("CRITICAL: Could not write to ID counter file '" + filename + "'. Error: " + e.getMessage());
        }
    }
}

