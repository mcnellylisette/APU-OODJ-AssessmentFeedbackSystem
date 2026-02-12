/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package gui;

import com.mycompany.apu_oodj_afs.core.ModuleLecturerRepository;
import com.mycompany.apu_oodj_afs.core.ModuleRepository;
import com.mycompany.apu_oodj_afs.models.Module;
import com.mycompany.apu_oodj_afs.models.User;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

public class ModulesPanel extends JPanel {

    private final User leader;
    private final ModuleRepository repo = new ModuleRepository();
    private final ModuleLecturerRepository mlRepo = new ModuleLecturerRepository();

    private final DefaultTableModel model = new DefaultTableModel(
            new Object[]{"Module ID", "Module Name"}, 0
    ) {
        @Override public boolean isCellEditable(int r, int c) { return false; }
    };

    private final JTable table = new JTable(model);

    public ModulesPanel(User leader) {
        this.leader = leader;

        setLayout(new BorderLayout());

        JPanel top = new JPanel(new FlowLayout(FlowLayout.LEFT));
        JButton btnAdd = new JButton("Add");
        JButton btnEdit = new JButton("Edit");
        JButton btnDelete = new JButton("Delete");
        JButton btnRefresh = new JButton("Refresh");
        JButton btnAssign = new JButton("Assign Lecturer");

        top.add(btnAdd);
        top.add(btnEdit);
        top.add(btnDelete);
        top.add(btnRefresh);
        top.add(btnAssign);

        add(top, BorderLayout.NORTH);
        add(new JScrollPane(table), BorderLayout.CENTER);

        btnAdd.addActionListener(e -> addModule());
        btnEdit.addActionListener(e -> editModule());
        btnDelete.addActionListener(e -> deleteModule());
        btnRefresh.addActionListener(e -> loadModules());
        btnAssign.addActionListener(e -> assignLecturer());

        loadModules();
    }

    private void loadModules() {
        model.setRowCount(0);
        try {
            List<Module> all = repo.findAll();
            for (Module m : all) {
                if (m.getLeaderId().equalsIgnoreCase(leader.getUserID())) {
                    model.addRow(new Object[]{m.getModuleId(), m.getModuleName()});
                }
            }
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void addModule() {
        JTextField txtId = new JTextField();
        JTextField txtName = new JTextField();

        int ok = JOptionPane.showConfirmDialog(
                this,
                new Object[]{"Module ID:", txtId, "Module Name:", txtName},
                "Add Module",
                JOptionPane.OK_CANCEL_OPTION
        );
        if (ok != JOptionPane.OK_OPTION) return;

        try {
            String id = txtId.getText().trim();
            String name = txtName.getText().trim();
            if (id.isEmpty() || name.isEmpty()) throw new Exception("Both fields are required.");

            List<Module> all = repo.findAll();
            for (Module m : all) {
                if (m.getModuleId().equalsIgnoreCase(id)) throw new Exception("Module ID already exists.");
            }

            all.add(new Module(id, name, leader.getUserID()));
            repo.saveAll(all);
            loadModules();

        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void editModule() {
        int row = table.getSelectedRow();
        if (row < 0) { JOptionPane.showMessageDialog(this, "Select a module first."); return; }

        String moduleId = model.getValueAt(row, 0).toString();
        String oldName = model.getValueAt(row, 1).toString();

        JTextField txtName = new JTextField(oldName);

        int ok = JOptionPane.showConfirmDialog(
                this,
                new Object[]{"New module name:", txtName},
                "Edit Module",
                JOptionPane.OK_CANCEL_OPTION
        );
        if (ok != JOptionPane.OK_OPTION) return;

        try {
            String newName = txtName.getText().trim();
            if (newName.isEmpty()) throw new Exception("Module name cannot be empty.");

            List<Module> all = repo.findAll();
            for (Module m : all) {
                if (m.getModuleId().equalsIgnoreCase(moduleId)
                        && m.getLeaderId().equalsIgnoreCase(leader.getUserID())) {
                    m.setModuleName(newName);
                }
            }

            repo.saveAll(all);
            loadModules();

        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void deleteModule() {
        int row = table.getSelectedRow();
        if (row < 0) { JOptionPane.showMessageDialog(this, "Select a module first."); return; }

        String moduleId = model.getValueAt(row, 0).toString();

        int ok = JOptionPane.showConfirmDialog(
                this,
                "Delete module " + moduleId + "?",
                "Confirm",
                JOptionPane.OK_CANCEL_OPTION
        );
        if (ok != JOptionPane.OK_OPTION) return;

        try {
            List<Module> all = repo.findAll();
            all.removeIf(m -> m.getModuleId().equalsIgnoreCase(moduleId)
                    && m.getLeaderId().equalsIgnoreCase(leader.getUserID()));

            repo.saveAll(all);
            loadModules();

        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void assignLecturer() {
        int row = table.getSelectedRow();
        if (row < 0) { JOptionPane.showMessageDialog(this, "Select a module first."); return; }

        String moduleId = model.getValueAt(row, 0).toString();

        String lecturerId = JOptionPane.showInputDialog(
                this,
                "Enter Lecturer ID (example: L001):"
        );
        if (lecturerId == null || lecturerId.trim().isEmpty()) return;

        try {
            mlRepo.assign(moduleId, lecturerId.trim());
            JOptionPane.showMessageDialog(this, "Lecturer assigned.");
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
}

