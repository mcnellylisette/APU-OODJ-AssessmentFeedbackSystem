/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.apu_oodj_afs.models;

/**
 *
 * @author jamesmcnellylisette
 */
//Import Array utility
import java.util.ArrayList; 

public class Admin extends User {
    public Admin(String userID, String username , String password, String name,String role, String email){
        super(userID, username, password, name ,role, email);
        }
    
    @Override
    public void displayDashboard() {
        // Eventually, this will open your Admin GUI!
        System.out.println("Welcome, Admin! Opening your dashboard...");
    }
    
    public boolean createUser(User user){
    return true;
    }
    
   public ArrayList<User> viewAllUsers() {
       ArrayList<User> userList = new ArrayList<>();
       return userList;
   }
   
   public boolean updateUser(User user) {
    return true;
    
    }
    
   public boolean deleteUser (String UserID) {
       return true;
     }
   
   public boolean createClass(Class classObj){
       return true;
   
   }
   
   public ArrayList<GradingSystem> viewGradingSystem() {
      ArrayList<GradingSystem> grades = new ArrayList<>(); 
      return grades;
   }
   
   
   }
    
    
    




