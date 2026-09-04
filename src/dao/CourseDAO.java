/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dao;

//imported files
import db.DBConnection;
import model.Course;

//imported libraries
import java.sql.*;
import java.util.*;

public class CourseDAO 
{
    //method to add new course
    public static boolean insertCourse(Course course) 
    {
        //sql query to add course details to database
        String sql = "INSERT INTO courses (course_name, department_id, lecturer_id, duration, fee) VALUES (?, ?, ?, ?, ?)";
        try (Connection conn = DBConnection.getInstance().getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) 
        {
            stmt.setString(1, course.getCourseName());
            stmt.setInt(2, course.getDepartmentId());
            stmt.setInt(3, course.getLecturerId());
            stmt.setString(4, course.getDuration());
            stmt.setBigDecimal(5, course.getFee());
            return stmt.executeUpdate() > 0;
        } 
        catch (Exception e) 
        {
            e.printStackTrace();
        }
        return false;
    }

    //method to update course
    public static boolean updateCourse(Course course) 
    {
        //sql query to edit course details in database
        String sql = "UPDATE courses SET course_name=?, department_id=?, lecturer_id=?, duration=?, fee=? WHERE course_id=?";
        try (Connection conn = DBConnection.getInstance().getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) 
        {
            stmt.setString(1, course.getCourseName());
            stmt.setInt(2, course.getDepartmentId());
            stmt.setInt(3, course.getLecturerId());
            stmt.setString(4, course.getDuration());
            stmt.setBigDecimal(5, course.getFee());
            stmt.setInt(6, course.getCourseId());
            return stmt.executeUpdate() > 0;
        } 
        catch (Exception e) 
        {
            e.printStackTrace();
        }
        return false;
    }

    //method to delete course record
    public static boolean deleteCourse(int courseId) 
    {
        //sql query to delete course course record database
        String sql = "DELETE FROM courses WHERE course_id=?";
        try (Connection conn = DBConnection.getInstance().getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) 
        {
            stmt.setInt(1, courseId);
            return stmt.executeUpdate() > 0;
        } 
        catch (Exception e) 
        {
            e.printStackTrace();
        }
        return false;
    }

    //method to list all courses
    public static List<Course> getAllCourses() 
    {
        List<Course> list = new ArrayList<>();
        String sql = "SELECT * FROM courses";
        try (Connection conn = DBConnection.getInstance().getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) 
        {
            while (rs.next()) 
            {
                Course course = new Course(
                    rs.getInt("course_id"),
                    rs.getString("course_name"),
                    rs.getInt("department_id"),
                    rs.getInt("lecturer_id"),
                    rs.getString("duration"),
                    rs.getBigDecimal("fee")
                );
                list.add(course);
            }
        } 
        catch (Exception e) 
        {
            e.printStackTrace();
        }
        return list;
    }
    
    //method to get course name by ID
    public static String getCourseNameById(int id) 
    {
        try 
        {
            Connection conn = DBConnection.getInstance().getConnection();
            String sql = "SELECT course_name FROM courses WHERE course_id = ?";
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) 
            {
                return rs.getString("course_name");
            }
        } 
        catch (Exception e) 
        { 
            e.printStackTrace(); 
        }
        return null;
    }
}