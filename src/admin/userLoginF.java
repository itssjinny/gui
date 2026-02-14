    /*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package admin;


import config.Session;
import config.dbConnector;
import java.awt.Color;
import java.awt.List;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import java.util.ArrayList;
import java.util.Arrays;
import javax.swing.JComboBox;
import javax.swing.JOptionPane;
import javax.swing.event.TableModelEvent;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;

import net.proteanit.sql.DbUtils;
import testappnew.loginF;



/**
 *
 * @author User
 */
public class userLoginF extends javax.swing.JFrame {

    /**
     * Creates new form Admindashboard
     */
    public userLoginF() {
        initComponents();
         displayData();
    }
    
    
    private JComboBox<String> type;
    
        Color navcolor = new Color(51,255,255);
        Color hovercolor = new Color(204,255,204);
    
     public void displayData(){
        try{
            dbConnector dbc = new dbConnector();
            ResultSet rs = dbc.getData("SELECT u_id, u_fname, u_lname, u_username, u_email, u_status FROM tbl_users");
            user_table.setModel(DbUtils.resultSetToTableModel(rs));
             rs.close();
        }catch(SQLException ex){
            System.out.println("Errors: "+ex.getMessage());
        
        }
        
    
    }
    
      
        DefaultTableModel model = new DefaultTableModel();
        
