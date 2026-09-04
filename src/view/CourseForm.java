/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package view;

//imported files
import controller.CourseController;
import dao.DepartmentDAO;
import dao.LecturerDAO;
import model.Course;
import model.Department;
import model.Lecturer;

//imported libraries
import javax.swing.*;
import javax.swing.table.*;
import java.awt.*;
import java.math.BigDecimal;
import java.util.List;

public class CourseForm extends JFrame 
{
    private final JTable courseTable;
    private final DefaultTableModel courseTableModel;
    private final JLabel lblTitle, lblPageStatus, lblStatus;
    private final JButton btnBack, btnPrevPage, btnNextPage;
    private int currentPage = 1, rowsPerPage = 10, totalPages = 1;
    private List<Course> fullCourseList;

    public CourseForm() 
    {        
        setTitle("Course Management");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setSize(1000, 600);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        // Top Panel
        JPanel topPanel = new JPanel(new BorderLayout());
        topPanel.setBackground(new Color(0, 51, 102));
        topPanel.setPreferredSize(new Dimension(getWidth(), 70));
        topPanel.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));

        ImageIcon rawIcon = new ImageIcon(getClass().getResource("/images/courses.png"));
        Image scaled = rawIcon.getImage().getScaledInstance(60, 60, Image.SCALE_SMOOTH);
        ImageIcon scaledIcon = new ImageIcon(scaled);
        JLabel lblLogo = new JLabel(scaledIcon);
        lblLogo.setPreferredSize(new Dimension(60, 60));
        lblLogo.setHorizontalAlignment(SwingConstants.LEFT);
        
        lblTitle = new JLabel("Course Management");
        lblTitle.setFont(new Font("Segoe UI", Font.BOLD, 26));
        lblTitle.setForeground(Color.WHITE);
        lblTitle.setHorizontalAlignment(SwingConstants.CENTER);

        btnBack = new JButton("Back");
        btnBack.setBackground(new Color(204, 0, 0));
        btnBack.setForeground(Color.WHITE);
        btnBack.setFocusPainted(false);
        btnBack.setFont(new Font("Segoe UI", Font.BOLD, 14));
        btnBack.addActionListener(e -> this.dispose());

        topPanel.add(lblLogo, BorderLayout.WEST);
        topPanel.add(lblTitle, BorderLayout.CENTER);
        topPanel.add(btnBack, BorderLayout.EAST);

        // Search Panel
        JPanel searchPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        JLabel lblSearch = new JLabel("Search Course Name / Lecturer / Department: ");
        JTextField txtSearch = new JTextField(25);
        searchPanel.add(lblSearch);
        searchPanel.add(txtSearch);
        searchPanel.setBackground(new Color(220, 230, 240));

        // North Container holds topPanel + searchPanel vertically
        JPanel northContainer = new JPanel();
        northContainer.setLayout(new BoxLayout(northContainer, BoxLayout.Y_AXIS));
        northContainer.add(topPanel);
        northContainer.add(searchPanel);
        add(northContainer, BorderLayout.NORTH);

        // Table Model & Table
        courseTableModel = new DefaultTableModel(new Object[]{"ID", "Name", "Department", "Lecturer", "Duration", "Fee (LKR)"}, 0) 
        {
            @Override public boolean isCellEditable(int row, int column) 
            {
                return false;
            }
        };

        courseTable = new JTable(courseTableModel);
        courseTable.setRowHeight(30);
        courseTable.setFont(new Font("Segoe UI", Font.PLAIN, 14));

        DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
        centerRenderer.setHorizontalAlignment(JLabel.CENTER);
        for (int i = 0; i < courseTable.getColumnCount(); i++) 
        {
            courseTable.getColumnModel().getColumn(i).setCellRenderer(centerRenderer);
        }

        // Set preferred column widths
        int[] columnWidths = {10, 200, 150, 150, 20, 30};
        TableColumnModel columnModel = courseTable.getColumnModel();
        for (int i = 0; i < columnWidths.length; i++) 
        {
            columnModel.getColumn(i).setPreferredWidth(columnWidths[i]);
        }
        
        JTableHeader header = courseTable.getTableHeader();
        header.setBackground(new Color(70, 130, 180));
        header.setForeground(Color.WHITE);
        header.setFont(new Font("Segoe UI", Font.BOLD, 14));

        JScrollPane scrollPane = new JScrollPane(courseTable);
        JPanel centerPanel = new JPanel(new BorderLayout());
        centerPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        centerPanel.add(scrollPane, BorderLayout.CENTER);
        add(centerPanel, BorderLayout.CENTER);

        // Pagination Panel
        JPanel paginationPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        btnPrevPage = new JButton("Previous");
        btnNextPage = new JButton("Next");
        lblPageStatus = new JLabel();
        paginationPanel.add(btnPrevPage);
        paginationPanel.add(lblPageStatus);
        paginationPanel.add(btnNextPage);

        // Status Label
        lblStatus = new JLabel("Status: Ready");
        lblStatus.setBorder(BorderFactory.createEmptyBorder(5, 10, 5, 10));
        lblStatus.setAlignmentX(Component.CENTER_ALIGNMENT);

        // Button Panel
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        JButton btnAdd = new JButton("Add Course");
        JButton btnEdit = new JButton("Edit Selected");
        JButton btnDelete = new JButton("Delete Selected");
        buttonPanel.add(btnAdd);
        buttonPanel.add(btnEdit);
        buttonPanel.add(btnDelete);

        // South Container holds pagination, status and buttons vertically
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

        // Add Listeners
        btnAdd.addActionListener(e -> showCourseDialog(null));
        btnEdit.addActionListener(e -> editSelectedCourse());
        btnDelete.addActionListener(e -> deleteSelectedCourse());

        btnPrevPage.addActionListener(e -> 
        {
            if (currentPage > 1) 
            {
                currentPage--;
                loadCourseData();
            }
        });

        btnNextPage.addActionListener(e -> 
        {
            if (currentPage < totalPages) 
            {
                currentPage++;
                loadCourseData();
            }
        });

        // --- TableRowSorter for search/filter ---
        TableRowSorter<DefaultTableModel> rowSorter = new TableRowSorter<>(courseTableModel);
        courseTable.setRowSorter(rowSorter);

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
                    // Filter on Course Name(1), Department(2), Lecturer(3) columns
                    rowSorter.setRowFilter(RowFilter.regexFilter("(?i)" + text, 1, 2, 3));
                }
            }
        });

        // Load initial data
        loadCourseData();
    }

    private void updateStatus(String message) 
    {
        lblStatus.setText("Status: " + message);
    }

    private void loadCourseData() 
    {
        courseTableModel.setRowCount(0);
        fullCourseList = CourseController.getAllCourses();
        int totalRows = fullCourseList.size();
        totalPages = (int) Math.ceil((double) totalRows / rowsPerPage);
        if (totalPages == 0) totalPages = 1;
        if (currentPage > totalPages) currentPage = totalPages;
        int start = (currentPage - 1) * rowsPerPage;
        int end = Math.min(start + rowsPerPage, totalRows);

        for (int i = start; i < end; i++) 
        {
            Course c = fullCourseList.get(i);
            String deptName = DepartmentDAO.getDepartmentNameById(c.getDepartmentId());
            String lecturerName = LecturerDAO.getLecturerNameById(c.getLecturerId());
            courseTableModel.addRow(new Object[]{
                c.getCourseId(), c.getCourseName(), deptName, lecturerName, c.getDuration(), c.getFee()
            });
        }

        updateStatus("Page " + currentPage + " of " + totalPages + " | Total Courses: " + totalRows);
        btnPrevPage.setEnabled(currentPage > 1);
        btnNextPage.setEnabled(currentPage < totalPages);
        lblPageStatus.setText("Page " + currentPage + " of " + totalPages);
    }

    private void showCourseDialog(Course course) 
    {
        JTextField txtId = new JTextField();
        JTextField txtName = new JTextField();
        JTextField txtDuration = new JTextField();
        JTextField txtFee = new JTextField();

        JComboBox<String> departmentCombo = new JComboBox<>();
        List<Department> departments = DepartmentDAO.getAllDepartments();
        for (Department d : departments) 
        {
            departmentCombo.addItem(d.getName());
        }

        JComboBox<String> lecturerCombo = new JComboBox<>();
        List<Lecturer> lecturers = LecturerDAO.getAllLecturers();
        for (Lecturer l : lecturers) 
        {
            lecturerCombo.addItem(l.getName());
        }

        if (course != null) 
        {
            txtId.setText(String.valueOf(course.getCourseId()));
            txtId.setEnabled(false);
            txtName.setText(course.getCourseName());
            txtDuration.setText(course.getDuration());
            txtFee.setText(course.getFee().toString());
            departmentCombo.setSelectedItem(DepartmentDAO.getDepartmentNameById(course.getDepartmentId()));
            lecturerCombo.setSelectedItem(LecturerDAO.getLecturerNameById(course.getLecturerId()));
        } 
        else 
        {
            txtId.setText("Auto-generated");
            txtId.setEnabled(false);
        }

        JPanel panel = new JPanel(new GridLayout(0, 1));
        panel.add(new JLabel("Course ID:")); panel.add(txtId);
        panel.add(new JLabel("Course Name:")); panel.add(txtName);
        panel.add(new JLabel("Department:")); panel.add(departmentCombo);
        panel.add(new JLabel("Lecturer:")); panel.add(lecturerCombo);
        panel.add(new JLabel("Duration:")); panel.add(txtDuration);
        panel.add(new JLabel("Fee (LKR):")); panel.add(txtFee);

        int result = JOptionPane.showConfirmDialog(this, panel, course == null ? "Add Course" : "Edit Course", JOptionPane.OK_CANCEL_OPTION);
        if (result == JOptionPane.OK_OPTION) 
        {
            try 
            {
                int depId = DepartmentDAO.getDepartmentIdByName((String) departmentCombo.getSelectedItem());
                int lecId = LecturerDAO.getLecturerIdByName((String) lecturerCombo.getSelectedItem());

                Course c = new Course(
                    course != null ? course.getCourseId() : 0,
                    txtName.getText(),
                    depId,
                    lecId,
                    txtDuration.getText(),
                    new BigDecimal(txtFee.getText())
                );

                boolean success = (course == null) ? CourseController.addCourse(c) : CourseController.updateCourse(c);
                if (success) 
                {
                    updateStatus(course == null ? "Course added." : "Course updated.");
                    loadCourseData();
                } 
                else 
                {
                    JOptionPane.showMessageDialog(this, "Operation failed.", "Error", JOptionPane.ERROR_MESSAGE);
                }
            } 
            catch (Exception e) 
            {
                JOptionPane.showMessageDialog(this, "Invalid input.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    private void editSelectedCourse() 
    {
        int row = courseTable.getSelectedRow();
        if (row == -1) 
        {
            JOptionPane.showMessageDialog(this, "Select a row to edit.");
            return;
        }
        row = courseTable.convertRowIndexToModel(row);
        Course course = fullCourseList.get(row);
        showCourseDialog(course);
    }

    private void deleteSelectedCourse() 
    {
        int row = courseTable.getSelectedRow();
        if (row == -1) 
        {
            JOptionPane.showMessageDialog(this, "Select a row to delete.");
            return;
        }
        row = courseTable.convertRowIndexToModel(row);
        int id = (int) courseTableModel.getValueAt(row, 0);
        int confirm = JOptionPane.showConfirmDialog(this, "Are you sure to delete course ID " + id + "?", "Confirm", JOptionPane.YES_NO_OPTION);
        if (confirm == JOptionPane.YES_OPTION) 
        {
            if (CourseController.deleteCourse(id)) 
            {
                updateStatus("Course deleted successfully.");
                loadCourseData();
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
