package com.mycompany.apu_oodj_afs.gui;

import com.mycompany.apu_oodj_afs.core.StudentDataService;
import com.mycompany.apu_oodj_afs.models.Student;

import javax.swing.*;
import java.awt.*;

public class StudentClassRegistrationFrame extends JFrame {

    private final Student student;

    public StudentClassRegistrationFrame(Student student) {
        this.student = student;

        setTitle("Class Registration");
        setSize(520, 260);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        JTextField classIdField = new JTextField();
        JTextField moduleCodeField = new JTextField();

        JPanel form = new JPanel(new GridLayout(2, 2, 10, 10));
        form.setBorder(BorderFactory.createEmptyBorder(18, 18, 10, 18));
        form.add(new JLabel("Class ID:"));
        form.add(classIdField);
        form.add(new JLabel("Module Code:"));
        form.add(moduleCodeField);

        JButton enrollBtn = new JButton("Enroll");
        enrollBtn.addActionListener(e -> {
            try {
                StudentDataService.addEnrollment(
                        student.getuserID(),
                        classIdField.getText().trim(),
                        moduleCodeField.getText().trim()
                );
                JOptionPane.showMessageDialog(this, "Enrollment successful!", "Success", JOptionPane.INFORMATION_MESSAGE);
                classIdField.setText("");
                moduleCodeField.setText("");
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, ex.getMessage(), "Enrollment Failed", JOptionPane.ERROR_MESSAGE);
            }
        });

        JButton closeBtn = new JButton("Close");
        closeBtn.addActionListener(e -> dispose());

        JPanel buttons = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        buttons.add(enrollBtn);
        buttons.add(closeBtn);

        add(form, BorderLayout.CENTER);
        add(buttons, BorderLayout.SOUTH);
    }
}
