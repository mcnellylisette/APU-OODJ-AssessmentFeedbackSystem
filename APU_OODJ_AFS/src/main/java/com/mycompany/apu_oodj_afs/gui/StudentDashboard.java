package com.mycompany.apu_oodj_afs.gui;

import com.mycompany.apu_oodj_afs.models.Student;

import javax.swing.*;
import java.awt.*;

public class StudentDashboard extends JFrame {

    private final Student student;

    public StudentDashboard(Student student) {
        this.student = student;

        setTitle("Student Dashboard - " + student.getName() + " (" + student.getuserID() + ")");
        setSize(520, 360);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLayout(new BorderLayout(10, 10));

        JLabel header = new JLabel("Welcome, " + student.getName(), SwingConstants.CENTER);
        header.setFont(header.getFont().deriveFont(Font.BOLD, 18f));

        JPanel buttons = new JPanel(new GridLayout(8, 1, 10, 10));
        buttons.setBorder(BorderFactory.createEmptyBorder(15, 20, 15, 20));

        JButton regBtn = new JButton("Register for Class");
        regBtn.addActionListener(e -> new StudentClassRegistrationFrame(student).setVisible(true));

        JButton assessmentMarksBtn = new JButton("View Assessment Marks");
            assessmentMarksBtn.addActionListener(e ->
            new StudentResultsFrame(student).setVisible(true)
        );

        JButton feedbackBtn = new JButton("View Feedback");
        feedbackBtn.addActionListener(e -> new StudentFeedbackFrame(student).setVisible(true));
        
        JButton viewEnrollmentBtn = new JButton("View Enrollment");
        viewEnrollmentBtn.addActionListener(e ->
                new StudentEnrollmentViewFrame(student).setVisible(true)
        );
        
        JButton finalGradeBtn = new JButton("View Final Grades");
        finalGradeBtn.addActionListener(e ->
                new StudentFinalGradeFrame(student).setVisible(true)
        );

        JButton commentBtn = new JButton("Comment to Lecturer");
        commentBtn.addActionListener(e -> new StudentCommentFrame(student).setVisible(true));
        
        JButton editProfileBtn = new JButton("Edit Profile");
        editProfileBtn.addActionListener(e -> new StudentEditProfileFrame(student).setVisible(true));

        JButton logoutBtn = new JButton("Logout / Close");
        logoutBtn.addActionListener(e -> dispose());

        buttons.add(regBtn);
        buttons.add(assessmentMarksBtn);
        buttons.add(feedbackBtn);
        buttons.add(viewEnrollmentBtn);
        buttons.add(finalGradeBtn);
        buttons.add(commentBtn);
        buttons.add(editProfileBtn);
        buttons.add(logoutBtn);

        add(header, BorderLayout.NORTH);
        add(buttons, BorderLayout.CENTER);
    }
}
