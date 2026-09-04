/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dao;

//imported files
import db.DBConnection;
import model.Enrollment;

//imported libraries
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class EnrollmentDAO 
{
    //method to add new enrollment
    public static boolean addEnrollment(Enrollment enrollment) 
    {
        String sql = "INSERT INTO enrollments (student_id, course_id, department_id, date_enrolled) VALUES (?, ?, ?, ?)";
        try (Connection conn = DBConnection.getInstance().getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) 
        {
            stmt.setInt(1, enrollment.getStudentId());
            stmt.setInt(2, enrollment.getCourseId());
            stmt.setInt(3, enrollment.getDepartmentId());
            stmt.setDate(4, Date.valueOf(enrollment.getDateEnrolled()));

            return stmt.executeUpdate() > 0;
        } 
        catch (SQLException e) 
        {
            System.err.println("Error adding enrollment: " + e.getMessage());
            return false;
        }
    }

    //method to get all enrollments
    public static List<Enrollment> getAllEnrollments() 
    {
        List<Enrollment> list = new ArrayList<>();
        String sql = "SELECT * FROM enrollments ORDER BY enrollment_id DESC";

        try (Connection conn = DBConnection.getInstance().getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) 
        {
            while (rs.next()) 
            {
                Enrollment e = new Enrollment(
                        rs.getInt("enrollment_id"),
                        rs.getInt("student_id"),
                        rs.getInt("course_id"),
                        rs.getInt("department_id"),
                        rs.getDate("date_enrolled").toLocalDate()
                );
                list.add(e);
            }

        } 
        catch (SQLException e) 
        {
            System.err.println("Error fetching enrollments: " + e.getMessage());
        }
        return list;
    }
    
    //method to update existing enrollment details
    public static boolean updateEnrollment(Enrollment enrollment) 
    {
        String sql = "UPDATE enrollments SET student_id = ?, course_id = ?, department_id = ?, date_enrolled = ? WHERE enrollment_id = ?";
        try (Connection conn = DBConnection.getInstance().getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) 
        {
            stmt.setInt(1, enrollment.getStudentId());
            stmt.setInt(2, enrollment.getCourseId());
            stmt.setInt(3, enrollment.getDepartmentId());
            stmt.setDate(4, Date.valueOf(enrollment.getDateEnrolled()));
            stmt.setInt(5, enrollment.getEnrollmentId());

            return stmt.executeUpdate() > 0;
        } 
        catch (SQLException e) 
        {
            System.err.println("Error updating enrollment: " + e.getMessage());
            return false;
        }
    }


    //method to delete enrollment
    public static boolean deleteEnrollment(int enrollmentId) 
    {
        String sql = "DELETE FROM enrollments WHERE enrollment_id = ?";
        try (Connection conn = DBConnection.getInstance().getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) 
        {
            stmt.setInt(1, enrollmentId);
            return stmt.executeUpdate() > 0;
        } 
        catch (SQLException e) 
        {
            System.err.println("Error deleting enrollment: " + e.getMessage());
            return false;
        }
    }

    //method to get enrollment by ID
    public static Enrollment getEnrollmentById(int id) 
    {
        String sql = "SELECT * FROM enrollments WHERE enrollment_id = ?";
        try (Connection conn = DBConnection.getInstance().getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) 
        {
            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) 
            {
                return new Enrollment(
                        rs.getInt("enrollment_id"),
                        rs.getInt("student_id"),
                        rs.getInt("course_id"),
                        rs.getInt("department_id"),
                        rs.getDate("date_enrolled").toLocalDate()
                );
            }
        } 
        catch (SQLException e) 
        {
            System.err.println("Error retrieving enrollment: " + e.getMessage());
        }
        return null;
    }
}
