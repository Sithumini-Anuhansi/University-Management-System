/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package view;

//imported files
import reports.ReportGenerator;

//imported libraries
import javax.swing.*;
import java.awt.*;

public class ReportsForm extends JFrame 
{
    private final JLabel lblTitle;
    private final JButton btnBack;

    public ReportsForm() 
    {       
        setTitle("Reports");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setSize(1000, 600);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        // ----- TOP PANEL -----
        JPanel topPanel = new JPanel(new BorderLayout());
        topPanel.setBackground(new Color(0, 51, 102));
        topPanel.setPreferredSize(new Dimension(getWidth(), 70));
        topPanel.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));
        
        ImageIcon rawIcon = new ImageIcon(getClass().getResource("/images/reports.png"));
        Image scaled = rawIcon.getImage().getScaledInstance(60, 60, Image.SCALE_SMOOTH);
        ImageIcon scaledIcon = new ImageIcon(scaled);
        JLabel lblLogo = new JLabel(scaledIcon);
        lblLogo.setPreferredSize(new Dimension(60, 60));
        lblLogo.setHorizontalAlignment(SwingConstants.LEFT);

        lblTitle = new JLabel("Reports");
        lblTitle.setFont(new Font("Segoe UI", Font.BOLD, 26));
        lblTitle.setForeground(Color.WHITE);
        lblTitle.setHorizontalAlignment(SwingConstants.CENTER);

        btnBack = new JButton("Back");
        btnBack.setBackground(new Color(204, 0, 0));
        btnBack.setForeground(Color.WHITE);
        btnBack.setFont(new Font("Segoe UI", Font.BOLD, 14));
        btnBack.setFocusPainted(false);
        btnBack.addActionListener(e -> this.dispose());

        topPanel.add(lblLogo, BorderLayout.WEST);
        topPanel.add(lblTitle, BorderLayout.CENTER);
        topPanel.add(btnBack, BorderLayout.EAST);
        add(topPanel, BorderLayout.NORTH);

        // ----- CONTENT PANEL WITH REPORT BUTTONS -----
        JPanel contentPanel = new JPanel();
        contentPanel.setLayout(new GridLayout(3, 1, 20, 20));
        contentPanel.setBorder(BorderFactory.createEmptyBorder(40, 100, 40, 100));
        contentPanel.setBackground(new Color(219,241,255));

        JButton btnReport1 = new JButton("Department-Course Enrollment Summary");
        JButton btnReport2 = new JButton("Student Enrollment and Demographics Report");
        JButton btnReport3 = new JButton("Department Financial Overview");

        Font reportBtnFont = new Font("Segoe UI", Font.BOLD, 18);
        Color reportBtnColor = new Color(100, 149, 237);

        for (JButton btn : new JButton[]{btnReport1, btnReport2, btnReport3}) 
        {
            btn.setFont(reportBtnFont);
            btn.setBackground(reportBtnColor);
            btn.setForeground(Color.WHITE);
            btn.setFocusPainted(false);
            contentPanel.add(btn);
        }

        // ----- BUTTON ACTIONS -----
        btnReport1.addActionListener(e -> 
        {
            try 
            {
                ReportGenerator.generateDepartmentCourseEnrollmentReport();
            } 
            catch (Exception ex) 
            {
                showError(ex);
            }
        });

        btnReport2.addActionListener(e -> 
        {
            try 
            {
                ReportGenerator.generateStudentEnrollmentAndDemographicsReport();
            } 
            catch (Exception ex) 
            {
                showError(ex);
            }
        });

        btnReport3.addActionListener(e -> 
        {
            try 
            {
                ReportGenerator.generateDepartmentFinancialOverviewReport();
            } 
            catch (Exception ex) 
            {
                showError(ex);
            }
        });

        add(contentPanel, BorderLayout.CENTER);
    }

    private void showError(Exception ex) 
    {
        JOptionPane.showMessageDialog(this, "Error generating report:\n" + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        ex.printStackTrace();
    }
    
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 400, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 300, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    // Variables declaration - do not modify//GEN-BEGIN:variables
    // End of variables declaration//GEN-END:variables
}
