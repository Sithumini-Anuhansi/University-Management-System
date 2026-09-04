/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package view;

//imported libraries
import javax.swing.*;

//imported files
import controller.AuthController;

public class RegisterForm extends JFrame 
{
    //default constructor
    public RegisterForm() 
    {
        initComponents();
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        RegisterPanel = new javax.swing.JPanel();
        btnRegister = new javax.swing.JButton();
        LoginLink = new javax.swing.JButton();
        formLabel = new javax.swing.JLabel();
        usernameLabel = new javax.swing.JLabel();
        txtUsername = new javax.swing.JTextField();
        passwordLabel = new javax.swing.JLabel();
        txtPassword = new javax.swing.JPasswordField();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        RegisterPanel.setBackground(new java.awt.Color(0, 0, 102));

        btnRegister.setBackground(new java.awt.Color(0, 102, 255));
        btnRegister.setFont(new java.awt.Font("Segoe UI Semibold", 1, 18)); // NOI18N
        btnRegister.setForeground(new java.awt.Color(255, 255, 255));
        btnRegister.setText("REGISTER");
        btnRegister.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        btnRegister.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnRegisterActionPerformed(evt);
            }
        });

        LoginLink.setBackground(new java.awt.Color(204, 204, 204));
        LoginLink.setFont(new java.awt.Font("Segoe UI Semibold", 1, 18)); // NOI18N
        LoginLink.setForeground(new java.awt.Color(102, 102, 102));
        LoginLink.setText("Already have an account? Login");
        LoginLink.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        LoginLink.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                LoginLinkActionPerformed(evt);
            }
        });

        formLabel.setBackground(new java.awt.Color(0, 0, 102));
        formLabel.setFont(new java.awt.Font("Segoe UI Black", 1, 24)); // NOI18N
        formLabel.setForeground(new java.awt.Color(255, 255, 255));
        formLabel.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        formLabel.setText("Admin/Staff Register Form");
        formLabel.setToolTipText("");
        formLabel.setOpaque(true);

        usernameLabel.setBackground(new java.awt.Color(0, 0, 102));
        usernameLabel.setFont(new java.awt.Font("Segoe UI Semibold", 1, 18)); // NOI18N
        usernameLabel.setForeground(new java.awt.Color(255, 255, 255));
        usernameLabel.setText("Username");
        usernameLabel.setOpaque(true);

        txtUsername.setBackground(new java.awt.Color(153, 204, 255));
        txtUsername.setFont(new java.awt.Font("Segoe UI Semibold", 0, 18)); // NOI18N

        passwordLabel.setBackground(new java.awt.Color(0, 0, 102));
        passwordLabel.setFont(new java.awt.Font("Segoe UI Semibold", 1, 18)); // NOI18N
        passwordLabel.setForeground(new java.awt.Color(255, 255, 255));
        passwordLabel.setText("Password");
        passwordLabel.setOpaque(true);

        txtPassword.setBackground(new java.awt.Color(153, 204, 255));
        txtPassword.setFont(new java.awt.Font("Segoe UI Semibold", 0, 18)); // NOI18N

        javax.swing.GroupLayout RegisterPanelLayout = new javax.swing.GroupLayout(RegisterPanel);
        RegisterPanel.setLayout(RegisterPanelLayout);
        RegisterPanelLayout.setHorizontalGroup(
            RegisterPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(RegisterPanelLayout.createSequentialGroup()
                .addContainerGap(48, Short.MAX_VALUE)
                .addGroup(RegisterPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(formLabel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(btnRegister, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(LoginLink, javax.swing.GroupLayout.DEFAULT_SIZE, 357, Short.MAX_VALUE)
                    .addComponent(usernameLabel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(txtUsername)
                    .addComponent(passwordLabel, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(txtPassword))
                .addContainerGap(55, Short.MAX_VALUE))
        );
        RegisterPanelLayout.setVerticalGroup(
            RegisterPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, RegisterPanelLayout.createSequentialGroup()
                .addGap(30, 30, 30)
                .addComponent(formLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 45, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(44, 44, 44)
                .addComponent(usernameLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(txtUsername, javax.swing.GroupLayout.PREFERRED_SIZE, 39, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(24, 24, 24)
                .addComponent(passwordLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(txtPassword, javax.swing.GroupLayout.PREFERRED_SIZE, 39, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(47, 47, 47)
                .addComponent(btnRegister, javax.swing.GroupLayout.PREFERRED_SIZE, 39, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(LoginLink, javax.swing.GroupLayout.PREFERRED_SIZE, 39, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(73, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(RegisterPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(RegisterPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    //register button logic
    private void btnRegisterActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnRegisterActionPerformed
        String username = txtUsername.getText().trim();
        String password = new String(txtPassword.getPassword()).trim();

        if (username.isEmpty() || password.isEmpty()) 
        {
            JOptionPane.showMessageDialog(this, "Please enter both username and password.");
            return;
        }

        boolean success = AuthController.register(username, password);

        if (success)
        {
            JOptionPane.showMessageDialog(this, "Registration Successful");
            this.dispose();
            new LoginForm().setVisible(true);
        }
        else
        {
            JOptionPane.showMessageDialog(this, "Registration Failed. Username may already exist.");
        }                                    
    }//GEN-LAST:event_btnRegisterActionPerformed

    //login button logic
    private void LoginLinkActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_LoginLinkActionPerformed
        this.dispose();
        new LoginForm().setVisible(true);
    }//GEN-LAST:event_LoginLinkActionPerformed

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton LoginLink;
    private javax.swing.JPanel RegisterPanel;
    private javax.swing.JButton btnRegister;
    private javax.swing.JLabel formLabel;
    private javax.swing.JLabel passwordLabel;
    private javax.swing.JPasswordField txtPassword;
    private javax.swing.JTextField txtUsername;
    private javax.swing.JLabel usernameLabel;
    // End of variables declaration//GEN-END:variables
}
