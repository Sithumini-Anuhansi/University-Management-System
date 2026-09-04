/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dao;

//imported files
import db.DBConnection;

//imported libraries
import java.sql.*;

public class DashboardDAO 
{
    //method to get total count of table data
    public static int getTotal(String tableName) 
    {
        int count = 0;
        String query = "SELECT COUNT(*) FROM " + tableName;

        try (Connection conn = DBConnection.getInstance().getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(query)) 
        {

            if (rs.next()) count = rs.getInt(1);
        } 
        catch (SQLException e) 
        {
            e.printStackTrace();
        }
        return count;
    }

    //method to get total number of enrollments done withinn the day
    public static int getEnrollmentsToday() 
    {
        int count = 0;
        String query = "SELECT COUNT(*) FROM enrollments WHERE DATE(date_enrolled) = CURDATE()";

        try (Connection conn = DBConnection.getInstance().getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(query)) 
        {

            if (rs.next()) count = rs.getInt(1);
        } 
        catch (SQLException e) 
        {
            e.printStackTrace();
        }
        return count;
    }
}
