package com.mycompany.apu_oodj_afs.core;

import java.io.*;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * LecturerData
 *
 * PURPOSE:
 * - Simple file helper methods for Lecturer module
 * - Store ONLY raw marks per assessment (no grades per assessment)
 * - Calculate TOTAL marks per module and then derive final grade
 *
 * FILE FORMATS:
 * users.txt:
 *   userID|username|password|name|email|role
 *
 * assessments.txt:
 *   assessmentId|moduleCode|type|title|totalMarks|lecturerId
 *
 * marks.txt:  (NO grade here)
 *   markId|studentId|assessmentId|marks|lecturerId
 *
 * feedback.txt:
 *   feedbackId|studentId|assessmentId|lecturerId|comment
 *
 * ACADEMIC RULE:
 * - Assignments / Quizzes / Exams have NO letter grade individually
 * - Grade is calculated ONLY from TOTAL marks for a module (course)
 */
public class LecturerData {

   
    // File paths
    
    public static final String USERS_FILE = "data/users.txt";
    public static final String ASSESSMENTS_FILE = "data/assessments.txt";
    public static final String MARKS_FILE = "data/marks.txt";
    public static final String FEEDBACK_FILE = "data/feedback.txt";

   
    // FILE HELPERS
   

    /**
     * Ensure file + parent folder exist.
     * @param path
     */
    public static void ensureFile(String path) {
        try {
            File file = new File(path);
            File parent = file.getParentFile();

            if (parent != null && !parent.exists()) {
                parent.mkdirs();
            }

            if (!file.exists()) {
                file.createNewFile();
            }
        } catch (IOException e) {
            throw new RuntimeException("Cannot create file: " + path, e);
        }
    }

    /**
     * Read all non-empty lines from a file.
     * @param path
     * @return 
     */
    public static List<String> readAll(String path) {
        ensureFile(path);
        List<String> lines = new ArrayList<>();

        try (BufferedReader br = new BufferedReader(new FileReader(path))) {
            String line;
            while ((line = br.readLine()) != null) {
                if (!line.trim().isEmpty()) {
                    lines.add(line);
                }
            }
        } catch (IOException e) {
            throw new RuntimeException("Cannot read file: " + path, e);
        }

        return lines;
    }

