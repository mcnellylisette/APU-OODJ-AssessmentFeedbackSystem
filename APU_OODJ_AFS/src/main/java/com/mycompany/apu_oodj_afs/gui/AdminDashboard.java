/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package com.mycompany.apu_oodj_afs.gui;

import com.mycompany.apu_oodj_afs.core.IDGenerator;
import com.mycompany.apu_oodj_afs.models.Admin;
import com.mycompany.apu_oodj_afs.models.Student;
import com.mycompany.apu_oodj_afs.models.User;
import java.awt.Color;
import javax.swing.table.DefaultTableModel;
import javax.swing.JOptionPane;
import java.util.ArrayList;

/**
 * Admin Dashboard GUI - Delegates all business logic to Admin model class
 * @author jamesmcnellylisette
 */
public class AdminDashboard extends javax.swing.JFrame {
    
    private Admin admin;  // The Admin object that handles all business logic
    private String currentlyEditingUserID = null; 
    
    /**
     * Constructor with Admin dependency injection
     * @param admin The Admin object to handle business logic
     */
    public AdminDashboard(Admin admin) {
        this.admin = admin;
        initComponents();
        setLocationRelativeTo(null); // Center window
        loadUsersToTable(); // Load users on startup
    }
    
    /**
     * No-arg constructor for testing purposes
     * Creates a temporary Admin user
     */
    public AdminDashboard() {
        // Create a temporary Admin for testing
        this.admin = new Admin("ADMIN001", "admin", "admin123", "System Admin", "Admin", "admin@system.com");
        initComponents();
        setLocationRelativeTo(null);
        loadUsersToTable();
    }

    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLabel1 = new javax.swing.JLabel();
        txtName = new javax.swing.JTextField();
        jLabel2 = new javax.swing.JLabel();
        txtPassword = new javax.swing.JPasswordField();
        comboUserRole = new javax.swing.JComboBox<>();
        btnCreateUser = new javax.swing.JButton();
        lblFeedback = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        txtEmail = new javax.swing.JTextField();
        jScrollPane1 = new javax.swing.JScrollPane();
        tblUsers = new javax.swing.JTable();
        btnRefresh = new javax.swing.JButton();
        btnDeleteUser = new javax.swing.JButton();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        btnEditUser = new javax.swing.JButton();
        btnViewGrades = new javax.swing.JButton();
        Create_New_Class = new javax.swing.JButton();
        Filter = new javax.swing.JComboBox<>();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jLabel1.setText("Full name");

