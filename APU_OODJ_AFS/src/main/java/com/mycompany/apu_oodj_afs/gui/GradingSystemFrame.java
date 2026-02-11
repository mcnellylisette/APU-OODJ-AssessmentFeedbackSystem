/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package com.mycompany.apu_oodj_afs.gui;
import com.mycompany.apu_oodj_afs.models.GradingSystem;
import javax.swing.table.DefaultTableModel;
import java.util.List;
/**
 *
 * @author jamesmcnellylisette
 */
public class GradingSystemFrame extends javax.swing.JFrame {
    private static final java.util.logging.Logger logger = java.util.logging.Logger.getLogger(GradingSystemFrame.class.getName());
 public GradingSystemFrame() {
            initComponents();
            loadGradingData();
            setLocationRelativeTo(null);
            setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        }

  
    

@SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jScrollPane1 = new javax.swing.JScrollPane();
        tblGrades = new javax.swing.JTable();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        tblGrades.setModel(new javax.swing.table.DefaultTableModel(
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
        jScrollPane1.setViewportView(tblGrades);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(37, 37, 37)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(58, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents
  
    // =========================================================================
    // CUSTOM METHOD - Load Grading Data
    // =========================================================================
    
    /**
     * Loads grading system data from file and populates the table.
     * This method is called from the constructor to initialize the table with data.
     */
    private void loadGradingData() {
        try {
            // Call the static method from GradingSystem to load data from file
            List<GradingSystem> gradingList = GradingSystem.loadGradesFromFile();
            
            // Define column names for the table
            String[] columnNames = {"Grade", "Min Score", "Max Score", "Grade Point"};
            
            // Create a DefaultTableModel with the column names and 0 rows initially
            DefaultTableModel model = new DefaultTableModel(columnNames, 0) {
                @Override
                public boolean isCellEditable(int row, int column) {
                    // Make all cells non-editable
                    return false;
                }
            };
            
            // Loop through the List of GradingSystem objects and add each as a row
            for (GradingSystem grade : gradingList) {
                Object[] rowData = {
                    grade.getGradeLetter(),
                    grade.getMinScore(),
                    grade.getMaxScore(),
                    grade.getGradePoint()
                };
                model.addRow(rowData);
            }
            
            // Set the model to the tblGrades table
            tblGrades.setModel(model);
            
            // Optional: Adjust column widths for better visibility
            if (tblGrades.getColumnModel().getColumnCount() > 0) {
                tblGrades.getColumnModel().getColumn(0).setPreferredWidth(80);  // Grade
                tblGrades.getColumnModel().getColumn(1).setPreferredWidth(100); // Min Score
                tblGrades.getColumnModel().getColumn(2).setPreferredWidth(100); // Max Score
                tblGrades.getColumnModel().getColumn(3).setPreferredWidth(120); // Grade Point
            }
            
            // Log success
            logger.info("Successfully loaded " + gradingList.size() + " grading records into table.");
            
        } catch (Exception e) {
            // Log error
            logger.log(java.util.logging.Level.SEVERE, "Error loading grading data", e);
            
            // Show error message to user
            javax.swing.JOptionPane.showMessageDialog(this,
                "Error loading grading system data: " + e.getMessage(),
                "Load Error",
                javax.swing.JOptionPane.ERROR_MESSAGE);
        }
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ReflectiveOperationException | javax.swing.UnsupportedLookAndFeelException ex) {
            logger.log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(() -> new GradingSystemFrame().setVisible(true));
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable tblGrades;
    // End of variables declaration//GEN-END:variables
}