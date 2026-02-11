package com.mycompany.apu_oodj_afs.models;

import java.util.UUID;

public class Result {

    private final String resultID;
    private final String studentID;
    private final String assessmentID;   
    private final String moduleCode;
    private final double marksObtained;
    private final String grade;

    public Result(String studentID, String assessmentID, String moduleCode, double marksObtained, String grade) {
        this.resultID = UUID.randomUUID().toString();
        this.studentID = studentID;
        this.assessmentID = assessmentID;
        this.moduleCode = moduleCode;
        this.marksObtained = marksObtained;
        this.grade = grade;
    }

    // For file parse including resultID
    public Result(String resultID, String studentID, String assessmentID, String moduleCode, double marksObtained, String grade) {
        this.resultID = resultID;
        this.studentID = studentID;
        this.assessmentID = assessmentID;
        this.moduleCode = moduleCode;
        this.marksObtained = marksObtained;
        this.grade = grade;
    }

    public String getResultID() { return resultID; }
    public String getStudentId() { return studentID; }
    public String getAssessmentID() { return assessmentID; }
    public String getModuleCode() { return moduleCode; }
    public double getMarks() { return marksObtained; }
    public String getGrade() { return grade; }

    public boolean validateMarks() {
        return marksObtained >= 0 && marksObtained <= 100;
    }

    public static String calculateGrade(double marks) {
        if (marks >= 90) return "A+";
        if (marks >= 80) return "A";
        if (marks >= 75) return "B+";
        if (marks >= 70) return "B";
        if (marks >= 65) return "C+";
        if (marks >= 60) return "C";
        if (marks >= 50) return "D";
        return "F";
    }

    // resultID|studentID|assessmentID|moduleCode|marks|grade
    public String toFileLine() {
        return resultID + "|" + studentID + "|" + assessmentID + "|" + moduleCode + "|" + marksObtained + "|" + grade;
    }

    public static Result tryParse(String line) {
        try {
            String[] p = line.split("\\|", -1);

            // Backward compatibility:
            // Old marks.txt format: studentId|moduleCode|assessmentType|marks|grade
            if (p.length == 5) {
                String assessmentType = p[2]; // use assessmentType as assessmentID fallback
                double m = Double.parseDouble(p[3]);
                return new Result(
                        UUID.randomUUID().toString(),
                        p[0],
                        assessmentType,
                        p[1],
                        m,
                        p[4]
                );
            }

            // New format:
            if (p.length >= 6) {
                double m = Double.parseDouble(p[4]);
                return new Result(p[0], p[1], p[2], p[3], m, p[5]);
            }

            return null;
        } catch (Exception ex) {
            return null;
        }
    }
}