        txtName.setText("                     ");
        txtName.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtNameActionPerformed(evt);
            }
        });

        jLabel2.setText("Password");

        txtPassword.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtPasswordActionPerformed(evt);
            }
        });

        comboUserRole.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Admin ", "Academic Leader", "Lecturer ", "Student" }));
        comboUserRole.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                comboUserRoleActionPerformed(evt);
            }
        });

        btnCreateUser.setText(" Create User");
        btnCreateUser.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCreateUserActionPerformed(evt);
            }
        });

        lblFeedback.setFont(new java.awt.Font("Helvetica Neue", 0, 18)); // NOI18N
        lblFeedback.setText("Feedback");

        jLabel3.setText("Email:");

        txtEmail.setText("                ");
        txtEmail.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtEmailActionPerformed(evt);
            }
        });

        tblUsers.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        jScrollPane1.setViewportView(tblUsers);

        btnRefresh.setText("Refresh List");
        btnRefresh.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnRefreshActionPerformed(evt);
            }
        });

        btnDeleteUser.setText("Delete Selected User");
        btnDeleteUser.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnDeleteUserActionPerformed(evt);
            }
        });

        jLabel4.setFont(new java.awt.Font("Helvetica Neue", 0, 24)); // NOI18N
        jLabel4.setText("Create New User");

        jLabel5.setText("Role:");

        btnEditUser.setText("Edit Selected User");
        btnEditUser.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnEditUserActionPerformed(evt);
            }
        });

        btnViewGrades.setText("View Grading System");
        btnViewGrades.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnViewGradesActionPerformed(evt);
            }
        });

        Create_New_Class.setText("Create New Class");
        Create_New_Class.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                Create_New_ClassActionPerformed(evt);
            }
        });

        Filter.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "All User", "Academic Leader", "Lecturer", "Student" }));
        Filter.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                FilterActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addContainerGap()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                                .addComponent(lblFeedback)
                                .addGap(88, 88, 88))
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                                .addComponent(btnCreateUser, javax.swing.GroupLayout.PREFERRED_SIZE, 166, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(41, 41, 41))))
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addGap(13, 13, 13)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(layout.createSequentialGroup()
                                        .addComponent(jLabel5)
                                        .addGap(40, 40, 40)
                                        .addComponent(comboUserRole, javax.swing.GroupLayout.PREFERRED_SIZE, 146, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addComponent(jLabel3)))
                            .addGroup(layout.createSequentialGroup()
                                .addGap(36, 36, 36)
                                .addComponent(jLabel4))
                            .addGroup(layout.createSequentialGroup()
                                .addContainerGap()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addComponent(txtEmail, javax.swing.GroupLayout.PREFERRED_SIZE, 146, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                        .addGroup(layout.createSequentialGroup()
                                            .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 69, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                            .addComponent(txtName))
                                        .addGroup(layout.createSequentialGroup()
                                            .addComponent(jLabel2)
                                            .addGap(18, 18, 18)
                                            .addComponent(txtPassword, javax.swing.GroupLayout.PREFERRED_SIZE, 145, javax.swing.GroupLayout.PREFERRED_SIZE))))))
                        .addGap(32, 32, 32)))
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(21, 21, 21)
                        .addComponent(btnDeleteUser)
                        .addGap(36, 36, 36)
                        .addComponent(btnEditUser, javax.swing.GroupLayout.PREFERRED_SIZE, 144, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 32, Short.MAX_VALUE)
                        .addComponent(btnRefresh, javax.swing.GroupLayout.PREFERRED_SIZE, 142, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(85, 85, 85)
                        .addComponent(btnViewGrades)
                        .addGap(61, 61, 61)
                        .addComponent(Create_New_Class)
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 522, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(78, 78, 78))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addGap(0, 0, Short.MAX_VALUE)
                .addComponent(Filter, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(111, 111, 111))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(15, 15, 15)
                .addComponent(Filter, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addGap(23, 23, 23)
                                .addComponent(jLabel4)
                                .addGap(47, 47, 47)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(jLabel1)
                                    .addComponent(txtName, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(jLabel2)
                                    .addComponent(txtPassword, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(12, 12, 12)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(jLabel3)
                                    .addComponent(txtEmail, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(9, 9, 9)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel5)
                                    .addComponent(comboUserRole, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(23, 23, 23)
                                .addComponent(btnCreateUser, javax.swing.GroupLayout.PREFERRED_SIZE, 46, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 284, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(btnRefresh)
                            .addComponent(btnDeleteUser)
                            .addComponent(btnEditUser))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 33, Short.MAX_VALUE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(lblFeedback)
                        .addGap(14, 14, 14)))
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnViewGrades)
                    .addComponent(Create_New_Class))
                .addGap(15, 15, 15))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void txtNameActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtNameActionPerformed

    }//GEN-LAST:event_txtNameActionPerformed
 
    private void comboUserRoleActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_comboUserRoleActionPerformed
 
    
    }//GEN-LAST:event_comboUserRoleActionPerformed

    private void btnCreateUserActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCreateUserActionPerformed
        // TODO add your handling code here:
   // Get input from form
        String fullName = txtName.getText().trim();
        String password = new String(txtPassword.getPassword()).trim();
        String email = txtEmail.getText().trim();
        String selectedRole = (String) comboUserRole.getSelectedItem();

        // Validation
        if (fullName.isEmpty() || password.isEmpty() || email.isEmpty()) {
            lblFeedback.setForeground(Color.RED);
            lblFeedback.setText("Error: All fields required!");
            return;
        }
        
        // Email format validation
        if (!email.contains("@") || !email.contains(".")) {
            lblFeedback.setForeground(Color.RED);
            lblFeedback.setText("Error: Invalid email format!");
            return;
        }
        
        // =========================================================================
        // CHECK IF WE'RE IN EDIT MODE (Button says "Save Changes")
        // =========================================================================
        if (btnCreateUser.getText().equals("Save Changes")) {
            // =====================================================================
            // UPDATE LOGIC - Edit existing user
            // =====================================================================
            
            // Get the current username from the table (we need to preserve it)
            String currentUsername = getCurrentUsernameForID(currentlyEditingUserID);
            
            if (currentUsername == null) {
                lblFeedback.setForeground(Color.RED);
                lblFeedback.setText("Error: Could not find user to update!");
                return;
            }
            
            // Create updated User object with the same ID and username
            User updatedUser = createUserObject(
                currentlyEditingUserID, 
                currentUsername, 
                password, 
                fullName, 
                selectedRole, 
                email
            );
            
            // Call admin.updateUser()
            if (admin.updateUser(updatedUser)) {
                lblFeedback.setForeground(new java.awt.Color(0, 153, 0));
                lblFeedback.setText("User updated successfully!");
                
                // Reset edit mode
                currentlyEditingUserID = null;
                btnCreateUser.setText("Create User");
                
                // Clear fields
                clearFields();
                
                // Refresh the table
                loadUsersToTable();
            } else {
                lblFeedback.setForeground(Color.RED);
                lblFeedback.setText("Error updating user!");
            }
            
        } else {
            // =====================================================================
            // CREATE LOGIC - Create new user
            // =====================================================================
            
            // Determine ID Prefix based on role
            String idPrefix;
            if (selectedRole.contains("Admin")) { 
                idPrefix = "Admin"; 
            } else if (selectedRole.contains("Academic")) { 
                idPrefix = "Academic Leader"; 
            } else if (selectedRole.contains("Lecturer")) { 
                idPrefix = "Lecturer"; 
            } else { 
                idPrefix = "Student"; 
            }

            // Generate UNIQUE ID and UNIQUE Username using the Generator
            String id = IDGenerator.generateNewUserID(idPrefix);
            String username = IDGenerator.generateUsername(fullName);

            // Create User object
            User newUser = createUserObject(id, username, password, fullName, selectedRole, email);
            
            // Call admin.createUser()
            if (admin.createUser(newUser)) {
                lblFeedback.setForeground(new java.awt.Color(0, 153, 0));
                lblFeedback.setText("Created: " + id + " (" + username + ")");
                
                // Clear fields
                clearFields();
                
                // Refresh the table to show the new user
                loadUsersToTable();
            } else {
                lblFeedback.setForeground(Color.RED);
                lblFeedback.setText("Save Error!");
            }
        }
    
    }//GEN-LAST:event_btnCreateUserActionPerformed

    private void txtEmailActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtEmailActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtEmailActionPerformed

    private void btnDeleteUserActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnDeleteUserActionPerformed
        // TODO add your handling code here:
         int selectedRow = tblUsers.getSelectedRow();
        
        // Check if a row is selected
        if (selectedRow == -1) {
            lblFeedback.setForeground(Color.RED);
            lblFeedback.setText("Error: No user selected!");
            JOptionPane.showMessageDialog(this, 
                "Please select a user from the table to delete.", 
                "No Selection", 
                JOptionPane.WARNING_MESSAGE);
            return;
        }
        
        // Get user details for confirmation
        String userID = tblUsers.getValueAt(selectedRow, 0).toString();
        String username = tblUsers.getValueAt(selectedRow, 1).toString();
        String name = tblUsers.getValueAt(selectedRow, 3).toString();
        
        // Confirm deletion
        int confirm = JOptionPane.showConfirmDialog(this, 
            String.format("Delete user:%n%nName: %s%nUsername: %s%nID: %s", 
                name, username, userID), 
            "Confirm Deletion", 
            JOptionPane.YES_NO_OPTION, 
            JOptionPane.WARNING_MESSAGE);
        
        if (confirm != JOptionPane.YES_OPTION) {
            lblFeedback.setForeground(Color.BLUE);
            lblFeedback.setText("Deletion cancelled.");
            return;
        }
        
        // Call admin.deleteUser()
        if (admin.deleteUser(userID)) {
            lblFeedback.setForeground(new java.awt.Color(0, 153, 0));
            lblFeedback.setText("User deleted successfully!");
            
            // Refresh the table
            loadUsersToTable();
        } else {
            lblFeedback.setForeground(Color.RED);
            lblFeedback.setText("Error deleting user!");
        }
    
    }//GEN-LAST:event_btnDeleteUserActionPerformed

    private void btnRefreshActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnRefreshActionPerformed
        // TODO add your handling code here:
        loadUsersToTable();
        lblFeedback.setForeground(Color.BLUE);
        lblFeedback.setText("Table refreshed.");
    }//GEN-LAST:event_btnRefreshActionPerformed

    private void btnEditUserActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnEditUserActionPerformed
        // TODO add your handling code here:
        // Get the selected row from the table
        int selectedRow = tblUsers.getSelectedRow();
        
        // Check if a row is selected
        if (selectedRow == -1) {
            lblFeedback.setForeground(Color.RED);
            lblFeedback.setText("Error: No user selected!");
            JOptionPane.showMessageDialog(this, 
                "Please select a user from the table to edit.", 
                "No Selection", 
                JOptionPane.WARNING_MESSAGE);
            return;
        }
        
        // Get data from the selected row
        String userID = tblUsers.getValueAt(selectedRow, 0).toString();        // Column 0: User ID
        String username = tblUsers.getValueAt(selectedRow, 1).toString();      // Column 1: Username
        String password = tblUsers.getValueAt(selectedRow, 2).toString();      // Column 2: Password
        String fullName = tblUsers.getValueAt(selectedRow, 3).toString();      // Column 3: Full Name
        String email = tblUsers.getValueAt(selectedRow, 4).toString();         // Column 4: Email
        String role = tblUsers.getValueAt(selectedRow, 5).toString();          // Column 5: Role
        
        // Populate the form fields with the selected user's data
        txtName.setText(fullName);
        txtPassword.setText(password);
        txtEmail.setText(email);
        
        // Set the combo box to the correct role
        for (int i = 0; i < comboUserRole.getItemCount(); i++) {
            if (comboUserRole.getItemAt(i).trim().equals(role.trim())) {
                comboUserRole.setSelectedIndex(i);
                break;
            }
        }
        
        // Store the User ID for later use when saving changes
        currentlyEditingUserID = userID;
        
        // Change the button text to indicate we're now in "edit mode"
        btnCreateUser.setText("Save Changes");
        
        // Update feedback
        lblFeedback.setForeground(Color.BLUE);
        lblFeedback.setText("Editing user: " + username);
                                            

    }//GEN-LAST:event_btnEditUserActionPerformed

    private void btnViewGradesActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnViewGradesActionPerformed
        // TODO add your handling code here:
        // This opens the new window
        new GradingSystemFrame().setVisible(true);

    }//GEN-LAST:event_btnViewGradesActionPerformed

    private void Create_New_ClassActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_Create_New_ClassActionPerformed
        // TODO add your handling code here:
        new CreateClassFrame().setVisible(true);
    }//GEN-LAST:event_Create_New_ClassActionPerformed

    private void FilterActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_FilterActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_FilterActionPerformed

    private void txtPasswordActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtPasswordActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtPasswordActionPerformed
