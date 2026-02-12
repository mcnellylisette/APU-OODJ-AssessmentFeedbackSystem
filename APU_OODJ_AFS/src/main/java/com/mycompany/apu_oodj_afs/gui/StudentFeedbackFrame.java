package com.mycompany.apu_oodj_afs.gui;

import com.mycompany.apu_oodj_afs.core.StudentDataService;
import com.mycompany.apu_oodj_afs.models.Student;
import com.mycompany.apu_oodj_afs.models.StudentFeedback;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.util.List;

public class StudentFeedbackFrame extends JFrame {

    private final Student student;
    private final DefaultTableModel model;

    public StudentFeedbackFrame(Student student) {
        this.student = student;

        setTitle("Student Feedback");
        setSize(800, 400);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        // Table Model
        model = new DefaultTableModel(
                new Object[]{"Module", "Assessment ID", "Feedback", "Lecturer", "Date"},
                0
        ) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        JTable table = new JTable(model);
        JScrollPane scrollPane = new JScrollPane(table);

        // --- Search Section ---
        JTextField assessmentIdField = new JTextField(12);

        JButton searchBtn = new JButton("Search");
        searchBtn.addActionListener(e -> searchByAssessment(assessmentIdField.getText().trim()));

        JButton viewAllBtn = new JButton("View All");
        viewAllBtn.addActionListener(e -> loadAllFeedback());

        JPanel topPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        topPanel.add(new JLabel("Student: " + student.getuserID()));
        topPanel.add(new JLabel("Assessment ID:"));
        topPanel.add(assessmentIdField);
        topPanel.add(searchBtn);
        topPanel.add(viewAllBtn);

        JButton closeBtn = new JButton("Close");
        closeBtn.addActionListener(e -> dispose());

        add(topPanel, BorderLayout.NORTH);
        add(scrollPane, BorderLayout.CENTER);
        add(closeBtn, BorderLayout.SOUTH);

        loadAllFeedback();
    }

    // Load all feedback for student
    private void loadAllFeedback() {
        model.setRowCount(0);
        try {
            List<StudentFeedback> feedbackList =
                    StudentDataService.getFeedbackByStudent(student.getuserID());

            for (StudentFeedback f : feedbackList) {
                model.addRow(new Object[]{
                        f.getModuleCode(),
                        f.getAssessmentID(),
                        f.getFeedbackText(),
                        f.getLecturerId(),
                        f.getDate()
                });
            }

            if (feedbackList.isEmpty()) {
                JOptionPane.showMessageDialog(this,
                        "No feedback found.",
                        "Information",
                        JOptionPane.INFORMATION_MESSAGE);
            }

        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this,
                    ex.getMessage(),
                    "Error",
                    JOptionPane.ERROR_MESSAGE);
        }
    }

    // Search feedback by Assessment ID (diagram aligned)
    private void searchByAssessment(String assessmentID) {
        try {
            if (assessmentID.isBlank()) {
                throw new IllegalArgumentException("Enter an Assessment ID.");
            }

            model.setRowCount(0);

            StudentFeedback f = student.viewFeedback(assessmentID);

            if (f == null) {
                JOptionPane.showMessageDialog(this,
                        "No feedback found for Assessment ID: " + assessmentID,
                        "Not Found",
                        JOptionPane.INFORMATION_MESSAGE);
                return;
            }

            model.addRow(new Object[]{
                    f.getModuleCode(),
                    f.getAssessmentID(),
                    f.getFeedbackText(),
                    f.getLecturerId(),
                    f.getDate()
            });

        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this,
                    ex.getMessage(),
                    "Search Failed",
                    JOptionPane.ERROR_MESSAGE);
        }
    }
}