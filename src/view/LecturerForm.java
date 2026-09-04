/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package view;

//imported files
import controller.LecturerController;
import model.Lecturer;
import model.Department;
import dao.DepartmentDAO;

//imported libraries
import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.table.*;
import java.awt.*;
import java.awt.event.*;
import java.util.List;

public class LecturerForm extends JFrame 
{
    //table and table model variables
    private JTable lecturerTable;
    private DefaultTableModel lecturerTableModel;
    
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
    private List<Lecturer> fullLecturerList;

    //constructor
    public LecturerForm() 
    {          
        setTitle("Lecturer Management");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setSize(1000, 600);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        // ---- TOP PANEL ----
        JPanel topPanel = new JPanel(new BorderLayout());
        topPanel.setBackground(new Color(0, 51, 102));
        topPanel.setPreferredSize(new Dimension(getWidth(), 70));
        topPanel.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));

        ImageIcon rawIcon = new ImageIcon(getClass().getResource("/images/lecturers.png"));
        Image scaled = rawIcon.getImage().getScaledInstance(60, 60, Image.SCALE_SMOOTH);
        ImageIcon scaledIcon = new ImageIcon(scaled);
        JLabel lblLogo = new JLabel(scaledIcon);
        lblLogo.setPreferredSize(new Dimension(60, 60));
        lblLogo.setHorizontalAlignment(SwingConstants.LEFT);
        
        lblTitle = new JLabel("Lecturer Management");
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

        // Search panel with padding
        JPanel searchPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        JLabel lblSearch = new JLabel("Search Lecturer Name: ");
        JTextField txtSearch = new JTextField(20);
        searchPanel.add(lblSearch);
        searchPanel.add(txtSearch);
        searchPanel.setBackground(new Color(220, 230, 240));
        searchPanel.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));

        // Combine top and search panels with vertical BoxLayout
        JPanel northContainer = new JPanel();
        northContainer.setLayout(new BoxLayout(northContainer, BoxLayout.Y_AXIS));
        northContainer.add(topPanel);
        northContainer.add(searchPanel);

        add(northContainer, BorderLayout.NORTH);

        // Table setup with bigger font and row height
        lecturerTableModel = new DefaultTableModel(new Object[]{"ID", "Name", "Email", "Gender", "Age", "Phone", "Department"}, 0) 
        {
            @Override
            public boolean isCellEditable(int row, int column) 
            {
                return false;
            }
        };

        lecturerTable = new JTable(lecturerTableModel);
        lecturerTable.setAutoCreateRowSorter(true);
        lecturerTable.setRowHeight(30);
        lecturerTable.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        lecturerTable.getTableHeader().setFont(new Font("Segoe UI", Font.BOLD, 14));

        // Center align table cells
        DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
        centerRenderer.setHorizontalAlignment(SwingConstants.CENTER);
        for (int i = 0; i < lecturerTable.getColumnCount(); i++) 
        {
            lecturerTable.getColumnModel().getColumn(i).setCellRenderer(centerRenderer);
        }
        
        // Set preferred column widths
        int[] columnWidths = {10, 150, 150, 20, 10, 80, 120};
        TableColumnModel columnModel = lecturerTable.getColumnModel();
        
        for (int i = 0; i < columnWidths.length; i++) 
        {
            columnModel.getColumn(i).setPreferredWidth(columnWidths[i]);
        }

        JScrollPane scrollPane = new JScrollPane(lecturerTable);
        scrollPane.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));
        add(scrollPane, BorderLayout.CENTER);

        // Header colors
        JTableHeader header = lecturerTable.getTableHeader();
        header.setBackground(new Color(70, 130, 180)); 
        header.setForeground(Color.WHITE);

        TableRowSorter<DefaultTableModel> rowSorter = new TableRowSorter<>(lecturerTableModel);
        lecturerTable.setRowSorter(rowSorter);

        // Live search filtering
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

        // Mouse hover highlight for rows
        lecturerTable.addMouseMotionListener(new MouseMotionAdapter() 
        {
            int hoveredRow = -1;
            @Override
            public void mouseMoved(MouseEvent e) 
            {
                int row = lecturerTable.rowAtPoint(e.getPoint());
                if (row != hoveredRow) 
                {
                    hoveredRow = row;
                    lecturerTable.repaint();
                }
            }
        });

        lecturerTable.setDefaultRenderer(Object.class, new DefaultTableCellRenderer() 
        {
            @Override public Component getTableCellRendererComponent(JTable table, Object value,
                                                           boolean isSelected, boolean hasFocus,
                                                           int row, int column) 
            {
                Component c = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
                
                if (!isSelected) 
                {
                    int hoverRow = table.getMousePosition() != null ? table.rowAtPoint(table.getMousePosition()) : -1;
                    c.setBackground(row == hoverRow ? new Color(230, 240, 255) : new Color(245, 245, 255));
                }
                return c;
            }
        });

        // Pagination panel with padding
        JPanel paginationPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        paginationPanel.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));
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
                loadLecturerData();
            }
        });
        btnNextPage.addActionListener(e -> 
        {
            if (currentPage < totalPages) 
            {
                currentPage++;
                loadLecturerData();
            }
        });

        // Status label with padding
        lblStatus = new JLabel("Status: Ready");
        lblStatus.setBorder(BorderFactory.createEmptyBorder(5, 20, 5, 20));
        lblStatus.setAlignmentX(Component.CENTER_ALIGNMENT);

        // Button panel with padding
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        buttonPanel.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));
        JButton btnAdd = new JButton("Add Lecturer");
        JButton btnEdit = new JButton("Edit Selected");
        JButton btnDelete = new JButton("Delete Selected");
        buttonPanel.add(btnAdd);
        buttonPanel.add(btnEdit);
        buttonPanel.add(btnDelete);

        // Bottom container with vertical BoxLayout and background color
        JPanel southContainer = new JPanel();
        southContainer.setLayout(new BoxLayout(southContainer, BoxLayout.Y_AXIS));
        southContainer.add(paginationPanel);
        southContainer.add(lblStatus);
        southContainer.add(buttonPanel);
        southContainer.setBackground(new Color(220, 230, 240));
        paginationPanel.setBackground(new Color(220, 230, 240));
        buttonPanel.setBackground(new Color(220, 230, 240));

        // Buttons color styling
        Color btnBlue = new Color(100, 149, 237);
        btnAdd.setBackground(btnBlue);
        btnAdd.setForeground(Color.WHITE);
        btnEdit.setBackground(btnBlue);
        btnEdit.setForeground(Color.WHITE);
        btnDelete.setBackground(btnBlue);
        btnDelete.setForeground(Color.WHITE);

        add(southContainer, BorderLayout.SOUTH);

        // Button event handlers
        btnAdd.addActionListener(e -> 
        {
            updateStatus("Add lecturer clicked");
            showLecturerDialog(null);
        });
        btnEdit.addActionListener(e -> editSelectedLecturer());
        btnDelete.addActionListener(e -> deleteSelectedLecturer());

        loadLecturerData();
    }

    //method to update status
    private void updateStatus(String message) 
    {
        if (lblStatus != null) 
        {
            lblStatus.setText("Status: " + message);
        }
    }

    //method to load Lecturer data
    private void loadLecturerData() 
    {
        lecturerTableModel.setRowCount(0);
        fullLecturerList = LecturerController.getAllLecturers();

        int totalRows = fullLecturerList.size();
        totalPages = (int) Math.ceil((double) totalRows / rowsPerPage);
        if (totalPages == 0) totalPages = 1;

        if (currentPage > totalPages) currentPage = totalPages;

        int start = (currentPage - 1) * rowsPerPage;
        int end = Math.min(start + rowsPerPage, totalRows);
        for (int i = start; i < end; i++) 
        {
            Lecturer l = fullLecturerList.get(i);
            lecturerTableModel.addRow(new Object[]
            {
                    l.getLecturerId(),
                    l.getName(),
                    l.getEmail(),
                    l.getGender(),
                    l.getAge(),
                    l.getPhoneNo(),
                    l.getDepartmentName()
            });
        }

        updateStatus("Page " + currentPage + " of " + totalPages + " | Total Lecturers: " + totalRows);
        btnPrevPage.setEnabled(currentPage > 1);
        btnNextPage.setEnabled(currentPage < totalPages);
        lblPageStatus.setText("Page " + currentPage + " of " + totalPages);
    }

    //dialog box to add and edit Lecturers
    private void showLecturerDialog(Lecturer lecturer) 
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

        if (lecturer != null) 
        {
            txtId.setText(String.valueOf(lecturer.getLecturerId()));
            txtId.setEnabled(false);
            txtName.setText(lecturer.getName());
            txtEmail.setText(lecturer.getEmail());
            txtAge.setText(String.valueOf(lecturer.getAge()));
            txtPhone.setText(String.valueOf(lecturer.getPhoneNo()));
            
            if ("Male".equals(lecturer.getGender())) male.setSelected(true);
            else female.setSelected(true);
            
            departmentCombo.setSelectedItem(lecturer.getDepartmentName());
        }
        
        else 
        {
            txtId.setText("Auto-generated");
            txtId.setEnabled(false);
            male.setSelected(true);
        }

        JPanel panel = new JPanel(new GridLayout(0, 1));
        panel.add(new JLabel("Lecturer ID:"));
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

        int result = JOptionPane.showConfirmDialog(this, panel, lecturer == null ? "Add Lecturer" : "Edit Lecturer", JOptionPane.OK_CANCEL_OPTION);
        if (result == JOptionPane.OK_OPTION) 
        {
            try 
            {
                String gender = male.isSelected() ? "Male" : "Female";
                String depName = (String) departmentCombo.getSelectedItem();
                int depId = DepartmentDAO.getDepartmentIdByName(depName);

                Lecturer l = new Lecturer(
                        lecturer != null ? lecturer.getLecturerId() : 0,
                        txtName.getText(),
                        txtEmail.getText(),
                        gender,
                        Integer.parseInt(txtAge.getText()),
                        Integer.parseInt(txtPhone.getText()),
                        depId,
                        depName
                );

                boolean success = (lecturer == null) ? LecturerController.addLecturer(l) : LecturerController.updateLecturer(l);
                if (success) 
                {
                    updateStatus(lecturer == null ? "Lecturer added." : "Lecturer updated.");
                    loadLecturerData();
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

    //method to edit lecturer details
    private void editSelectedLecturer() 
    {
        int row = lecturerTable.getSelectedRow();
        if (row == -1) 
        {
            JOptionPane.showMessageDialog(this, "Select a row to edit.");
            return;
        }
        row = lecturerTable.convertRowIndexToModel(row);
        Lecturer lecturer = new Lecturer(
                (int) lecturerTableModel.getValueAt(row, 0),
                (String) lecturerTableModel.getValueAt(row, 1),
                (String) lecturerTableModel.getValueAt(row, 2),
                (String) lecturerTableModel.getValueAt(row, 3),
                (int) lecturerTableModel.getValueAt(row, 4),
                (int) lecturerTableModel.getValueAt(row, 5),
                DepartmentDAO.getDepartmentIdByName((String) lecturerTableModel.getValueAt(row, 6)),
                (String) lecturerTableModel.getValueAt(row, 6)
        );
        showLecturerDialog(lecturer);
    }

    //method to delete lecturer
    private void deleteSelectedLecturer() 
    {
        int row = lecturerTable.getSelectedRow();
        if (row == -1) 
        {
            JOptionPane.showMessageDialog(this, "Select a row to delete.");
            return;
        }
        
        row = lecturerTable.convertRowIndexToModel(row);
        int id = (int) lecturerTableModel.getValueAt(row, 0);
        int confirm = JOptionPane.showConfirmDialog(this, "Are you sure to delete lecturer ID " + id + "?", "Confirm", JOptionPane.YES_NO_OPTION);
        if (confirm == JOptionPane.YES_OPTION) 
        {
            if (LecturerController.deleteLecturer(id)) 
            {
                updateStatus("Lecturer deleted successfully.");
                loadLecturerData();
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