         public void tableChanged(TableModelEvent e) {
        if (e.getType() == TableModelEvent.UPDATE) {
            int row = e.getFirstRow();
            int column = e.getColumn();

            
            if (row == -1 || column == -1) {
                return; 
            }

            updateDatabase(row, column);
        }
         Session sess = Session.getInstance();
    
    String[] columnNames = {"u_id", "u_email", "u_type", "u_username","u_password", "u_status"};
    model.setColumnIdentifiers(columnNames); 
    model.setRowCount(0);

    String sql = "SELECT u_id, u_email, u_type, u_username, u_password, u_status FROM tbl_users WHERE u_id != '"+sess.getUid()+"';";

    try (Connection connect = new dbConnector().getConnection();
         PreparedStatement pst = connect.prepareStatement(sql);
         ResultSet rs = pst.executeQuery()) {

        while (rs.next()) {
            Object[] row = {
                rs.getInt("u_id"),
                rs.getString("email"),
                rs.getString("type"),
                rs.getString("username"),
                rs.getString("pass"),
                rs.getString("status")
            };
            model.addRow(row); 
        }

    } catch (SQLException ex) {
        JOptionPane.showMessageDialog(this, "Database Error: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
    }
    }
    
     private void updateDatabase(int row, int column) {
    try (Connection connect = (Connection) new dbConnector().getConnection()) {
        String columnName = user_table.getColumnName(column); 
        String newValue = user_table.getValueAt(row, column).toString(); 
        int userId = Integer.parseInt(user_table.getValueAt(row, 0).toString());

        String sql = "UPDATE tbl_users SET " + columnName + " = ? WHERE u_id = ?";
        try (PreparedStatement pst = connect.prepareStatement(sql)) {
            pst.setString(1, newValue);
            pst.setInt(2, userId);
            pst.executeUpdate();
            JOptionPane.showMessageDialog(null, "Database Updated Successfully!");
        }

    } catch (SQLException ex) {
        JOptionPane.showMessageDialog(null, "Database Error: " + ex.getMessage());
    }
}


      void addUser() {
   
     CreateUsersF createuserform = new CreateUsersF();
     createuserform.setVisible(true);
}
   public boolean addUser(String firstname, String lastname, String email, String type, String username, String password, String status) {
    Connection con = null;
    PreparedStatement pst = null;

    try {
        // Get database connection
        con = new dbConnector().getConnection();

        // SQL query to insert user data
        String query = "INSERT INTO tbl_users (u_fname, u_lname, u_username, u_email, u_password, u_status, u_type) VALUES (?, ?, ?, ?, ?, ?, ?)";
        pst = con.prepareStatement(query);

        // Set values correctly in PreparedStatement
        pst.setString(1, firstname);
        pst.setString(2, lastname);
        pst.setString(3, username);
        pst.setString(4, email); // Fixed order: email should be here
        pst.setString(5, password);
        pst.setString(6, status);
        pst.setString(7, type);  // Fixed order: type should be last

        // Execute update and check if insertion was successful
        int rowsInserted = pst.executeUpdate();
        return rowsInserted > 0;

    } catch (SQLException e) {
        e.printStackTrace();
        return false;
    } finally {
        // Close resources in finally block
        try {
            if (pst != null) pst.close();
            if (con != null) con.close();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }
}

      public boolean updateUser(String firstname, String lastname, String email, String type, String username, String password, String status) {
    try {
        
        Connection con = new dbConnector().getConnection();
        String query = "UPDATE tbl_users SET u_fname=?, u_lname=?, u_email=?, u_type=?, u_password=?, u_status=? WHERE u_username=?";
        PreparedStatement pst = con.prepareStatement(query);
       pst.setString(1, firstname);
       pst.setString(2, lastname);
       pst.setString(3, username);
       pst.setString(4, type);
       pst.setString(5, email);
       pst.setString(6, password);
       pst.setString(7, status);

        int rowsUpdated = pst.executeUpdate();
        return rowsUpdated > 0;
    } catch (SQLException e) {
        e.printStackTrace();
        return false;
    }
}

        private void loadUsersData() {
    DefaultTableModel model = (DefaultTableModel) user_table.getModel();
    model.setRowCount(0); // Clear the table before reloading

    String sql = "SELECT u_id, u_fname, u_lname, u_username, u_email, u_status FROM tbl_users";

    try (Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/loanapp", "root", "");
         PreparedStatement pst = con.prepareStatement(sql);
         ResultSet rs = pst.executeQuery()) {

        while (rs.next()) {
            model.addRow(new Object[]{
                rs.getInt("u_id"),
                rs.getString("u_fname"),
                rs.getString("u_lname"),
                rs.getString("u_username"),
                rs.getString("u_email"),
                rs.getString("u_status")
            });
        }
    } catch (SQLException ex) {
        JOptionPane.showMessageDialog(this, "Error loading user data: " + ex.getMessage(), "Database Error", JOptionPane.ERROR_MESSAGE);
    }
}

        private void deleteUser() {
           
            Session sess = Session.getInstance();  // Logged-in admin
    int selectedRow = user_table.getSelectedRow();
    if (selectedRow == -1) {
        JOptionPane.showMessageDialog(this, "Please select a user to delete.");
        return;
    }

    int userId = (int) user_table.getValueAt(selectedRow, 0);
    int confirm = JOptionPane.showConfirmDialog(this, "Are you sure you want to delete this user?", "Confirm Deletion", JOptionPane.YES_NO_OPTION);

    if (confirm == JOptionPane.YES_OPTION) {
        String sql = "DELETE FROM tbl_users WHERE u_id=?";

        try (Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/loanapp", "root", "");
             PreparedStatement pst = con.prepareStatement(sql)) {

            pst.setInt(1, userId);
            int affectedRows = pst.executeUpdate();

            if (affectedRows > 0) {
                JOptionPane.showMessageDialog(this, "User Deleted Successfully!");

                // ✅ Logging the deletion action
                String logQuery = "INSERT INTO tbl_log (u_id, u_username, u_type, log_status, log_description) VALUES (?, ?, ?, ?, ?)";
                try (PreparedStatement logPst = con.prepareStatement(logQuery)) {
                    logPst.setInt(1, sess.getUid()); // Admin ID from session
                    logPst.setString(2, sess.getUsername()); // Admin username
                    logPst.setString(3, sess.getType()); // Admin type, e.g., "Admin"
                    logPst.setString(4, "Active"); // Status of the log
                    logPst.setString(5, "Deleted user account with ID: " + userId); // Description
                    logPst.executeUpdate();
                } catch (SQLException logEx) {
                    JOptionPane.showMessageDialog(this, "Log Error: " + logEx.getMessage(), "Log Error", JOptionPane.ERROR_MESSAGE);
                }

                loadUsersData();  // Ensure this method exists to reload the table or data

            } else {
                JOptionPane.showMessageDialog(this, "No user found to delete.", "Deletion Failed", JOptionPane.WARNING_MESSAGE);
            }

        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(this, "Error: " + ex.getMessage(), "Database Error", JOptionPane.ERROR_MESSAGE);
        }
    }
}

         private String hashPassword(String password) {
    try {
        MessageDigest md = MessageDigest.getInstance("SHA-256");
        byte[] hash = md.digest(password.getBytes());
        StringBuilder hexString = new StringBuilder();
        for (byte b : hash) {
            hexString.append(String.format("%02x", b));
        }
        return hexString.toString();
    } catch (NoSuchAlgorithmException e) {
        throw new RuntimeException(e);
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

        jPanel5 = new javax.swing.JPanel();
        jPanel1 = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();
        jLabel15 = new javax.swing.JLabel();
        acc_id = new javax.swing.JLabel();
        acc_fname = new javax.swing.JLabel();
        acc_lname = new javax.swing.JLabel();
        jPanel2 = new javax.swing.JPanel();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        add = new javax.swing.JButton();
        update = new javax.swing.JButton();
        register1 = new javax.swing.JButton();
        jButton1 = new javax.swing.JButton();
        jPanel3 = new javax.swing.JPanel();
        jPanel4 = new javax.swing.JPanel();
        jPanel6 = new javax.swing.JPanel();
        jPanel7 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        user_table = new javax.swing.JTable();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowActivated(java.awt.event.WindowEvent evt) {
                formWindowActivated(evt);
            }
        });
        getContentPane().setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jPanel1.setBackground(new java.awt.Color(255, 255, 255));
        jPanel1.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/VVT.png"))); // NOI18N
        jPanel1.add(jLabel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 20, 60, 80));

        jLabel15.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel15.setText("              Current User");
        jLabel15.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        jPanel1.add(jLabel15, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 290, 140, 20));

