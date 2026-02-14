/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package user;



import static admin.CreateUsersF.username;
import admin.Admindashboard;
import config.Session;
import config.dbConnector;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.Date;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.filechooser.FileNameExtensionFilter;
import testappnew.loginF;


/**
 *
 * @author User
 */
public class accountD extends javax.swing.JFrame {

    /**
     * Creates new form Admindashboard
     */
    public accountD() {
        initComponents();
        
    }
    
    //public String destination = "";
   //// File selectedFile;
   //public String oldpath;
   // public String path;
   
       public boolean updateCheck(){
        
     dbConnector dbc = new dbConnector();
    Session sess = Session.getInstance();

    try {
        String query = "SELECT * FROM tbl_users WHERE (u_username = '" + acc_email.getText() 
                     + "' OR u_email = '" + acc_uname.getText() 
                     + "') AND u_id != '" + sess.getUid() + "'";

        ResultSet resultSet = dbc.getData(query);

        if (resultSet.next()) {
            String email = resultSet.getString("u_email"); 
            if (email.equals(acc_uname.getText())) { 
                JOptionPane.showMessageDialog(null, "Email is Already Used!");
                acc_uname.setText(""); 
            }

            String username = resultSet.getString("u_username");
            if (username.equals(acc_email.getText())) { 
                JOptionPane.showMessageDialog(null, "Username is Already Used!");
                acc_email.setText(""); 
            }

            return true;
        } else {
            return false;
        }
    } catch (SQLException ex) {
        System.out.println("" + ex);
        return false;
    }
        
    }
       
     private void uploadImage() {
    JFileChooser fileChooser = new JFileChooser();
    fileChooser.setFileFilter(new FileNameExtensionFilter("Image Files", "jpg", "jpeg", "png"));

    int result = fileChooser.showOpenDialog(this);
    if (result == JFileChooser.APPROVE_OPTION) {
        File selectedFile = fileChooser.getSelectedFile();
        
        // Define destination path inside src/images/
        File destinationFolder = new File("src/images/");
        if (!destinationFolder.exists()) {
            destinationFolder.mkdirs(); // Create folder if it does not exist
        }

        File destinationFile = new File(destinationFolder, selectedFile.getName());

        try {
            // Copy file to the destination folder
            Files.copy(selectedFile.toPath(), destinationFile.toPath(), StandardCopyOption.REPLACE_EXISTING);
            
            // Store the image path
            selectedImagePath = "src/images/" + selectedFile.getName();

            // Update the image label
            image.setIcon(new javax.swing.ImageIcon(destinationFile.getAbsolutePath()));

            JOptionPane.showMessageDialog(this, "Image uploaded successfully!");
        } catch (IOException e) {
            JOptionPane.showMessageDialog(this, "Error uploading image: " + e.getMessage());
        }
    }
}
   
