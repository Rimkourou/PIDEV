/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package utils;
import entities.Promotion;
import entities.User;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/**
 *
 * @author ASUS
 */
public class Mysqlconncet {
    
    private String url="jdbc:mysql://localhost/project";
    private String login="root";
    private String pwd="";

    private Connection cnx;

    private static Mysqlconncet instance;

    private Mysqlconncet() {
        try {
            cnx= DriverManager.getConnection(url, login, pwd);
            System.out.println("Connexion");
        } catch (SQLException ex) {
            Logger.getLogger(Mysqlconncet.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    public static ObservableList<User> getDataUsers(){
       // Connection conn =connectDb();
        Connection conn = Mysqlconncet.getInstance().getCnx();
        ObservableList<User> list=FXCollections.observableArrayList();
        try {
            PreparedStatement ps =(PreparedStatement) conn.prepareStatement("Select * from user");
            ResultSet rs=ps.executeQuery();
            
            while(rs.next())
            {
                list.add(new User(rs.getInt("id"), 
                        rs.getString("nom"),
                        rs.getString("prenom"),
                        rs.getDate("age"),
                        rs.getString("role"), 
                        rs.getString("email"),
                        rs.getString("password"),
                        rs.getString("state"),
                        rs.getString("s_ques"),
                        rs.getString("answer")));
            }
        } catch (Exception e) {
        }
        return list;
    }
    

    public static Mysqlconncet getInstance(){
        if(instance==null)
            instance=new Mysqlconncet();
        return instance;
    }

    public Connection getCnx() {
        return cnx;
    }
    public static ObservableList<Promotion> getDataPromotion(){
        Connection conn =Mysqlconncet.getInstance().getCnx();
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
                        Integer.parseInt(rs1.getString("idUser")),
                        rs1.getString(("castPhoto")),
                        rs1.getString("reduction")
                ));
            }
        } catch (Exception e) {
        }
        return listP;
    }
    

}


