package com.mycompany.apu_oodj_afs.models;

import java.time.LocalDate;

public class StudentFeedback {
    private final String studentId;
    private final String moduleCode;
    private final String assessmentID;     // diagram-aligned
    private final String feedbackText;
    private final String lecturerId;
    private final LocalDate date;

    public StudentFeedback(String studentId, String moduleCode, String assessmentID,
                           String feedbackText, String lecturerId, LocalDate date) {
        this.studentId = studentId;
        this.moduleCode = moduleCode;
        this.assessmentID = assessmentID;
        this.feedbackText = feedbackText;
        this.lecturerId = lecturerId;
        this.date = date;
    }

    public String getStudentId() { return studentId; }
    public String getModuleCode() { return moduleCode; }
    public String getAssessmentID() { return assessmentID; }
    public String getFeedbackText() { return feedbackText; }
    public String getLecturerId() { return lecturerId; }
    public LocalDate getDate() { return date; }

    // Expected feedback.txt format:
    // studentId|moduleCode|assessmentID|feedbackText|lecturerId|date(yyyy-mm-dd)
    public static StudentFeedback tryParse(String line) {
        try {
            String[] p = line.split("\\|", -1);
            if (p.length < 6) return null;
            return new StudentFeedback(p[0], p[1], p[2], p[3], p[4], LocalDate.parse(p[5]));
        } catch (Exception ex) {
            return null;
        }
    }
}
