package com.mycompany.apu_oodj_afs.gui;

import com.mycompany.apu_oodj_afs.core.StudentDataService;
import com.mycompany.apu_oodj_afs.models.Result;
import com.mycompany.apu_oodj_afs.models.Student;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

public class StudentResultsFrame extends JFrame {

    private final Student student;
    private final DefaultTableModel model;

    public StudentResultsFrame(Student student) {
        this.student = student;

        setTitle("Assessment Marks");
        setSize(820, 460);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        model = new DefaultTableModel(new Object[]{"Module", "Assessment", "Marks", "Grade"}, 0) {
            @Override public boolean isCellEditable(int r, int c) { return false; }
        };

        JTable table = new JTable(model);
        JScrollPane scroll = new JScrollPane(table);

        JButton refreshBtn = new JButton("Refresh");
        refreshBtn.addActionListener(e -> load());

        JButton closeBtn = new JButton("Close");
        closeBtn.addActionListener(e -> dispose());

        JPanel top = new JPanel(new FlowLayout(FlowLayout.LEFT));
        top.add(new JLabel("Student: " + student.getuserID()));
        top.add(refreshBtn);

        JPanel bottom = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        bottom.add(closeBtn);

        add(top, BorderLayout.NORTH);
        add(scroll, BorderLayout.CENTER);
        add(bottom, BorderLayout.SOUTH);

        load();
    }

    private void load() {
        try {
            model.setRowCount(0);
            List<Result> list = StudentDataService.getResultsByStudent(student.getuserID());
            for (Result r : list) {
                model.addRow(new Object[]{
                    r.getModuleCode(),
                    r.getAssessmentID(),
                    r.getMarks(),
                    r.getGrade()
                });
            }
            if (list.isEmpty()) {
                JOptionPane.showMessageDialog(this, "No results found yet.", "Info", JOptionPane.INFORMATION_MESSAGE);
            }
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Failed to load results: " + ex.getMessage(),
                    "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
}
