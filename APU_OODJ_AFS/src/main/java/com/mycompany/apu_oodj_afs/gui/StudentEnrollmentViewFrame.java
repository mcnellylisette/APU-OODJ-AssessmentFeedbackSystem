package com.mycompany.apu_oodj_afs.gui;

import com.mycompany.apu_oodj_afs.models.Enrollment;
import com.mycompany.apu_oodj_afs.models.Student;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.ArrayList;

public class StudentEnrollmentViewFrame extends JFrame {

    private final Student student;
    private final DefaultTableModel model;

    public StudentEnrollmentViewFrame(Student student) {
        this.student = student;

        setTitle("My Enrollments");
        setSize(750, 400);
        setLocationRelativeTo(null);

        model = new DefaultTableModel(
                new Object[]{"Enrollment ID", "Class ID", "Module Code", "Date", "Status"},
                0
        ) {
            @Override
            public boolean isCellEditable(int row, int col) {
                return false;
            }
        };

        JTable table = new JTable(model);
        JScrollPane scrollPane = new JScrollPane(table);

        JButton refreshBtn = new JButton("Refresh");
        refreshBtn.addActionListener(e -> load());

        JButton closeBtn = new JButton("Close");
        closeBtn.addActionListener(e -> dispose());

        JPanel top = new JPanel(new FlowLayout(FlowLayout.LEFT));
        top.add(new JLabel("Student: " + student.getName()));
        top.add(refreshBtn);

        JPanel bottom = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        bottom.add(closeBtn);

        add(top, BorderLayout.NORTH);
        add(scrollPane, BorderLayout.CENTER);
        add(bottom, BorderLayout.SOUTH);

        load();
    }

    private void load() {
        model.setRowCount(0);
        ArrayList<Enrollment> enrollments = student.viewEnrollment();

        for (Enrollment e : enrollments) {
            model.addRow(new Object[]{
                    e.getEnrollmentID(),
                    e.getClassId(),
                    e.getModuleCode(),
                    e.getEnrollDate(),
                    e.getStatus()
            });
        }

        if (enrollments.isEmpty()) {
            JOptionPane.showMessageDialog(this,
                    "No enrollments found.",
                    "Information",
                    JOptionPane.INFORMATION_MESSAGE);
        }
    }
}
