/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controller;

//imported files
import dao.CourseDAO;
import model.Course;

//imported libraries
import java.util.List;

public class CourseController 
{
    //add new course 
    public static boolean addCourse(Course course) 
    { return CourseDAO.insertCourse(course); }

    //update course details
    public static boolean updateCourse(Course course) 
    { return CourseDAO.updateCourse(course); }

    //delete course record
    public static boolean deleteCourse(int courseId) 
    { return CourseDAO.deleteCourse(courseId); }

    //get list of all courses
    public static List<Course> getAllCourses() 
    { return CourseDAO.getAllCourses(); }
    
    //get course name using course id
    public static String getCourseNameById(int id) 
    { return CourseDAO.getCourseNameById(id); }
}