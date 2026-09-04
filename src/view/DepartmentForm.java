/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package view;

//imported files
import model.Department;
import controller.DepartmentController;

//imported libraries
import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.table.*;
import java.awt.*;
import java.awt.event.*;
import java.util.List;

public class DepartmentForm extends JFrame 
{
    private JTable departmentTable;
    private DefaultTableModel departmentTableModel;

    private JLabel lblTitle;
    private JButton btnBack;

    private int currentPage = 1;
    private int rowsPerPage = 8;
    private int totalPages = 1;
    private final JButton btnPrevPage, btnNextPage;
    private final JLabel lblPageStatus, lblStatus;

    private List<Department> fullDepartmentList;

    public DepartmentForm() 
    {
        setTitle("Department Management");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setSize(1000, 600);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        // Top panel
        JPanel topPanel = new JPanel(new BorderLayout());
        topPanel.setBackground(new Color(0, 51, 102));
        topPanel.setPreferredSize(new Dimension(getWidth(), 70));
        topPanel.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));

        ImageIcon rawIcon = new ImageIcon(getClass().getResource("/images/departments.png"));
        Image scaled = rawIcon.getImage().getScaledInstance(60, 60, Image.SCALE_SMOOTH);
        ImageIcon scaledIcon = new ImageIcon(scaled);
        JLabel lblLogo = new JLabel(scaledIcon);
        lblLogo.setPreferredSize(new Dimension(60, 60));
        lblLogo.setHorizontalAlignment(SwingConstants.LEFT);
        
        lblTitle = new JLabel("Department Management");
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

        // Search panel
        JPanel searchPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        JLabel lblSearch = new JLabel("Search Department Name: ");
        JTextField txtSearch = new JTextField(20);
        searchPanel.add(lblSearch);
        searchPanel.add(txtSearch);
        searchPanel.setBackground(new Color(220, 230, 240));

        JPanel northContainer = new JPanel();
        northContainer.setLayout(new BoxLayout(northContainer, BoxLayout.Y_AXIS));
        northContainer.add(topPanel);
        northContainer.add(searchPanel);

        add(northContainer, BorderLayout.NORTH);

        // Table setup
        departmentTableModel = new DefaultTableModel(new Object[]{"ID", "Department Name"}, 0) 
        {
            @Override
            public boolean isCellEditable(int row, int column) 
            {
                return false;
            }
        };

        departmentTable = new JTable(departmentTableModel);
        departmentTable.setFont(new Font("Segoe UI", Font.PLAIN, 16));
        departmentTable.setRowHeight(36);

        // Center align all columns
        DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
        centerRenderer.setHorizontalAlignment(SwingConstants.CENTER);
        for (int i = 0; i < departmentTable.getColumnCount(); i++) 
        {
            departmentTable.getColumnModel().getColumn(i).setCellRenderer(centerRenderer);
        }

        JScrollPane scrollPane = new JScrollPane(departmentTable);

        JTableHeader header = departmentTable.getTableHeader();
        header.setBackground(new Color(70, 130, 180));
        header.setForeground(Color.WHITE);
        header.setFont(new Font("Segoe UI", Font.BOLD, 18));

        TableRowSorter<DefaultTableModel> rowSorter = new TableRowSorter<>(departmentTableModel);
        departmentTable.setRowSorter(rowSorter);

        txtSearch.getDocument().addDocumentListener(new DocumentListener() 
        {
            @Override public void insertUpdate(DocumentEvent e) { filter(); }
            @Override public void removeUpdate(DocumentEvent e) { filter(); }
            @Override public void changedUpdate(DocumentEvent e) { filter(); }

            private void filter() 
            {
                String text = txtSearch.getText();
                rowSorter.setRowFilter(text.trim().length() == 0 ? null : RowFilter.regexFilter("(?i)" + text, 1));
            }
        });

        departmentTable.addMouseMotionListener(new MouseMotionAdapter() 
        {
            int hoveredRow = -1;
            @Override public void mouseMoved(MouseEvent e) 
            {
                int row = departmentTable.rowAtPoint(e.getPoint());
                if (row != hoveredRow) 
                {
                    hoveredRow = row;
                    departmentTable.repaint();
                }
            }
        });

        departmentTable.setDefaultRenderer(Object.class, new DefaultTableCellRenderer() 
        {
            @Override public Component getTableCellRendererComponent(JTable table, Object value,
                                                           boolean isSelected, boolean hasFocus,
                                                           int row, int column) 
            {
                Component c = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
                setHorizontalAlignment(SwingConstants.CENTER);
                if (!isSelected) 
                {
                    int hoverRow = table.getMousePosition() != null ? table.rowAtPoint(table.getMousePosition()) : -1;
                    c.setBackground(row == hoverRow ? new Color(230, 240, 255) : new Color(245, 245, 255));
                }
                return c;
            }
        });

        // Uniform spacing for table
        JPanel centerPanel = new JPanel(new BorderLayout());
        centerPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20)); 
        centerPanel.add(scrollPane, BorderLayout.CENTER);
        add(centerPanel, BorderLayout.CENTER);

        // Pagination panel
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
                loadDepartmentData();
            }
        });

        btnNextPage.addActionListener(e -> 
        {
            if (currentPage < totalPages) 
            {
                currentPage++;
                loadDepartmentData();
            }
        });

        lblStatus = new JLabel("Status: Ready");
        lblStatus.setBorder(BorderFactory.createEmptyBorder(5, 10, 5, 10));
        lblStatus.setAlignmentX(Component.CENTER_ALIGNMENT);

        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        JButton btnAdd = new JButton("Add Department");
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

        btnAdd.addActionListener(e -> showDepartmentDialog(null));
        btnEdit.addActionListener(e -> editSelectedDepartment());
        btnDelete.addActionListener(e -> deleteSelectedDepartment());

        loadDepartmentData();
    }

    private void updateStatus(String message) 
    {
        lblStatus.setText("Status: " + message);
    }

    private void loadDepartmentData() 
    {
        departmentTableModel.setRowCount(0);
        fullDepartmentList = DepartmentController.getAllDepartments();

        int totalRows = fullDepartmentList.size();
        totalPages = (int) Math.ceil((double) totalRows / rowsPerPage);
        if (totalPages == 0) totalPages = 1;
        if (currentPage > totalPages) currentPage = totalPages;

        int start = (currentPage - 1) * rowsPerPage;
        int end = Math.min(start + rowsPerPage, totalRows);
        for (int i = start; i < end; i++) 
        {
            Department d = fullDepartmentList.get(i);
            departmentTableModel.addRow(new Object[]{d.getDepId(), d.getName()});
        }

        updateStatus("Page " + currentPage + " of " + totalPages + " | Total Departments: " + totalRows);
        btnPrevPage.setEnabled(currentPage > 1);
        btnNextPage.setEnabled(currentPage < totalPages);
        lblPageStatus.setText("Page " + currentPage + " of " + totalPages);
    }

    private void showDepartmentDialog(Department department) 
    {
        JTextField txtDepId = new JTextField();
        JTextField txtDepName = new JTextField();

        if (department != null) 
        {
            txtDepId.setText(String.valueOf(department.getDepId()));
            txtDepId.setEnabled(false);
            txtDepName.setText(department.getName());
        } 
        else 
        {
            txtDepId.setText("Auto-generated");
            txtDepId.setEnabled(false);
        }

        JPanel panel = new JPanel(new GridLayout(0, 1));
        panel.add(new JLabel("Department ID:"));
        panel.add(txtDepId);
        panel.add(new JLabel("Department Name:"));
        panel.add(txtDepName);

        int result = JOptionPane.showConfirmDialog(this, panel,
                department == null ? "Add Department" : "Edit Department",
                JOptionPane.OK_CANCEL_OPTION);

        if (result == JOptionPane.OK_OPTION) 
        {
            try 
            {
                String depName = txtDepName.getText().trim();
                if (depName.isEmpty()) throw new IllegalArgumentException("Department name cannot be empty.");

                Department d = new Department(department != null ? department.getDepId() : 0, depName);
                boolean success = (department == null)
                        ? DepartmentController.addDepartment(d)
                        : DepartmentController.updateDepartment(d);

                if (success) 
                {
                    updateStatus(department == null ? "Department added." : "Department updated.");
                    loadDepartmentData();
                } 
                else 
                {
                    JOptionPane.showMessageDialog(this, "Operation failed.", "Error", JOptionPane.ERROR_MESSAGE);
                }
            } 
            catch (Exception e) 
            {
                JOptionPane.showMessageDialog(this, "Invalid input: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    private void editSelectedDepartment() 
    {
        int row = departmentTable.getSelectedRow();
        if (row == -1) 
        {
            JOptionPane.showMessageDialog(this, "Select a row to edit.");
            return;
        }

        row = departmentTable.convertRowIndexToModel(row);
        Department department = new Department(
                (int) departmentTableModel.getValueAt(row, 0),
                (String) departmentTableModel.getValueAt(row, 1)
        );

        showDepartmentDialog(department);
    }

    private void deleteSelectedDepartment() 
    {
        int row = departmentTable.getSelectedRow();
        if (row == -1) 
        {
            JOptionPane.showMessageDialog(this, "Select a row to delete.");
            return;
        }

        row = departmentTable.convertRowIndexToModel(row);
        int id = (int) departmentTableModel.getValueAt(row, 0);

        int confirm = JOptionPane.showConfirmDialog(this,
                "Are you sure to delete department ID " + id + "?",
                "Confirm", JOptionPane.YES_NO_OPTION);

        if (confirm == JOptionPane.YES_OPTION) 
        {
            if (DepartmentController.deleteDepartment(id)) 
            {
                updateStatus("Department deleted successfully.");
                loadDepartmentData();
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
