/*
// * To change this license header, choose License Headers in Project Properties.
// * To change this template file, choose Tools | Templates
// * and open the template in the editor.
// */
package utils;

import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.sql.Connection;

/**
 *
 * @author Marvin
 */
public class ConnexionBD {
      			

    private static String url="jdbc:mysql://localhost:3306/project" ; //jdbc est un framework // 3306:port d'ecoute
    private static String usr="root";
    private static String pwd="";
    private static Connection cnx ;
    private static ConnexionBD mycon;
    
    public static Connection getCnx(){
        return cnx;
    }
    
    private ConnexionBD(){
        try{
        cnx=DriverManager.getConnection(url,usr,pwd); // creer une instance
           System.out.println("Connexion etablie!") ; 
    }
    catch (SQLException ex){
        Logger.getLogger(ConnexionBD.class.getName()).log(Level.SEVERE,null,ex);
    }
    }
        public static ConnexionBD getInstance(){
            if(mycon==null)
                mycon=new ConnexionBD() ;
            return mycon ;
        }
    }
    

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */



/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */


/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
//package utils;

/**
 *
 * @author rk
 */
//
//import java.sql.Connection;
//import java.sql.DriverManager;
//import java.sql.SQLException;
//import javafx.scene.control.Alert;
//
//public class ConnexionBD {
// private static ConnexionBD instance; 	
// private Connection cnx;
// private final String URL="jdbc:mysql://localhost:3306/project?useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC";
// private final String USERNAME="root";
// private final String PASSWORD="";
// 
//   public static void infoBox(String infoMessage,String title,String herdertext){
//                Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
//                alert.setContentText(infoMessage);
//                alert.setTitle(title);
//                alert.setHeaderText(herdertext);
//                alert.showAndWait();
//
//               }
// private ConnexionBD() {
//	  
//        try {
//	cnx = DriverManager.getConnection(URL,USERNAME,PASSWORD);
//	System.out.println("Connected to Database");
//	 }catch(SQLException ex) {
//		 System.out.println(ex.getMessage());
//                infoBox("bad connexion database",null,"FAILED");
//                System.exit(0);
//	 }
//}
// public static ConnexionBD getInstance() {//only one instance 
//	 if (instance==null)
//		 instance=new ConnexionBD();
//	return instance;
// }
//public Connection getCnx() {
//	return cnx;
//}

