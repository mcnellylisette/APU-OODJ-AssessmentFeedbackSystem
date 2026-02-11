package com.mycompany.apu_oodj_afs.models;

import com.mycompany.apu_oodj_afs.core.StudentDataService;
import java.time.LocalDate;

public class LecturerComment {
    private final String studentId;
    private final String lecturerId;
    private final String moduleCode;
    private final int rating; // 1-5
    private final String commentText;
    private final LocalDate date;

    public LecturerComment(String studentId, String lecturerId, String moduleCode,
                           int rating, String commentText, LocalDate date) {
        this.studentId = studentId;
        this.lecturerId = lecturerId;
        this.moduleCode = moduleCode;
        this.rating = rating;
        this.commentText = commentText;
        this.date = date;
    }

    public String getStudentId() { return studentId; }
    public String getLecturerId() { return lecturerId; }
    public String getModuleCode() { return moduleCode; }
    public int getRating() { return rating; }
    public String getCommentText() { return commentText; }
    public LocalDate getDate() { return date; }

    public String toFileLine() {
        return studentId + "|" + lecturerId + "|" + moduleCode + "|" + rating + "|" +
               StudentDataService.safeText(commentText) + "|" + date;
    }
}
