/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dao;

//imported files
import db.DBConnection;
import model.Department;

//imported libraries
import java.sql.*;
import java.util.*;

public class DepartmentDAO 
{
    //method to add department
    public static boolean addDepartment(Department d) 
    {
        try 
        {
            Connection conn = DBConnection.getInstance().getConnection();
            String sql = "INSERT INTO departments (department_name) VALUES (?)";
            PreparedStatement ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            ps.setString(1, d.getName());

            int affectedRows = ps.executeUpdate();
            if (affectedRows == 0) 
            {
                throw new SQLException("Adding department failed, no rows affected.");
            }

            try (ResultSet generatedKeys = ps.getGeneratedKeys()) 
            {
                if (generatedKeys.next()) 
                {
                    // set generated ID back to model
                    d.setDepId(generatedKeys.getInt(1));
                } 
                else 
                {
                    throw new SQLException("Adding department failed, no ID obtained.");
                }
            }
            return true;
        } 
        catch (Exception e) 
        {
            e.printStackTrace();
            return false;
        }
    }

    //method to update department
    public static boolean updateDepartment(Department d) 
    {
        try 
        {
            Connection conn = DBConnection.getInstance().getConnection();
            String sql = "UPDATE departments SET department_name = ? WHERE department_id = ?";
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, d.getName());
            ps.setInt(2, d.getDepId());

            return ps.executeUpdate() > 0;
        } 
        catch (Exception e) 
        {
            e.printStackTrace();
            return false;
        }
    }

    //method to delete department
    public static boolean deleteDepartment(int id) 
    {
        try 
        {
            Connection conn = DBConnection.getInstance().getConnection();
            String sql = "DELETE FROM departments WHERE department_id = ?";
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setInt(1, id);

            return ps.executeUpdate() > 0;
        } 
        catch (Exception e) 
        {
            e.printStackTrace();
            return false;
        }
    }

    //method to get all departments
    public static List<Department> getAllDepartments() 
    {
        List<Department> list = new ArrayList<>();
        try 
        {
            Connection conn = DBConnection.getInstance().getConnection();
            String sql = "SELECT * FROM departments";
            PreparedStatement ps = conn.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) 
            {
                Department d = new Department(
                        rs.getInt("department_id"),
                        rs.getString("department_name")
                );
                list.add(d);
            }
        } 
        catch (Exception e) 
        {
            e.printStackTrace();
        }
        return list;
    }

    //method to get department name by ID
    public static String getDepartmentNameById(int id) 
    {
        try 
        {
            Connection conn = DBConnection.getInstance().getConnection();
            String sql = "SELECT department_name FROM departments WHERE department_id = ?";
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) 
            {
                return rs.getString("department_name");
            }
        } 
        catch (Exception e) 
        {
            e.printStackTrace();
        }
        return null;
    }

    //method to get department ID by name
    public static int getDepartmentIdByName(String name) 
    {
        try 
        {
            Connection conn = DBConnection.getInstance().getConnection();
            String sql = "SELECT department_id FROM departments WHERE department_name = ?";
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, name);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) 
            {
                return rs.getInt("department_id");
            }
        } 
        catch (Exception e) 
        {
            e.printStackTrace();
        }
        return -1;
    }
}