    /**
     * Append one line to the end of file.
     * @param path
     * @param line
     */
    public static void appendLine(String path, String line) {
        ensureFile(path);
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(path, true))) {
            bw.write(line);
            bw.newLine();
        } catch (IOException e) {
            throw new RuntimeException("Cannot write file: " + path, e);
        }
    }

    /**
     * Overwrite file with the provided lines.
     * Used for update/delete operations.
     * @param path
     * @param lines
     */
    public static void overwriteAll(String path, List<String> lines) {
        ensureFile(path);
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(path))) {
            for (String l : lines) {
                bw.write(l);
                bw.newLine();
            }
        } catch (IOException e) {
            throw new RuntimeException("Cannot overwrite file: " + path, e);
        }
    }

    /**
     * Delete a record by ID (first column before "|") from a file.
     * Returns true if deleted, false if not found.
     *
     * Example:
     * - assessments: delete by assessmentId
     * - marks: delete by markId (if you ever add delete marks)
     * @param path
     * @param id
     * @return 
     */
    public static boolean deleteById(String path, String id) {
        List<String> lines = readAll(path);

        boolean removed = lines.removeIf(line -> {
            String[] parts = line.split("\\|");
            return parts.length > 0 && parts[0].equals(id);
        });

        if (removed) {
            overwriteAll(path, lines);
        }

        return removed;
    }

    /**
     * Simple unique ID generator.
     * @param prefix
     * @return 
     */
    public static String nextId(String prefix) {
        return prefix + System.currentTimeMillis();
    }

    
    // DROPDOWN / DISPLAY LISTS
    

    /**
     * Load students for dropdown display.
     * Output format: "S001 - Alice"
     */
    public static List<String> loadStudentDisplayList() {
        List<String> list = new ArrayList<>();

        for (String line : readAll(USERS_FILE)) {
            String[] d = line.split("\\|");
            if (d.length >= 6 && d[5].equalsIgnoreCase("Student")) {
                list.add(d[0] + " - " + d[3]);
            }
        }

        return list;
    }

    /**
     * Load assessments for dropdown display (only assessments created by lecturer).
     * Output format: "A001 - CS101 - Quiz - Week 1"
     */
    public static List<String> loadAssessmentDisplayListByLecturer(String lecturerId) {
        List<String> list = new ArrayList<>();

        for (String line : readAll(ASSESSMENTS_FILE)) {
            String[] d = line.split("\\|");
            if (d.length >= 6 && d[5].equals(lecturerId)) {
                list.add(d[0] + " - " + d[1] + " - " + d[2] + " - " + d[3]);
            }
        }

        return list;
    }

    /**
     * Returns unique module codes taught by this lecturer (based on assessments file).
     */
    public static List<String> loadModulesByLecturer(String lecturerId) {
        Set<String> modules = new HashSet<>();

        for (String line : readAll(ASSESSMENTS_FILE)) {
            String[] d = line.split("\\|");
            if (d.length >= 6 && d[5].equals(lecturerId)) {
                modules.add(d[1]); // moduleCode
            }
        }

        return new ArrayList<>(modules);
    }

  
    // MARKS LOGIC (RAW MARKS ONLY)
    

    /**
     * Checks if a mark already exists for the same:
     * studentId + assessmentId + lecturerId
     *
     * marks.txt format:
     * markId|studentId|assessmentId|marks|lecturerId
     */
    public static boolean markExists(String studentId, String assessmentId, String lecturerId) {
        for (String line : readAll(MARKS_FILE)) {
            String[] d = line.split("\\|");
            if (d.length >= 5 &&
                    d[1].equals(studentId) &&
                    d[2].equals(assessmentId) &&
                    d[4].equals(lecturerId)) {
                return true;
            }
        }
        return false;
    }

    /**
     * Calculate TOTAL marks for a student in a module.
     * It sums marks for ALL assessments that belong to that module.
     *
     * NOTE:
     * This does not apply grades per assessment.
     */
    public static double calculateTotalMarks(String studentId, String moduleCode) {
        double total = 0;

        // Go through each mark
        for (String markLine : readAll(MARKS_FILE)) {
            String[] m = markLine.split("\\|");
            if (m.length < 5) continue;

            String markStudentId = m[1];
            String assessmentId = m[2];

            if (!markStudentId.equals(studentId)) continue;

            double marks;
            try {
                marks = Double.parseDouble(m[3]);
            } catch (NumberFormatException ex) {
                continue; // skip bad data line
            }

            // Find the assessment to check which module it belongs to
            String assessModule = getModuleByAssessmentId(assessmentId);

            // Add marks if module matches
            if (moduleCode.equals(assessModule)) {
                total += marks;
            }
        }

        return total;
    }

    /**
     * Get moduleCode from assessments.txt using assessmentId.
     * assessments.txt:
     * assessmentId|moduleCode|type|title|totalMarks|lecturerId
     */
    public static String getModuleByAssessmentId(String assessmentId) {
        for (String assessLine : readAll(ASSESSMENTS_FILE)) {
            String[] a = assessLine.split("\\|");
            if (a.length >= 6 && a[0].equals(assessmentId)) {
                return a[1]; // moduleCode
            }
        }
        return "";
    }

    
    // FINAL RESULTS (TOTAL + GRADE)
    

    /**
     * Convert TOTAL marks into final grade.
     * Adjust grading scale if needed.
     */
    public static String gradeByTotal(double total) {
        if (total >= 90) return "A+";
        if (total >= 80) return "A";
        if (total >= 75) return "B+";
        if (total >= 70) return "B";
        if (total >= 65) return "C+";
        if (total >= 60) return "C";
        if (total >= 50) return "D";
        return "F";
    }

    /**
     * Calculate final results for a module (Course Summary JTable).
     * Returns rows like:
     * [studentId, studentName, totalMarks, grade]
     *
     * Only includes students with total > 0 (students who have marks in that module).
     */
    public static List<String[]> calculateFinalResultsByModule(String moduleCode) {
        List<String[]> results = new ArrayList<>();

        // Loop through students from users file
        for (String userLine : readAll(USERS_FILE)) {
            String[] u = userLine.split("\\|");
            if (u.length < 6) continue;

            String role = u[5];
            if (!role.equalsIgnoreCase("Student")) continue;

            String studentId = u[0];
            String studentName = u[3];

            double total = calculateTotalMarks(studentId, moduleCode);

            if (total > 0) {
                String grade = gradeByTotal(total);
                results.add(new String[]{
                        studentId,
                        studentName,
                        String.valueOf(total),
                        grade
                });
            }
        }

        return results;
    }
}
