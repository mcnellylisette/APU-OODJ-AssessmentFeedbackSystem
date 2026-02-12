package com.mycompany.apu_oodj_afs.gui;

import com.mycompany.apu_oodj_afs.models.Result;
import com.mycompany.apu_oodj_afs.models.Student;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.BorderLayout;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class StudentFinalGradeFrame extends JFrame {

    private final Student student;
    private final DefaultTableModel model;

    public StudentFinalGradeFrame(Student student) {
        this.student = student;

        setTitle("Final Grades");
        setSize(700, 400);
        setLocationRelativeTo(null);

        model = new DefaultTableModel(
                new Object[]{"Module", "Average Marks", "Final Grade"},
                0
        ) {
            @Override
            public boolean isCellEditable(int row, int col) {
                return false;
            }
        };

        JTable table = new JTable(model);
        JScrollPane scrollPane = new JScrollPane(table);

        JButton closeBtn = new JButton("Close");
        closeBtn.addActionListener(e -> dispose());

        add(scrollPane, BorderLayout.CENTER);
        add(closeBtn, BorderLayout.SOUTH);

        load();
    }

    private void load() {
        model.setRowCount(0);

        ArrayList<Result> results = student.viewResults();

        Map<String, List<Double>> moduleMarks = new HashMap<>();

        for (Result r : results) {
            moduleMarks
                    .computeIfAbsent(r.getModuleCode(), k -> new ArrayList<>())
                    .add(r.getMarks());
        }

        for (String module : moduleMarks.keySet()) {
            List<Double> marks = moduleMarks.get(module);
            double avg = marks.stream().mapToDouble(Double::doubleValue).average().orElse(0);

            String grade = Result.calculateGrade(avg);

            model.addRow(new Object[]{module, avg, grade});
        }

        if (moduleMarks.isEmpty()) {
            JOptionPane.showMessageDialog(this,
                    "No results available.",
                    "Information",
                    JOptionPane.INFORMATION_MESSAGE);
        }
    }
}
