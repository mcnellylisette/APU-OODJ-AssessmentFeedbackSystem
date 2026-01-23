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
     // 1. Prepare data with 6 fields: ID|Username|Password|Name|Email|Role
    String data = user.getUserID() + "|" + 
                  user.getUsername() + "|" + 
                  "temp123" + "|" + 
                  user.getName() + "|" + 
                  user.getEmail() + "|" + 
                  user.getRole();

    // 2. Write to file using Try-With-Resources
    try (java.io.BufferedWriter bw = new java.io.BufferedWriter(new java.io.FileWriter("data/users.txt", true))) {
        bw.write(data);
        bw.newLine();
        return true; // Success!
        } 
        catch (java.io.IOException e)
        {
        return false; // Error handling
        }
       
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
    //Fuck you 
    
    




