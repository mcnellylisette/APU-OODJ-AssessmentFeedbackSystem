package com.mycompany.apu_oodj_afs.gui;

import com.mycompany.apu_oodj_afs.core.StudentDataService;
import com.mycompany.apu_oodj_afs.models.Assessment;
import com.mycompany.apu_oodj_afs.models.Student;
import com.mycompany.apu_oodj_afs.models.StudentFeedback;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.util.List;
import java.util.Map;

public class StudentFeedbackFrame extends JFrame {

    private final Student student;
    private final DefaultTableModel model;

    public StudentFeedbackFrame(Student student) {
        this.student = student;

        setTitle("Student Feedback");
        setSize(900, 420);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        model = new DefaultTableModel(
                new Object[]{"Module", "Assessment", "Feedback", "Lecturer", "Date"},
                0
        ) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        JTable table = new JTable(model);
        JScrollPane scrollPane = new JScrollPane(table);

        JTextField assessmentIdField = new JTextField(14);

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

    private void loadAllFeedback() {
        model.setRowCount(0);

        try {
            // Load mapping once
            Map<String, Assessment> amap = StudentDataService.loadAssessmentMap();

            // Load all feedback for student (service)
            List<StudentFeedback> list = StudentDataService.getFeedbackByStudent(student.getuserID());

            for (StudentFeedback fb : list) {
                String assessmentID = fb.getAssessmentID();

                String module = "N/A";
                String assessmentName = assessmentID;

                Assessment a = amap.get(assessmentID);
                if (a != null) {
                    module = a.getModuleCode();
                    assessmentName = a.getDisplayName();
                }

                model.addRow(new Object[]{
                        module,
                        assessmentName,
                        fb.getFeedbackText(),
                        fb.getLecturerId(),
                        fb.getDate()
                });
            }

            if (list.isEmpty()) {
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

    private void searchByAssessment(String assessmentID) {
        try {
            if (assessmentID == null || assessmentID.isBlank()) {
                throw new IllegalArgumentException("Enter an Assessment ID.");
            }

            model.setRowCount(0);

            Map<String, Assessment> amap = StudentDataService.loadAssessmentMap();

            StudentFeedback fb = student.viewFeedback(assessmentID);

            if (fb == null) {
                JOptionPane.showMessageDialog(this,
                        "No feedback found for Assessment ID: " + assessmentID,
                        "Not Found",
                        JOptionPane.INFORMATION_MESSAGE);
                return;
            }

            String module = "N/A";
            String assessmentName = assessmentID;

            Assessment a = amap.get(assessmentID);
            if (a != null) {
                module = a.getModuleCode();
                assessmentName = a.getDisplayName();
            }

            model.addRow(new Object[]{
                    module,
                    assessmentName,
                    fb.getFeedbackText(),
                    fb.getLecturerId(),
                    fb.getDate()
            });

        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this,
                    ex.getMessage(),
                    "Search Failed",
                    JOptionPane.ERROR_MESSAGE);
        }
    }
}
