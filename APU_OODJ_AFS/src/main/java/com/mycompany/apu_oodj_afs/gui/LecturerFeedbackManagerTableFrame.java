/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.apu_oodj_afs.gui;

import com.mycompany.apu_oodj_afs.core.LecturerData;
import com.mycompany.apu_oodj_afs.models.Lecturer;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

/**
 * LecturerFeedbackManagerTableFrame
 *
 * Purpose:
 * - Lecturer writes feedback comment for student + assessment
 * - Lecturer can update/correct feedback
 *
 * File format (feedback.txt):
 * feedbackId|studentId|assessmentId|lecturerId|comment
 */
public class LecturerFeedbackManagerTableFrame extends JFrame {

    private final Lecturer lecturer;

    private final DefaultTableModel model;
    private final JTable table;

    private final JComboBox<String> cbStudent;
    private final JComboBox<String> cbAssessment;
    private final JTextArea taComment = new JTextArea(4, 40);

    public LecturerFeedbackManagerTableFrame(Lecturer lecturer) {
        this.lecturer = lecturer;

        setTitle("Feedback Manager");
        setSize(1000, 600);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        // Table model (not editable)
        model = new DefaultTableModel(new String[]{"FeedbackID", "StudentID", "AssessmentID", "Comment"}, 0) {
            @Override public boolean isCellEditable(int r, int c) { return false; }
        };

        table = new JTable(model);
        table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

        // Dropdown lists
        cbStudent = new JComboBox<>(LecturerData.loadStudentDisplayList().toArray(new String[0]));
        cbAssessment = new JComboBox<>(LecturerData.loadAssessmentDisplayListByLecturer(lecturer.getuserID()).toArray(new String[0]));

        // Buttons
        JButton btnSave = new JButton("Save Feedback");
        JButton btnUpdate = new JButton("Update Selected");
        JButton btnRefresh = new JButton("Refresh");
        JButton btnClear = new JButton("Clear");

        btnSave.addActionListener(e -> saveFeedback());
        btnUpdate.addActionListener(e -> updateFeedback());
        btnRefresh.addActionListener(e -> {
            reloadCombos();
            loadTable();
        });
        btnClear.addActionListener(e -> clearForm());

        // Load selected row into form (easy update)
        table.getSelectionModel().addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting()) fillFormFromSelectedRow();
        });

        // Top controls
        JPanel top = new JPanel(new FlowLayout(FlowLayout.LEFT));
        top.add(btnRefresh);

        // Form panel
        JPanel form = new JPanel(new BorderLayout(10, 10));
        form.setBorder(BorderFactory.createTitledBorder("Provide / Update Feedback"));

        JPanel row1 = new JPanel(new GridLayout(1, 4, 10, 10));
        row1.add(new JLabel("Student"));
        row1.add(cbStudent);
        row1.add(new JLabel("Assessment"));
        row1.add(cbAssessment);

        JScrollPane commentScroll = new JScrollPane(taComment);

        JPanel actions = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        actions.add(btnSave);
        actions.add(btnUpdate);
        actions.add(btnClear);

        form.add(row1, BorderLayout.NORTH);
        form.add(commentScroll, BorderLayout.CENTER);
        form.add(actions, BorderLayout.SOUTH);

        // Layout
        setLayout(new BorderLayout(10, 10));
        add(top, BorderLayout.NORTH);
        add(new JScrollPane(table), BorderLayout.CENTER);
        add(form, BorderLayout.SOUTH);

        loadTable();
    }

    private void reloadCombos() {
        cbStudent.removeAllItems();
        for (String s : LecturerData.loadStudentDisplayList()) cbStudent.addItem(s);

        cbAssessment.removeAllItems();
        for (String a : LecturerData.loadAssessmentDisplayListByLecturer(lecturer.getuserID())) cbAssessment.addItem(a);
    }

    /**
     * Load feedback into table (only lecturer's feedback).
     */
    private void loadTable() {
        model.setRowCount(0);
        LecturerData.ensureFile(LecturerData.FEEDBACK_FILE);

        for (String line : LecturerData.readAll(LecturerData.FEEDBACK_FILE)) {
            String[] d = line.split("\\|");
            // feedbackId|studentId|assessmentId|lecturerId|comment
            if (d.length >= 5 && d[3].equals(lecturer.getuserID())) {
                model.addRow(new Object[]{d[0], d[1], d[2], d[4]});
            }
        }
    }

    /**
     * Save new feedback.
     */
    private void saveFeedback() {
        try {
            String studentDisplay = (String) cbStudent.getSelectedItem();
            String assessmentDisplay = (String) cbAssessment.getSelectedItem();

            if (studentDisplay == null || assessmentDisplay == null) {
                JOptionPane.showMessageDialog(this, "No student/assessment found.");
                return;
            }

            String comment = taComment.getText().trim();
            if (comment.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Comment cannot be empty.");
                return;
            }

            String studentId = studentDisplay.split(" - ")[0].trim();
            String assessmentId = assessmentDisplay.split(" - ")[0].trim();

            String feedbackId = LecturerData.nextId("F");

            // Replace new lines to keep file clean
            String clean = comment.replace("\n", " ");

            String line = feedbackId + "|" + studentId + "|" + assessmentId + "|" + lecturer.getuserID() + "|" + clean;
            LecturerData.appendLine(LecturerData.FEEDBACK_FILE, line);

            clearForm();
            loadTable();
            JOptionPane.showMessageDialog(this, "Saved feedback: " + feedbackId);

        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Error: " + ex.getMessage());
        }
    }

    /**
     * Update selected feedback.
     */
    private void updateFeedback() {
        int row = table.getSelectedRow();
        if (row < 0) {
            JOptionPane.showMessageDialog(this, "Select a feedback row to update.");
            return;
        }

        try {
            String feedbackId = model.getValueAt(row, 0).toString();

            String studentDisplay = (String) cbStudent.getSelectedItem();
            String assessmentDisplay = (String) cbAssessment.getSelectedItem();

            if (studentDisplay == null || assessmentDisplay == null) {
                JOptionPane.showMessageDialog(this, "No student/assessment found.");
                return;
            }

            String comment = taComment.getText().trim();
            if (comment.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Comment cannot be empty.");
                return;
            }

            String studentId = studentDisplay.split(" - ")[0].trim();
            String assessmentId = assessmentDisplay.split(" - ")[0].trim();

            String clean = comment.replace("\n", " ");

            List<String> lines = LecturerData.readAll(LecturerData.FEEDBACK_FILE);

            boolean updated = false;
            for (int i = 0; i < lines.size(); i++) {
                String[] d = lines.get(i).split("\\|");
                if (d.length >= 5 && d[0].equals(feedbackId)) {
                    lines.set(i, feedbackId + "|" + studentId + "|" + assessmentId + "|" + lecturer.getuserID() + "|" + clean);
                    updated = true;
                    break;
                }
            }

            if (!updated) {
                JOptionPane.showMessageDialog(this, "Update failed: feedback not found.");
                return;
            }

            LecturerData.overwriteAll(LecturerData.FEEDBACK_FILE, lines);

            clearForm();
            loadTable();
            JOptionPane.showMessageDialog(this, "Updated feedback: " + feedbackId);

        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Update failed: " + ex.getMessage());
        }
    }

    /**
     * Load selected row comment into textarea.
     */
    private void fillFormFromSelectedRow() {
        int row = table.getSelectedRow();
        if (row < 0) return;

        String comment = model.getValueAt(row, 3).toString();
        taComment.setText(comment);
    }

    private void clearForm() {
        taComment.setText("");
        table.clearSelection();
    }
}
