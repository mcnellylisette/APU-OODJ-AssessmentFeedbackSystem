/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.apu_oodj_afs.models;

import com.mycompany.apu_oodj_afs.gui.LecturerDashboardFrame;
import javax.swing.SwingUtilities;

public class Lecturer extends User {

    public Lecturer(String userID, String username, String password, String name, String role, String email) {
        super(userID, username, password, name, role, email);
    }

    @Override
    public void displayDashboard() {
        SwingUtilities.invokeLater(() -> new LecturerDashboardFrame(this).setVisible(true));
    }
}
