/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dao;

//imported files
import db.DBConnection;
import model.Lecturer;

//imported libraries
import java.sql.*;
import java.util.*;

public class LecturerDAO 
{
    //method to add lecturer
    public static boolean addLecturer(Lecturer l) 
    {
        try 
        {
            Connection conn = DBConnection.getInstance().getConnection();
            String sql = "INSERT INTO lecturers (name, email, gender, age, phone_no, department_id) VALUES (?, ?, ?, ?, ?, ?)";
            PreparedStatement ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            ps.setString(1, l.getName());
            ps.setString(2, l.getEmail());
            ps.setString(3, l.getGender());
            ps.setInt(4, l.getAge());
            ps.setInt(5, l.getPhoneNo());
            ps.setInt(6, l.getDepId());

            int affectedRows = ps.executeUpdate();
            if (affectedRows == 0) 
            {
                throw new SQLException("Adding lecturer failed, no rows affected.");
            }

            try (ResultSet generatedKeys = ps.getGeneratedKeys()) 
            {
                if (generatedKeys.next()) 
                {
                      //set generated ID back to model
                    l.setLecturerId(generatedKeys.getInt(1));
                } 
                else 
                {
                    throw new SQLException("Adding lecturer failed, no ID obtained.");
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

    //method to update lecturer
    public static boolean updateLecturer(Lecturer l) 
    {
        try 
        {
            Connection conn = DBConnection.getInstance().getConnection();
            String sql = "UPDATE lecturers SET name = ?, email = ?, gender = ?, age = ?, phone_no = ?, department_id = ? WHERE lecturer_id = ?";
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, l.getName());
            ps.setString(2, l.getEmail());
            ps.setString(3, l.getGender());
            ps.setInt(4, l.getAge());
            ps.setInt(5, l.getPhoneNo());
            ps.setInt(6, l.getDepId());
            ps.setInt(7, l.getLecturerId());
            return ps.executeUpdate() > 0;
        } 
        catch (Exception e) 
        {
            e.printStackTrace();
            return false;
        }
    }

    //method to delete lecturer
    public static boolean deleteLecturer(int id) 
    {
        try 
        {
            Connection conn = DBConnection.getInstance().getConnection();
            String sql = "DELETE FROM lecturers WHERE lecturer_id = ?";
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

    //method to get a list of all lecturers
    public static List<Lecturer> getAllLecturers() 
    {
        List<Lecturer> lecturers = new ArrayList<>();
        try 
        {
            Connection conn = DBConnection.getInstance().getConnection();
            String sql = "SELECT l.*, d.department_name FROM lecturers l JOIN departments d ON l.department_id = d.department_id";

            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(sql);
            while (rs.next()) 
            {
                Lecturer l = new Lecturer(
                        rs.getInt("lecturer_id"),
                        rs.getString("name"),
                        rs.getString("email"),
                        rs.getString("gender"),
                        rs.getInt("age"),
                        rs.getInt("phone_no"),
                        rs.getInt("department_id"),
                        rs.getString("department_name")
                );
                lecturers.add(l);
            }
        } 
        catch (Exception e) 
        {
            e.printStackTrace();
        }
        return lecturers;
    }
    
    //method to get department name by ID
    public static String getLecturerNameById(int id) 
    {
        try 
        {
            Connection conn = DBConnection.getInstance().getConnection();
            String sql = "SELECT name FROM lecturers WHERE lecturer_id = ?";
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) 
            {
                return rs.getString("name");
            }
        } 
        catch (Exception e) 
        {
            e.printStackTrace();
        }
        return null;
    }

    //method to get department ID by name
    public static int getLecturerIdByName(String name) 
    {
        try 
        {
            Connection conn = DBConnection.getInstance().getConnection();
            String sql = "SELECT lecturer_id FROM lecturers WHERE name = ?";
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, name);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) 
            {
                return rs.getInt("lecturer_id");
            }
        } 
        catch (Exception e) 
        {
            e.printStackTrace();
        }
        return -1;
    }
}
