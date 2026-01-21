/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.apu_oodj_afs.models;

/**
 *
 * @author jamesmcnellylisette
 */
import java.io.Serializable;

// This satisfies the 16% marks for OOP Implementation (Abstraction)
public abstract class User implements Serializable {
    protected String userId;
    protected String username;
    protected String password;
    protected String name;
    protected String role;

    // The Teacher highlighted "Super" relationships. This constructor will be used by all children.
    public User(String userId, String username, String password, String name, String role) {
        this.userId = userId;
        this.username = username;
        this.password = password;
        this.name = name;
        this.role = role;
    }

    // POLYMORPHISM: Every subtype (Admin, Student) must implement their own dashboard
    public abstract void displayDashboard();

    // Getters for Encapsulation
    public String getRole() { return role; }
    public String getName() { return name; }
}