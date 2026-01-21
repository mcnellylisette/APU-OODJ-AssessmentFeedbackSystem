/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.apu_oodj_afs.models;

/**
 *
 * @author jamesmcnellylisette
 */
public class Admin extends User {
    
    public Admin(String userId, String username, String password, String name, String role) {
        // Calls the User parent constructor (Constructor Chaining)
        super(userId, username, password, name, role);
    }

    @Override
    public void displayDashboard() {
        // Person 1 (Admin lead) will put their GUI code here
        System.out.println("Opening Admin Dashboard for: " + this.name);
    }
}