/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package gui;

import com.mycompany.apu_oodj_afs.models.User;

import javax.swing.*;
import java.awt.*;

public class AcademicLeaderDashboard extends JFrame {

    private final User leader;

    public AcademicLeaderDashboard(User leader) {
        this.leader = leader;

        setTitle("Academic Leader Dashboard - " + leader.getName());
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(900, 550);
        setLocationRelativeTo(null);

        JTabbedPane tabs = new JTabbedPane();
        tabs.addTab("Modules", new ModulesPanel(leader));

        // Logout tab
        JButton btnLogout = new JButton("Logout");
        btnLogout.addActionListener(e -> {
            dispose();
            new javax.swing.JFrame(); 
        });
        JPanel logoutPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        logoutPanel.add(btnLogout);
        tabs.addTab("Logout", logoutPanel);

        setContentPane(tabs);
    }
}
