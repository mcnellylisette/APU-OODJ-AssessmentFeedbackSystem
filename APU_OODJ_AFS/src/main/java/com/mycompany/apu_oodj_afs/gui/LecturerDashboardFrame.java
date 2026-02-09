/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package gui;

import com.mycompany.apu_oodj_afs.models.Lecturer;

import javax.swing.*;
import java.awt.*;

/**
 * LecturerDashboardFrame
 *
 * This is the main dashboard shown after a Lecturer logs in.
 * From here, the Lecturer can:
 *  - Manage Assessments
 *  - Manage Marks
 *  - Manage Feedback
 *  - Calculate Final Results (Course Summary)
 *  - Close the dashboard
 *
 * Each button opens a new JFrame.
 */
public class LecturerDashboardFrame extends JFrame {

    // Logged-in lecturer (passed from Login process)
    private final Lecturer lecturer;

    /**
     * Constructor
     * @param lecturer the currently logged-in Lecturer
     */
    public LecturerDashboardFrame(Lecturer lecturer) {
        this.lecturer = lecturer;

        // Window settings
        setTitle("Lecturer Dashboard - " + lecturer.getName());
        setSize(480, 360); // slightly taller because we added a new button
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        // Title label
        JLabel lblTitle = new JLabel("Welcome, " + lecturer.getName(), SwingConstants.CENTER);
        lblTitle.setFont(new Font("Arial", Font.BOLD, 18));

        // Buttons
        JButton btnAssessments = new JButton("Manage Assessments");
        JButton btnMarks = new JButton("Manage Marks");
        JButton btnFeedback = new JButton("Manage Feedback");
        JButton btnFinalResults = new JButton("Calculate Final Results"); // 
        JButton btnClose = new JButton("Close");

        // Button actions

        // Open Assessments JTable screen
        btnAssessments.addActionListener(e ->
                new LecturerAssessmentManagerTableFrame(lecturer).setVisible(true)
        );

        // Open Marks JTable screen
        btnMarks.addActionListener(e ->
                new LecturerMarksManagerTableFrame(lecturer).setVisible(true)
        );

        // Open Feedback JTable screen
        btnFeedback.addActionListener(e ->
                new LecturerFeedbackManagerTableFrame(lecturer).setVisible(true)
        );

        //  Open Final Results (Course Summary JTable)
        btnFinalResults.addActionListener(e ->
                new LecturerFinalResultFrame(lecturer).setVisible(true)
        );

        // Close dashboard AND login page (exit application)
        btnClose.addActionListener(e -> System.exit(0));


        // Center panel (button layout)
        
        JPanel centerPanel = new JPanel(new GridLayout(5, 1, 10, 10));
        centerPanel.setBorder(BorderFactory.createEmptyBorder(20, 40, 20, 40));

        centerPanel.add(btnAssessments);
        centerPanel.add(btnMarks);
        centerPanel.add(btnFeedback);
        centerPanel.add(btnFinalResults); // 
        centerPanel.add(btnClose);

        // Frame layout
        setLayout(new BorderLayout());
        add(lblTitle, BorderLayout.NORTH);
        add(centerPanel, BorderLayout.CENTER);
    }
}
