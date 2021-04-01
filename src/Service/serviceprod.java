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
 * @author user
 */





import Entity.Produit;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
//import java.time.Duration;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javax.management.Notification;
import tray.notification.NotificationType;
import tray.notification.TrayNotification;
import utils.ConnexionBD;
import javafx.util.Duration;

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
   
     p.setPrix(rs.getFloat(3));
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

    public void ajouter(Produit t) {
         try {
            String requete = "INSERT INTO produit (nom , prix , Qte , idCategorie, image) VALUES (?,?,?,?,?)";
            PreparedStatement pst = cn.prepareStatement(requete);
            pst.setString(1, t.getNom());
            pst.setFloat(2, (float) t.getPrix());
            pst.setInt(3, t.getQte());
            pst.setString(4, t.getIdCategorie());
            pst.setString(5, t.getImage());            
            pst.executeUpdate();
//            System.out.println("Produit Ajoutée!");
            TrayNotification tray = null;
            tray = new TrayNotification("TuniShow!", "Prod Added ", NotificationType.SUCCESS);
            tray.showAndDismiss(Duration.seconds(5));

            
            
        } catch(SQLException ex) {
            System.err.println(ex.getMessage());
        }
    }

   
    public void modifier(Produit t) {
        try {
            String requete = "UPDATE produit SET nom=? , prix=? , Qte=? , idCategorie=? , image=? WHERE id=?";
            PreparedStatement pst = cn.prepareStatement(requete);
            pst.setString(1, t.getNom());
            pst.setFloat(2, (float) t.getPrix());
            pst.setInt(3, t.getQte());
            pst.setString(4, t.getIdCategorie());
            pst.setInt(6, t.getId());
            pst.setString(5, t.getImage());
            pst.executeUpdate();
            System.out.println("Produit Modfié !");
        } catch(SQLException ex) {
            System.err.println(ex.getMessage());
        }
    }

   
    public void supprimer(Produit t) {
        try {
            String requete = "DELETE FROM produit WHERE id=?";
            PreparedStatement pst = cn.prepareStatement(requete);
            pst.setInt(1, t.getId());
            pst.executeUpdate();
            System.out.println("Produit Supprimée !");
        } catch(SQLException ex) {
            System.err.println(ex.getMessage());
        }
    }

public void supprimerparID (int id) {
        System.out.print(id);
        try { 
            String query ="DElETE FROM produit WHERE id="+id ; 
            Statement st=cn.createStatement();
            st.executeUpdate(query) ; 
           
            
        }catch (SQLException ex){
            System.out.println(ex.getMessage()) ; 
            
        }
                    System.out.println("le produit de l id = "+id+"a ete bien supprimer ") ; 

    }
   
    public ObservableList<Produit> afficher() {
        ObservableList<Produit> list = FXCollections.observableArrayList();
        
        try {
            String requete = "SELECT * FROM Produit";
            Statement pst = cn.prepareStatement(requete);
            ResultSet rs = pst.executeQuery(requete);
            while (rs.next()) {
                list.add(new Produit(rs.getInt(1), rs.getString(2), rs.getFloat(3), rs.getInt(4), rs.getString(5),rs.getString(6)));
            }
            
        } catch(SQLException ex) {
            System.err.println(ex.getMessage());
        }
        return list;
    }
    
    public ObservableList<Produit> afficherCombo(String variable) {
        ObservableList<Produit> list = FXCollections.observableArrayList();
        
        try {
            String requete = "SELECT * FROM Produit where idCategorie=?";
            PreparedStatement pst = cn.prepareStatement(requete);
            pst.setString(1, variable);
            ResultSet rs = pst.executeQuery();
            while (rs.next()) {
                list.add(new Produit(rs.getInt(1), rs.getString(2), rs.getFloat(3), rs.getInt(4), rs.getString(5),rs.getString(6)));
            }
            
        } catch(SQLException ex) {
            System.err.println(ex.getMessage());
        }
        return list;
    }
    
     
    
    public Produit rechercheParId(int id) {
        Produit d = new Produit();
        try {
            String req = "SELECT * FROM produit where id = '"+id+"'";
            PreparedStatement pre = cn.prepareStatement(req);
            ResultSet rs = pre.executeQuery();
            while (rs.next()){
                d.setId(rs.getInt(1));
                d.setNom(rs.getString(2));              
                d.setPrix(rs.getFloat(3));
                d.setQte(rs.getInt(4));
                d.setIdCategorie(rs.getString(5));
                d.setImage(rs.getString(6));
            }
        
        if(d.getNom() == null){
            System.out.println("Produit n'existe pas!");
        } else {
            System.out.println("Produit de nom "+d.getNom()+" Trouvé!");
        }
        
        } catch(SQLException ex) {
           System.out.println("erreur de requette "+ex);
       }
       return d;
    }
    
    public List<Produit> RechercheParNom(String search) {
        String req = "SELECT * FROM produit WHERE nom like"+(char)34+search+(char)34;
        List<Produit> list = new ArrayList<>();
        try {
            PreparedStatement pre = cn.prepareStatement(req);
            ResultSet rs = pre.executeQuery();
            while (rs.next()) {
                list.add(new Produit(rs.getInt("id"), rs.getString("nom"), rs.getFloat("prix"), rs.getInt("Qte"), rs.getString("idCategorie"), rs.getString("image")));
            }
            
            if(list.isEmpty()){
                System.out.println("N'existe pas dans la base de donnée");
            }else{
                System.out.println("Produit Trouvé!");
            }

        } catch (SQLException ex) {
            Logger.getLogger(serviceprod.class.getName()).log(Level.SEVERE, null, ex);
        }
        return list;
    }

    public List<Produit> getTrierParNom() throws SQLException {
        List<Produit> arr = new ArrayList<>();
        String req ="select * from produit ORDER BY nom ASC";
        PreparedStatement pre = cn.prepareStatement(req);
        ResultSet rs = pre.executeQuery();
         while (rs.next()) {                
                   int id = rs.getInt(1);
                   String nom = rs.getString(2);
                   Float prix = rs.getFloat(3);
                   int Qte = rs.getInt(4);
                   String id_Categorie = rs.getString(5);
                   String image = rs.getString(6);                   
                   Produit p = new Produit(id,nom,prix,Qte,id_Categorie,image);
            arr.add(p);
        }
         
        System.out.println("trié avec succés ");
        return arr;
    }
    
    public ObservableList<Produit> TrieParNom() throws SQLException {
        ObservableList<Produit> list = (ObservableList<Produit>) this.ListProd();
        Collections.sort(list, new Produit());
        Collections.reverse(list);
        return list;
    }
        public ObservableList<Produit> ListProd() throws SQLException{
    ObservableList<Produit> list =FXCollections.observableArrayList(); 
    String r="select * from Produit" ; 
    Statement stt=cn.createStatement() ; 
    ResultSet rs=stt.executeQuery(r); 
    while(rs.next()) {
        Produit p; 
         p = new Produit();

         p.setId(rs.getInt(1));
         p.setNom(rs.getString(2));
         p.setPrix(rs.getFloat(3));
         p.setQte(rs.getInt(4));
         p.setIdCategorie(rs.getString(5));
         p.setImage(rs.getString(6));


        list.add(p) ; 
    }


    return list ; 
    } 
}

    