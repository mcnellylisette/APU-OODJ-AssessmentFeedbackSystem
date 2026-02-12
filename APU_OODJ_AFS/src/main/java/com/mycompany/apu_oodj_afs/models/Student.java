package com.mycompany.apu_oodj_afs.models;

import com.mycompany.apu_oodj_afs.core.StudentDataService;
import com.mycompany.apu_oodj_afs.gui.StudentDashboard;

import java.util.ArrayList;

public class Student extends User {

    private String program;
    private int yearOfStudy;

    public Student(String userID, String username, String password, String name, String role, String email) {
        super(userID, username, password, name, role, email);
        // default values (can be updated later via profile enhancement)
        this.program = "Not Set";
        this.yearOfStudy = 1;
    }

    // Overloaded constructor (if later you store program/year in file)
    public Student(String userID, String username, String password, String name, String role, String email,
                   String program, int yearOfStudy) {
        super(userID, username, password, name, role, email);
        this.program = program;
        this.yearOfStudy = yearOfStudy;
    }

    public String getProgram() { return program; }
    public int getYearOfStudy() { return yearOfStudy; }

    public void setProgram(String program) { this.program = program; }
    public void setYearOfStudy(int yearOfStudy) { this.yearOfStudy = yearOfStudy; }

    
    public boolean registerForClass(String classID, String moduleCode) {
        try {
            StudentDataService.addEnrollment(getuserID(), classID, moduleCode);
            return true;
        } catch (Exception ex) {
            return false;
        }
    }

    public ArrayList<Enrollment> viewEnrollment() {
        try {
            return new ArrayList<>(StudentDataService.getEnrollmentsByStudent(getuserID()));
        } catch (Exception ex) {
            return new ArrayList<>();
        }
    }

    public ArrayList<Result> viewResults() {
        try {
            return new ArrayList<>(StudentDataService.getResultsByStudent(getuserID()));
        } catch (Exception ex) {
            return new ArrayList<>();
        }
    }

    public StudentFeedback viewFeedback(String assessmentID) {
        try {
            return StudentDataService.getFeedbackByAssessment(getuserID(), assessmentID);
        } catch (Exception ex) {
            return null;
        }
    }

    // Polymorphism
    @Override
    public void displayDashboard() {
        javax.swing.SwingUtilities.invokeLater(() -> new StudentDashboard(this).setVisible(true));
    }
}

//Zahra