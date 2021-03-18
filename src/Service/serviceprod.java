/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Service;

import Entity.Produit;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import utils.ConnexionBD;

/**
 *
 * @author Saif
 */
public class serviceprod  {
     Connection cn= ConnexionBD.getInstance().getCnx();
         
     public List<Produit> getListProd() throws SQLException{
List<Produit> list = new ArrayList<>() ; 
String r="SELECT * FROM `Produit`" ; 
try {
Statement stt=cn.createStatement() ; 
ResultSet rs=stt.executeQuery(r); 
while(rs.next()) {
    Produit p; 
     p = new Produit();
   
     p.setPrix(rs.getDouble(3));
     p.setNom(rs.getString(2));
     p.setId(rs.getInt(1));
    list.add(p) ; 
}

        }
catch (SQLException ex) {
    System.out.print(ex.getMessage());
}
return list ; 
} 
}
