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
 * LecturerMarksManagerTableFrame
 *
 * This JFrame allows a lecturer to:
 * - Enter raw marks for students (no grades here)
 * - Update/correct existing marks
 * - View all marks entered by the logged-in lecturer
 *
 * IMPORTANT:
 * - Grades are NOT assigned here
 * - Grades are calculated later using total marks per module
 *
 * marks.txt format:
 * markId|studentId|assessmentId|marks|lecturerId
 */
public class LecturerMarksManagerTableFrame extends JFrame {

    // The currently logged-in lecturer
    private final Lecturer lecturer;

    // Table model and JTable to display marks
    private final DefaultTableModel model;
    private final JTable table;

    // Dropdowns for selecting student and assessment
    private final JComboBox<String> cbStudent;
    private final JComboBox<String> cbAssessment;

    // Text field for entering marks
    private final JTextField tfMarks = new JTextField();

    /**
     * Constructor
     * @param lecturer the logged-in lecturer
     */
    public LecturerMarksManagerTableFrame(Lecturer lecturer) {
        this.lecturer = lecturer;

        //  Window settings
        setTitle("Marks Entry (No Grades)");
        setSize(900, 500);
        setLocationRelativeTo(null); // center window
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        //  JTable model 
        // Columns: MarkID, StudentID, AssessmentID, Marks
        model = new DefaultTableModel(
                new String[]{"MarkID", "StudentID", "AssessmentID", "Marks"}, 0
        ) {
            // Prevent editing directly in JTable
            @Override
            public boolean isCellEditable(int r, int c) {
                return false;
            }
        };

        // Create JTable using the model
        table = new JTable(model);
        table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

        //  Dropdowns 
        // Load students and assessments from text files
        cbStudent = new JComboBox<>(
                LecturerData.loadStudentDisplayList().toArray(new String[0])
        );

        cbAssessment = new JComboBox<>(
                LecturerData.loadAssessmentDisplayListByLecturer(
                        lecturer.getuserID()
                ).toArray(new String[0])
        );

        //  Buttons 
        JButton btnSave = new JButton("Save Mark");
        JButton btnUpdate = new JButton("Update Mark");
        JButton btnRefresh = new JButton("Refresh");

        // Button actions
        btnSave.addActionListener(e -> saveMark());
        btnUpdate.addActionListener(e -> updateMark());
        btnRefresh.addActionListener(e -> loadTable());

        //  Form panel 
        JPanel form = new JPanel(new GridLayout(2, 3, 10, 10));
        form.setBorder(BorderFactory.createTitledBorder("Enter Marks"));

        form.add(new JLabel("Student"));
        form.add(new JLabel("Assessment"));
        form.add(new JLabel("Marks"));

        form.add(cbStudent);
        form.add(cbAssessment);
        form.add(tfMarks);

        //  Action buttons panel 
        JPanel actions = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        actions.add(btnSave);
        actions.add(btnUpdate);
        actions.add(btnRefresh);

        //  Frame layout 
        setLayout(new BorderLayout(10, 10));
        add(form, BorderLayout.NORTH);
        add(new JScrollPane(table), BorderLayout.CENTER);
        add(actions, BorderLayout.SOUTH);

        // Load existing marks into table on startup
        loadTable();
    }

    /**
     * Loads marks from marks.txt into the JTable.
     * Only marks created by the logged-in lecturer are shown.
     */
    private void loadTable() {
        // Clear existing rows
        model.setRowCount(0);

        // Read marks file
        for (String line : LecturerData.readAll(LecturerData.MARKS_FILE)) {
            String[] d = line.split("\\|");

            // Ensure correct format and lecturer ownership
            if (d.length >= 5 && d[4].equals(lecturer.getuserID())) {
                model.addRow(new Object[]{
                        d[0], // MarkID
                        d[1], // StudentID
                        d[2], // AssessmentID
                        d[3]  // Marks
                });
            }
        }
    }

    /**
     * Saves a new mark record to marks.txt.
     * Prevents duplicate entries for the same student + assessment.
     */
    private void saveMark() {
        try {
            // Extract IDs from dropdown display text
            String studentId = cbStudent
                    .getSelectedItem().toString().split(" - ")[0];

            String assessmentId = cbAssessment
                    .getSelectedItem().toString().split(" - ")[0];

            // Parse marks input
            double marks = Double.parseDouble(tfMarks.getText().trim());

            // Prevent duplicate marks
            if (LecturerData.markExists(
                    studentId, assessmentId, lecturer.getuserID())) {
                JOptionPane.showMessageDialog(
                        this,
                        "Mark already exists. Use Update."
                );
                return;
            }

            // Generate unique mark ID
            String id = LecturerData.nextId("M");

            // Build record line
            String line = id + "|" +
                          studentId + "|" +
                          assessmentId + "|" +
                          marks + "|" +
                          lecturer.getuserID();

            // Save to file
            LecturerData.appendLine(
                    LecturerData.MARKS_FILE, line
            );

            // Clear input and refresh table
            tfMarks.setText("");
            loadTable();

        } catch (Exception ex) {
            JOptionPane.showMessageDialog(
                    this,
                    "Invalid input."
            );
        }
    }

    /**
     * Updates an existing mark record.
     * Replaces the selected mark line in marks.txt.
     */
    private void updateMark() {
        int row = table.getSelectedRow();

        // Ensure a row is selected
        if (row < 0) return;

        try {
            // Get selected mark ID from table
            String markId = model.getValueAt(row, 0).toString();

            // Get selected student and assessment IDs
            String studentId = cbStudent
                    .getSelectedItem().toString().split(" - ")[0];

            String assessmentId = cbAssessment
                    .getSelectedItem().toString().split(" - ")[0];

            // Parse updated marks
            double marks = Double.parseDouble(tfMarks.getText().trim());

            // Read all marks
            List<String> lines = LecturerData.readAll(
                    LecturerData.MARKS_FILE
            );

            // Find and replace the selected mark
            for (int i = 0; i < lines.size(); i++) {
                String[] d = lines.get(i).split("\\|");
                if (d[0].equals(markId)) {
                    lines.set(
                            i,
                            markId + "|" +
                            studentId + "|" +
                            assessmentId + "|" +
                            marks + "|" +
                            lecturer.getuserID()
                    );
                    break;
                }
            }

            // Save updated file and refresh table
            LecturerData.overwriteAll(
                    LecturerData.MARKS_FILE, lines
            );
            loadTable();

        } catch (Exception ex) {
            JOptionPane.showMessageDialog(
                    this,
                    "Update failed."
            );
        }
    }
}
