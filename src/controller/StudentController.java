/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controller;

//imported files
import model.Student;
import dao.StudentDAO;

//imported classes
import java.util.*;

public class StudentController 
{    
    //add new student
    public static boolean addStudent(Student student) 
    { return StudentDAO.addStudent(student); }

    //update student details
    public static boolean updateStudent(Student student) 
    { return StudentDAO.updateStudent(student); }

    //delete student record
    public static boolean deleteStudent(int id) 
    { return StudentDAO.deleteStudent(id); }

    //get list of all students
    public static List<Student> getAllStudents() 
    { return StudentDAO.getAllStudents(); }
    
    //get student name using student id
    public static String getStudentNameById(int id) 
    { return StudentDAO.getStudentNameById(id); }
}