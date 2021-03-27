/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Services;

import entities.Promotion;
import entities.User;
import java.io.FileInputStream;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javax.swing.JOptionPane;
import utils.Mysqlconncet;

import java.io.File;

/**
 *
 * @author ASUS
 */
public class PromotionService  implements iServices.iServicePromotion <Promotion>{
    Connection conn=null;
    ResultSet rs=null;
    PreparedStatement pst=null;

    public PromotionService() {
         conn = Mysqlconncet.getInstance().getCnx();
    }
    
    @Override
    public void Add_Promotions(Promotion t) throws SQLException {
        Statement st = conn.createStatement();
     String sql ="insert into promotion(critaire,dateD,dateF,idUser,castPhoto,reduction) values('"+t.getCritaire()+"','"+t.getDateD()+"','"+t.getDateF()+"','"+t.getIdUser()+"','"+t.getCastPhoto()+"','"+t.getReduction()+"')";
     JOptionPane.showMessageDialog(null, "ajout avec succé");
     st.executeUpdate(sql);
    }
    
    @Override
    public ObservableList<Promotion> read(){
        String req = "select * from promotion";

        ObservableList<Promotion> listP = FXCollections.observableArrayList();
        try {
            Statement st = conn.createStatement();
            rs = st.executeQuery(req);
            while (rs.next()) {
                listP.add(new Promotion(rs.getInt("id"),
                        rs.getString("critaire"),
                        rs.getDate("dateD"),
                        rs.getDate("dateF"),
                        rs.getInt("idUser"),
                        rs.getString("castPhoto"),
                        rs.getString("reduction")
                        
                ));
            }

        } catch (SQLException ex) {
            Logger.getLogger(PromotionService.class.getName()).log(Level.SEVERE, null, ex);
        }
        return listP;
    }

    @Override
    public void EditP(Promotion t) throws SQLException {
        PreparedStatement pt = conn.prepareStatement("update promotion set critaire= ? ,dateD= ?, dateF= ?,reduction=? where id =?");
        
        pt.setString(1, t.getCritaire());
        pt.setDate(2, t.getDateD());
        pt.setDate(3, t.getDateF());
        pt.setString(4,t.getReduction());
        
        pt.setInt(5, t.getId());
        pt.executeUpdate(); 
    }

    @Override
    public void DeleteP(int id) throws SQLException {
        String sql = "delete from promotion where id=? ";
        try {
            pst = conn.prepareStatement(sql);
            pst.setInt(1, id);
            pst.executeUpdate();
            System.out.println("promotion deleted successfully");

        } catch (SQLException ex) {
            Logger.getLogger(PromotionService.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
}