     private String selectedImagePath = "";
      



    
        public void logEvent(int userId, String username, String userType, String logDescription) {
    dbConnector dbc = new dbConnector();
    Connection con = dbc.getConnection();
    PreparedStatement pstmt = null;

    try {
        String sql = "INSERT INTO tbl_log (u_id, u_username, login_time, u_type, log_status, log_description) VALUES (?, ?, ?, ?, ?, ?)";
        pstmt = con.prepareStatement(sql);

        pstmt.setInt(1, userId);
        pstmt.setString(2, username);
        pstmt.setTimestamp(3, new Timestamp(new Date().getTime()));
        pstmt.setString(4, userType); // This should be "Admin" or "User"
        pstmt.setString(5, "Active");
        pstmt.setString(6, logDescription); // Insert the log description

        pstmt.executeUpdate();
        System.out.println("Log recorded successfully.");
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
       
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel2 = new javax.swing.JPanel();
        jLabel9 = new javax.swing.JLabel();
        idd = new javax.swing.JLabel();
        jPanel8 = new javax.swing.JPanel();
        jPanel7 = new javax.swing.JPanel();
        jPanel3 = new javax.swing.JPanel();
        acc_fname = new javax.swing.JTextField();
        jLabel3 = new javax.swing.JLabel();
        jPanel4 = new javax.swing.JPanel();
        acc_lname = new javax.swing.JTextField();
        jLabel6 = new javax.swing.JLabel();
        jPanel6 = new javax.swing.JPanel();
        jLabel5 = new javax.swing.JLabel();
        acc_uname = new javax.swing.JTextField();
        jPanel5 = new javax.swing.JPanel();
        jLabel4 = new javax.swing.JLabel();
        acc_email = new javax.swing.JTextField();
        cancel = new javax.swing.JButton();
        update = new javax.swing.JButton();
        jPanel9 = new javax.swing.JPanel();
        image = new javax.swing.JLabel();
        acc_id = new javax.swing.JLabel();
        acc_type = new javax.swing.JLabel();
        jButton2 = new javax.swing.JButton();
        jButton1 = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setPreferredSize(new java.awt.Dimension(650, 450));
        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowActivated(java.awt.event.WindowEvent evt) {
                formWindowActivated(evt);
            }
        });
        getContentPane().setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jPanel2.setBackground(new java.awt.Color(102, 102, 102));
        jPanel2.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel9.setFont(new java.awt.Font("Century Gothic", 3, 24)); // NOI18N
        jLabel9.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel9.setText("ACCOUNT INFROMATION");
        jPanel2.add(jLabel9, new org.netbeans.lib.awtextra.AbsoluteConstraints(210, 20, 330, 34));

        idd.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        idd.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        idd.setText("(UID)");
        jPanel2.add(idd, new org.netbeans.lib.awtextra.AbsoluteConstraints(540, 0, 90, 80));

