/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.apu_oodj_afs.models;

/**
 *
 * @author jamesmcnellylisette
 */

// Person 4 will work on this
public class Student extends User {
   public Student(String userID, String username, String password, String name, String role, String email) {
        // Update: Pass 'email' to super()
        super(userID, username, password, name, role, email);
    }

    @Override
    public void displayDashboard() {
        // This will be linked to the StudentDashboard JFrame later
        System.out.println("Opening Student Dashboard...");
    }
}