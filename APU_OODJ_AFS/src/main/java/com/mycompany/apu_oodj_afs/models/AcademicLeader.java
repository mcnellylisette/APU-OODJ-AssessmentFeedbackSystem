package com.mycompany.apu_oodj_afs.models;

import java.util.ArrayList;

public class AcademicLeader extends User {

    public AcademicLeader(String userID, String username, String password,
                           String name, String role, String email) {
        super(userID, username, password, name, role, email);
    }

    @Override
    public void displayDashboard() {
        System.out.println("Opening Academic Leader Dashboard...");
    }

    // ===== Module Management Methods (Diagram Aligned) =====

    public void createModule(Module module) {
        if (!module.validateModule()) {
            throw new IllegalArgumentException("Invalid module data.");
        }

        System.out.println("Module created: " + module.getModuleCode());
        // File persistence will be handled in AcademicLeaderDataService later
    }

    public ArrayList<Module> viewAllModules() {
        System.out.println("Viewing all modules...");
        return new ArrayList<>();
    }

    public void updateModule(Module module) {
        if (!module.validateModule()) {
            throw new IllegalArgumentException("Invalid module data.");
        }

        System.out.println("Module updated: " + module.getModuleCode());
    }

    public void deleteModule(String moduleCode) {
        if (moduleCode == null || moduleCode.isBlank()) {
            throw new IllegalArgumentException("Module code cannot be empty.");
        }

        System.out.println("Module deleted: " + moduleCode);
    }

    // ===== Student Report =====

    public String generateStudentReport(String studentID) {
        if (studentID == null || studentID.isBlank()) {
            throw new IllegalArgumentException("Student ID cannot be empty.");
        }

        System.out.println("Generating report for student: " + studentID);

        return "Report generated for student: " + studentID;
    }
}