        getContentPane().add(jPanel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 760, 80));

        jPanel8.setBackground(new java.awt.Color(255, 255, 255));
        jPanel8.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jPanel7.setBackground(new java.awt.Color(102, 102, 102));
        jPanel7.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jPanel3.setBackground(new java.awt.Color(51, 255, 153));
        jPanel3.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());
        jPanel3.add(acc_fname, new org.netbeans.lib.awtextra.AbsoluteConstraints(70, 0, 190, 30));

        jLabel3.setFont(new java.awt.Font("Tahoma", 1, 13)); // NOI18N
        jLabel3.setText("First Name");
        jPanel3.add(jLabel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 70, 30));

        jPanel7.add(jPanel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 50, 270, 30));

        jPanel4.setBackground(new java.awt.Color(51, 255, 153));
        jPanel4.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());
        jPanel4.add(acc_lname, new org.netbeans.lib.awtextra.AbsoluteConstraints(70, 0, 190, 30));

        jLabel6.setFont(new java.awt.Font("Tahoma", 1, 13)); // NOI18N
        jLabel6.setText("Last Name");
        jPanel4.add(jLabel6, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 70, 30));

        jPanel7.add(jPanel4, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 90, 270, -1));

        jPanel6.setBackground(new java.awt.Color(51, 255, 153));
        jPanel6.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel5.setFont(new java.awt.Font("Tahoma", 1, 13)); // NOI18N
        jLabel5.setText("Username");
        jPanel6.add(jLabel5, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 70, 30));
        jPanel6.add(acc_uname, new org.netbeans.lib.awtextra.AbsoluteConstraints(70, 0, 190, 30));

        jPanel7.add(jPanel6, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 130, 270, -1));

        jPanel5.setBackground(new java.awt.Color(51, 255, 153));
        jPanel5.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel4.setFont(new java.awt.Font("Tahoma", 1, 13)); // NOI18N
        jLabel4.setText("Email");
        jPanel5.add(jLabel4, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 70, 30));
        jPanel5.add(acc_email, new org.netbeans.lib.awtextra.AbsoluteConstraints(70, 0, 190, 30));

        jPanel7.add(jPanel5, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 170, 270, -1));

        cancel.setBackground(new java.awt.Color(255, 255, 255));
        cancel.setFont(new java.awt.Font("Yu Gothic UI", 1, 12)); // NOI18N
        cancel.setForeground(new java.awt.Color(27, 57, 77));
        cancel.setText("Cancel");
        cancel.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                cancelMouseClicked(evt);
            }
        });
        cancel.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cancelActionPerformed(evt);
            }
        });
        jPanel7.add(cancel, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 250, 100, 30));

        update.setBackground(new java.awt.Color(27, 55, 77));
        update.setFont(new java.awt.Font("Yu Gothic UI", 1, 12)); // NOI18N
        update.setForeground(new java.awt.Color(255, 255, 255));
        update.setText("Save ");
        update.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                updateMouseClicked(evt);
            }
        });
        update.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                updateActionPerformed(evt);
            }
        });
        jPanel7.add(update, new org.netbeans.lib.awtextra.AbsoluteConstraints(400, 260, 90, 30));

        jPanel9.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jPanel9MouseClicked(evt);
            }
        });
        jPanel9.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        image.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        image.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/add.png"))); // NOI18N
        image.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                imageMouseClicked(evt);
            }
        });
        jPanel9.add(image, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 30, 190, 120));

        jPanel7.add(jPanel9, new org.netbeans.lib.awtextra.AbsoluteConstraints(320, 40, 210, 160));

        acc_id.setFont(new java.awt.Font("Arial", 1, 18)); // NOI18N
        acc_id.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        acc_id.setText("ID");
        jPanel7.add(acc_id, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 10, 110, 30));

        acc_type.setFont(new java.awt.Font("Arial", 1, 18)); // NOI18N
        acc_type.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        acc_type.setText("Type");
        jPanel7.add(acc_type, new org.netbeans.lib.awtextra.AbsoluteConstraints(140, 10, 140, 30));

        jButton2.setFont(new java.awt.Font("Century Gothic", 1, 14)); // NOI18N
        jButton2.setText("Change Pass");
        jButton2.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jButton2MouseClicked(evt);
            }
        });
        jPanel7.add(jButton2, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 310, 180, 50));

        jButton1.setText("Select");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });
        jPanel7.add(jButton1, new org.netbeans.lib.awtextra.AbsoluteConstraints(390, 210, -1, -1));

        jPanel8.add(jPanel7, new org.netbeans.lib.awtextra.AbsoluteConstraints(80, 100, 610, 390));

        getContentPane().add(jPanel8, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 760, 520));

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void formWindowActivated(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowActivated
        Session sess = Session.getInstance();
        if (sess.getUid() == 0) {

            loginF l = new loginF();
            l.setVisible(true);
            this.dispose();
            JOptionPane.showMessageDialog(null, "No Account, Login FIrst");
        } else {
            
            
            acc_fname.setText("" + sess.getFname());
            acc_lname.setText("" + sess.getLname());
            acc_email.setText("" + sess.getEmail());
            acc_uname.setText("" + sess.getUsername());
            acc_type.setText("" + sess.getType());
            acc_id.setText("" + sess.getUid());
         

       
    }//GEN-LAST:event_formWindowActivated
    }
    private void cancelMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_cancelMouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_cancelMouseClicked

    private void cancelActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cancelActionPerformed

        UserDashboard ru = new UserDashboard();
        ru.setVisible(true);
        this.dispose();
    }//GEN-LAST:event_cancelActionPerformed
    
    private void updateMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_updateMouseClicked
  
        
       
    }//GEN-LAST:event_updateMouseClicked

    private void updateActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_updateActionPerformed
  dbConnector dbc = new dbConnector();
Session sess = Session.getInstance();
dbConnector connector = new dbConnector();
int userId = 0;
String uname2 = null;

// Check if username or email already exists
if (updateCheck()) {
    return;
}

// Validate inputs
if (acc_fname.getText().isEmpty() || acc_lname.getText().isEmpty() ||
    acc_email.getText().isEmpty() || acc_uname.getText().isEmpty()) {
    JOptionPane.showMessageDialog(this, "All fields are required!", "Error", JOptionPane.ERROR_MESSAGE);
    return;
}

// SQL Query to update the user profile
String query = "UPDATE tbl_users SET u_fname=?, u_lname=?, u_email=?, u_username=?, u_image=? WHERE u_id=?";

