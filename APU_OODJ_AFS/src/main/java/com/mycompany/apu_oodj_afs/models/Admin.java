/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.apu_oodj_afs.models;

import java.io.*;
import java.util.ArrayList;

public class Admin extends User {

// Constructor: Passes data to Parent (User)
public Admin(String userID, String username, String password, String name, String role, String email) {
super(userID, username, password, name, role, email);
}

@Override
public void displayDashboard() {
System.out.println("Opening Admin Dashboard for: " + getName());
// new AdminDashboard(this).setVisible(true);
}

// --- CREATE USER (Now with real password) ---
public boolean createUser(User user) {
// Use user.getPassword() instead of "temp123"
String data = user.getuserID() + "|" + user.getUsername() + "|" + user.getPassword() + "|"
+ user.getName() + "|" + user.getEmail() + "|" + user.getRole();

try (BufferedWriter bw = new BufferedWriter(new FileWriter("data/users.txt", true))) {
    bw.write(data);
    bw.newLine();
    return true;
} 
catch (IOException e) {
System.err.println("Error saving user: " + e.getMessage());
    return false;
}
}

// --- VIEW ALL USERS (Reads from file) ---
public ArrayList<User> viewAllUsers() {
ArrayList<User> userList = new ArrayList<>();
try (BufferedReader br = new BufferedReader(new FileReader("data/users.txt"))) {
String line;
while ((line = br.readLine()) != null) {
String[] p = line.split("\\|");
// Validate line length to prevent crashes
if (p.length >= 6) {
    if (p[5].equalsIgnoreCase("Admin")) {
        userList.add(new Admin(p[0], p[1], p[2], p[3], p[5], p[4]));
        } else if 
                (p[5].equalsIgnoreCase("Student")) {
userList.add(new Student(p[0], p[1], p[2], p[3], p[5], p[4]));
}
// Add Lecturer/AcademicLeader logic here later
}
}
} catch (IOException e) {
System.err.println("Error reading users: " + e.getMessage());
}
return userList;
}

// Placeholder methods for future implementation
public boolean updateUser(User user) { return true; }
public boolean deleteUser(String UserID) { return true; }
public ArrayList<GradingSystem> viewGradingSystem() { return new ArrayList<>(); }
}
