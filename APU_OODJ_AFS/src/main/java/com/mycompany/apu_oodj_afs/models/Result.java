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

            // TEAM FORMAT (6):
            // markID|studentID|assessmentID|marks|grade|lecturerID
            if (p.length == 6) {
                String resultId = p[0].trim();
                String studentId = p[1].trim();
                String assessmentId = p[2].trim();
                double marks = Double.parseDouble(p[3].trim());
                String grade = p[4].trim();

                // moduleCode is not available in this format -> store assessmentId as moduleCode fallback
                return new Result(resultId, studentId, assessmentId, assessmentId, marks, grade);
            }

            // TEAM FORMAT (5) BUGGY:
            // markID|studentID|assessmentID|marks|lecturerID
            if (p.length == 5) {
                String resultId = p[0].trim();
                String studentId = p[1].trim();
                String assessmentId = p[2].trim();
                double marks = Double.parseDouble(p[3].trim());

                // grade missing -> compute it
                String grade = Result.calculateGrade(marks);

                return new Result(resultId, studentId, assessmentId, assessmentId, marks, grade);
            }

            // OLD FORMAT (5):
            // studentId|moduleCode|assessmentType|marks|grade
            if (p.length == 5) {
                String studentId = p[0].trim();
                String moduleCode = p[1].trim();
                String assessmentId = p[2].trim(); // treat as assessmentID
                double marks = Double.parseDouble(p[3].trim());
                String grade = p[4].trim();

                return new Result(java.util.UUID.randomUUID().toString(), studentId, assessmentId, moduleCode, marks, grade);
            }

            // NEW DIAGRAM FORMAT (6):
            // resultID|studentID|assessmentID|moduleCode|marks|grade
            if (p.length == 6) {
                String resultId = p[0].trim();
                String studentId = p[1].trim();
                String assessmentId = p[2].trim();
                String moduleCode = p[3].trim();
                double marks = Double.parseDouble(p[4].trim());
                String grade = p[5].trim();

                return new Result(resultId, studentId, assessmentId, moduleCode, marks, grade);
            }

            return null;

        } catch (Exception ex) {
            return null;
        }
    }

}
