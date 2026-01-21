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
    // This makes your Admin GUI visible when the login is successful
     new com.mycompany.apu_oodj_afs.gui.AdminDashboard().setVisible(true);
    }
}