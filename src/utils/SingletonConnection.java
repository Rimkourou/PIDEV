package utils;

import javax.activation.DataSource;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class SingletonConnection {
    private String url="jdbc:mysql://127.0.0.1/project";
    private String login="root";
    private String pwd="";

    private Connection cnx;

    private static SingletonConnection instance;

    private SingletonConnection() {
        try {
            cnx= DriverManager.getConnection(url, login, pwd);
            System.out.println("Connexion");
        } catch (SQLException ex) {
            Logger.getLogger(DataSource.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public static SingletonConnection getInstance(){
        if(instance==null)
            instance=new SingletonConnection();
        return instance;
    }

    public Connection getCnx() {
        return cnx;
    }

}
