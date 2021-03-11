/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package services;

import entities.User;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import utils.Mysqlconnect;

/**
 *
 * @author ASUS
 */
public class UserService implements iService.iServiceUser<User>{
    Connection conn=null;
    ResultSet rs=null;
    PreparedStatement pst=null;
    
    public UserService() {
        conn = Mysqlconnect.getInstance().getCnx();
    }
    @Override
    public void Add_users(User u) throws SQLException{
     Statement st = conn.createStatement();
     String sql ="insert into user(nom,prenom,role,email,password,state)values('"+u.getNom()+"','"+u.getPrenom()+"','"+u.getRole()+"','"+u.getEmail()+"','"+u.getPassword()+"','"+u.getState()+"')";
     JOptionPane.showMessageDialog(null, "ajout avec succé");
     st.executeUpdate(sql);
    }
    
    
    @Override
    public void login() throws SQLException{
     Statement st = conn.createStatement();
     String sql ="Select * from user where nom = ? and password = ? ";
     try {
            pst = conn.prepareStatement(sql);
            pst.executeUpdate();

        } catch (SQLException ex) {
            Logger.getLogger(UserService.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
}
