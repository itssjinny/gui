/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package user;

import config.Session;
import config.dbConnector;
import config.passwordHasher;
import java.security.NoSuchAlgorithmException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.Date;
import javax.swing.JButton;
import javax.swing.JOptionPane;
import testappnew.loginF;

/**
 *
 * @author milan
 */
public class ForgetPassword extends javax.swing.JFrame {
  private String correctAnswer;
    /**
     * Creates new form ForgetPassword
     */
    public ForgetPassword() {
        initComponents();
        
        
    }
    


   private void fetchSecurityQuestion() {
    String username = un.getText();  
    if (username.isEmpty()) {
        JOptionPane.showMessageDialog(this, "Please enter your username.");
        return;
    }

    // Create a database connection
    dbConnector db = new dbConnector();  // Instantiate dbConnector
    Connection con = db.getConnection(); // Get connection

    if (con == null) {
        JOptionPane.showMessageDialog(this, "Database connection failed. Please try again later.");
        return;
    }

    try {
        PreparedStatement stmt = con.prepareStatement(
            "SELECT security_question, security_answer FROM tbl_users WHERE u_username = ?"
        );
        stmt.setString(1, username);
        ResultSet rs = stmt.executeQuery();

        if (rs.next()) {
            sq.removeAllItems();
            sq.addItem(rs.getString("security_question"));
            sq.setEnabled(true);
            correctAnswer = rs.getString("security_answer");
            Submit.setEnabled(true);
        } else {
            JOptionPane.showMessageDialog(this, "Username not found.");
        }

    } catch (SQLException e) {
        e.printStackTrace();
        JOptionPane.showMessageDialog(this, "An error occurred while fetching the security question.");
    } finally {
        try {
            if (con != null) {
                con.close(); // Close the connection after use
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
   
public void logEvent(int userId, String username, String description) {
    dbConnector dbc = new dbConnector();
    Connection con = dbc.getConnection();
    PreparedStatement pstmt = null;

    try {
        // Fixed: include `log_description` in your INSERT
        String sql = "INSERT INTO tbl_log (u_id, u_username, login_time, u_type, log_status, log_description) VALUES (?, ?, ?, ?, ?, ?)";
        pstmt = con.prepareStatement(sql);

        pstmt.setInt(1, userId);
        pstmt.setString(2, username);
        pstmt.setTimestamp(3, new Timestamp(new Date().getTime())); // login_time
        pstmt.setString(4, "Success - User Action"); // u_type (general category)
        pstmt.setString(5, "Active"); // log_status
        pstmt.setString(6, description); // log_description (e.g., "User Reset Their Password")

        pstmt.executeUpdate();
        System.out.println("Log event recorded successfully.");
    } catch (SQLException e) {
        System.out.println("Error recording log: " + e.getMessage());
    } finally {
        try {
            if (pstmt != null) pstmt.close();
            if (con != null) con.close();
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error closing resources: " + e.getMessage());
        }
    }
}

   


   private void resetPassword() {
    String enteredAnswer = ans.getText();
    String newPassword = new String(Newpass.getPassword());

    // Declare variables here to fix your error
    int userId = -1;
    String uname2 = "";

    if (correctAnswer == null) {
        JOptionPane.showMessageDialog(this, "Please search for your username first.");
        return;
    }

    try {
        if (!passwordHasher.hashPassword(enteredAnswer).equals(correctAnswer)) {
            JOptionPane.showMessageDialog(this, "Incorrect security answer.");
            return;
        }
    } catch (NoSuchAlgorithmException ex) {
        JOptionPane.showMessageDialog(this, "Error verifying answer: " + ex.getMessage());
        return;
    }

    try {
        // Hash the new password before storing it
        String hashedPassword = passwordHasher.hashPassword(newPassword);

        // Instantiate the database connection
        dbConnector db = new dbConnector();
        Connection con = db.getConnection();
        dbConnector connector = new dbConnector(); // For logging query
        Session sess = Session.getInstance();

        if (con == null) {
            JOptionPane.showMessageDialog(this, "Database connection failed. Please try again later.");
            return;
        }

        try {
            // Update password in the database
            PreparedStatement stmt = con.prepareStatement(
                "UPDATE tbl_users SET u_password = ? WHERE u_username = ?"
            );
            stmt.setString(1, hashedPassword);
            stmt.setString(2, un.getText());

            int rowsUpdated = stmt.executeUpdate();
            if (rowsUpdated > 0) {
                JOptionPane.showMessageDialog(this, "Password successfully reset!");

                // Try to log the password reset
                try {
                    String query2 = "SELECT * FROM tbl_users WHERE u_username = ?";
                    PreparedStatement pstmt = connector.getConnection().prepareStatement(query2);
                    pstmt.setString(1, un.getText());

                    ResultSet resultSet = pstmt.executeQuery();
                    if (resultSet.next()) {
                        userId = resultSet.getInt("u_id");
                        uname2 = resultSet.getString("u_username");

                        // Log the event
                   logEvent(userId, uname2, "User Reset Their Password");

                    }
                } catch (SQLException ex) {
                    System.out.println("Log Error (SQL): " + ex.getMessage());
                }

                dispose(); // Close the window
            } else {
                JOptionPane.showMessageDialog(this, "Error: Username not found or password update failed.");
            }

        } finally {
            con.close();
        }

    } catch (NoSuchAlgorithmException ex) {
        JOptionPane.showMessageDialog(this, "Error hashing password: " + ex.getMessage());
    } catch (SQLException e) {
        JOptionPane.showMessageDialog(this, "An error occurred while updating the password.");
    }
}

    
    
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jPanel2 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        un = new javax.swing.JTextField();
        sq = new javax.swing.JComboBox<>();
        ans = new javax.swing.JTextField();
        jLabel2 = new javax.swing.JLabel();
        Newpass = new javax.swing.JPasswordField();
        Submit = new javax.swing.JButton();
        Search = new javax.swing.JButton();
        jButton1 = new javax.swing.JButton();
        jLabel3 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        getContentPane().setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jPanel1.setBackground(new java.awt.Color(255, 255, 255));
        jPanel1.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jPanel2.setBackground(new java.awt.Color(153, 153, 153));
        jPanel2.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel1.setFont(new java.awt.Font("Yu Gothic", 1, 18)); // NOI18N
        jLabel1.setText("Username");
        jPanel2.add(jLabel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 20, 90, -1));
        jPanel2.add(un, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 50, 180, 40));

        sq.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "What's the name of your first pet?", "What's the lastname of your Mother?", "What's your favorite food?", "What's your favorite Color? What's your birth month?" }));
        jPanel2.add(sq, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 120, 230, -1));
        jPanel2.add(ans, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 150, 180, 40));

        jLabel2.setFont(new java.awt.Font("Yu Gothic", 1, 18)); // NOI18N
        jLabel2.setText("New Password");
        jPanel2.add(jLabel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 200, -1, -1));
        jPanel2.add(Newpass, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 230, 190, 40));

        Submit.setText("Submit");
        Submit.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                SubmitMouseClicked(evt);
            }
        });
        jPanel2.add(Submit, new org.netbeans.lib.awtextra.AbsoluteConstraints(390, 270, -1, -1));

        Search.setText("Search");
        Search.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                SearchMouseClicked(evt);
            }
        });
        jPanel2.add(Search, new org.netbeans.lib.awtextra.AbsoluteConstraints(210, 50, 80, 40));

        jButton1.setText("Cancel");
        jButton1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jButton1MouseClicked(evt);
            }
        });
        jPanel2.add(jButton1, new org.netbeans.lib.awtextra.AbsoluteConstraints(280, 270, -1, -1));

        jPanel1.add(jPanel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(100, 90, 540, 340));

        jLabel3.setFont(new java.awt.Font("Yu Gothic", 3, 36)); // NOI18N
        jLabel3.setText("FORGOT PASSWORD");
        jPanel1.add(jLabel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(190, 20, 390, -1));

        getContentPane().add(jPanel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 740, 490));

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void SubmitMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_SubmitMouseClicked
    
      resetPassword();  
    }//GEN-LAST:event_SubmitMouseClicked

    private void SearchMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_SearchMouseClicked
  String username = un.getText();  
    if (username.isEmpty()) {
        JOptionPane.showMessageDialog(this, "Please enter your username.");
        return;
    }

    // Create a database connection
    dbConnector db = new dbConnector();  // Instantiate dbConnector
    Connection con = db.getConnection(); // Get connection

    if (con == null) {
        JOptionPane.showMessageDialog(this, "Database connection failed. Please try again later.");
        return;
    }

    try {
        PreparedStatement stmt = con.prepareStatement(
            "SELECT security_question, security_answer FROM tbl_users WHERE u_username = ?"
        );
        stmt.setString(1, username);
        ResultSet rs = stmt.executeQuery();

        if (rs.next()) {
            sq.removeAllItems();
            sq.addItem(rs.getString("security_question"));
            sq.setEnabled(true);
          correctAnswer = rs.getString("security_answer"); // still okay if hashed

            Submit.setEnabled(true);
        } else {
            JOptionPane.showMessageDialog(this, "Username not found.");
        }

    } catch (SQLException e) {
        e.printStackTrace();
        JOptionPane.showMessageDialog(this, "An error occurred while fetching the security question.");
    } finally {
        try {
            if (con != null) {
                con.close(); // Close the connection after use
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }    // TODO add your handling code here:
    }//GEN-LAST:event_SearchMouseClicked

    private void jButton1MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jButton1MouseClicked
     new loginF().setVisible(true); // Open loginF
    dispose();         // TODO add your handling code here:
    }//GEN-LAST:event_jButton1MouseClicked

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
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(ForgetPassword.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(ForgetPassword.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(ForgetPassword.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(ForgetPassword.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        java.awt.EventQueue.invokeLater(() -> new ForgetPassword().setVisible(true));

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new ForgetPassword().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPasswordField Newpass;
    private javax.swing.JButton Search;
    private javax.swing.JButton Submit;
    private javax.swing.JTextField ans;
    private javax.swing.JButton jButton1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JComboBox<String> sq;
    private javax.swing.JTextField un;
    // End of variables declaration//GEN-END:variables
}
