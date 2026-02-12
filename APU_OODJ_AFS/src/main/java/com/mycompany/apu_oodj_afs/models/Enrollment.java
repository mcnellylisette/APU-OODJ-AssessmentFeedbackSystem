package com.mycompany.apu_oodj_afs.models;

import java.time.LocalDate;
import java.util.UUID;

public class Enrollment {

    private final String enrollmentID;
    private final String studentID;
    private final String classID;
    private final String moduleCode;
    private final LocalDate enrollmentDate;
    private String status; // Active / Dropped etc.

    public Enrollment(String studentID, String classID, String moduleCode, LocalDate enrollmentDate) {
        this.enrollmentID = UUID.randomUUID().toString();
        this.studentID = studentID;
        this.classID = classID;
        this.moduleCode = moduleCode;
        this.enrollmentDate = enrollmentDate;
        this.status = "Active";
    }

    // Constructor for parsed file lines (keeps existing data stable)
    public Enrollment(String enrollmentID, String studentID, String classID, String moduleCode,
                      LocalDate enrollmentDate, String status) {
        this.enrollmentID = enrollmentID;
        this.studentID = studentID;
        this.classID = classID;
        this.moduleCode = moduleCode;
        this.enrollmentDate = enrollmentDate;
        this.status = status;
    }

    public String getEnrollmentID() { return enrollmentID; }
    public String getStudentId() { return studentID; }
    public String getClassId() { return classID; }
    public String getModuleCode() { return moduleCode; }
    public LocalDate getEnrollDate() { return enrollmentDate; }
    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }

    public boolean validateEnrollment() {
        return studentID != null && !studentID.isBlank()
                && classID != null && !classID.isBlank()
                && moduleCode != null && !moduleCode.isBlank()
                && enrollmentDate != null;
    }

    public boolean enrollStudent() {
        return validateEnrollment();
    }

    // enrollmentID|studentID|classID|moduleCode|enrollmentDate|status
    public String toFileLine() {
        return enrollmentID + "|" + studentID + "|" + classID + "|" + moduleCode + "|" + enrollmentDate + "|" + status;
    }

    public static Enrollment tryParse(String line) {
        try {
            String[] p = line.split("\\|", -1);

            // Backward compatibility:
            if (p.length == 4) {
                return new Enrollment(
                        UUID.randomUUID().toString(),
                        p[0], p[1], p[2],
                        LocalDate.parse(p[3]),
                        "Active"
                );
            }

            // New format:
            if (p.length >= 6) {
                return new Enrollment(
                        p[0], p[1], p[2], p[3],
                        LocalDate.parse(p[4]),
                        p[5]
                );
            }
            return null;

        } catch (Exception ex) {
            return null;
        }
    }
}
