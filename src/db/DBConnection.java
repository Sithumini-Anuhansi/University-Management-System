/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package db;

//imported libraries 
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBConnection 
{
    //variables
    private static DBConnection instance;
    private Connection conn;

    //database credentials
    private final String URL = "jdbc:mysql://localhost:3306/university_db";
    private final String USER = "root";
    private final String PASSWORD = "";

    //connect to the database
    private DBConnection() 
    {
        try 
        {
            Class.forName("com.mysql.cj.jdbc.Driver");
            this.conn = DriverManager.getConnection(URL, USER, PASSWORD);
            System.out.println("Database connected successfully.");
        } 
        catch (ClassNotFoundException | SQLException e) 
        {
            e.printStackTrace();
        }
    }

    //create a single instance
    public static synchronized DBConnection getInstance() 
    {
        if (instance == null) 
        {
            instance = new DBConnection();
        }
        return instance;
    }

    //return the db connection
    public Connection getConnection() 
    {
        try 
        {
            if (conn == null || conn.isClosed()) 
            {
                conn = DriverManager.getConnection(URL, USER, PASSWORD);
                System.out.println("Reconnected to the database.");
            }
        } 
        catch (SQLException e) 
        {
            e.printStackTrace();
        }
        return conn;
    }
}
 
    
    