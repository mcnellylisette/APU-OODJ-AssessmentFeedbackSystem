package com.mycompany.apu_oodj_afs.models;

public class Assessment {
    private final String assessmentID;
    private final String moduleCode;
    private final String assessmentType;
    private final String title;
    private final double weight;
    private final String lecturerId;

    public Assessment(String assessmentID, String moduleCode, String assessmentType, String title, double weight, String lecturerId) {
        this.assessmentID = assessmentID;
        this.moduleCode = moduleCode;
        this.assessmentType = assessmentType;
        this.title = title;
        this.weight = weight;
        this.lecturerId = lecturerId;
    }

    public String getAssessmentID() { return assessmentID; }
    public String getModuleCode() { return moduleCode; }
    public String getAssessmentType() { return assessmentType; }
    public String getTitle() { return title; }
    public double getWeight() { return weight; }
    public String getLecturerId() { return lecturerId; }

    public String getDisplayName() {
        // e.g., "Exam - Final exams"
        return assessmentType + " - " + title;
    }

    public static Assessment tryParse(String line) {
        try {
            String[] p = line.split("\\|", -1);
            if (p.length < 6) return null;

            String id = p[0].trim();
            String module = p[1].trim();
            String type = p[2].trim();
            String title = p[3].trim();
            double weight = Double.parseDouble(p[4].trim());
            String lecturer = p[5].trim();

            return new Assessment(id, module, type, title, weight, lecturer);
        } catch (Exception ex) {
            return null;
        }
    }
}
