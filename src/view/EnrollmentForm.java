/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package view;

//imported files
import controller.EnrollmentController;
import dao.CourseDAO;
import dao.DepartmentDAO;
import dao.StudentDAO;
import model.Course;
import model.Enrollment;
import model.Student;

//imported libraries
import javax.swing.*;
import javax.swing.table.*;
import java.awt.*;
import java.time.LocalDate;
import java.util.List;

public class EnrollmentForm extends JFrame 
{
    private final JTable table;
    private final DefaultTableModel tableModel;
    private final JLabel lblTitle, lblPageStatus, lblStatus;
    private final JButton btnBack, btnPrevPage, btnNextPage;
    private int currentPage = 1, rowsPerPage = 10, totalPages = 1;
    private List<Enrollment> fullEnrollmentList;

    public EnrollmentForm() 
    {
        setTitle("Enrollment Management");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setSize(1000, 600);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        // ---- TOP PANEL ----
        JPanel topPanel = new JPanel(new BorderLayout());
        topPanel.setBackground(new Color(0, 51, 102));
        topPanel.setPreferredSize(new Dimension(getWidth(), 70));
        topPanel.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));

        ImageIcon rawIcon = new ImageIcon(getClass().getResource("/images/enrollments.png"));
        Image scaled = rawIcon.getImage().getScaledInstance(60, 60, Image.SCALE_SMOOTH);
        ImageIcon scaledIcon = new ImageIcon(scaled);
        JLabel lblLogo = new JLabel(scaledIcon);
        lblLogo.setPreferredSize(new Dimension(60, 60));
        lblLogo.setHorizontalAlignment(SwingConstants.LEFT);
        
        lblTitle = new JLabel("Enrollment Management");
        lblTitle.setFont(new Font("Segoe UI", Font.BOLD, 26));
        lblTitle.setForeground(Color.WHITE);
        lblTitle.setHorizontalAlignment(SwingConstants.CENTER);

        btnBack = new JButton("Back");
        btnBack.setBackground(new Color(204, 0, 0));
        btnBack.setForeground(Color.WHITE);
        btnBack.setFont(new Font("Segoe UI", Font.BOLD, 14));
        btnBack.addActionListener(e -> this.dispose());

        topPanel.add(lblLogo, BorderLayout.WEST);
        topPanel.add(lblTitle, BorderLayout.CENTER);
        topPanel.add(btnBack, BorderLayout.EAST);

        // ---- SEARCH PANEL ----
        JPanel searchPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        JLabel lblSearch = new JLabel("Search Student / Course / Department: ");
        JTextField txtSearch = new JTextField(25);
        searchPanel.add(lblSearch);
        searchPanel.add(txtSearch);
        searchPanel.setBackground(new Color(220, 230, 240));

        JPanel northContainer = new JPanel();
        northContainer.setLayout(new BoxLayout(northContainer, BoxLayout.Y_AXIS));
        northContainer.add(topPanel);
        northContainer.add(searchPanel);
        add(northContainer, BorderLayout.NORTH);

        tableModel = new DefaultTableModel(new Object[]{"ID", "Student Name", "Course", "Department", "Date"}, 0) 
        {
            public boolean isCellEditable(int row, int column) 
            {
                return false;
            }
        };

        table = new JTable(tableModel);
        table.setRowHeight(30);
        table.setFont(new Font("Segoe UI", Font.PLAIN, 14));

        DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
        centerRenderer.setHorizontalAlignment(JLabel.CENTER);
        for (int i = 0; i < table.getColumnCount(); i++) 
        {
            table.getColumnModel().getColumn(i).setCellRenderer(centerRenderer);
        }

        int[] columnWidths = {10, 150, 200, 150, 30};
        TableColumnModel columnModel = table.getColumnModel();
        for (int i = 0; i < columnWidths.length; i++) 
        {
            columnModel.getColumn(i).setPreferredWidth(columnWidths[i]);
        }

        JTableHeader header = table.getTableHeader();
        header.setBackground(new Color(70, 130, 180));
        header.setForeground(Color.WHITE);
        header.setFont(new Font("Segoe UI", Font.BOLD, 14));

        JScrollPane scrollPane = new JScrollPane(table);
        JPanel centerPanel = new JPanel(new BorderLayout());
        centerPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        centerPanel.add(scrollPane, BorderLayout.CENTER);
        add(centerPanel, BorderLayout.CENTER);

        JPanel paginationPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        btnPrevPage = new JButton("Previous");
        btnNextPage = new JButton("Next");
        lblPageStatus = new JLabel();
        paginationPanel.add(btnPrevPage);
        paginationPanel.add(lblPageStatus);
        paginationPanel.add(btnNextPage);

        lblStatus = new JLabel("Status: Ready");
        lblStatus.setBorder(BorderFactory.createEmptyBorder(5, 10, 5, 10));
        lblStatus.setAlignmentX(Component.CENTER_ALIGNMENT);

        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        JButton btnAdd = new JButton("Add Enrollment");
        JButton btnEdit = new JButton("Edit Selected");
        JButton btnDelete = new JButton("Delete Selected");
        buttonPanel.add(btnAdd);
        buttonPanel.add(btnEdit);
        buttonPanel.add(btnDelete);

        JPanel southContainer = new JPanel();
        southContainer.setLayout(new BoxLayout(southContainer, BoxLayout.Y_AXIS));
        southContainer.add(paginationPanel);
        southContainer.add(lblStatus);
        southContainer.add(buttonPanel);
        southContainer.setBackground(new Color(220, 230, 240));
        paginationPanel.setBackground(new Color(220, 230, 240));
        buttonPanel.setBackground(new Color(220, 230, 240));

        Color btnBlue = new Color(100, 149, 237);
        btnAdd.setBackground(btnBlue);
        btnAdd.setForeground(Color.WHITE);
        btnEdit.setBackground(btnBlue);
        btnEdit.setForeground(Color.WHITE);
        btnDelete.setBackground(btnBlue);
        btnDelete.setForeground(Color.WHITE);

        add(southContainer, BorderLayout.SOUTH);

        btnAdd.addActionListener(e -> showEnrollmentDialog(null));
        btnEdit.addActionListener(e -> editSelectedEnrollment());
        btnDelete.addActionListener(e -> deleteSelectedEnrollment());

        btnPrevPage.addActionListener(e -> 
        {
            if (currentPage > 1) 
            {
                currentPage--;
                loadEnrollmentData();
            }
        });

        btnNextPage.addActionListener(e -> 
        {
            if (currentPage < totalPages) 
            {
                currentPage++;
                loadEnrollmentData();
            }
        });

        TableRowSorter<DefaultTableModel> rowSorter = new TableRowSorter<>(tableModel);
        table.setRowSorter(rowSorter);

        txtSearch.getDocument().addDocumentListener(new javax.swing.event.DocumentListener() 
        {
            @Override public void insertUpdate(javax.swing.event.DocumentEvent e) { filter(); }
            @Override public void removeUpdate(javax.swing.event.DocumentEvent e) { filter(); }            
            @Override public void changedUpdate(javax.swing.event.DocumentEvent e) { filter(); }

            private void filter() 
            {
                String text = txtSearch.getText();
                if (text.trim().length() == 0) 
                {
                    rowSorter.setRowFilter(null);
                } 
                else 
                {
                    rowSorter.setRowFilter(RowFilter.regexFilter("(?i)" + text, 1, 2, 3));
                }
            }
        });

        loadEnrollmentData();
    }

    private void updateStatus(String message) 
    {
        lblStatus.setText("Status: " + message);
    }

    private void loadEnrollmentData() 
    {
        tableModel.setRowCount(0);
        fullEnrollmentList = EnrollmentController.getAllEnrollments();
        int totalRows = fullEnrollmentList.size();
        totalPages = (int) Math.ceil((double) totalRows / rowsPerPage);
        if (totalPages == 0) totalPages = 1;
        if (currentPage > totalPages) currentPage = totalPages;
        int start = (currentPage - 1) * rowsPerPage;
        int end = Math.min(start + rowsPerPage, totalRows);

        for (int i = start; i < end; i++) 
        {
            Enrollment e = fullEnrollmentList.get(i);
            String studentName = StudentDAO.getStudentNameById(e.getStudentId());
            String courseName = CourseDAO.getCourseNameById(e.getCourseId());
            String deptName = DepartmentDAO.getDepartmentNameById(e.getDepartmentId());
            tableModel.addRow(new Object[]{e.getEnrollmentId(), studentName, courseName, deptName, e.getDateEnrolled()});
        }

        updateStatus("Page " + currentPage + " of " + totalPages + " | Total Enrollments: " + totalRows);
        btnPrevPage.setEnabled(currentPage > 1);
        btnNextPage.setEnabled(currentPage < totalPages);
        lblPageStatus.setText("Page " + currentPage + " of " + totalPages);
    }

    private void showEnrollmentDialog(Enrollment enrollment) 
    {
        JTextField txtId = new JTextField();
        txtId.setEnabled(false);
        if (enrollment != null) 
        {
            txtId.setText(String.valueOf(enrollment.getEnrollmentId()));
        } 
        else 
        {
            txtId.setText("Auto-generated");
        }

        JComboBox<String> studentCombo = new JComboBox<>();
        List<Student> students = StudentDAO.getAllStudents();
        for (Student s : students) studentCombo.addItem(s.getStudentId() + " - " + s.getName());

        JLabel lblStudentName = new JLabel("-");
        if (enrollment != null) 
        {
            int index = findStudentIndex(students, enrollment.getStudentId());
            studentCombo.setSelectedIndex(index);
            lblStudentName.setText(students.get(index).getName());
        }
        studentCombo.addActionListener(e -> 
        {
            if (studentCombo.getSelectedIndex() >= 0) 
            {
                lblStudentName.setText(students.get(studentCombo.getSelectedIndex()).getName());
            }
        });

        JComboBox<String> courseCombo = new JComboBox<>();
        List<Course> courses = CourseDAO.getAllCourses();
        for (Course c : courses) courseCombo.addItem(c.getCourseName());

        JLabel lblDeptName = new JLabel("-");
        if (enrollment != null) 
        {
            int index = findCourseIndex(courses, enrollment.getCourseId());
            courseCombo.setSelectedIndex(index);
            lblDeptName.setText(DepartmentDAO.getDepartmentNameById(courses.get(index).getDepartmentId()));
        }
        courseCombo.addActionListener(e -> 
        {
            if (courseCombo.getSelectedIndex() >= 0) 
            {
                Course c = courses.get(courseCombo.getSelectedIndex());
                lblDeptName.setText(DepartmentDAO.getDepartmentNameById(c.getDepartmentId()));
            }
        });

        JPanel panel = new JPanel(new GridLayout(0, 1));
        panel.add(new JLabel("Enrollment ID:")); panel.add(txtId);
        panel.add(new JLabel("Select Student:")); panel.add(studentCombo);
        panel.add(new JLabel("Student Name:")); panel.add(lblStudentName);
        panel.add(new JLabel("Select Course:")); panel.add(courseCombo);
        panel.add(new JLabel("Department:")); panel.add(lblDeptName);

        int result = JOptionPane.showConfirmDialog(this, panel, enrollment == null ? "Add Enrollment" : "Edit Enrollment", JOptionPane.OK_CANCEL_OPTION);
        if (result == JOptionPane.OK_OPTION) 
        {
            int studentId = students.get(studentCombo.getSelectedIndex()).getStudentId();
            Course selectedCourse = courses.get(courseCombo.getSelectedIndex());
            LocalDate date = enrollment != null ? enrollment.getDateEnrolled() : LocalDate.now();

            Enrollment newEnrollment = new Enrollment(
                    enrollment != null ? enrollment.getEnrollmentId() : 0,
                    studentId,
                    selectedCourse.getCourseId(),
                    selectedCourse.getDepartmentId(),
                    date
            );

            boolean success = (enrollment == null) ? EnrollmentController.addEnrollment(newEnrollment) : EnrollmentController.updateEnrollment(newEnrollment);
            if (success) 
            {
                updateStatus(enrollment == null ? "Enrollment added successfully." : "Enrollment updated successfully.");
                loadEnrollmentData();
            } 
            else 
            {
                JOptionPane.showMessageDialog(this, "Operation failed.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    private int findStudentIndex(List<Student> students, int studentId) 
    {
        for (int i = 0; i < students.size(); i++) 
        {
            if (students.get(i).getStudentId() == studentId) return i;
        }
        return 0;
    }

    private int findCourseIndex(List<Course> courses, int courseId) 
    {
        for (int i = 0; i < courses.size(); i++) 
        {
            if (courses.get(i).getCourseId() == courseId) return i;
        }
        return 0;
    }

    private void editSelectedEnrollment() 
    {
        int row = table.getSelectedRow();
        if (row == -1) 
        {
            JOptionPane.showMessageDialog(this, "Select a row to edit.");
            return;
        }
        row = table.convertRowIndexToModel(row);
        Enrollment e = fullEnrollmentList.get(row);
        showEnrollmentDialog(e);
    }

    private void deleteSelectedEnrollment() 
    {
        int row = table.getSelectedRow();
        if (row == -1) 
        {
            JOptionPane.showMessageDialog(this, "Select a row to delete.");
            return;
        }
        
        row = table.convertRowIndexToModel(row);
        int id = (int) tableModel.getValueAt(row, 0);
        int confirm = JOptionPane.showConfirmDialog(this, "Are you sure to delete enrollment ID " + id + "?", "Confirm", JOptionPane.YES_NO_OPTION);
        if (confirm == JOptionPane.YES_OPTION) 
        {
            if (EnrollmentController.deleteEnrollment(id)) 
            {
                updateStatus("Enrollment deleted successfully.");
                loadEnrollmentData();
            } 
            else 
            {
                JOptionPane.showMessageDialog(this, "Delete failed.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
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