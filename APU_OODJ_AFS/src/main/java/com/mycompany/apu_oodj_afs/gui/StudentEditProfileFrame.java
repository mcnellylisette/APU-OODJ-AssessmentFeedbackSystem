package com.mycompany.apu_oodj_afs.gui;

import com.mycompany.apu_oodj_afs.core.UserProfileService;
import com.mycompany.apu_oodj_afs.models.Student;

import javax.swing.*;
import java.awt.*;

public class StudentEditProfileFrame extends JFrame {

    private final Student student;

    public StudentEditProfileFrame(Student student) {
        this.student = student;

        setTitle("Edit Profile");
        setSize(520, 360);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        JTextField userIdField = new JTextField(student.getuserID());
        userIdField.setEditable(false);

        JTextField usernameField = new JTextField(student.getUsername());
        usernameField.setEditable(false);

        JTextField roleField = new JTextField(student.getRole());
        roleField.setEditable(false);

        JTextField nameField = new JTextField(student.getName());
        JTextField emailField = new JTextField(student.getEmail());

        JCheckBox changePassCheck = new JCheckBox("Change password");
        JPasswordField passField = new JPasswordField();
        JPasswordField confirmPassField = new JPasswordField();

        passField.setEnabled(false);
        confirmPassField.setEnabled(false);

        changePassCheck.addActionListener(e -> {
            boolean on = changePassCheck.isSelected();
            passField.setEnabled(on);
            confirmPassField.setEnabled(on);
            if (!on) {
                passField.setText("");
                confirmPassField.setText("");
            }
        });

        JPanel form = new JPanel(new GridLayout(8, 2, 10, 10));
        form.setBorder(BorderFactory.createEmptyBorder(16, 16, 10, 16));

        form.add(new JLabel("User ID:"));
        form.add(userIdField);

        form.add(new JLabel("Username:"));
        form.add(usernameField);

        form.add(new JLabel("Role:"));
        form.add(roleField);

        form.add(new JLabel("Full Name:"));
        form.add(nameField);

        form.add(new JLabel("Email:"));
        form.add(emailField);

        form.add(new JLabel(" "));
        form.add(changePassCheck);

        form.add(new JLabel("New Password:"));
        form.add(passField);

        form.add(new JLabel("Confirm Password:"));
        form.add(confirmPassField);

        JButton saveBtn = new JButton("Save Changes");
        saveBtn.addActionListener(e -> {
            try {
                String newName = nameField.getText().trim();
                String newEmail = emailField.getText().trim();

                String newPassword = null;

                if (changePassCheck.isSelected()) {
                    String p1 = new String(passField.getPassword());
                    String p2 = new String(confirmPassField.getPassword());

                    if (p1.isBlank()) throw new IllegalArgumentException("New password cannot be empty.");
                    if (p1.length() < 4) throw new IllegalArgumentException("Password must be at least 4 characters.");
                    if (!p1.equals(p2)) throw new IllegalArgumentException("Passwords do not match.");

                    newPassword = p1;
                }

                UserProfileService.updateUserProfile(student, newName, newEmail, newPassword);

                JOptionPane.showMessageDialog(this, "Profile updated successfully!", "Success",
                        JOptionPane.INFORMATION_MESSAGE);

                dispose(); // close window

            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, ex.getMessage(), "Update Failed",
                        JOptionPane.ERROR_MESSAGE);
            }
        });

        JButton cancelBtn = new JButton("Cancel");
        cancelBtn.addActionListener(e -> dispose());

        JPanel buttons = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        buttons.add(saveBtn);
        buttons.add(cancelBtn);

        add(form, BorderLayout.CENTER);
        add(buttons, BorderLayout.SOUTH);
    }
}