try (Connection conn = dbc.getConnection();
     PreparedStatement pstmt = conn.prepareStatement(query)) {

    pstmt.setString(1, acc_fname.getText());
    pstmt.setString(2, acc_lname.getText());
    pstmt.setString(3, acc_email.getText());
    pstmt.setString(4, acc_uname.getText());
    pstmt.setString(5, selectedImagePath);  // Store uploaded image path
    pstmt.setInt(6, sess.getUid());

    int rowsAffected = pstmt.executeUpdate();

    if (rowsAffected > 0) {
        JOptionPane.showMessageDialog(this, "Profile updated successfully!");

        try {
            String query2 = "SELECT * FROM tbl_users WHERE u_id = ?"; //use prepared statement.
            PreparedStatement pstmt2 = connector.getConnection().prepareStatement(query2);
            pstmt2.setInt(1, sess.getUid());

            ResultSet resultSet = pstmt2.executeQuery();

            if (resultSet.next()) {
                userId = resultSet.getInt("u_id");  // Update the outer `userId` correctly
                uname2 = resultSet.getString("u_username");
            }
        } catch (SQLException ex) {
            System.out.println("SQL Exception: " + ex);
        }

        logEvent(userId, uname2, sess.getType(), "User Changed Their Details"); //fixed
    } else {
        JOptionPane.showMessageDialog(this, "Failed to update profile!", "Error", JOptionPane.ERROR_MESSAGE);
    }

} catch (SQLException e) {
    JOptionPane.showMessageDialog(this, "Database error: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
}

    }//GEN-LAST:event_updateActionPerformed

    private void jButton2MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jButton2MouseClicked
         ChangeP cp = new ChangeP();
        cp.setVisible(true);
        this.dispose();        // TODO add your handling code here:
    }//GEN-LAST:event_jButton2MouseClicked

    private void jPanel9MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jPanel9MouseClicked
           // TODO add your handling code here:
    }//GEN-LAST:event_jPanel9MouseClicked

    private void imageMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_imageMouseClicked
      // TODO add your handling code here:
    }//GEN-LAST:event_imageMouseClicked

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
      JFileChooser fileChooser = new JFileChooser();
    fileChooser.setFileFilter(new FileNameExtensionFilter("Image Files", "jpg", "jpeg", "png"));

    int result = fileChooser.showOpenDialog(this);
    if (result == JFileChooser.APPROVE_OPTION) {
        File selectedFile = fileChooser.getSelectedFile();
        
        // Define destination path inside src/images/
        File destinationFolder = new File("src/images/");
        if (!destinationFolder.exists()) {
            destinationFolder.mkdirs(); // Create folder if it does not exist
        }

        File destinationFile = new File(destinationFolder, selectedFile.getName());

        try {
            // Copy file to the destination folder
            Files.copy(selectedFile.toPath(), destinationFile.toPath(), StandardCopyOption.REPLACE_EXISTING);
            
            // Store the image path
            selectedImagePath = "src/images/" + selectedFile.getName();

            // Update the image label
            image.setIcon(new javax.swing.ImageIcon(destinationFile.getAbsolutePath()));

            JOptionPane.showMessageDialog(this, "Image uploaded successfully!");
        } catch (IOException e) {
            JOptionPane.showMessageDialog(this, "Error uploading image: " + e.getMessage());
        }
    }    // TODO add your handling code here:
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
            java.util.logging.Logger.getLogger(accountD.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(accountD.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(accountD.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(accountD.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new accountD().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JTextField acc_email;
    private javax.swing.JTextField acc_fname;
    private javax.swing.JLabel acc_id;
    private javax.swing.JTextField acc_lname;
    private javax.swing.JLabel acc_type;
    private javax.swing.JTextField acc_uname;
    public javax.swing.JButton cancel;
    private javax.swing.JLabel idd;
    private javax.swing.JLabel image;
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JPanel jPanel7;
    private javax.swing.JPanel jPanel8;
    private javax.swing.JPanel jPanel9;
    public javax.swing.JButton update;
    // End of variables declaration//GEN-END:variables
}
