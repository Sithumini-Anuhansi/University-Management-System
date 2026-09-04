/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package view;

//imported files
import dao.DashboardDAO;

//imported libraries
import javax.swing.*;
import java.awt.*;

public class DashboardForm extends JFrame 
{
    private final JLabel lblTitle, lblWelcome;
    private final JButton btnLogout;
    private final JPanel sideMenu, contentPanel;

    public DashboardForm(String username) 
    {
        setTitle("Admin/Staff Dashboard");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(900, 600);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        // ----- TOP PANEL -----
        JPanel topPanel = new JPanel(new BorderLayout());
        topPanel.setBackground(new Color(0, 51, 102));
        topPanel.setPreferredSize(new Dimension(getWidth(), 70));
        topPanel.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));

        ImageIcon rawIcon = new ImageIcon(getClass().getResource("/images/logo.png"));
        Image scaled = rawIcon.getImage().getScaledInstance(60, 60, Image.SCALE_SMOOTH);
        ImageIcon scaledIcon = new ImageIcon(scaled);
        JLabel lblLogo = new JLabel(scaledIcon);
        lblLogo.setPreferredSize(new Dimension(60, 60));
        lblLogo.setHorizontalAlignment(SwingConstants.LEFT);
        
        lblTitle = new JLabel("Admin/Staff Dashboard");
        lblTitle.setFont(new Font("Segoe UI", Font.BOLD, 26));
        lblTitle.setForeground(Color.WHITE);
        lblTitle.setHorizontalAlignment(SwingConstants.LEFT);

        JPanel westPanel = new JPanel();
        westPanel.setLayout(new BoxLayout(westPanel, BoxLayout.X_AXIS));
        westPanel.setOpaque(false);
        westPanel.setPreferredSize(new Dimension(380, 65));
        lblLogo.setAlignmentY(Component.CENTER_ALIGNMENT);
        lblTitle.setAlignmentY(Component.CENTER_ALIGNMENT);
        lblTitle.setBorder(BorderFactory.createEmptyBorder(0, 10, 0, 0));
        westPanel.add(lblLogo);
        westPanel.add(lblTitle);

        lblWelcome = new JLabel("Welcome, " + username);
        lblWelcome.setFont(new Font("Segoe UI", Font.BOLD, 18));
        lblWelcome.setForeground(new Color(220, 230, 240));
        lblWelcome.setHorizontalAlignment(SwingConstants.CENTER);

        btnLogout = new JButton("Logout");
        btnLogout.setBackground(new Color(204, 0, 0));
        btnLogout.setForeground(Color.WHITE);
        btnLogout.setFocusPainted(false);
        btnLogout.setFont(new Font("Segoe UI", Font.BOLD, 14));
        btnLogout.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));

        btnLogout.addActionListener(e -> 
        {
            this.dispose();
            new LoginForm().setVisible(true);
        });

        //topPanel.add(lblLogo, BorderLayout.WEST);
        topPanel.add(westPanel, BorderLayout.WEST);
        topPanel.add(lblWelcome, BorderLayout.CENTER);
        topPanel.add(btnLogout, BorderLayout.EAST);

        add(topPanel, BorderLayout.NORTH);

        // ----- SIDE MENU -----
        sideMenu = new JPanel();
        sideMenu.setLayout(new BoxLayout(sideMenu, BoxLayout.Y_AXIS));
        sideMenu.setBackground(new Color(0, 76, 153));
        sideMenu.setPreferredSize(new Dimension(180, getHeight()));
        sideMenu.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        addMenuItem("Students", () -> new StudentForm().setVisible(true));
        addMenuItem("Lecturers", () -> new LecturerForm().setVisible(true));
        addMenuItem("Courses", () -> new CourseForm().setVisible(true));
        addMenuItem("Departments", () -> new DepartmentForm().setVisible(true));
        addMenuItem("Enrollments", () -> new EnrollmentForm().setVisible(true));
        addMenuItem("Reports", () -> new ReportsForm().setVisible(true));

        add(sideMenu, BorderLayout.WEST);

        // ----- MAIN CONTENT PANEL -----
        contentPanel = new JPanel();
        contentPanel.setLayout(new BorderLayout());
        contentPanel.setBackground(new Color(219,241,255));
        contentPanel.setBorder(BorderFactory.createEmptyBorder(30, 30, 30, 30));

        JLabel lblMainWelcome = new JLabel("System Statistics");
        lblMainWelcome.setFont(new Font("Segoe UI", Font.BOLD, 24));
        lblMainWelcome.setHorizontalAlignment(SwingConstants.CENTER);

        // ----- STAT BOX PANEL -----
        JPanel statPanel = new JPanel(new GridLayout(2, 3, 20, 20));
        statPanel.setBackground(new Color(219,241,255));
        statPanel.setBorder(BorderFactory.createEmptyBorder(30, 0, 0, 0));

        int students = DashboardDAO.getTotal("students");
        int lecturers = DashboardDAO.getTotal("lecturers");
        int courses = DashboardDAO.getTotal("courses");
        int departments = DashboardDAO.getTotal("departments");
        int todayEnrollments = DashboardDAO.getEnrollmentsToday();

        statPanel.add(createStatBox("Total Students", String.valueOf(students), new ImageIcon(getClass().getResource("/images/students.png"))));
        statPanel.add(createStatBox("Total Lecturers", String.valueOf(lecturers), new ImageIcon(getClass().getResource("/images/lecturers.png"))));
        statPanel.add(createStatBox("Total Courses", String.valueOf(courses), new ImageIcon(getClass().getResource("/images/courses.png"))));
        statPanel.add(createStatBox("Total Departments", String.valueOf(departments), new ImageIcon(getClass().getResource("/images/departments.png"))));
        statPanel.add(createStatBox("Enrollments Today", String.valueOf(todayEnrollments), new ImageIcon(getClass().getResource("/images/enrollments.png"))));

        contentPanel.add(lblMainWelcome, BorderLayout.NORTH);
        contentPanel.add(statPanel, BorderLayout.CENTER);

        add(contentPanel, BorderLayout.CENTER);
    }

    private void addMenuItem(String title, Runnable action) 
    {
        JButton button = new JButton(title);
        button.setMaximumSize(new Dimension(Integer.MAX_VALUE, 35));
        button.setBackground(new Color(0, 102, 204));
        button.setForeground(Color.WHITE);
        button.setFont(new Font("Segoe UI", Font.BOLD, 14));
        button.setFocusPainted(false);
        button.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));
        button.setAlignmentX(Component.CENTER_ALIGNMENT);
        button.addActionListener(e -> action.run());

        sideMenu.add(button);
        sideMenu.add(Box.createRigidArea(new Dimension(0, 30)));
    }

    private JPanel createStatBox(String title, String value, ImageIcon icon) 
    {
        JPanel box = new JPanel(new BorderLayout(10, 10));
        box.setBackground(new Color(117, 168, 255));
        box.setBorder(BorderFactory.createLineBorder(Color.WHITE, 1));
        box.setPreferredSize(new Dimension(180, 120));

        //scale the images
        Image scaledImage = icon.getImage().getScaledInstance(120, 100, Image.SCALE_SMOOTH);
        ImageIcon scaledIcon = new ImageIcon(scaledImage);

        JLabel lblIcon = new JLabel(scaledIcon);
        lblIcon.setHorizontalAlignment(SwingConstants.CENTER);

        JLabel lblTitle = new JLabel(title, SwingConstants.CENTER);
        lblTitle.setFont(new Font("Segoe UI", Font.BOLD, 18));
        lblTitle.setForeground(Color.DARK_GRAY);

        JLabel lblValue = new JLabel(value, SwingConstants.CENTER);
        lblValue.setFont(new Font("Segoe UI Black", Font.BOLD, 22));
        lblValue.setForeground(Color.DARK_GRAY);

        //add components in order
        box.add(lblTitle, BorderLayout.NORTH);
        box.add(lblIcon, BorderLayout.CENTER);
        box.add(lblValue, BorderLayout.SOUTH);

        return box;
    }
    
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setBackground(new java.awt.Color(219, 241, 255));

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
