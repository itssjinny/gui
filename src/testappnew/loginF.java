/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package testappnew;

import admin.Admindashboard;
import static com.sun.jmx.remote.internal.IIOPHelper.connect;
import config.Session;


import config.dbConnector;
import config.passwordHasher;
import java.security.NoSuchAlgorithmException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.Date;
import static javax.management.remote.JMXConnectorFactory.connect;
import javax.swing.JOptionPane;
import javax.swing.border.EmptyBorder;
import register.registF1;
import user.ForgetPassword;
import user.UserDashboard;


/**
 *
 * @author User
 */
public class loginF extends javax.swing.JFrame {

    /**
     * Creates new form loginF
     */
    public loginF() {
        initComponents();
        us.setBorder(new EmptyBorder(0,10,0,0));
        pw.setBorder(new EmptyBorder(0,10,0,0));
    }
    
     static String status;
    static String type;
    
  public static boolean loginAcc(String username, String password) {
    dbConnector conn = new dbConnector(); // Create the dbConnector instance

    String query = "SELECT * FROM tbl_users WHERE u_username = ?";

    try (Connection connection = conn.getConnection(); // Use conn to get the connection
         PreparedStatement stmt = connection.prepareStatement(query)) {

        stmt.setString(1, username);
        ResultSet resultSet = stmt.executeQuery();

        if (resultSet.next()) {
            String hashedPass = resultSet.getString("u_password");
            String rehashedPass = passwordHasher.hashPassword(password);

            if (hashedPass.equals(rehashedPass)) {
                status = resultSet.getString("u_status");
                type = resultSet.getString("u_type");

                // Set session details
                Session sess = Session.getInstance();
                sess.setUid(resultSet.getInt("u_id"));
                sess.setFname(resultSet.getString("u_fname"));
                sess.setLname(resultSet.getString("u_lname"));
                sess.setEmail(resultSet.getString("u_email"));
                sess.setUsername(resultSet.getString("u_username"));
                sess.setType(resultSet.getString("u_type"));
                sess.setStatus(resultSet.getString("u_status"));

                return true;
            }
        }
    } catch (SQLException | NoSuchAlgorithmException ex) {
        // Log the error for debugging
    }
    return false;
}

    
      public String getUserId(String username) {
       
        dbConnector dbc = new dbConnector();
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        String userId = null;

        try {
         
            String sql = "SELECT u_id FROM tbl_users WHERE u_username = ?";
            pstmt = dbc.connect.prepareStatement(sql);
            pstmt.setString(1, username);
            rs = pstmt.executeQuery();

            if (rs.next()) {
                userId = rs.getString("u_id");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            if (rs != null) {
                try {
                    rs.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
            if (pstmt != null) {
                try {
                    pstmt.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
           
       
        }
        return userId;
    }
    public void logEvent(int userId, String username, String userType) {
    dbConnector dbc = new dbConnector();
    Connection con = dbc.getConnection();
    PreparedStatement pstmt = null;
    String ut = null;

    try {
        // Assuming there's no 'log_time' column, remove it from the query
        String sql = "INSERT INTO tbl_log (u_id, u_username, login_time, u_type, log_status) VALUES (?, ?, ?, ?, ?)";
        pstmt = con.prepareStatement(sql);

        pstmt.setInt(1, userId);
        pstmt.setString(2, username);
        pstmt.setTimestamp(3, new Timestamp(new Date().getTime())); // Make sure 'login_time' column exists
        pstmt.setString(4, userType);
        ut = "Active";
        pstmt.setString(5, ut);

        pstmt.executeUpdate();
        System.out.println("Login log recorded successfully.");
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

     public String getUserTypeFromDatabase(String username) {
    String type = "";
    String query = "SELECT u_type FROM tbl_users WHERE LOWER(u_username) = LOWER(?)";
    
    // Use an instance of dbConnector to get the connection
    dbConnector connector = new dbConnector();  // Create instance of dbConnector
    try (Connection con = connector.getConnection(); 
         PreparedStatement stmt = con.prepareStatement(query)) {
        stmt.setString(1, username);
        ResultSet rs = stmt.executeQuery();
        if (rs.next()) {
            type = rs.getString("u_type");
        }
    } catch (SQLException e) {
        e.printStackTrace();
    }
    return type;
}
      public String getStatusFromDatabase(String username) {
    String status = "";
    String query = "SELECT log_status FROM tbl_log WHERE LOWER(u_username) = LOWER(?) ORDER BY login_time DESC LIMIT 1";
    
    // Use an instance of dbConnector to get the connection
    dbConnector connector = new dbConnector();  // Create instance of dbConnector
    try (Connection con = connector.getConnection(); 
         PreparedStatement stmt = con.prepareStatement(query)) {
        stmt.setString(1, username);
        ResultSet rs = stmt.executeQuery();
        if (rs.next()) {
            status = rs.getString("log_status");
            System.out.println("status: "+status);
        }
    } catch (SQLException e) {
        e.printStackTrace();
    }
    return status;
}
     


    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jSpinner1 = new javax.swing.JSpinner();
        jPanel1 = new javax.swing.JPanel();
        jPanel4 = new javax.swing.JPanel();
        jButton2 = new javax.swing.JButton();
        jButton1 = new javax.swing.JButton();
        jPanel6 = new javax.swing.JPanel();
        jPanel5 = new javax.swing.JPanel();
        check = new javax.swing.JCheckBox();
        jPanel3 = new javax.swing.JPanel();
        jLabel3 = new javax.swing.JLabel();
        pw = new javax.swing.JPasswordField();
        jPanel2 = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();
        us = new javax.swing.JTextField();
        jLabel13 = new javax.swing.JLabel();
        MRmoving = new javax.swing.JLabel();
        jPanel7 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowOpened(java.awt.event.WindowEvent evt) {
                formWindowOpened(evt);
            }
        });

        jPanel1.setBackground(new java.awt.Color(0, 51, 255));
        jPanel1.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jPanel4.setBackground(new java.awt.Color(51, 255, 51));
        jPanel4.setLayout(null);

        jButton2.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jButton2.setText("REGISTER");
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });
        jPanel4.add(jButton2);
        jButton2.setBounds(0, 0, 100, 20);

        jButton1.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jButton1.setText("LOG-IN");
        jButton1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jButton1MouseClicked(evt);
            }
        });
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });
        jPanel4.add(jButton1);
        jButton1.setBounds(100, 0, 80, 20);

        javax.swing.GroupLayout jPanel6Layout = new javax.swing.GroupLayout(jPanel6);
        jPanel6.setLayout(jPanel6Layout);
        jPanel6Layout.setHorizontalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 130, Short.MAX_VALUE)
        );
        jPanel6Layout.setVerticalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 20, Short.MAX_VALUE)
        );

        jPanel4.add(jPanel6);
        jPanel6.setBounds(40, -40, 130, 20);

        jPanel1.add(jPanel4, new org.netbeans.lib.awtextra.AbsoluteConstraints(280, 290, 180, 20));

        jPanel5.setBackground(new java.awt.Color(255, 204, 204));
        jPanel5.setLayout(null);

        check.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        check.setText("Show");
        check.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                checkActionPerformed(evt);
            }
        });
        jPanel5.add(check);
        check.setBounds(0, 0, 70, 30);

        jPanel1.add(jPanel5, new org.netbeans.lib.awtextra.AbsoluteConstraints(460, 210, 70, 30));

        jPanel3.setBackground(new java.awt.Color(204, 255, 255));
        jPanel3.setLayout(null);

        jLabel3.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel3.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel3.setText("Password");
        jPanel3.add(jLabel3);
        jLabel3.setBounds(10, 0, 59, 30);

        pw.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        pw.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                pwActionPerformed(evt);
            }
        });
        jPanel3.add(pw);
        pw.setBounds(90, 0, 150, 30);

        jPanel1.add(jPanel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(210, 210, 250, 30));

        jPanel2.setBackground(new java.awt.Color(204, 255, 255));
        jPanel2.setLayout(null);

        jLabel2.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel2.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel2.setText("Username");
        jPanel2.add(jLabel2);
        jLabel2.setBounds(10, 0, 59, 30);

        us.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        jPanel2.add(us);
        us.setBounds(90, 0, 150, 30);

        jPanel1.add(jPanel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(210, 160, 250, 30));

        jLabel13.setForeground(new java.awt.Color(255, 255, 255));
        jLabel13.setIcon(new javax.swing.ImageIcon(getClass().getResource("/register/YU.png"))); // NOI18N
        jPanel1.add(jLabel13, new org.netbeans.lib.awtextra.AbsoluteConstraints(580, 10, -1, 90));

        MRmoving.setFont(new java.awt.Font("Times New Roman", 1, 24)); // NOI18N
        MRmoving.setForeground(new java.awt.Color(51, 51, 51));
        MRmoving.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        MRmoving.setText("LOAN APP PH");
        jPanel1.add(MRmoving, new org.netbeans.lib.awtextra.AbsoluteConstraints(310, 60, -1, -1));

        jPanel7.setBackground(new java.awt.Color(255, 255, 255));
        jPanel7.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jPanel7MouseClicked(evt);
            }
        });

        jLabel1.setText("Forgot Password?");

        javax.swing.GroupLayout jPanel7Layout = new javax.swing.GroupLayout(jPanel7);
        jPanel7.setLayout(jPanel7Layout);
        jPanel7Layout.setHorizontalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel7Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel1)
                .addContainerGap(16, Short.MAX_VALUE))
        );
        jPanel7Layout.setVerticalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel7Layout.createSequentialGroup()
                .addGap(0, 0, Short.MAX_VALUE)
                .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        jPanel1.add(jPanel7, new org.netbeans.lib.awtextra.AbsoluteConstraints(310, 250, 130, 20));

        jLabel5.setIcon(new javax.swing.ImageIcon("C:\\Users\\Gwapo ko\\Downloads\\kupal1.png")); // NOI18N
        jLabel5.setText("jLabel5");
        jPanel1.add(jLabel5, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 680, 430));

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, 425, javax.swing.GroupLayout.PREFERRED_SIZE)
        );

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    
    
    private void formWindowOpened(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowOpened
        
       
    }//GEN-LAST:event_formWindowOpened

    private void pwActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_pwActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_pwActionPerformed

    private void checkActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_checkActionPerformed
        boolean isSelected = check.isSelected();

        if (isSelected) {
            pw.setEchoChar((char)0);
        } else {
            pw.setEchoChar('*');
        }
    }//GEN-LAST:event_checkActionPerformed

    private void jButton1MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jButton1MouseClicked
    String username = us.getText();
