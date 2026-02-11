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
 * LecturerAssessmentManagerTableFrame
 *
 * Purpose:
 * - Lecturer can CREATE assessments (Assignment/Quiz/Exam)
 * - Lecturer can DELETE an assessment
 * - Lecturer can VIEW their assessments in a JTable
 *
 * File format (assessments.txt):
 * assessmentId|moduleCode|type|title|totalMarks|lecturerId
 *

 
 * - Total marks is the "maximum marks" of that assessment.
 */
public class LecturerAssessmentManagerTableFrame extends JFrame {

    // Logged-in lecturer
    private final Lecturer lecturer;

    // JTable components
    private final DefaultTableModel model;
    private final JTable table;

    // Input fields
    private final JTextField tfModule = new JTextField();
    private final JComboBox<String> cbType = new JComboBox<>(new String[]{"Assignment", "Quiz", "Exam"});
    private final JTextField tfTitle = new JTextField();
    private final JTextField tfTotal = new JTextField();

    public LecturerAssessmentManagerTableFrame(Lecturer lecturer) {
        this.lecturer = lecturer;

        // Window settings
        setTitle("Assessments Manager (Lecturer)");
        setSize(950, 560);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        // Table model (not editable)
        model = new DefaultTableModel(new String[]{"AssessmentID", "Module", "Type", "Title", "Total"}, 0) {
            @Override public boolean isCellEditable(int r, int c) { return false; }
        };

        table = new JTable(model);
        table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

        // Buttons
        JButton btnRefresh = new JButton("Refresh");
        JButton btnDelete = new JButton("Delete Selected");
        JButton btnAdd = new JButton("Add Assessment");
        JButton btnClear = new JButton("Clear");

        btnRefresh.addActionListener(e -> loadTable());
        btnDelete.addActionListener(e -> deleteSelected());
        btnAdd.addActionListener(e -> addAssessment());
        btnClear.addActionListener(e -> clearForm());

        // Top panel (Refresh + Delete)
        JPanel top = new JPanel(new FlowLayout(FlowLayout.LEFT));
        top.add(btnRefresh);
        top.add(btnDelete);

        // Form panel (Create assessment)
        JPanel form = new JPanel(new GridLayout(2, 4, 10, 10));
        form.setBorder(BorderFactory.createTitledBorder("Create New Assessment"));
        form.add(new JLabel("Module Code"));
        form.add(new JLabel("Type"));
        form.add(new JLabel("Title"));
        form.add(new JLabel("Total Marks"));

        form.add(tfModule);
        form.add(cbType);
        form.add(tfTitle);
        form.add(tfTotal);

        // Bottom-right buttons (Add + Clear)
        JPanel bottomButtons = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        bottomButtons.add(btnClear);
        bottomButtons.add(btnAdd);

        // Build south panel (Form + buttons)
        JPanel south = new JPanel(new BorderLayout());
        south.add(form, BorderLayout.CENTER);
        south.add(bottomButtons, BorderLayout.SOUTH);

        // Layout
        setLayout(new BorderLayout(10, 10));
        add(top, BorderLayout.NORTH);
        add(new JScrollPane(table), BorderLayout.CENTER);
        add(south, BorderLayout.SOUTH);

        // Load existing assessments
        loadTable();
    }

    /**
     * Loads lecturer's assessments from file into the table.
     */
    private void loadTable() {
        model.setRowCount(0); // clear table

        LecturerData.ensureFile(LecturerData.ASSESSMENTS_FILE);

        List<String> lines = LecturerData.readAll(LecturerData.ASSESSMENTS_FILE);
        for (String line : lines) {
            String[] d = line.split("\\|");

            // assessmentId|moduleCode|type|title|totalMarks|lecturerId
            if (d.length >= 6 && d[5].equals(lecturer.getuserID())) {
                model.addRow(new Object[]{d[0], d[1], d[2], d[3], d[4]});
            }
        }
    }

    /**
     * Adds a new assessment to file.
     */
    private void addAssessment() {
        try {
            String module = tfModule.getText().trim();
            String type = String.valueOf(cbType.getSelectedItem());
            String title = tfTitle.getText().trim();

            // total marks must be numeric
            double total = Double.parseDouble(tfTotal.getText().trim());

            // basic validation
            if (module.isEmpty() || title.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Module and Title cannot be empty.");
                return;
            }
            if (total <= 0) {
                JOptionPane.showMessageDialog(this, "Total marks must be greater than 0.");
                return;
            }

            // Generate ID
            String id = LecturerData.nextId("A");

            // assessmentId|moduleCode|type|title|totalMarks|lecturerId
            String line = id + "|" + module + "|" + type + "|" + title + "|" + total + "|" + lecturer.getuserID();

            LecturerData.appendLine(LecturerData.ASSESSMENTS_FILE, line);

            clearForm();
            loadTable();

            JOptionPane.showMessageDialog(this, "Assessment added: " + id);

        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Total marks must be a number.");
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Error: " + ex.getMessage());
        }
    }

    /**
     * Deletes selected assessment using its ID.
     */
    private void deleteSelected() {
        int row = table.getSelectedRow();
        if (row < 0) {
            JOptionPane.showMessageDialog(this, "Select an assessment row first.");
            return;
        }

        String id = model.getValueAt(row, 0).toString();

        int confirm = JOptionPane.showConfirmDialog(
                this,
                "Delete assessment " + id + "?",
                "Confirm Delete",
                JOptionPane.YES_NO_OPTION
        );

        if (confirm != JOptionPane.YES_OPTION) return;

        boolean ok = LecturerData.deleteById(LecturerData.ASSESSMENTS_FILE, id);

        if (ok) {
            loadTable();
            JOptionPane.showMessageDialog(this, "Deleted: " + id);
        } else {
            JOptionPane.showMessageDialog(this, "Delete failed (ID not found).");
        }
    }

    /**
     * Clear input fields.
     */
    private void clearForm() {
        tfModule.setText("");
        tfTitle.setText("");
        tfTotal.setText("");
        cbType.setSelectedIndex(0);
    }
}
