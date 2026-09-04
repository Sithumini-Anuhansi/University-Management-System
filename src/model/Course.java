/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

//imported libraries
import java.math.BigDecimal;

public class Course 
{
    //variables
    private int courseId;
    private String CourseName;
    private int departmentId;
    private int lecturerId;
    private String duration;
    private BigDecimal fee;

    //parameterized constructor
    public Course(int courseId, String CourseName, int departmentId, int lecturerId, String duration, BigDecimal fee) 
    {
        this.courseId = courseId;
        this.CourseName = CourseName;
        this.departmentId = departmentId;
        this.lecturerId = lecturerId;
        this.duration = duration;
        this.fee = fee;
    }

    //getters
    public int getCourseId() { return courseId; }
    public String getCourseName() { return CourseName; }
    public int getDepartmentId() { return departmentId; }
    public int getLecturerId() { return lecturerId; }
    public String getDuration() { return duration; }
    public BigDecimal getFee() { return fee; }

    //setters
    public void setCourseId(int courseId) { this.courseId = courseId; }
    public void setCourseName(String CourseName) { this.CourseName = CourseName; }
    public void setDepartmentId(int departmentId) { this.departmentId = departmentId; }
    public void setLecturerId(int lecturerId) { this.lecturerId = lecturerId; }
    public void setDuration(String duration) { this.duration = duration; }
    public void setFee(BigDecimal fee) { this.fee = fee; }
}