String password = pw.getText();
        dbConnector connector = new dbConnector();
        String status2 = null;

if (loginAcc(username, password)) {
    Session sess = Session.getInstance();
    int userId = sess.getUid();

    // Retrieve the status and type after the login
    String status = getStatusFromDatabase(username);  // Method to get the status (Active/Inactive)
    String type = getUserTypeFromDatabase(username);  // Method to get the user type (Admin/User)
    
    
    
    try {
        String query2 = "SELECT * FROM tbl_users WHERE u_username = '" + us.getText() + "'";
        PreparedStatement pstmt = connector.getConnection().prepareStatement(query2);

        ResultSet resultSet = pstmt.executeQuery();

        if (resultSet.next()) {
            status2 = resultSet.getString("u_status");
        }
    } catch (SQLException ex) {
        System.out.println("SQL Exception: " + ex);
    }
    
    

    if (!status2.equals("Active")) {
        JOptionPane.showMessageDialog(null, "In-Active Account, Contact the Admin!");
       logEvent(userId, username, "Failed - Inactive Account");
    } else {
        if (type.equals("Admin")) {
            JOptionPane.showMessageDialog(null, "Login Success! Welcome Admin.");
            new Admindashboard().setVisible(true);
            logEvent(userId, username, "Success - Admin Login");
        } else if (type.equals("User")) {
            JOptionPane.showMessageDialog(null, "Login Success! Welcome User.");
            new UserDashboard().setVisible(true);
            logEvent(userId, username, "Success - User Login");
            
        }
        this.dispose();
    }
} else {
    JOptionPane.showMessageDialog(null, "Invalid Username or Password!");
    logEvent(-1, username, "Failed - Invalid Login");
}


    }//GEN-LAST:event_jButton1MouseClicked

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
        registF1 ads = new registF1();
        ads.setVisible(true);
        this.dispose();
    }//GEN-LAST:event_jButton2ActionPerformed

    private void jPanel7MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jPanel7MouseClicked
          new ForgetPassword().setVisible(true); // Open ForgotPassword form
          dispose();
    }//GEN-LAST:event_jPanel7MouseClicked

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jButton1ActionPerformed

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
            java.util.logging.Logger.getLogger(loginF.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(loginF.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(loginF.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(loginF.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new loginF().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel MRmoving;
    private javax.swing.JCheckBox check;
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JPanel jPanel7;
    private javax.swing.JSpinner jSpinner1;
    private javax.swing.JPasswordField pw;
    private javax.swing.JTextField us;
    // End of variables declaration//GEN-END:variables
}