        acc_id.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        acc_id.setText("ID");
        jPanel1.add(acc_id, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 320, 140, 20));

        acc_fname.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        acc_fname.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        acc_fname.setText("ACC_FNAME");
        jPanel1.add(acc_fname, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 100, 140, 20));

        acc_lname.setText("ACC_LNAME");
        jPanel1.add(acc_lname, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 120, -1, -1));

        getContentPane().add(jPanel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, -1, 400));

        jPanel2.setBackground(new java.awt.Color(153, 153, 153));
        jPanel2.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel4.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel4.setText("USERS");
        jPanel2.add(jLabel4, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 90, 50, 30));

        jLabel5.setFont(new java.awt.Font("Yu Gothic", 1, 36)); // NOI18N
        jLabel5.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel5.setText("x");
        jLabel5.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabel5MouseClicked(evt);
            }
        });
        jPanel2.add(jLabel5, new org.netbeans.lib.awtextra.AbsoluteConstraints(640, 20, 30, 20));

        add.setFont(new java.awt.Font("Arial", 1, 14)); // NOI18N
        add.setText("ADD");
        add.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                addActionPerformed(evt);
            }
        });
        jPanel2.add(add, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 20, 140, 40));

        update.setFont(new java.awt.Font("Arial", 1, 14)); // NOI18N
        update.setText("UPDATE");
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
        jPanel2.add(update, new org.netbeans.lib.awtextra.AbsoluteConstraints(160, 20, 140, 40));

        register1.setFont(new java.awt.Font("Arial", 1, 14)); // NOI18N
        register1.setText("DELETE");
        register1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                register1ActionPerformed(evt);
            }
        });
        jPanel2.add(register1, new org.netbeans.lib.awtextra.AbsoluteConstraints(310, 20, 130, 40));

        jButton1.setFont(new java.awt.Font("Arial", 1, 14)); // NOI18N
        jButton1.setText("REFRESH");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });
        jPanel2.add(jButton1, new org.netbeans.lib.awtextra.AbsoluteConstraints(450, 20, 130, 40));

        getContentPane().add(jPanel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(140, 0, 710, 70));

        jPanel3.setBackground(new java.awt.Color(255, 0, 0));
        getContentPane().add(jPanel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(140, 70, -1, 330));

        jPanel4.setBackground(new java.awt.Color(255, 0, 0));
        getContentPane().add(jPanel4, new org.netbeans.lib.awtextra.AbsoluteConstraints(150, 70, 700, -1));

        jPanel6.setBackground(new java.awt.Color(255, 0, 0));
        getContentPane().add(jPanel6, new org.netbeans.lib.awtextra.AbsoluteConstraints(150, 390, 700, -1));

        jPanel7.setBackground(new java.awt.Color(255, 0, 0));
        getContentPane().add(jPanel7, new org.netbeans.lib.awtextra.AbsoluteConstraints(840, 80, -1, 310));

        user_table.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {

            }
        ));
        jScrollPane1.setViewportView(user_table);

        getContentPane().add(jScrollPane1, new org.netbeans.lib.awtextra.AbsoluteConstraints(150, 80, 690, 310));

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void jLabel5MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel5MouseClicked
     Admindashboard ds = new Admindashboard();
     ds.setVisible(true);
     this.dispose();
    }//GEN-LAST:event_jLabel5MouseClicked

    private void formWindowActivated(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowActivated
          Session sess = Session.getInstance();
       if(sess.getUid() == 0)
       {
           JOptionPane.showMessageDialog(null,"No Account, Login FIrst");
           loginF c = new loginF();
           c.setVisible(true);
           this.dispose();
       }else
       {
           
          acc_fname.setText("" + sess.getFname());
          acc_lname.setText("" + sess.getLname());
           acc_id.setText("" + sess.getUid());
       }        // TODO add your handling code here:  
    }//GEN-LAST:event_formWindowActivated

    private void addActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_addActionPerformed

        addUser();
    }//GEN-LAST:event_addActionPerformed

    private void updateMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_updateMouseClicked
        int selectedRow = user_table.getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(this, "Please select a user to edit.", "Selection Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        String username = user_table.getValueAt(selectedRow, user_table.getColumn("u_username").getModelIndex()).toString();

        if (username == null || username.trim().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Selected user has no username.", "Selection Error", JOptionPane.ERROR_MESSAGE);
            return;

        }
    }//GEN-LAST:event_updateMouseClicked

    private void updateActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_updateActionPerformed
        int rowIndex = user_table.getSelectedRow();
        if (rowIndex < 0) {
            JOptionPane.showMessageDialog(null, "Please select a User!");
        } else {
            try {
                dbConnector dbc = new dbConnector();
                TableModel tbl = user_table.getModel();

                String userId = tbl.getValueAt(rowIndex, 0).toString();
                String query = "SELECT * FROM tbl_users WHERE u_id = ?";

                PreparedStatement pst = dbc.getConnection().prepareStatement(query);
                pst.setString(1, userId);
                ResultSet rs = pst.executeQuery();

                if (rs.next()) {
                    CreateUsersF cuf = new CreateUsersF();
                    cuf.uid.setText(userId);
                    cuf.fn.setText(rs.getString("u_fname"));  // Ensure u_fname is the correct column name
                    cuf.ln.setText(rs.getString("u_lname"));
                    cuf.stat.setSelectedItem(rs.getString("u_status"));  // Ensure 'status' is set first
                    cuf.ut.setSelectedItem(rs.getString("u_type"));      // And 'type' is set second
                    cuf.mail.setText(rs.getString("u_email"));
                    cuf.us.setText(rs.getString("u_username"));
                    cuf.pw.setText(rs.getString("u_password"));
                    cuf.pw.setEnabled(false);

                    cuf.setVisible(true);
                    this.dispose();
                }
            } catch (SQLException ex) {
                System.out.println("Error: " + ex.getMessage());
            }        // TODO add your handling code here:
    }//GEN-LAST:event_updateActionPerformed
    }
    private void register1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_register1ActionPerformed
        deleteUser();
    }//GEN-LAST:event_register1ActionPerformed

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        loadUsersData(); // TODO add your handling code here:
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
            java.util.logging.Logger.getLogger(userLoginF.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(userLoginF.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(userLoginF.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(userLoginF.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new userLoginF().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel acc_fname;
    private javax.swing.JLabel acc_id;
    private javax.swing.JLabel acc_lname;
    private javax.swing.JButton add;
    private javax.swing.JButton jButton1;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JPanel jPanel7;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JButton register1;
    private javax.swing.JButton update;
    private javax.swing.JTable user_table;
    // End of variables declaration//GEN-END:variables
}
