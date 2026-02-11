package com.mycompany.apu_oodj_afs.core;

import com.mycompany.apu_oodj_afs.models.*;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public final class StudentDataService {

    private static final Path DATA_DIR = Paths.get("data");
    private static final Path ENROLLMENTS = DATA_DIR.resolve("enrollments.txt");
    private static final Path MARKS = DATA_DIR.resolve("marks.txt");           // read-only for students
    private static final Path FEEDBACK = DATA_DIR.resolve("feedback.txt");     // read-only for students
    private static final Path COMMENTS = DATA_DIR.resolve("student_comments.txt");

    private StudentDataService() {}

    public static void ensureFilesExist() throws IOException {
        if (!Files.exists(DATA_DIR)) Files.createDirectories(DATA_DIR);
        touch(ENROLLMENTS);
        touch(MARKS);
        touch(FEEDBACK);
        touch(COMMENTS);
    }

    private static void touch(Path file) throws IOException {
        if (!Files.exists(file)) Files.createFile(file);
    }

    // ========== Enrollment ==========
    public static void addEnrollment(String studentId, String classId, String moduleCode) throws IOException {
        ensureFilesExist();

        if (isBlank(studentId)) throw new IllegalArgumentException("Student ID is required.");
        if (isBlank(classId)) throw new IllegalArgumentException("Class ID is required.");
        if (isBlank(moduleCode)) throw new IllegalArgumentException("Module code is required.");

        if (isAlreadyEnrolled(studentId, classId, moduleCode)) {
            throw new IllegalStateException("Already enrolled in this class/module.");
        }

        Enrollment e = new Enrollment(studentId.trim(), classId.trim(), moduleCode.trim(), LocalDate.now());
        appendLine(ENROLLMENTS, e.toFileLine());
    }

    public static boolean isAlreadyEnrolled(String studentId, String classId, String moduleCode) throws IOException {
        List<Enrollment> list = getEnrollmentsByStudent(studentId);
        for (Enrollment e : list) {
            if (e.getClassId().equalsIgnoreCase(classId) && e.getModuleCode().equalsIgnoreCase(moduleCode)) {
                return true;
            }
        }
        return false;
    }

    public static List<Enrollment> getEnrollmentsByStudent(String studentId) throws IOException {
        ensureFilesExist();
        List<Enrollment> list = new ArrayList<>();
        for (String line : readLines(ENROLLMENTS)) {
            Enrollment e = Enrollment.tryParse(line);
            if (e != null && e.getStudentId().equalsIgnoreCase(studentId)) list.add(e);
        }
        return list;
    }

    // ========== Results ==========
    // Expected marks.txt format:
    // studentId|moduleCode|assessmentType|marks|grade
    public static List<Result> getResultsByStudent(String studentId) throws IOException {
        ensureFilesExist();
        List<Result> list = new ArrayList<>();
        for (String line : readLines(MARKS)) {
            Result r = Result.tryParse(line);
            if (r != null && r.getStudentId().equalsIgnoreCase(studentId)) list.add(r);
        }
        return list;
    }

    // ========== Feedback ==========
    // Expected feedback.txt format:
    // studentId|moduleCode|assessmentType|feedbackText|lecturerId|date(yyyy-mm-dd)
    public static List<StudentFeedback> getFeedbackByStudent(String studentId) throws IOException {
        ensureFilesExist();
        List<StudentFeedback> list = new ArrayList<>();
        for (String line : readLines(FEEDBACK)) {
            StudentFeedback f = StudentFeedback.tryParse(line);
            if (f != null && f.getStudentId().equalsIgnoreCase(studentId)) list.add(f);
        }
        return list;
    }

    // ========== Lecturer Comments ==========
    public static void addLecturerComment(LecturerComment c) throws IOException {
        ensureFilesExist();
        if (c == null) throw new IllegalArgumentException("Comment is required.");
        appendLine(COMMENTS, c.toFileLine());
    }
    
    public static StudentFeedback getFeedbackByAssessment(String studentId, String assessmentID) throws IOException {
        ensureDataFiles();
        for (String line : readAllLines(FEEDBACK)) {
            StudentFeedback f = StudentFeedback.tryParse(line);
            if (f != null
                    && f.getStudentId().equalsIgnoreCase(studentId)
                    && f.getAssessmentID().equalsIgnoreCase(assessmentID)) {
                return f;
            }
        }
        return null;
    }

    // ========== Helpers ==========
    private static void ensureDataFiles() throws IOException {
        if (!Files.exists(DATA_DIR)) {
            Files.createDirectories(DATA_DIR);
        }

        touchIfMissing(ENROLLMENTS);
        touchIfMissing(MARKS);
        touchIfMissing(FEEDBACK);
        touchIfMissing(COMMENTS);
    }

    private static void touchIfMissing(Path file) throws IOException {
        if (!Files.exists(file)) {
            Files.createFile(file);
        }
    }

    // Reads all non-empty lines from file
    private static List<String> readAllLines(Path file) throws IOException {
        List<String> lines = new ArrayList<>();
        try (BufferedReader br = Files.newBufferedReader(file, StandardCharsets.UTF_8)) {
            String line;
            while ((line = br.readLine()) != null) {
                line = line.trim();
                if (!line.isEmpty()) {
                    lines.add(line);
                }
            }
        }
        return lines;
    }

    private static List<String> readLines(Path file) throws IOException {
        List<String> lines = new ArrayList<>();
        try (BufferedReader br = Files.newBufferedReader(file, StandardCharsets.UTF_8)) {
            String line;
            while ((line = br.readLine()) != null) {
                line = line.trim();
                if (!line.isEmpty()) lines.add(line);
            }
        }
        return lines;
    }

    private static void appendLine(Path file, String line) throws IOException {
        try (BufferedWriter bw = Files.newBufferedWriter(file, StandardCharsets.UTF_8, StandardOpenOption.APPEND)) {
            bw.write(line);
            bw.newLine();
        }
    }

    public static String safeText(String s) {
        // avoid breaking pipe-delimited format
        return s == null ? "" : s.replace("|", "/");
    }

    private static boolean isBlank(String s) {
        return s == null || s.trim().isEmpty();
    }
}
