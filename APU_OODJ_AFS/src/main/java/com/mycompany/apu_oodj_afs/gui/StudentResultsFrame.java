package com.mycompany.apu_oodj_afs.gui;

import com.mycompany.apu_oodj_afs.core.StudentDataService;
import com.mycompany.apu_oodj_afs.models.Assessment;
import com.mycompany.apu_oodj_afs.models.Result;
import com.mycompany.apu_oodj_afs.models.Student;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.util.ArrayList;
import java.util.Map;

public class StudentResultsFrame extends JFrame {

    private final Student student;
    private final DefaultTableModel model;

    public StudentResultsFrame(Student student) {
        this.student = student;

        setTitle("Assessment Marks");
        setSize(850, 420);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        model = new DefaultTableModel(
                new Object[]{"Module", "Assessment", "Marks", "Grade"},
                0
        ) {
            @Override
            public boolean isCellEditable(int row, int col) {
                return false;
            }
        };

        JTable table = new JTable(model);
        JScrollPane scroll = new JScrollPane(table);

        JButton refreshBtn = new JButton("Refresh");
        refreshBtn.addActionListener(e -> load());

        JPanel top = new JPanel(new FlowLayout(FlowLayout.LEFT));
        top.add(new JLabel("Student: " + student.getuserID()));
        top.add(refreshBtn);

        JButton closeBtn = new JButton("Close");
        closeBtn.addActionListener(e -> dispose());

        add(top, BorderLayout.NORTH);
        add(scroll, BorderLayout.CENTER);
        add(closeBtn, BorderLayout.SOUTH);

        load();
    }

    private void load() {
        model.setRowCount(0);

        try {
            // Results come from Student model (diagram-aligned)
            ArrayList<Result> results = student.viewResults();

            // AssessmentID -> Assessment metadata map (module, type, title)
            Map<String, Assessment> amap = StudentDataService.loadAssessmentMap();

            for (Result r : results) {
                String assessmentID = r.getAssessmentID();

                String module = "N/A";
                String assessmentName = assessmentID;

                Assessment a = amap.get(assessmentID);
                if (a != null) {
                    module = a.getModuleCode();
                    assessmentName = a.getDisplayName(); // "Exam - Final exams"
                }

                // If grade in file is "-" or empty, compute it from marks
                String grade = r.getGrade();
                if (grade == null || grade.isBlank() || grade.equals("-")) {
                    grade = Result.calculateGrade(r.getMarks());
                }

                model.addRow(new Object[]{
                        module,
                        assessmentName,
                        r.getMarks(),
                        grade
                });
            }

            if (results.isEmpty()) {
                JOptionPane.showMessageDialog(this,
                        "No results found yet.",
                        "Info",
                        JOptionPane.INFORMATION_MESSAGE);
            }

        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this,
                    ex.getMessage(),
                    "Error",
                    JOptionPane.ERROR_MESSAGE);
        }
    }
}