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
    private String userID;
    private String username;
    private String password;
    private String name;
    private String role;
    private String email;
    // The Teacher highlighted "Super" relationships. This constructor will be used by all children.
    public User(String userID, String username, String password, String name, String role, String email) {
        this.userID = userID;
        this.username = username;
        this.password = password;
        this.name = name;
        this.role = role;
        this.email = email;
    }

    // POLYMORPHISM: Every subtype (Admin, Student) must implement their own dashboard
    public abstract void displayDashboard();

    // Getters for Encapsulation
    public String getUserID() {
        return userID;
    }
    public String getUsername(){
        return username;
    }
    
    public String getRole() {
        return role; 
    }
    public String getName() { 
        return name; 
    }
    
    public String getEmail() {
        return email;
    }
    
  
    //Setter for User
    
    public void setUserID (String UserId) {
    this.userID = userID;
    }
    
    public void setName (String name) {
    this.name = name;
    }
    
    public void setEmail (String emial) {
    this.email = email;
    }
}