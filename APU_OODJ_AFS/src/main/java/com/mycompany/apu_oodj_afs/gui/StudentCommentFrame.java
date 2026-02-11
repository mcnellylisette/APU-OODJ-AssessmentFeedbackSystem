package com.mycompany.apu_oodj_afs.gui;

import com.mycompany.apu_oodj_afs.core.StudentDataService;
import com.mycompany.apu_oodj_afs.models.LecturerComment;
import com.mycompany.apu_oodj_afs.models.Student;

import javax.swing.*;
import java.awt.*;
import java.time.LocalDate;

public class StudentCommentFrame extends JFrame {

    private final Student student;

    public StudentCommentFrame(Student student) {
        this.student = student;

        setTitle("Comment to Lecturer");
        setSize(660, 440);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLayout(new BorderLayout(10, 10));

        JTextField lecturerIdField = new JTextField();
        JTextField moduleCodeField = new JTextField();
        JSpinner ratingSpinner = new JSpinner(new SpinnerNumberModel(5, 1, 5, 1));
        JTextArea commentArea = new JTextArea(8, 40);
        commentArea.setLineWrap(true);
        commentArea.setWrapStyleWord(true);

        JPanel form = new JPanel(new GridLayout(3, 2, 10, 10));
        form.setBorder(BorderFactory.createEmptyBorder(15, 15, 5, 15));
        form.add(new JLabel("Lecturer ID:"));
        form.add(lecturerIdField);
        form.add(new JLabel("Module Code:"));
        form.add(moduleCodeField);
        form.add(new JLabel("Rating (1-5):"));
        form.add(ratingSpinner);

        JPanel commentPanel = new JPanel(new BorderLayout());
        commentPanel.setBorder(BorderFactory.createEmptyBorder(0, 15, 0, 15));
        commentPanel.add(new JLabel("Comment:"), BorderLayout.NORTH);
        commentPanel.add(new JScrollPane(commentArea), BorderLayout.CENTER);

        JButton submitBtn = new JButton("Submit");
        submitBtn.addActionListener(e -> {
            try {
                String lecturerId = lecturerIdField.getText().trim();
                String moduleCode = moduleCodeField.getText().trim();
                int rating = (Integer) ratingSpinner.getValue();
                String text = commentArea.getText().trim();

                if (lecturerId.isBlank()) throw new IllegalArgumentException("Lecturer ID is required.");
                if (moduleCode.isBlank()) throw new IllegalArgumentException("Module code is required.");
                if (text.isBlank()) throw new IllegalArgumentException("Comment cannot be empty.");

                LecturerComment c = new LecturerComment(
                        student.getuserID(),
                        lecturerId,
                        moduleCode,
                        rating,
                        text,
                        LocalDate.now()
                );

                StudentDataService.addLecturerComment(c);

                JOptionPane.showMessageDialog(this, "Comment submitted!", "Success", JOptionPane.INFORMATION_MESSAGE);
                lecturerIdField.setText("");
                moduleCodeField.setText("");
                ratingSpinner.setValue(5);
                commentArea.setText("");
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, ex.getMessage(), "Submit Failed", JOptionPane.ERROR_MESSAGE);
            }
        });

        JButton closeBtn = new JButton("Close");
        closeBtn.addActionListener(e -> dispose());

        JPanel buttons = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        buttons.add(submitBtn);
        buttons.add(closeBtn);

        add(form, BorderLayout.NORTH);
        add(commentPanel, BorderLayout.CENTER);
        add(buttons, BorderLayout.SOUTH);
    }
}
