/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

public class Lecturer 
{
    //variables
    private int lecturerId;
    private String name;
    private String email;
    private String gender;
    private int age;
    private int phoneNo;
    private int depId;
    private String departmentName;

    //parameterized constructor
    public Lecturer(int lecturerId, String name, String email, String gender, int age, int phoneNo, int depId, String departmentName) 
    {
        this.lecturerId = lecturerId;
        this.name = name;
        this.email = email;
        this.gender = gender;
        this.age = age;
        this.phoneNo = phoneNo;
        this.depId = depId;
        this.departmentName = departmentName;
    }

    //getters
    public int getLecturerId() { return lecturerId; }
    public String getName() { return name; }
    public String getEmail() { return email; }
    public String getGender() { return gender; }
    public int getAge() { return age; }
    public int getPhoneNo() { return phoneNo; }
    public int getDepId() { return depId; }
    public String getDepartmentName() { return departmentName; }

    //setters
    public void setLecturerId(int lecturerId) { this.lecturerId = lecturerId; }
    public void setName(String name) { this.name = name; }
    public void setEmail(String email) { this.email = email; }
    public void setGender(String gender) { this.gender = gender; }
    public void setAge(int age) { this.age = age; }
    public void setPhoneNo(int phoneNo) { this.phoneNo = phoneNo; }
    public void setDepId(int depId) { this.depId = depId; }
    public void setDepartmentName(String departmentName) { this.departmentName = departmentName; }
}
