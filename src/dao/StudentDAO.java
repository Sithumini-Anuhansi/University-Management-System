/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dao;

//imported files
import db.DBConnection;
import model.Student;

//imported libraries
import java.sql.*;
import java.util.*;

public class StudentDAO 
{
    //method to add student
    public static boolean addStudent(Student s) 
    {
        try 
        {
            Connection conn = DBConnection.getInstance().getConnection();
            String sql = "INSERT INTO students (name, email, gender, age, phone_no, department_id) VALUES (?, ?, ?, ?, ?, ?)";
            PreparedStatement ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            ps.setString(1, s.getName());
            ps.setString(2, s.getEmail());
            ps.setString(3, s.getGender());
            ps.setInt(4, s.getAge());
            ps.setInt(5, s.getPhoneNo());
            ps.setInt(6, s.getDepId());

            int affectedRows = ps.executeUpdate();
            if (affectedRows == 0) 
            {
                throw new SQLException("Adding student failed, no rows affected.");
            }

            try (ResultSet generatedKeys = ps.getGeneratedKeys()) 
            {
                if (generatedKeys.next()) 
                {
                      //set generated ID back to model
                    s.setStudentId(generatedKeys.getInt(1));
                } 
                else 
                {
                    throw new SQLException("Adding student failed, no ID obtained.");
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

    //method to update student
    public static boolean updateStudent(Student s) 
    {
        try 
        {
            Connection conn = DBConnection.getInstance().getConnection();
            String sql = "UPDATE students SET name = ?, email = ?, gender = ?, age = ?, phone_no = ?, department_id = ? WHERE student_id = ?";
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, s.getName());
            ps.setString(2, s.getEmail());
            ps.setString(3, s.getGender());
            ps.setInt(4, s.getAge());
            ps.setInt(5, s.getPhoneNo());
            ps.setInt(6, s.getDepId());
            ps.setInt(7, s.getStudentId());
            return ps.executeUpdate() > 0;
        } 
        catch (Exception e) 
        {
            e.printStackTrace();
            return false;
        }
    }

    //method to delete student
    public static boolean deleteStudent(int id) 
    {
        try 
        {
            Connection conn = DBConnection.getInstance().getConnection();
            String sql = "DELETE FROM students WHERE student_id = ?";
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

    //method to get a list of all students
    public static List<Student> getAllStudents() 
    {
        List<Student> students = new ArrayList<>();
        try 
        {
            Connection conn = DBConnection.getInstance().getConnection();
            String sql = "SELECT s.*, d.department_name FROM students s JOIN departments d ON s.department_id = d.department_id";

            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(sql);
            while (rs.next()) 
            {
                Student s = new Student(
                        rs.getInt("student_id"),
                        rs.getString("name"),
                        rs.getString("email"),
                        rs.getString("gender"),
                        rs.getInt("age"),
                        rs.getInt("phone_no"),
                        rs.getInt("department_id"),
                        rs.getString("department_name")
                );
                students.add(s);
            }
        } 
        catch (Exception e) 
        {
            e.printStackTrace();
        }
        return students;
    }
    
    //method to get student name by ID
    public static String getStudentNameById(int id) 
    {
        try 
        {
            Connection conn = DBConnection.getInstance().getConnection();
            String sql = "SELECT name FROM students WHERE student_id = ?";
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
}


