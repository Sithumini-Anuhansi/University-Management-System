/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controller;

//imported files
import dao.AuthDAO;
import model.User;

public class AuthController 
{
    //handles user login by passing credentials to AuthDAO
    public static boolean login(String username, String password) 
    {
        User user = new User(username, password);
        return AuthDAO.authenticate(user);
    }
    
    //handles user registration by passing credentials to AuthDAO
    public static boolean register(String username, String password) 
    {
        User user = new User(username, password);
        return AuthDAO.insertUser(user);
    }
}