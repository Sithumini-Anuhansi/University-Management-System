/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controller;

//imported files
import model.Department;
import dao.DepartmentDAO;

//imported libraries
import java.util.List;

public class DepartmentController 
{
    //add new department   
    public static boolean addDepartment(Department department) 
    { return DepartmentDAO.addDepartment(department); }

    //update department details
    public static boolean updateDepartment(Department department) 
    { return DepartmentDAO.updateDepartment(department); }

    //delete department record
    public static boolean deleteDepartment(int id) 
    { return DepartmentDAO.deleteDepartment(id); }
    
    //get list of all departments
    public static List<Department> getAllDepartments() 
    { return DepartmentDAO.getAllDepartments(); }

    //get department id using department name
    public static int getDepartmentIdByName(String name) 
    { return DepartmentDAO.getDepartmentIdByName(name); }

    //getdepartment name using department id
    public static String getDepartmentNameById(int id) 
    { return DepartmentDAO.getDepartmentNameById(id); }
}