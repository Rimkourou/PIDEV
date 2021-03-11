/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package utils;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author ASUS
 */
public class Mysqlconnect {
    
    private String url="jdbc:mysql://localhost/project";
    private String login="root";
    private String pwd="";

    private Connection cnx;

    private static Mysqlconnect instance;

    private Mysqlconnect() {
        try {
            cnx= DriverManager.getConnection(url, login, pwd);
            System.out.println("Connexion");
        } catch (SQLException ex) {
            Logger.getLogger(Mysqlconnect.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public static Mysqlconnect getInstance(){
        if(instance==null)
            instance=new Mysqlconnect();
        return instance;
    }

    public Connection getCnx() {
        return cnx;
    }

}
