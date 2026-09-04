/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controller;

//imported files
import dao.EnrollmentDAO;
import model.Enrollment;

//imported libraries
import java.util.List;

public class EnrollmentController 
{
    //add new enrollment
    public static boolean addEnrollment(Enrollment enrollment) 
    { return EnrollmentDAO.addEnrollment(enrollment); }

    //update enrollment details
    public static boolean updateEnrollment(Enrollment enrollment) 
    { return EnrollmentDAO.updateEnrollment(enrollment); }
    
    //delete enrollment record
    public static boolean deleteEnrollment(int enrollmentId) 
    { return EnrollmentDAO.deleteEnrollment(enrollmentId); }
    
    //get list of all enrollments
    public static List<Enrollment> getAllEnrollments() 
    {  return EnrollmentDAO.getAllEnrollments(); }

    //get enrollment details using enrollment id
    public static Enrollment getEnrollmentById(int id) 
    { return EnrollmentDAO.getEnrollmentById(id); }
}