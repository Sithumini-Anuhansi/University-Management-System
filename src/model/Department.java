/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

public class Department 
{
    //variables
    private int depId;
    private String name;

    //parameterized constructor
    public Department(int depId, String name) 
    {
        this.depId = depId;
        this.name = name;
    }

    //getters
    public int getDepId() { return depId; }
    public String getName() { return name; }
    
    //setters
    public void setDepId(int depId) { this.depId = depId; }
    public void setName(String name) { this.name = name; }
}
