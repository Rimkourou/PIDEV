/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package table_users;

import com.mysql.jdbc.PreparedStatement;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javax.swing.JOptionPane;

/**
 *
 * @author ASUS
 */
public class mysqlconnect  {
    Connection conn=null;
    public static Connection connectDb(){
        try {
           Class.forName("com.mysql.jdbc.Driver");
           Connection conn=DriverManager.getConnection("jdbc:mysql://localhost/project","root","");
           JOptionPane.showMessageDialog(null, "Connection Established");
           return conn;
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e);
            return null;
        }
      
    }
    public static ObservableList<User> getDataUsers(){
        Connection conn =connectDb();
        ObservableList<User> list=FXCollections.observableArrayList();
        try {
            PreparedStatement ps =(PreparedStatement) conn.prepareStatement("Select * from user");
            ResultSet rs=ps.executeQuery();
            
            while(rs.next())
            {
                list.add(new User(Integer.parseInt(rs.getString("id")), 
                        rs.getString(("cin")),
                        rs.getString("nom"),
                        rs.getString("prenom"),
                        rs.getString("role"), 
                        rs.getString("email"),
                        rs.getString("password")));
            }
        } catch (Exception e) {
        }
        return list;
    }
    public static ObservableList<Promotion> getDataPromotion(){
        Connection conn =connectDb();
        ObservableList<Promotion> listP=FXCollections.observableArrayList();
        try {
            PreparedStatement ps1 =(PreparedStatement) conn.prepareStatement("Select * from promotion");
            ResultSet rs1=ps1.executeQuery();
            
            while(rs1.next())
            {
                listP.add(new Promotion(Integer.parseInt(rs1.getString("id")), 
                        rs1.getString(("critaire")),
                        rs1.getDate("dateD"),
                        rs1.getDate("dateF"),
                        Integer.parseInt(rs1.getString("idUser"))
                ));
            }
        } catch (Exception e) {
        }
        return listP;
    }
    
}

