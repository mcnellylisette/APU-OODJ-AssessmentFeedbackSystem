/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.apu_oodj_afs.models;

import java.io.Serializable;
/**
 * User - Abstract base class for all user types in the system
 * @author jamesmcnellylisette
 */
public abstract class User implements Serializable {
    
    private static final long serialVersionUID = 1L;
    
    private String userID;
    private String username;
    private String password;
    private String name;
    private String role;
    private String email;
    
 //Constructor
    
    public User(String userID, String username, String password, String name, String role, String email) {
        this.userID = userID;
        this.username = username;
        this.password = password;
        this.name = name;
        this.role = role;
        this.email = email;
    }
    /**
     * POLYMORPHISM: Every subtype (Admin, Student) must implement their own dashboard
     * This abstract method forces all subclasses to provide their specific implementation
     */
    public abstract void displayDashboard();
    
    // ========================================================================
    // GETTERS (Accessors) - For Encapsulation
    // ========================================================================
    
    public String getuserID() {
        return userID;
    }
    public String getUsername() {
        return username;
    } 
    public String getPassword() {
        return password;
    } 
    public String getName() {
        return name;
    }  
    public String getEmail() {
        return email;
    }   
    public String getRole() {
        return role;
    } 
    // ========================================================================
    // SETTERS (Mutators) - For Encapsulation
    // ======================================================================== 
    public void setuserID(String userID) {
        this.userID = userID;
    }    
    public void setUsername(String username) {
        this.username = username;
    }  
    public void setPassword(String password) {
        this.password = password;
    }   
    public void setName(String name) {
        this.name = name;
    }   
    public void setEmail(String email) {
        this.email = email;
    }   
    public void setRole(String role) {
        this.role = role;
    }
}