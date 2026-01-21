/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.apu_oodj_afs.models;

/**
 *
 * @author jamesmcnellylisette
 */

// Person 3 will work on this
public class Lecturer extends User {
    public Lecturer(String userId, String username, String password, String name, String role) {
        super(userId, username, password, name, role);
    }

    @Override
    public void displayDashboard() {
        // This will be linked to the LecturerDashboard JFrame later
        System.out.println("Opening Lecturer Dashboard...");
    }
}