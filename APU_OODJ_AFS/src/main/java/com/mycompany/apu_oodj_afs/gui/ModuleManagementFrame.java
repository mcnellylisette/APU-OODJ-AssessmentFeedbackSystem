package com.mycompany.apu_oodj_afs.gui;

import com.mycompany.apu_oodj_afs.core.AcademicLeaderDataService;
import com.mycompany.apu_oodj_afs.models.Module;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.ArrayList;

public class ModuleManagementFrame extends JFrame {

    private DefaultTableModel model;
    private JTextField txtCode, txtName, txtCredits, txtDescription;
    private JTable table;

    public ModuleManagementFrame() {

        setTitle("Module Management");
        setSize(900, 500);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        model = new DefaultTableModel(
                new Object[]{"Module Code", "Module Name", "Credits", "Description"},
                0
        );

        table = new JTable(model);
        
        table.getSelectionModel().addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting() && table.getSelectedRow() != -1) {
                int row = table.getSelectedRow();
                txtCode.setText(model.getValueAt(row, 0).toString());
                txtName.setText(model.getValueAt(row, 1).toString());
                txtCredits.setText(model.getValueAt(row, 2).toString());
                txtDescription.setText(model.getValueAt(row, 3).toString());
                txtCode.setEditable(false); // important: module code should not change on update
            }
        });

        JScrollPane scrollPane = new JScrollPane(table);

        // ===== Input Fields =====
        txtCode = new JTextField(10);
        txtName = new JTextField(15);
        txtCredits = new JTextField(5);
        txtDescription = new JTextField(20);

        JPanel inputPanel = new JPanel();
        inputPanel.add(new JLabel("Code:"));
        inputPanel.add(txtCode);
        inputPanel.add(new JLabel("Name:"));
        inputPanel.add(txtName);
        inputPanel.add(new JLabel("Credits:"));
        inputPanel.add(txtCredits);
        inputPanel.add(new JLabel("Description:"));
        inputPanel.add(txtDescription);

        // ===== Buttons =====
        JButton btnAdd = new JButton("Add");
        JButton btnUpdate = new JButton("Update");
        JButton btnDelete = new JButton("Delete");
        JButton btnRefresh = new JButton("Refresh");

        btnAdd.addActionListener(e -> addModule());
        btnUpdate.addActionListener(e -> updateModule());
        btnDelete.addActionListener(e -> deleteModule());
        btnRefresh.addActionListener(e -> loadModules());

        JPanel buttonPanel = new JPanel();
        buttonPanel.add(btnAdd);
        buttonPanel.add(btnUpdate);
        buttonPanel.add(btnDelete);
        buttonPanel.add(btnRefresh);

        setLayout(new BorderLayout());
        add(inputPanel, BorderLayout.NORTH);
        add(scrollPane, BorderLayout.CENTER);
        add(buttonPanel, BorderLayout.SOUTH);

        loadModules();
    }

    private void loadModules() {
        try {
            model.setRowCount(0);
            ArrayList<Module> modules = AcademicLeaderDataService.loadModules();

            for (Module m : modules) {
                model.addRow(new Object[]{
                        m.getModuleCode(),
                        m.getModuleName(),
                        m.getCreditHours(),
                        m.getDescription()
                });
            }

        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this,
                    ex.getMessage(),
                    "Error",
                    JOptionPane.ERROR_MESSAGE);
        }
    }

    private void addModule() {
        try {
            Module module = new Module(
                    txtCode.getText().trim(),
                    txtName.getText().trim(),
                    Integer.parseInt(txtCredits.getText().trim()),
                    txtDescription.getText().trim()
            );

            AcademicLeaderDataService.addModule(module);
            loadModules();
            clearFields();

        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this,
                    ex.getMessage(),
                    "Add Failed",
                    JOptionPane.ERROR_MESSAGE);
        }
    }

    private void updateModule() {
        try {
            int selected = table.getSelectedRow();
            if (selected == -1) {
                throw new IllegalArgumentException("Select a module row to update.");
            }

            String code = txtCode.getText().trim();
            String name = txtName.getText().trim();
            String creditsStr = txtCredits.getText().trim();
            String desc = txtDescription.getText().trim();

            if (code.isEmpty() || name.isEmpty() || creditsStr.isEmpty()) {
                throw new IllegalArgumentException("Code, Name, and Credits cannot be empty.");
            }

            int credits = Integer.parseInt(creditsStr);
            if (credits <= 0) {
                throw new IllegalArgumentException("Credits must be a positive number.");
            }

            Module module = new Module(code, name, credits, desc);

            AcademicLeaderDataService.updateModule(module);
            loadModules();
            clearFields();

            JOptionPane.showMessageDialog(this, "Module updated successfully");

        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this,
                    "Credits must be a valid number.",
                    "Update Failed",
                    JOptionPane.ERROR_MESSAGE);

        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this,
                    ex.getMessage(),
                    "Update Failed",
                    JOptionPane.ERROR_MESSAGE);
        }
    }

    private void deleteModule() {
        try {
            int selected = table.getSelectedRow();
            if (selected == -1) {
                throw new IllegalArgumentException("Select a module row to delete.");
            }

            String code = txtCode.getText().trim();
            if (code.isEmpty()) {
                throw new IllegalArgumentException("Module code cannot be empty.");
            }

            int confirm = JOptionPane.showConfirmDialog(
                    this,
                    "Delete module " + code + "?",
                    "Confirm Delete",
                    JOptionPane.YES_NO_OPTION
            );

            if (confirm != JOptionPane.YES_OPTION) return;

            AcademicLeaderDataService.deleteModule(code);
            loadModules();
            clearFields();

            JOptionPane.showMessageDialog(this, "Module deleted successfully");

        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this,
                    ex.getMessage(),
                    "Delete Failed",
                    JOptionPane.ERROR_MESSAGE);
        }
    }

    private void clearFields() {
        txtCode.setText("");
        txtName.setText("");
        txtCredits.setText("");
        txtDescription.setText("");
        txtCode.setEditable(true);
        table.clearSelection();
    }
}
