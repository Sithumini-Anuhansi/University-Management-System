/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dao;

//imported files
import db.DBConnection;
import model.User;

//imported libraries
import java.sql.*;

public class AuthDAO 
{
    //method to authenticate user when login 
    public static boolean authenticate(User user) 
    {
        boolean isValid = false;
        try 
        {
            //get connection from the singleton DBConnection class
            Connection conn = DBConnection.getInstance().getConnection();

            //sql query for getting user details
            String sql = "SELECT * FROM users WHERE username = ? AND password = ?";
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setString(1, user.getUsername());
            stmt.setString(2, user.getPassword());

            //execute query
            ResultSet rs = stmt.executeQuery();

            //check if result exists
            isValid = rs.next();

            //close resources
            rs.close();
            stmt.close();
        } 
        catch (SQLException e) 
        {
            e.printStackTrace();
        }
        return isValid;
    }
    
    //method to insert user data when register
    public static boolean insertUser(User user) 
    {
        try 
        {
            //get connection from the singleton DBConnection class
            Connection conn = DBConnection.getInstance().getConnection();

            //check if username already exists
            String checkSql = "SELECT username FROM users WHERE username = ?";
            PreparedStatement checkStmt = conn.prepareStatement(checkSql);
            checkStmt.setString(1, user.getUsername());
            ResultSet rs = checkStmt.executeQuery();

            //close resources if username already exists
            if (rs.next()) 
            {
                rs.close();
                checkStmt.close();
                return false;
            }
            rs.close();
            checkStmt.close();

            //sql query to insert new user
            String insertSql = "INSERT INTO users (username, password) VALUES (?, ?)";
            PreparedStatement insertStmt = conn.prepareStatement(insertSql);
            insertStmt.setString(1, user.getUsername());
            insertStmt.setString(2, user.getPassword());

            int rows = insertStmt.executeUpdate();
            insertStmt.close();

            return rows > 0;
        } 
        catch (SQLException e) 
        {
            e.printStackTrace();
            return false;
        }
    }
}