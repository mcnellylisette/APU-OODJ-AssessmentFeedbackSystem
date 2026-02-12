/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.apu_oodj_afs.core;

import com.mycompany.apu_oodj_afs.models.*;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class LoginManager {

    public static User authenticate(String username, String password) {

        try (BufferedReader br = new BufferedReader(new FileReader("data/users.txt"))) {

            String line;
            while ((line = br.readLine()) != null) {
                if (line.trim().isEmpty()) continue;

                String[] data = line.split("\\|");
                if (data.length < 6) continue;

                String id = data[0].trim();
                String uname = data[1].trim();
                String pass = data[2].trim();
                String name = data[3].trim();
                String role = data[4].trim();   // ✅ role is index 4
                String email = data[5].trim();  // ✅ email is index 5

                if (uname.equals(username) && pass.equals(password)) {

                    // Return correct subclass based on ROLE
                    if (role.equalsIgnoreCase("ADMIN")) {
                        return new Admin(id, uname, pass, name, role, email);
                    }

                    if (role.equalsIgnoreCase("STUDENT")) {
                        return new Student(id, uname, pass, name, role, email);
                    }

                    if (role.equalsIgnoreCase("LECTURER")) {
                        return new Lecturer(id, uname, pass, name, role, email);
                    }

                    if (role.equalsIgnoreCase("ACADEMIC_LEADER")) {
                        return new AcademicLeader(id, uname, pass, name, role, email);
                    }

                    // fallback (in case role is something unexpected)

                    return new User(id, uname, pass, name, role, email) {
                        @Override
                        public void displayDashboard() {
                            throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
                        }
                    };
                }
            }

        } catch (IOException e) {
            System.out.println("Error reading users file: " + e.getMessage());
        }

        return null;
    }

    private static class AcademicLeader extends User {

        public AcademicLeader(String id, String uname, String pass,
                      String name, String role, String email) {
        super(id, uname, pass, name, role, email);
    }

        @Override
        public void displayDashboard() {
            throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
        }
    }
}