/**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ReflectiveOperationException | javax.swing.UnsupportedLookAndFeelException ex) {
            ex.printStackTrace(); 
        }
       
        /* Create and display the form */
        java.awt.EventQueue.invokeLater(() -> new AdminDashboard().setVisible(true));
    }
    
    /**
     * Loads all users from the Admin model and displays them in the table
     */
    private void loadUsersToTable() {
        // Define column names
        String[] columnNames = {"User ID", "Username", "Password", "Full Name", "Email", "Role"};
        
        // Create a DefaultTableModel
        DefaultTableModel model = new DefaultTableModel(columnNames, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                // Make table cells non-editable
                return false;
            }
        };
        
        // Get all users from Admin model
        ArrayList<User> users = admin.viewAllUsers();
        
        // Add each user to the table
        for (User user : users) {
            Object[] rowData = {
                user.getuserID(),
                user.getUsername(),
                user.getPassword(),
                user.getName(),
                user.getEmail(),
                user.getRole()
            };
            model.addRow(rowData);
        }
        
        // Set the model to the table
        tblUsers.setModel(model);
        
        // Optional: Adjust column widths for better visibility
        if (tblUsers.getColumnModel().getColumnCount() > 0) {
            tblUsers.getColumnModel().getColumn(0).setPreferredWidth(100); // User ID
            tblUsers.getColumnModel().getColumn(1).setPreferredWidth(100); // Username
            tblUsers.getColumnModel().getColumn(2).setPreferredWidth(80);  // Password
            tblUsers.getColumnModel().getColumn(3).setPreferredWidth(120); // Full Name
            tblUsers.getColumnModel().getColumn(4).setPreferredWidth(150); // Email
            tblUsers.getColumnModel().getColumn(5).setPreferredWidth(120); // Role
        }
        
        lblFeedback.setForeground(Color.BLUE);
        lblFeedback.setText(String.format("Loaded %d user(s).", model.getRowCount()));
    }
    
    /**
     * Creates a User object of the appropriate subclass based on role
     */
    private User createUserObject(String userID, String username, String password, 
                                   String name, String role, String email) {
        // Create the appropriate User subclass based on role
        if (role.contains("Admin")) {
            return new Admin(userID, username, password, name, role.trim(), email);
        } else if (role.contains("Student")) {
            return new Student(userID, username, password, name, role.trim(), email);
        } 
        // For now, default to Student for Lecturer and Academic Leader
        // TODO: Create Lecturer and AcademicLeader classes
        else {
            return new Student(userID, username, password, name, role.trim(), email);
        }
    }
    
    /**
     * Gets the current username for a given user ID
     */
    private String getCurrentUsernameForID(String userID) {
        ArrayList<User> users = admin.viewAllUsers();
        for (User user : users) {
            if (user.getuserID().equals(userID)) {
                return user.getUsername();
            }
        }
        return null;
    }
    
    /**
     * Clears all input fields
     */
    private void clearFields() {
        txtName.setText("");
        txtPassword.setText("");
        txtEmail.setText("");
        comboUserRole.setSelectedIndex(0);
    }
    
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton Create_New_Class;
    private javax.swing.JComboBox<String> Filter;
    private javax.swing.JButton btnCreateUser;
    private javax.swing.JButton btnDeleteUser;
    private javax.swing.JButton btnEditUser;
    private javax.swing.JButton btnRefresh;
    private javax.swing.JButton btnViewGrades;
    private javax.swing.JComboBox<String> comboUserRole;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JLabel lblFeedback;
    private javax.swing.JTable tblUsers;
    private javax.swing.JTextField txtEmail;
    private javax.swing.JTextField txtName;
    private javax.swing.JPasswordField txtPassword;
    // End of variables declaration//GEN-END:variables


}