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

            // TEAM FORMAT (5):
            // feedbackID|studentID|assessmentID|lecturerID|feedbackText
            if (p.length == 5) {
                String studentId = p[1].trim();
                String assessmentId = p[2].trim();
                String lecturerId = p[3].trim();
                String text = p[4].trim();

                // moduleCode + date not available in this format
                return new StudentFeedback(studentId, "N/A", assessmentId, text, lecturerId, java.time.LocalDate.now());
            }

            // OLD/OUR FORMAT (6):
            // studentId|moduleCode|assessmentID|feedbackText|lecturerId|date
            if (p.length >= 6) {
                return new StudentFeedback(
                        p[0].trim(), p[1].trim(), p[2].trim(),
                        p[3].trim(), p[4].trim(),
                        java.time.LocalDate.parse(p[5].trim())
                );
            }

            return null;

        } catch (Exception ex) {
            return null;
        }
    }

}
