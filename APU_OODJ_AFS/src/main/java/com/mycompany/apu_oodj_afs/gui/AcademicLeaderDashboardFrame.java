package com.mycompany.apu_oodj_afs.gui;

import com.mycompany.apu_oodj_afs.models.AcademicLeader;

import javax.swing.*;
import java.awt.*;

public class AcademicLeaderDashboardFrame extends JFrame {

    private final AcademicLeader leader;

    public AcademicLeaderDashboardFrame(AcademicLeader leader) {
        this.leader = leader;

        setTitle("Academic Leader Dashboard - " + leader.getName());
        setSize(500, 400);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        JLabel welcomeLabel = new JLabel("Welcome, " + leader.getName(), SwingConstants.CENTER);
        welcomeLabel.setFont(new Font("Arial", Font.BOLD, 18));

        JButton manageModulesBtn = new JButton("Manage Modules");
        JButton generateReportBtn = new JButton("Generate Student Report");
        JButton editProfileBtn = new JButton("Edit Profile");
        JButton logoutBtn = new JButton("Logout");

        manageModulesBtn.addActionListener(e ->
            new ModuleManagementFrame().setVisible(true)
        );

        generateReportBtn.addActionListener(e ->
            new StudentReportFrame().setVisible(true)
        );

        editProfileBtn.addActionListener(e ->
            new AcademicLeaderEditProfileFrame(leader).setVisible(true)
        );

        logoutBtn.addActionListener(e -> {
            dispose();
            new LoginPage().setVisible(true);
        });

        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new GridLayout(4, 1, 10, 10));
        buttonPanel.add(manageModulesBtn);
        buttonPanel.add(generateReportBtn);
        buttonPanel.add(editProfileBtn);
        buttonPanel.add(logoutBtn);

        setLayout(new BorderLayout());
        add(welcomeLabel, BorderLayout.NORTH);
        add(buttonPanel, BorderLayout.CENTER);
    }
}
