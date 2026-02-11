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
 * LecturerFinalResultFrame
 *
 * Purpose:
 * - Calculate TOTAL marks per student per course (module)
 * - Display final grade derived from total marks
 *
 * IMPORTANT:
 * - Grades are NOT stored
 * - Grades are calculated dynamically
 */
public class LecturerFinalResultFrame extends JFrame {

    private final Lecturer lecturer;

    private final JComboBox<String> cbModule;
    private final DefaultTableModel tableModel;
    private final JTable table;

    public LecturerFinalResultFrame(Lecturer lecturer) {
        this.lecturer = lecturer;

        setTitle("Calculate Final Results");
        setSize(800, 500);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        
        cbModule = new JComboBox<>();
        loadModules();

        JButton btnCalculate = new JButton("Calculate Results");
        btnCalculate.addActionListener(e -> loadResults());

        JPanel top = new JPanel(new FlowLayout(FlowLayout.LEFT));
        top.add(new JLabel("Module:"));
        top.add(cbModule);
        top.add(btnCalculate);

        //  JTable 
        tableModel = new DefaultTableModel(
                new String[]{"Student ID", "Student Name", "Total Marks", "Final Grade"}, 0
        ) {
            @Override public boolean isCellEditable(int r, int c) { return false; }
        };

        table = new JTable(tableModel);

        setLayout(new BorderLayout(10, 10));
        add(top, BorderLayout.NORTH);
        add(new JScrollPane(table), BorderLayout.CENTER);
    }

    /**
     * Loads unique modules taught by this lecturer.
     */
    private void loadModules() {
        cbModule.removeAllItems();

        for (String line : LecturerData.readAll(LecturerData.ASSESSMENTS_FILE)) {
            String[] d = line.split("\\|");
            if (d.length >= 6 && d[5].equals(lecturer.getuserID())) {
                String moduleCode = d[1];

                boolean exists = false;
                for (int i = 0; i < cbModule.getItemCount(); i++) {
                    if (cbModule.getItemAt(i).equals(moduleCode)) {
                        exists = true;
                        break;
                    }
                }
                if (!exists) {
                    cbModule.addItem(moduleCode);
                }
            }
        }
    }

    /**
     * Calculates and displays final results in JTable.
     */
    private void loadResults() {
        tableModel.setRowCount(0);

        String moduleCode = (String) cbModule.getSelectedItem();
        if (moduleCode == null) {
            JOptionPane.showMessageDialog(this, "No module selected.");
            return;
        }

        List<String[]> results = LecturerData.calculateFinalResultsByModule(moduleCode);

        if (results.isEmpty()) {
            JOptionPane.showMessageDialog(this, "No marks found for this module.");
            return;
        }

        for (String[] r : results) {
            tableModel.addRow(r);
        }
    }
}
