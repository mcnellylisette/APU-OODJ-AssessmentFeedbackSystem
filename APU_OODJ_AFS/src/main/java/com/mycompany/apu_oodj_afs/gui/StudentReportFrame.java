package com.mycompany.apu_oodj_afs.gui;

import com.mycompany.apu_oodj_afs.core.StudentDataService;
import com.mycompany.apu_oodj_afs.models.Assessment;
import com.mycompany.apu_oodj_afs.models.Result;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Map;

public class StudentReportFrame extends JFrame {

    private JTextField txtStudentID;
    private JLabel lblStudentName;
    private JLabel lblSummary;
    private DefaultTableModel model;

    // Save file for reports (simple text persistence)
    private static final String REPORTS_FILE = "data/reports.txt";

    public StudentReportFrame() {

        setTitle("Generate Student Report");
        setSize(1000, 520);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        // ===== Header UI =====
        txtStudentID = new JTextField(15);
        JButton btnGenerate = new JButton("Generate");
        JButton btnSave = new JButton("Save Report");

        lblStudentName = new JLabel("Student: -");
        lblSummary = new JLabel("Summary: -");

        btnGenerate.addActionListener(e -> generateReport());
        btnSave.addActionListener(e -> saveReport());

        JPanel top = new JPanel(new FlowLayout(FlowLayout.LEFT));
        top.add(new JLabel("Student ID:"));
        top.add(txtStudentID);
        top.add(btnGenerate);
        top.add(btnSave);

        JPanel meta = new JPanel(new GridLayout(2, 1));
        meta.add(lblStudentName);
        meta.add(lblSummary);

        JPanel north = new JPanel(new BorderLayout());
        north.add(top, BorderLayout.NORTH);
        north.add(meta, BorderLayout.SOUTH);

        // ===== Table =====
        model = new DefaultTableModel(
                new Object[]{"Module", "Assessment Type", "Title", "Weight", "Marks", "Grade"},
                0
        ) {
            @Override
            public boolean isCellEditable(int row, int col) {
                return false;
            }
        };

        JTable table = new JTable(model);
        JScrollPane scrollPane = new JScrollPane(table);

        setLayout(new BorderLayout());
        add(north, BorderLayout.NORTH);
        add(scrollPane, BorderLayout.CENTER);
    }

    private void generateReport() {
        try {
            model.setRowCount(0);

            String studentID = txtStudentID.getText().trim();
            if (studentID.isBlank()) {
                throw new IllegalArgumentException("Enter Student ID.");
            }

            // Student name (from users.txt)
            String studentName = findStudentName(studentID);
            lblStudentName.setText("Student: " + studentID + (studentName == null ? "" : " - " + studentName));

            // Pull results + assessment lookup
            List<Result> results = StudentDataService.getResultsByStudent(studentID);
            Map<String, Assessment> amap = StudentDataService.loadAssessmentMap();

            // Summary calculations
            int total = results.size();
            double sum = 0.0;
            int passCount = 0;

            for (Result r : results) {
                String assessmentID = r.getAssessmentID();

                String module = "N/A";
                String type = "N/A";
                String title = assessmentID;
                String weight = "N/A";

                Assessment a = amap.get(assessmentID);
                if (a != null) {
                    module = a.getModuleCode();
                    type = a.getAssessmentType();
                    title = a.getTitle();
                    weight = String.valueOf(a.getWeight());
                }

                // grade fallback (if some records have "-" or blank)
                String grade = r.getGrade();
                if (grade == null || grade.isBlank() || grade.equals("-")) {
                    grade = Result.calculateGrade(r.getMarks());
                }

                // pass logic: treat A/B/C/D as pass, F as fail
                if (!grade.equalsIgnoreCase("F")) {
                    passCount++;
                }

                sum += r.getMarks();

                model.addRow(new Object[]{
                        module,
                        type,
                        title,
                        weight,
                        r.getMarks(),
                        grade
                });
            }

            if (results.isEmpty()) {
                lblSummary.setText("Summary: No records found.");
                JOptionPane.showMessageDialog(this,
                        "No records found.",
                        "Info",
                        JOptionPane.INFORMATION_MESSAGE);
                return;
            }

            double avg = sum / total;
            double passRate = (passCount * 100.0) / total;

            lblSummary.setText(String.format(
                    "Summary: %d assessments | Average: %.2f | Pass rate: %.1f%%",
                    total, avg, passRate
            ));

        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this,
                    ex.getMessage(),
                    "Error",
                    JOptionPane.ERROR_MESSAGE);
        }
    }

    // ===== Save report to data/reports.txt =====
    private void saveReport() {
        try {
            String studentID = txtStudentID.getText().trim();
            if (studentID.isBlank()) {
                throw new IllegalArgumentException("Enter Student ID and generate the report first.");
            }

            if (model.getRowCount() == 0) {
                throw new IllegalArgumentException("No report data to save. Click Generate first.");
            }

            String studentName = findStudentName(studentID);
            String namePart = (studentName == null) ? "" : (" (" + studentName + ")");

            String reportId = "R" + System.currentTimeMillis();
            String createdAt = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));

            // Build a simple summary text
            String summary = lblSummary.getText().replace("Summary: ", "").trim();

            // Save format:
            // reportId|studentId|studentName|createdAt|summary
            try (BufferedWriter bw = new BufferedWriter(new FileWriter(REPORTS_FILE, true))) {
                bw.write(reportId + "|" + studentID + "|" + (studentName == null ? "" : studentName) + "|" + createdAt + "|" + summary);
                bw.newLine();
            }

            JOptionPane.showMessageDialog(this,
                    "Report saved successfully ✅\nReport ID: " + reportId + "\nStudent: " + studentID + namePart,
                    "Saved",
                    JOptionPane.INFORMATION_MESSAGE);

        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this,
                    ex.getMessage(),
                    "Save Failed",
                    JOptionPane.ERROR_MESSAGE);
        }
    }

    // ===== Helper: find student name from users.txt =====
    // users.txt format: userID|username|password|name|email|role
    private String findStudentName(String studentID) {
        try (BufferedReader br = new BufferedReader(new FileReader("data/users.txt"))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] p = line.split("\\|", -1);
                if (p.length < 6) continue;

                String id = p[0].trim();
                String name = p[3].trim();
                String role = p[5].trim();

                if (id.equalsIgnoreCase(studentID) && role.equalsIgnoreCase("Student")) {
                    return name;
                }
            }
        } catch (Exception ignored) {
        }
        return null;
    }
}