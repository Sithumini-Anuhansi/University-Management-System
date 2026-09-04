/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package view;

//imported files
import controller.StudentController;
import model.Student;
import model.Department;
import dao.DepartmentDAO;

//imported libraries
import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.table.*;
import java.awt.*;
import java.util.List;

public class StudentForm extends JFrame 
{
    //table and table model variables
    private final JTable studentTable;
    private final DefaultTableModel studentTableModel;
    
    //top label and back button variables
    private JLabel lblTitle;
    private JButton btnBack;

    //pagination-related variables
    private int currentPage = 1;
    private int rowsPerPage = 10;
    private int totalPages = 1;
    private final JButton btnPrevPage, btnNextPage;
    private final JLabel lblPageStatus, lblStatus;
    
    //student data list
    private List<Student> fullStudentList;

    //constructor
    public StudentForm() 
    {
        setTitle("Student Management");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setSize(1000, 600);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        // ---- TOP PANEL ----
        JPanel topPanel = new JPanel(new BorderLayout());
        topPanel.setBackground(new Color(0, 51, 102));
        topPanel.setPreferredSize(new Dimension(getWidth(), 70));
        topPanel.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));
        
        ImageIcon rawIcon = new ImageIcon(getClass().getResource("/images/students.png"));
        Image scaled = rawIcon.getImage().getScaledInstance(60, 60, Image.SCALE_SMOOTH);
        ImageIcon scaledIcon = new ImageIcon(scaled);
        JLabel lblLogo = new JLabel(scaledIcon);
        lblLogo.setPreferredSize(new Dimension(60, 60));
        lblLogo.setHorizontalAlignment(SwingConstants.LEFT);

        lblTitle = new JLabel("Student Management");
        lblTitle.setFont(new Font("Segoe UI", Font.BOLD, 26));
        lblTitle.setForeground(Color.WHITE);
        lblTitle.setHorizontalAlignment(SwingConstants.CENTER);

        btnBack = new JButton("Back");
        btnBack.setBackground(new Color(204, 0, 0));
        btnBack.setForeground(Color.WHITE);
        btnBack.setFocusPainted(false);
        btnBack.setFont(new Font("Segoe UI", Font.BOLD, 14));
        btnBack.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));
        btnBack.addActionListener(e -> this.dispose());

        topPanel.add(lblLogo, BorderLayout.WEST);
        topPanel.add(lblTitle, BorderLayout.CENTER);
        topPanel.add(btnBack, BorderLayout.EAST);

        JPanel searchPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        JLabel lblSearch = new JLabel("Search Student Name: ");
        JTextField txtSearch = new JTextField(20);
        searchPanel.add(lblSearch);
        searchPanel.add(txtSearch);
        searchPanel.setBackground(new Color(220, 230, 240));

        JPanel northContainer = new JPanel();
        northContainer.setLayout(new BoxLayout(northContainer, BoxLayout.Y_AXIS));
        northContainer.add(topPanel);
        northContainer.add(searchPanel);

        add(northContainer, BorderLayout.NORTH);

        studentTableModel = new DefaultTableModel(new Object[]{"ID", "Name", "Email", "Gender", "Age", "Phone", "Department"}, 0) 
        {
            public boolean isCellEditable(int row, int column) 
            {
                return false;
            }
        };

        studentTable = new JTable(studentTableModel);
        studentTable.setAutoCreateRowSorter(true);
        studentTable.setRowHeight(30);
        studentTable.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
        centerRenderer.setHorizontalAlignment(JLabel.CENTER);
        for (int i = 0; i < studentTable.getColumnCount(); i++) 
        {
            studentTable.getColumnModel().getColumn(i).setCellRenderer(centerRenderer);
        }

        // Set preferred column widths
        int[] columnWidths = {10, 150, 150, 20, 10, 80, 120};
        TableColumnModel columnModel = studentTable.getColumnModel();
        for (int i = 0; i < columnWidths.length; i++) 
        {
            columnModel.getColumn(i).setPreferredWidth(columnWidths[i]);
        }
        
        JTableHeader header = studentTable.getTableHeader();
        header.setBackground(new Color(70, 130, 180));
        header.setForeground(Color.WHITE);
        header.setFont(new Font("Segoe UI", Font.BOLD, 14));

        TableRowSorter<DefaultTableModel> rowSorter = new TableRowSorter<>(studentTableModel);
        studentTable.setRowSorter(rowSorter);

        txtSearch.getDocument().addDocumentListener(new DocumentListener() 
        {
            @Override public void insertUpdate(DocumentEvent e) { filter(); }
            @Override public void removeUpdate(DocumentEvent e) { filter(); }
            @Override public void changedUpdate(DocumentEvent e) { filter(); }

            private void filter() 
            {
                String text = txtSearch.getText();
                rowSorter.setRowFilter(text.trim().length() == 0 ? null : RowFilter.regexFilter("(?i)" + text, 1, 2));
            }
        });

        JScrollPane scrollPane = new JScrollPane(studentTable);
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

        btnPrevPage.addActionListener(e -> 
        {
            if (currentPage > 1) 
            {
                currentPage--;
                loadStudentData();
            }
        });

        btnNextPage.addActionListener(e -> 
        {
            if (currentPage < totalPages) 
            {
                currentPage++;
                loadStudentData();
            }
        });

        lblStatus = new JLabel("Status: Ready");
        lblStatus.setBorder(BorderFactory.createEmptyBorder(5, 10, 5, 10));
        lblStatus.setAlignmentX(Component.CENTER_ALIGNMENT);

        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        JButton btnAdd = new JButton("Add Student");
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

        btnAdd.addActionListener(e -> 
        {
            updateStatus("Add student clicked");
            showStudentDialog(null);
        });

        btnEdit.addActionListener(e -> editSelectedStudent());
        btnDelete.addActionListener(e -> deleteSelectedStudent());

        loadStudentData();
    }

    private void updateStatus(String message) 
    {
        if (lblStatus != null) 
        {
            lblStatus.setText("Status: " + message);
        }
    }

    private void loadStudentData() 
    {
        studentTableModel.setRowCount(0);
        fullStudentList = StudentController.getAllStudents();
        int totalRows = fullStudentList.size();
        totalPages = (int) Math.ceil((double) totalRows / rowsPerPage);
        if (totalPages == 0) totalPages = 1;
        if (currentPage > totalPages) currentPage = totalPages;
        int start = (currentPage - 1) * rowsPerPage;
        int end = Math.min(start + rowsPerPage, totalRows);
        
        for (int i = start; i < end; i++) 
        {
            Student s = fullStudentList.get(i);
            studentTableModel.addRow(new Object[]
            {
                s.getStudentId(), s.getName(), s.getEmail(), s.getGender(), s.getAge(), s.getPhoneNo(), s.getDepartmentName()
            });
        }
        
        updateStatus("Page " + currentPage + " of " + totalPages + " | Total Students: " + totalRows);
        btnPrevPage.setEnabled(currentPage > 1);
        btnNextPage.setEnabled(currentPage < totalPages);
        lblPageStatus.setText("Page " + currentPage + " of " + totalPages);
    }

    //dialog box to add and edit students
    private void showStudentDialog(Student student) 
    {
        JTextField txtId = new JTextField();
        JTextField txtName = new JTextField();
        JTextField txtEmail = new JTextField();
        JTextField txtAge = new JTextField();
        JTextField txtPhone = new JTextField();

        JRadioButton male = new JRadioButton("Male");
        JRadioButton female = new JRadioButton("Female");
        ButtonGroup genderGroup = new ButtonGroup();
        genderGroup.add(male);
        genderGroup.add(female);

        List<Department> departments = DepartmentDAO.getAllDepartments();
        JComboBox<String> departmentCombo = new JComboBox<>();
        for (Department d : departments) 
        {
            departmentCombo.addItem(d.getName());
        }

        if (student != null) 
        {
            txtId.setText(String.valueOf(student.getStudentId()));
            txtId.setEnabled(false);
            txtName.setText(student.getName());
            txtEmail.setText(student.getEmail());
            txtAge.setText(String.valueOf(student.getAge()));
            txtPhone.setText(String.valueOf(student.getPhoneNo()));
            
            if ("Male".equals(student.getGender())) male.setSelected(true);
            else female.setSelected(true);
            
            departmentCombo.setSelectedItem(student.getDepartmentName());
        }
        
        else 
        {
            txtId.setText("Auto-generated");
            txtId.setEnabled(false);
            male.setSelected(true);
        }

        JPanel panel = new JPanel(new GridLayout(0, 1));
        panel.add(new JLabel("Student ID:"));
        panel.add(txtId);
        panel.add(new JLabel("Full Name:"));
        panel.add(txtName);
        panel.add(new JLabel("Email:"));
        panel.add(txtEmail);
        panel.add(new JLabel("Gender:"));
        panel.add(male);
        panel.add(female);
        panel.add(new JLabel("Age:"));
        panel.add(txtAge);
        panel.add(new JLabel("Phone No:"));
        panel.add(txtPhone);
        panel.add(new JLabel("Department:"));
        panel.add(departmentCombo);

        int result = JOptionPane.showConfirmDialog(this, panel, student == null ? "Add Student" : "Edit Student", JOptionPane.OK_CANCEL_OPTION);
        if (result == JOptionPane.OK_OPTION) 
        {
            try 
            {
                String gender = male.isSelected() ? "Male" : "Female";
                String depName = (String) departmentCombo.getSelectedItem();
                int depId = DepartmentDAO.getDepartmentIdByName(depName);

                Student s = new Student(
                        student != null ? student.getStudentId() : 0,
                        txtName.getText(),
                        txtEmail.getText(),
                        gender,
                        Integer.parseInt(txtAge.getText()),
                        Integer.parseInt(txtPhone.getText()),
                        depId,
                        depName
                );

                boolean success = (student == null) ? StudentController.addStudent(s) : StudentController.updateStudent(s);
                if (success) 
                {
                    updateStatus(student == null ? "Student added." : "Student updated.");
                    loadStudentData();
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

    //method to edit student details
    private void editSelectedStudent() 
    {
        int row = studentTable.getSelectedRow();
        if (row == -1) 
        {
            JOptionPane.showMessageDialog(this, "Select a row to edit.");
            return;
        }
        row = studentTable.convertRowIndexToModel(row);
        Student student = new Student(
                (int) studentTableModel.getValueAt(row, 0),
                (String) studentTableModel.getValueAt(row, 1),
                (String) studentTableModel.getValueAt(row, 2),
                (String) studentTableModel.getValueAt(row, 3),
                (int) studentTableModel.getValueAt(row, 4),
                (int) studentTableModel.getValueAt(row, 5),
                DepartmentDAO.getDepartmentIdByName((String) studentTableModel.getValueAt(row, 6)),
                (String) studentTableModel.getValueAt(row, 6)
        );
        showStudentDialog(student);
    }

    //method to delete students
    private void deleteSelectedStudent() 
    {
        int row = studentTable.getSelectedRow();
        if (row == -1) 
        {
            JOptionPane.showMessageDialog(this, "Select a row to delete.");
            return;
        }
        
        row = studentTable.convertRowIndexToModel(row);
        int id = (int) studentTableModel.getValueAt(row, 0);
        int confirm = JOptionPane.showConfirmDialog(this, "Are you sure to delete student ID " + id + "?", "Confirm", JOptionPane.YES_NO_OPTION);
        if (confirm == JOptionPane.YES_OPTION) 
        {
            if (StudentController.deleteStudent(id)) 
            {
                updateStatus("Student deleted successfully.");
                loadStudentData();
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
