package com.mycompany.apu_oodj_afs.gui;

import com.mycompany.apu_oodj_afs.models.AcademicLeader;

import javax.swing.*;
import java.awt.*;
import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class AcademicLeaderEditProfileFrame extends JFrame {

    private final AcademicLeader leader;

    private JLabel lblUserIdValue;
    private JLabel lblUsernameValue;

    private JTextField txtName;
    private JTextField txtEmail;
    private JPasswordField txtNewPassword;
    private JPasswordField txtConfirmPassword;

    private static final String USERS_FILE = "data/users.txt";

    public AcademicLeaderEditProfileFrame(AcademicLeader leader) {
        this.leader = leader;

        setTitle("Edit Profile - Academic Leader (" + leader.getuserID() + ")");
        setSize(520, 320);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        initUI();
        loadCurrentDetails();
    }

    private void initUI() {
        JPanel panel = new JPanel(new GridBagLayout());
        panel.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));

        GridBagConstraints c = new GridBagConstraints();
        c.insets = new Insets(8, 8, 8, 8);
        c.fill = GridBagConstraints.HORIZONTAL;

        // Row 0 - UserID
        c.gridx = 0; c.gridy = 0;
        panel.add(new JLabel("User ID:"), c);

        lblUserIdValue = new JLabel("-");
        c.gridx = 1; c.gridy = 0;
        panel.add(lblUserIdValue, c);

        // Row 1 - Username
        c.gridx = 0; c.gridy = 1;
        panel.add(new JLabel("Username:"), c);

        lblUsernameValue = new JLabel("-");
        c.gridx = 1; c.gridy = 1;
        panel.add(lblUsernameValue, c);

        // Row 2 - Name
        c.gridx = 0; c.gridy = 2;
        panel.add(new JLabel("Name:"), c);

        txtName = new JTextField(25);
        c.gridx = 1; c.gridy = 2;
        panel.add(txtName, c);

        // Row 3 - Email
        c.gridx = 0; c.gridy = 3;
        panel.add(new JLabel("Email:"), c);

        txtEmail = new JTextField(25);
        c.gridx = 1; c.gridy = 3;
        panel.add(txtEmail, c);

        // Row 4 - New Password
        c.gridx = 0; c.gridy = 4;
        panel.add(new JLabel("New Password:"), c);

        txtNewPassword = new JPasswordField(25);
        c.gridx = 1; c.gridy = 4;
        panel.add(txtNewPassword, c);

        // Row 5 - Confirm Password
        c.gridx = 0; c.gridy = 5;
        panel.add(new JLabel("Confirm Password:"), c);

        txtConfirmPassword = new JPasswordField(25);
        c.gridx = 1; c.gridy = 5;
        panel.add(txtConfirmPassword, c);

        // Buttons
        JButton btnSave = new JButton("Save");
        JButton btnCancel = new JButton("Cancel");

        btnSave.addActionListener(e -> saveProfile());
        btnCancel.addActionListener(e -> dispose());

        JPanel btnPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        btnPanel.add(btnCancel);
        btnPanel.add(btnSave);

        getContentPane().setLayout(new BorderLayout());
        getContentPane().add(panel, BorderLayout.CENTER);
        getContentPane().add(btnPanel, BorderLayout.SOUTH);
    }

    private void loadCurrentDetails() {
        lblUserIdValue.setText(leader.getuserID());
        lblUsernameValue.setText(leader.getUsername());

        txtName.setText(leader.getName());
        txtEmail.setText(leader.getEmail());
    }

    private void saveProfile() {
        try {
            String newName = txtName.getText().trim();
            String newEmail = txtEmail.getText().trim();
            String newPass = new String(txtNewPassword.getPassword()).trim();
            String confirmPass = new String(txtConfirmPassword.getPassword()).trim();

            if (newName.isEmpty()) throw new IllegalArgumentException("Name cannot be empty.");
            if (newEmail.isEmpty()) throw new IllegalArgumentException("Email cannot be empty.");
            if (!newEmail.contains("@") || !newEmail.contains(".")) {
                throw new IllegalArgumentException("Please enter a valid email address.");
            }

            // Password rules:
            // - If both blank => keep old password
            // - If one blank => error
            // - If both filled => must match, then update
            boolean wantsPasswordChange = !newPass.isEmpty() || !confirmPass.isEmpty();
            if (wantsPasswordChange) {
                if (newPass.isEmpty() || confirmPass.isEmpty()) {
                    throw new IllegalArgumentException("Please fill both password fields to change password.");
                }
                if (!newPass.equals(confirmPass)) {
                    throw new IllegalArgumentException("Passwords do not match.");
                }
                if (newPass.length() < 4) {
                    throw new IllegalArgumentException("Password must be at least 4 characters.");
                }
            }

            // Load all lines
            List<String> updatedLines = new ArrayList<>();
            boolean updated = false;

            File file = new File(USERS_FILE);
            if (!file.exists()) {
                throw new FileNotFoundException("users.txt not found in /data. Expected: " + USERS_FILE);
            }

            try (BufferedReader br = new BufferedReader(new FileReader(USERS_FILE))) {
                String line;
                while ((line = br.readLine()) != null) {
                    if (line.trim().isEmpty()) continue;

                    String[] p = line.split("\\|", -1);
                    if (p.length < 6) {
                        // keep malformed lines unchanged
                        updatedLines.add(line);
                        continue;
                    }

                    String id = p[0].trim();
                    String uname = p[1].trim();
                    String pass = p[2]; // keep raw
                    String name = p[3];
                    String email = p[4];
                    String role = p[5];

                    if (id.equalsIgnoreCase(leader.getuserID())) {
                        // Keep username + role the same; update name/email/password as needed
                        String finalPassword = wantsPasswordChange ? newPass : pass;

                        String newLine =
                                id + "|" +
                                uname + "|" +
                                finalPassword + "|" +
                                newName + "|" +
                                newEmail + "|" +
                                role;

                        updatedLines.add(newLine);
                        updated = true;
                    } else {
                        updatedLines.add(line);
                    }
                }
            }

            if (!updated) {
                throw new IllegalStateException("User not found in users.txt: " + leader.getuserID());
            }

            // Write back
            try (BufferedWriter bw = new BufferedWriter(new FileWriter(USERS_FILE))) {
                for (String l : updatedLines) {
                    bw.write(l);
                    bw.newLine();
                }
            }

            JOptionPane.showMessageDialog(this,
                    "Profile updated successfully ✅\n\nYou can now login using your updated credentials.",
                    "Success",
                    JOptionPane.INFORMATION_MESSAGE);

            dispose();

        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this,
                    ex.getMessage(),
                    "Save Failed",
                    JOptionPane.ERROR_MESSAGE);
        }
    }
}