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

        // Method 1: Generates the User ID (A001, S001, etc.)
    public static synchronized String generateNewUserID(String prefix) {
        File dataDir = new File("data");
        if (!dataDir.exists()) dataDir.mkdirs();

        String type = prefix.toLowerCase().replace(" ", "_");
        String counterFile = "data/" + type + "_id_counter.txt";
        
        int nextId = 0;
        File f = new File(counterFile);
        if (f.exists()) {
            try (BufferedReader r = new BufferedReader(new FileReader(f))) {
                nextId = Integer.parseInt(r.readLine().trim());
            } catch (Exception e) {}
        }
        
        nextId++;
        try (BufferedWriter w = new BufferedWriter(new FileWriter(f))) {
            w.write(String.valueOf(nextId));
        } catch (IOException e) {}

        String finalPrefix = "S";
        if (prefix.equalsIgnoreCase("Admin")) finalPrefix = "A";
        else if (prefix.equalsIgnoreCase("Academic Leader")) finalPrefix = "AL";
        else if (prefix.equalsIgnoreCase("Lecturer")) finalPrefix = "L";

        return finalPrefix + String.format("%03d", nextId);
    }

    // Method 2: Generates the Unique Username (e.g., james42)
    public static String generateUsername(String fullName) {
        String firstName = fullName.split(" ")[0].toLowerCase();
        int randomNum = (int)(Math.random() * 90) + 10; // Random number 10-99
        return firstName + randomNum;
    }

}


