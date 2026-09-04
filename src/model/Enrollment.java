/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

//imported libraries
import java.time.LocalDate;

public class Enrollment 
{
    // variables
    private int enrollmentId;
    private int studentId;
    private int courseId;
    private int departmentId;
    private LocalDate dateEnrolled;

    // parameterized constructor
    public Enrollment(int enrollmentId, int studentId, int courseId, int departmentId, LocalDate dateEnrolled) 
    {
        this.enrollmentId = enrollmentId;
        this.studentId = studentId;
        this.courseId = courseId;
        this.departmentId = departmentId;
        this.dateEnrolled = dateEnrolled;
    }

    // getters
    public int getEnrollmentId() { return enrollmentId; }
    public int getStudentId() { return studentId; }
    public int getCourseId() { return courseId; }
    public int getDepartmentId() { return departmentId; }
    public LocalDate getDateEnrolled() { return dateEnrolled; }

    // setters
    public void setEnrollmentId(int enrollmentId) { this.enrollmentId = enrollmentId; }
    public void setStudentId(int studentId) { this.studentId = studentId; }
    public void setCourseId(int courseId) { this.courseId = courseId; }
    public void setDepartmentId(int departmentId) { this.departmentId = departmentId; }
    public void setDateEnrolled(LocalDate dateEnrolled) { this.dateEnrolled = dateEnrolled; }
}

