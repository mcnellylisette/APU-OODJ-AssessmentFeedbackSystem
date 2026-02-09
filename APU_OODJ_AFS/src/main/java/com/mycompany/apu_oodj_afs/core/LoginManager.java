/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.apu_oodj_afs.core;

import com.mycompany.apu_oodj_afs.models.*;
import java.io.*;

public class LoginManager {
    public static User authenticate(String username, String password) {
        try (BufferedReader br = new BufferedReader(new FileReader("data/users.txt"))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] data = line.split("\\|");
                if (data.length < 6) continue; 
                
                if (data[1].equals(username) && data[2].equals(password)) {
                    String role = data[5];
                    
                    if (role.equals("Admin")) {
                        return new Admin(data[0], data[1], data[2], data[3], data[5], data[4]);
                    } else if (role.equals("Student")) {
                        return new Student(data[0], data[1], data[2], data[3], data[5], data[4]);
                    } else if (role.equals("Lecturer")) {
                        return new Lecturer(data[0], data[1], data[2], data[3], data[5], data[4]);
                    }
                }
            }
        } catch (IOException e) {
            System.out.println("Error reading users file.");
        }
        return null;
    }
}