/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.apu_oodj_afs.models;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Represents a grading system with letter grades, score ranges, and grade points.
 * 
 * @author Senior Java Developer
 */
public class GradingSystem {
    
    // =========================================================================
    // Private Attributes
    // =========================================================================
    private String gradeLetter;
    private double minScore;
    private double maxScore;
    private double gradePoint;
    
    // =========================================================================
    // Constructor
    // =========================================================================
    
    /**
     * Default constructor
     */
    public GradingSystem() {
    }
    
    /**
     * Parameterized constructor
     * 
     * @param gradeLetter The letter grade (e.g., A, B, C)
     * @param minScore Minimum score for this grade
     * @param maxScore Maximum score for this grade
     * @param gradePoint Grade point value (e.g., 4.0 for A)
     */
    public GradingSystem(String gradeLetter, double minScore, double maxScore, double gradePoint) {
        this.gradeLetter = gradeLetter;
        this.minScore = minScore;
        this.maxScore = maxScore;
        this.gradePoint = gradePoint;
    }
    
    // =========================================================================
    // Getters and Setters
    // =========================================================================
    
    /**
     * Gets the letter grade
     * @return The letter grade
     */
    public String getGradeLetter() {
        return gradeLetter;
    }
    
    /**
     * Sets the letter grade
     * @param gradeLetter The letter grade to set
     */
    public void setGradeLetter(String gradeLetter) {
        this.gradeLetter = gradeLetter;
    }
    
    /**
     * Gets the minimum score
     * @return The minimum score
     */
    public double getMinScore() {
        return minScore;
    }
    
    /**
     * Sets the minimum score
     * @param minScore The minimum score to set
     */
    public void setMinScore(double minScore) {
        this.minScore = minScore;
    }
    
    /**
     * Gets the maximum score
     * @return The maximum score
     */
    public double getMaxScore() {
        return maxScore;
    }
    
    /**
     * Sets the maximum score
     * @param maxScore The maximum score to set
     */
    public void setMaxScore(double maxScore) {
        this.maxScore = maxScore;
    }
    
    /**
     * Gets the grade point
     * @return The grade point value
     */
    public double getGradePoint() {
        return gradePoint;
    }
    
    /**
     * Sets the grade point
     * @param gradePoint The grade point to set
     */
    public void setGradePoint(double gradePoint) {
        this.gradePoint = gradePoint;
    }
    
    // =========================================================================
    // Static Method - Load Grades from File
    // =========================================================================
    
    /**
     * Loads grading system data from the grading_system.txt file.
     * File format: GradeLetter|MinScore|MaxScore|GradePoint
     * Example: A|90|100|4.0
     * 
     * @return List of GradingSystem objects loaded from file
     */
    public static List<GradingSystem> loadGradesFromFile() {
        List<GradingSystem> gradingList = new ArrayList<>();
        File file = new File("data/grading_system.txt");
        
        // Check if file exists
        if (!file.exists()) {
            System.err.println("Warning: grading_system.txt not found at " + file.getAbsolutePath());
            return gradingList; // Return empty list
        }
        
        try (BufferedReader br = new BufferedReader(new FileReader(file))) {
            String line;
            int lineNumber = 0;
            
            while ((line = br.readLine()) != null) {
                lineNumber++;
                
                // Skip empty lines
                if (line.trim().isEmpty()) {
                    continue;
                }
                
                // Split by pipe delimiter
                String[] parts = line.split("\\|");
                
                // Validate that we have exactly 4 parts
                if (parts.length != 4) {
                    System.err.println("Warning: Invalid format at line " + lineNumber + 
                                     ". Expected 4 fields, found " + parts.length);
                    continue;
                }
                
                try {
                    // Parse the data
                    String gradeLetter = parts[0].trim();
                    double minScore = Double.parseDouble(parts[1].trim());
                    double maxScore = Double.parseDouble(parts[2].trim());
                    double gradePoint = Double.parseDouble(parts[3].trim());
                    
                    // Create GradingSystem object and add to list
                    GradingSystem grade = new GradingSystem(gradeLetter, minScore, maxScore, gradePoint);
                    gradingList.add(grade);
                    
                } catch (NumberFormatException e) {
                    System.err.println("Warning: Invalid number format at line " + lineNumber + 
                                     ": " + e.getMessage());
                }
            }
            
            System.out.println("Successfully loaded " + gradingList.size() + " grading records.");
            
        } catch (IOException e) {
            System.err.println("Error reading grading_system.txt: " + e.getMessage());
            e.printStackTrace();
        }
        
        return gradingList;
    }
    
    // =========================================================================
    // Utility Methods
    // =========================================================================
    
    /**
     * Returns a string representation of the GradingSystem object
     * @return String representation
     */
    @Override
    public String toString() {
        return String.format("Grade: %s | Score Range: %.1f - %.1f | Grade Point: %.1f",
                           gradeLetter, minScore, maxScore, gradePoint);
    }
    
    /**
     * Helper method to determine the grade letter based on a score
     * 
     * @param score The score to evaluate
     * @param gradingList List of grading system objects
     * @return The corresponding GradingSystem object, or null if not found
     */
    public static GradingSystem getGradeByScore(double score, List<GradingSystem> gradingList) {
        for (GradingSystem grade : gradingList) {
            if (score >= grade.getMinScore() && score <= grade.getMaxScore()) {
                return grade;
            }
        }
        return null; // No matching grade found
    }
}