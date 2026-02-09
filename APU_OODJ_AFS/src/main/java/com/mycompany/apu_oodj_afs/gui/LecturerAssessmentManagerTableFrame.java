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
 * LecturerAssessmentManagerTableFrame
 *
 * This screen allows a Lecturer to:
 * 1) View their created assessments in a JTable
 * 2) Add a new assessment (saved in assessments.txt)
 * 3) Delete an assessment (by ID)
 *
 * File format used (assessments.txt):
 * assessmentId|moduleCode|type|title|totalMarks|lecturerId
 */
public class LecturerAssessmentManagerTableFrame extends JFrame {

   
    // Fields (data + UI)
    
    private final Lecturer lecturer;              // currently logged in lecturer
    private final DefaultTableModel tableModel;   // table data model (rows/columns)
    private final JTable table;                   // table component

    // Input fields to create new assessment
    private final JTextField txtModule = new JTextField();
    private final JComboBox<String> cmbType =
            new JComboBox<>(new String[]{"Assignment", "Quiz", "Exam"});
    private final JTextField txtTitle = new JTextField();
    private final JTextField txtTotalMarks = new JTextField();

    // Table columns (beginner-friendly: keep in one place)
    private static final String[] COLUMNS =
            {"AssessmentID", "Module", "Type", "Title", "Total"};

    public LecturerAssessmentManagerTableFrame(Lecturer lecturer) {
        this.lecturer = lecturer;

        // Basic window settings
        setTitle("Assessments Manager");
        setSize(900, 520);
        setLocationRelativeTo(null);              // center window
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        // 1) Create the table model (non-editable cells)
        tableModel = new DefaultTableModel(COLUMNS, 0) {
            @Override
            public boolean isCellEditable(int row, int col) {
                return false; // user should not edit cells directly
            }
        };

        // 2) Create JTable using the model
        table = new JTable(tableModel);
        table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

        // 3) Build UI layout
        setLayout(new BorderLayout(10, 10));
        add(buildTopButtonsPanel(), BorderLayout.NORTH);
        add(new JScrollPane(table), BorderLayout.CENTER);
        add(buildBottomFormPanel(), BorderLayout.SOUTH);

        // 4) Load data into the table the first time
        refreshTable();
    }

    /**
     * Creates the top panel with Refresh and Delete buttons.
     */
    private JPanel buildTopButtonsPanel() {
        JButton btnRefresh = new JButton("Refresh");
        JButton btnDelete = new JButton("Delete Selected");

        // When Refresh is clicked, reload assessments from file
        btnRefresh.addActionListener(e -> refreshTable());

        // When Delete is clicked, delete the selected row
        btnDelete.addActionListener(e -> deleteSelectedAssessment());

        JPanel panel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        panel.add(btnRefresh);
        panel.add(btnDelete);
        return panel;
    }

    /**
     * Creates the bottom form area (module, type, title, total marks) + Add button.
     */
    private JPanel buildBottomFormPanel() {
        // Form inputs
        JPanel formPanel = new JPanel(new GridLayout(2, 4, 10, 10));
        formPanel.setBorder(BorderFactory.createTitledBorder("Create New Assessment"));

        formPanel.add(new JLabel("Module Code"));
        formPanel.add(new JLabel("Type"));
        formPanel.add(new JLabel("Title"));
        formPanel.add(new JLabel("Total Marks"));

        formPanel.add(txtModule);
        formPanel.add(cmbType);
        formPanel.add(txtTitle);
        formPanel.add(txtTotalMarks);

        // Add button below the form
        JButton btnAdd = new JButton("Add Assessment");
        btnAdd.addActionListener(e -> addAssessment());

        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        buttonPanel.add(btnAdd);

        // Combine form + button
        JPanel south = new JPanel(new BorderLayout());
        south.add(formPanel, BorderLayout.CENTER);
        south.add(buttonPanel, BorderLayout.SOUTH);

        return south;
    }

    /**
     * Refreshes the JTable by re-reading all assessments from the file
     * and showing ONLY the assessments created by the logged-in lecturer.
     */
    private void refreshTable() {
        // Clear old rows first
        tableModel.setRowCount(0);

        // Ensure file exists, then read
        LecturerData.ensureFile(LecturerData.ASSESSMENTS_FILE);
        List<String> lines = LecturerData.readAll(LecturerData.ASSESSMENTS_FILE);

        for (String line : lines) {
            String[] data = line.split("\\|");

            // Expected format:
            // assessmentId|moduleCode|type|title|totalMarks|lecturerId
            if (data.length >= 6) {
                String lecturerIdInFile = data[5];

                // Show only assessments created by THIS lecturer
                if (lecturerIdInFile.equals(lecturer.getUserID())) {
                    tableModel.addRow(new Object[]{
                            data[0], // assessmentId
                            data[1], // moduleCode
                            data[2], // type
                            data[3], // title
                            data[4]  // totalMarks
                    });
                }
            }
        }
    }

    /**
     * Reads inputs, validates them, then saves a new assessment line to file.
     */
    private void addAssessment() {
        try {
            // Read user input
            String module = txtModule.getText().trim();
            String type = (String) cmbType.getSelectedItem();
            String title = txtTitle.getText().trim();
            String totalText = txtTotalMarks.getText().trim();

            // Basic validation
            if (module.isEmpty() || title.isEmpty() || totalText.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Please fill in Module, Title, and Total Marks.");
                return;
            }

            // Convert total marks to number
            double totalMarks = Double.parseDouble(totalText);
            if (totalMarks <= 0) {
                JOptionPane.showMessageDialog(this, "Total Marks must be greater than 0.");
                return;
            }

            // Generate assessment ID (simple unique ID)
            String assessmentId = LecturerData.nextId("A");

            // Build the line in the agreed file format
            String line = assessmentId + "|" + module + "|" + type + "|" + title + "|" + totalMarks + "|" + lecturer.getUserID();

            // Save to file
            LecturerData.appendLine(LecturerData.ASSESSMENTS_FILE, line);

            // Clear inputs after saving
            txtModule.setText("");
            txtTitle.setText("");
            txtTotalMarks.setText("");

            // Reload table to show new row
            refreshTable();

            JOptionPane.showMessageDialog(this, "Assessment added: " + assessmentId);

        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Total Marks must be a number (e.g., 100).");
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Error: " + ex.getMessage());
        }
    }

    /**
     * Deletes the selected assessment (based on AssessmentID from column 0).
     */
    private void deleteSelectedAssessment() {
        int selectedRow = table.getSelectedRow();

        // No selection
        if (selectedRow < 0) {
            JOptionPane.showMessageDialog(this, "Please select a row first.");
            return;
        }

        // Get AssessmentID from first column
        String assessmentId = String.valueOf(tableModel.getValueAt(selectedRow, 0));

        // Confirm delete
        int confirm = JOptionPane.showConfirmDialog(
                this,
                "Delete assessment " + assessmentId + "?",
                "Confirm Delete",
                JOptionPane.YES_NO_OPTION
        );

        if (confirm != JOptionPane.YES_OPTION) {
            return; // user cancelled
        }

        // Delete record from file
        boolean deleted = LecturerData.deleteById(LecturerData.ASSESSMENTS_FILE, assessmentId);

        if (deleted) {
            refreshTable();
            JOptionPane.showMessageDialog(this, "Deleted: " + assessmentId);
        } else {
            JOptionPane.showMessageDialog(this, "Could not delete (not found).");
        }
    }
}
