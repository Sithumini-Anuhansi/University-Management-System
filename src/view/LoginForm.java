/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package view;

//imported libraries
import javax.swing.*;

//imported files
import controller.AuthController;

public class LoginForm extends JFrame 
{
    //default constructor
    public LoginForm() 
    {
        initComponents();
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        LoginPanel = new javax.swing.JPanel();
        btnLogin = new javax.swing.JButton();
        RegisterLink = new javax.swing.JButton();
        formLabel = new javax.swing.JLabel();
        usernameLabel = new javax.swing.JLabel();
        txtUsername = new javax.swing.JTextField();
        passwordLabel = new javax.swing.JLabel();
        txtPassword = new javax.swing.JPasswordField();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        LoginPanel.setBackground(new java.awt.Color(0, 0, 102));

        btnLogin.setBackground(new java.awt.Color(0, 102, 255));
        btnLogin.setFont(new java.awt.Font("Segoe UI Semibold", 1, 18)); // NOI18N
        btnLogin.setForeground(new java.awt.Color(255, 255, 255));
        btnLogin.setText("LOGIN");
        btnLogin.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        btnLogin.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnLoginActionPerformed(evt);
            }
        });

        RegisterLink.setBackground(new java.awt.Color(204, 204, 204));
        RegisterLink.setFont(new java.awt.Font("Segoe UI Semibold", 1, 18)); // NOI18N
        RegisterLink.setForeground(new java.awt.Color(102, 102, 102));
        RegisterLink.setText("Do not have an account? Register");
        RegisterLink.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        RegisterLink.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                RegisterLinkActionPerformed(evt);
            }
        });

        formLabel.setBackground(new java.awt.Color(0, 0, 102));
        formLabel.setFont(new java.awt.Font("Segoe UI Black", 1, 24)); // NOI18N
        formLabel.setForeground(new java.awt.Color(255, 255, 255));
        formLabel.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        formLabel.setText("Admin/Staff Login Form");
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

        javax.swing.GroupLayout LoginPanelLayout = new javax.swing.GroupLayout(LoginPanel);
        LoginPanel.setLayout(LoginPanelLayout);
        LoginPanelLayout.setHorizontalGroup(
            LoginPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(LoginPanelLayout.createSequentialGroup()
                .addContainerGap(48, Short.MAX_VALUE)
                .addGroup(LoginPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(formLabel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(btnLogin, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(RegisterLink, javax.swing.GroupLayout.DEFAULT_SIZE, 357, Short.MAX_VALUE)
                    .addComponent(usernameLabel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(txtUsername)
                    .addComponent(passwordLabel, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(txtPassword))
                .addContainerGap(55, Short.MAX_VALUE))
        );
        LoginPanelLayout.setVerticalGroup(
            LoginPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, LoginPanelLayout.createSequentialGroup()
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
                .addComponent(btnLogin, javax.swing.GroupLayout.PREFERRED_SIZE, 39, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(RegisterLink, javax.swing.GroupLayout.PREFERRED_SIZE, 39, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(73, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(LoginPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(LoginPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    //login button logic
    private void btnLoginActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnLoginActionPerformed
        String username = txtUsername.getText().trim();
        String password = new String(txtPassword.getPassword()).trim();
        
        if (username.isEmpty() || password.isEmpty()) 
        {
            JOptionPane.showMessageDialog(this, "Please enter both username and password.");
            return;
        }
        
        boolean success = AuthController.login(username, password);
        
        if (success)
        {
            JOptionPane.showMessageDialog(this, "Login Successful");
            this.dispose();
            new DashboardForm(username).setVisible(true);
        }
        else
        {
            JOptionPane.showMessageDialog(this, "Invalid Credentials");
        }
    }//GEN-LAST:event_btnLoginActionPerformed

    //register button logic
    private void RegisterLinkActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_RegisterLinkActionPerformed
        this.dispose();
        new RegisterForm().setVisible(true);
    }//GEN-LAST:event_RegisterLinkActionPerformed

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPanel LoginPanel;
    private javax.swing.JButton RegisterLink;
    private javax.swing.JButton btnLogin;
    private javax.swing.JLabel formLabel;
    private javax.swing.JLabel passwordLabel;
    private javax.swing.JPasswordField txtPassword;
    private javax.swing.JTextField txtUsername;
    private javax.swing.JLabel usernameLabel;
    // End of variables declaration//GEN-END:variables
}
