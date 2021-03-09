/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Services;

import entites.Promotion;
import entites.User;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.scene.control.cell.PropertyValueFactory;
import javax.swing.JOptionPane;
import utils.Mysqlconnect;

/**
 *
 * @author ASUS
 */
public class UserService implements iServices.iServiceUser<User>{
    Connection conn=null;
    ResultSet rs=null;
    PreparedStatement pst=null;
    
    public UserService() {
        conn = Mysqlconnect.getInstance().getCnx();
    }
   
   
    @Override
    public void Add_users(User u) throws SQLException{
     Statement st = conn.createStatement();
     String sql ="insert into user(cin,nom,prenom,role,email,password)values('"+u.getCin()+"','"+u.getNom()+"','"+u.getPrenom()+"','"+u.getRole()+"','"+u.getEmail()+"','"+u.getPassword()+"')";
     JOptionPane.showMessageDialog(null, "ajout avec succé");
     st.executeUpdate(sql);
    }
    
//    @Override
//    public ObservableList<User> read() throws SQLException {
//        List<User> ls = new ArrayList<User>();
//    Statement st = conn.createStatement();
//    String req = "select * from user";
//    ResultSet rs = st.executeQuery(req);
//     
//    while(rs.next()){
//        int id = rs.getInt("id");
//        String cin = rs.getString(2);
//        String nom = rs.getString(3);
//        String prenom = rs.getString(4);
//        String role = rs.getString(5);
//        String email = rs.getString(6);
//        String password = rs.getString(7);
//       
//        
//        User u = new User(id,cin, nom, prenom,role,email,password);
//        ls.add(u);
//       
//    }
//    return (ObservableList<User>) ls;
//    }
    @Override
    public ObservableList<User> read() {
        String req = "select * from user";

        ObservableList<User> listM = FXCollections.observableArrayList();
        try {
            Statement st = conn.createStatement();
            rs = st.executeQuery(req);
            while (rs.next()) {
                listM.add(new User(rs.getInt("id"),
                        rs.getString("cin"),
                        rs.getString("nom"),
                        rs.getString("prenom"),
                        rs.getString("role"),
                        rs.getString("email"),
                        //rs.getInt("idUser"),
                        rs.getString("password")
                ));
            }

        } catch (SQLException ex) {
            Logger.getLogger(UserService.class.getName()).log(Level.SEVERE, null, ex);
        }
        return listM;

    }
    
    @Override
     public void Edit(User u) throws SQLException {
        PreparedStatement pt = conn.prepareStatement("update user set cin= ? ,nom= ?, prenom= ? , role= ? , email=? , password=? where id =?");
        
        pt.setString(1, u.getCin());
        pt.setString(2, u.getNom());
        pt.setString(3, u.getPrenom());
        pt.setString(4, u.getRole());
        pt.setString(5, u.getEmail());
        pt.setString(6, u.getPassword());
        pt.setInt(7, u.getId());
        pt.executeUpdate(); 
    }
    

    @Override
    public void Delete(int id) throws SQLException {
        String sql = "delete from user where id=? ";
        try {
            pst = conn.prepareStatement(sql);
            pst.setInt(1, id);
            pst.executeUpdate();
            System.out.println("User deleted successfully");

        } catch (SQLException ex) {
            Logger.getLogger(UserService.class.getName()).log(Level.SEVERE, null, ex);
        }

   }
    


    }
    

