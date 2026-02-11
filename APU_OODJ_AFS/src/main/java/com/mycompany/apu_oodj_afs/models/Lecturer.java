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
    // Update: Added 'email' to the constructor
    public Lecturer(String userId, String username, String password, String name, String role, String email) {
        // Update: Passing 'email' to the User parent class
        super(userId, username, password, name, role, email);
    }

    @Override
    public void displayDashboard() {
   
        //System.out.println("Opening Lecturer Dashboard...");
    }
}