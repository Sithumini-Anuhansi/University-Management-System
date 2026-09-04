/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controller;

//imported files
import model.Lecturer;
import dao.LecturerDAO;

//imported classes
import java.util.*;

public class LecturerController 
{
    //add new lecturer
    public static boolean addLecturer(Lecturer lecturer) 
    { return LecturerDAO.addLecturer(lecturer); }

    //update lecturer details
    public static boolean updateLecturer(Lecturer lecturer) 
    { return LecturerDAO.updateLecturer(lecturer); }

    //delete lecturer record
    public static boolean deleteLecturer(int id) 
    { return LecturerDAO.deleteLecturer(id); }

    //get list of all lecturers
    public static List<Lecturer> getAllLecturers() 
    { return LecturerDAO.getAllLecturers(); }
    
    //get lecturer id using lecturer name
    public static int getLecturerIdByName(String name) 
    { return LecturerDAO.getLecturerIdByName(name); }

    //get lecturer name using lecturer id
    public static String getDepartmentNameById(int id) 
    { return LecturerDAO.getLecturerNameById(id); }
}