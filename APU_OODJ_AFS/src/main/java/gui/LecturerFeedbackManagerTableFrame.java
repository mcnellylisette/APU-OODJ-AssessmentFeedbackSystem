/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package gui;

import com.mycompany.apu_oodj_afs.core.LecturerData;
import com.mycompany.apu_oodj_afs.models.Lecturer;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

/**
 * LecturerFeedbackManagerTableFrame
 *
 * What this screen does:
 * 1) Shows ALL feedback created by the logged-in lecturer in a JTable
 * 2) Allows lecturer to select a student + assessment and type feedback
 * 3) Saves feedback into: data/feedback.txt
 *
 * File format (feedback.txt):
 * feedbackId|studentId|assessmentId|lecturerId|comment
 */
public class LecturerFeedbackManagerTableFrame extends JFrame {

    // Logged-in lecturer
    private final Lecturer lecturer;

    // JTable + model
    private final DefaultTableModel tableModel;
    private final JTable table;

    // Inputs for creating feedback
    private final JComboBox<String> cmbStudent;
    private final JComboBox<String> cmbAssessment;
    private final JTextArea txtComment = new JTextArea(4, 40);

    // Table column headers
    private static final String[] COLUMNS =
            {"FeedbackID", "StudentID", "AssessmentID", "LecturerID", "Comment"};

    public LecturerFeedbackManagerTableFrame(Lecturer lecturer) {
        this.lecturer = lecturer;

      
        // Window settings
        
        setTitle("Feedback Manager");
        setSize(1050, 600);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        // Table setup (non-editable)
    
        tableModel = new DefaultTableModel(COLUMNS, 0) {
            @Override
            public boolean isCellEditable(int row, int col) {
                return false;
            }
        };
        table = new JTable(tableModel);

        
        // Load dropdown data
        
        List<String> students = LecturerData.loadStudentDisplayList();
        List<String> assessments = LecturerData.loadAssessmentDisplayListByLecturer(lecturer.getUserID());

        cmbStudent = new JComboBox<>(students.toArray(new String[0]));
        cmbAssessment = new JComboBox<>(assessments.toArray(new String[0]));

        
        // Buttons
        
        JButton btnRefresh = new JButton("Refresh");
        JButton btnSave = new JButton("Save Feedback");

        btnRefresh.addActionListener(e -> refreshEverything());
        btnSave.addActionListener(e -> saveFeedback());

        
        // Top panel (Refresh button)
        
        JPanel topPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        topPanel.add(btnRefresh);

        
        // Form panel (student, assessment, comment, save)
        
        JPanel formPanel = new JPanel(new BorderLayout(10, 10));
        formPanel.setBorder(BorderFactory.createTitledBorder("Provide Feedback"));

        JPanel row1 = new JPanel(new GridLayout(1, 4, 10, 10));
        row1.add(new JLabel("Student"));
        row1.add(cmbStudent);
        row1.add(new JLabel("Assessment"));
        row1.add(cmbAssessment);

        JScrollPane commentScroll = new JScrollPane(txtComment);

        JPanel row3 = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        row3.add(btnSave);

        formPanel.add(row1, BorderLayout.NORTH);
        formPanel.add(commentScroll, BorderLayout.CENTER);
        formPanel.add(row3, BorderLayout.SOUTH);

        
        // Frame layout
        
        setLayout(new BorderLayout(10, 10));
        add(topPanel, BorderLayout.NORTH);
        add(new JScrollPane(table), BorderLayout.CENTER);
        add(formPanel, BorderLayout.SOUTH);

        // Load data into table at start
        refreshTable();
    }

    /**
     * Refresh dropdowns + refresh table (used when Refresh button is clicked).
     */
    private void refreshEverything() {
        reloadStudentCombo();
        reloadAssessmentCombo();
        refreshTable();
    }

    /**
     * Reload students from users.txt (role = Student).
     */
    private void reloadStudentCombo() {
        cmbStudent.removeAllItems();
        for (String s : LecturerData.loadStudentDisplayList()) {
            cmbStudent.addItem(s);
        }
    }

    /**
     * Reload assessments from assessments.txt created by this lecturer.
     */
    private void reloadAssessmentCombo() {
        cmbAssessment.removeAllItems();
        for (String a : LecturerData.loadAssessmentDisplayListByLecturer(lecturer.getUserID())) {
            cmbAssessment.addItem(a);
        }
    }

    /**
     * Reads feedback.txt and displays ONLY records created by this lecturer.
     */
    private void refreshTable() {
        tableModel.setRowCount(0); // clear existing rows

        LecturerData.ensureFile(LecturerData.FEEDBACK_FILE);

        // feedbackId|studentId|assessmentId|lecturerId|comment
        for (String line : LecturerData.readAll(LecturerData.FEEDBACK_FILE)) {
            String[] d = line.split("\\|");

            if (d.length >= 5) {
                String lecturerIdInFile = d[3];

                // show only this lecturer's feedback
                if (lecturerIdInFile.equals(lecturer.getUserID())) {
                    tableModel.addRow(new Object[]{d[0], d[1], d[2], d[3], d[4]});
                }
            }
        }
    }

    /**
     * Validates input and saves feedback into feedback.txt.
     */
    private void saveFeedback() {
        try {
            String studentDisplay = (String) cmbStudent.getSelectedItem();
            String assessmentDisplay = (String) cmbAssessment.getSelectedItem();
            String comment = txtComment.getText().trim();

            // Basic validation
            if (studentDisplay == null || assessmentDisplay == null) {
                JOptionPane.showMessageDialog(this, "No student or assessment available.");
                return;
            }
            if (comment.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Comment cannot be empty.");
                return;
            }

            // Extract IDs from display strings:
            // "S001 - John" -> S001
            // "A123 - CS101 - Quiz - Title" -> A123
            String studentId = studentDisplay.split(" - ")[0].trim();
            String assessmentId = assessmentDisplay.split(" - ")[0].trim();

            // Create feedback record
            String feedbackId = LecturerData.nextId("F");

            // Replace new lines to keep file as one line per record
            String cleanComment = comment.replace("\n", " ");

            // feedbackId|studentId|assessmentId|lecturerId|comment
            String line = feedbackId + "|" + studentId + "|" + assessmentId + "|" + lecturer.getUserID() + "|" + cleanComment;

            LecturerData.appendLine(LecturerData.FEEDBACK_FILE, line);

            // Clear comment box after save
            txtComment.setText("");

            // Refresh table to show new feedback
            refreshTable();

            JOptionPane.showMessageDialog(this, "Saved feedback: " + feedbackId);

        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Error: " + ex.getMessage());
        }
    }
}

