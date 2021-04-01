/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Service;

import Entity.Categorie;
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
import utils.ConnexionBD;

/**
 *
 * @author esprit
 */
public class ServiceCategorie implements IService<Categorie>{
    
    Connection cnx = ConnexionBD.getInstance().getCnx();

    
    public void ajouter(Categorie t) {
        try {
            String requete = "INSERT INTO categorie (idCategorie, nom) VALUES (?,?)";
            PreparedStatement pst = cnx.prepareStatement(requete);//analyse de requet
            pst.setInt(1, t.getIdCategorie());
            pst.setString(2, t.getNom());
            pst.executeUpdate();
            System.out.println("Catégorie Ajoutée!");
            
        } catch(SQLException ex) {
            System.err.println(ex.getMessage());
        }
    }


    
    public void modifier(Categorie t) {
        try {
            String requete = "UPDATE categorie SET nom=? WHERE idCategorie=?";
            PreparedStatement pst = cnx.prepareStatement(requete);
            pst.setString(1, t.getNom());
            pst.setInt(2, t.getIdCategorie());
            pst.executeUpdate();
            System.out.println("categorie Modfié !");
        } catch(SQLException ex) {
            System.err.println(ex.getMessage());
        }
    }

     
    public void supprimer(Categorie t) {
        try {
            String requete = "DELETE FROM categorie WHERE idCategorie=?";
            PreparedStatement pst = cnx.prepareStatement(requete);
            pst.setInt(1, t.getIdCategorie());
            pst.executeUpdate();
            System.out.println("Categorie Supprimée !");
        } catch(SQLException ex) {
            System.err.println(ex.getMessage());
        }
    }
    
public void supprimerparID (int idCategorie) {
        System.out.print(idCategorie);
        try { 
            String query ="DElETE FROM categorie WHERE idCategorie="+idCategorie ; 
            Statement st=cnx.createStatement();
            st.executeUpdate(query) ; 
           
            
        }catch (SQLException ex){
            System.out.println(ex.getMessage()) ; 
            
        }
                    System.out.println("le produit de l id = "+idCategorie+"a ete bien supprimer ") ; 

    }
    
      
    public ObservableList<Categorie> afficher() {
        ObservableList<Categorie> list = FXCollections.observableArrayList();
        
        try {
            String requete = "SELECT * FROM categorie";
            Statement pst = cnx.prepareStatement(requete);
            ResultSet rs = pst.executeQuery(requete);
            while (rs.next()) {
                list.add(new Categorie( rs.getInt(1), rs.getString(2)));
            }
            
        } catch(SQLException ex) {
            System.err.println(ex.getMessage());
        }
        return list;
    }
    
    public Categorie rechercheParId(int idCategorie) {
        Categorie d = new Categorie();
        try {
            String req = "SELECT * FROM categorie where idCategorie = '"+idCategorie+"'";
            PreparedStatement pre = cnx.prepareStatement(req);
            ResultSet rs = pre.executeQuery();
            while (rs.next()){
                d.setIdCategorie(rs.getInt(1));
                d.setNom(rs.getString(2));
            }
        
        if(d.getNom() == null){
            System.out.println("Categorie n'existe pas!");
        } else {
            System.out.println("Categorie de nom "+d.getNom()+" Trouvé!");
        }
        
        } catch(SQLException ex) {
           System.out.println("erreur de requette "+ex);
       }
       return d;
    }
    
    public List<Categorie> RechercheParNom(String search) {
        String req = "SELECT * FROM categorie WHERE nom="+(char)34+search+(char)34;
        List<Categorie> list = new ArrayList<>();
        try {
            PreparedStatement pre = cnx.prepareStatement(req);
            ResultSet rs = pre.executeQuery();
            while (rs.next()) {
                list.add(new Categorie(rs.getInt("idCategorie"), rs.getString("nom")));
            }
            
            if(list.isEmpty()){
                System.out.println("N'existe pas dans la base de donnée");
            }else{
                System.out.println("Categorie Trouvé!");
            }

        } catch (SQLException ex) {
            Logger.getLogger(ServiceCategorie.class.getName()).log(Level.SEVERE, null, ex);
        }
        return list;
        
    }

    //@Override
    public ObservableList<String> CategorieListeName() {
        String req = "select * from categorie";

        ObservableList<String> list = FXCollections.observableArrayList()    ;
        try {
            PreparedStatement pre = cnx.prepareStatement(req);
            ResultSet rs = pre.executeQuery();

            while (rs.next()) {
                list.add(rs.getString("nom"));
            }

        } catch (SQLException ex) {
            Logger.getLogger(ServiceCategorie.class.getName()).log(Level.SEVERE, null, ex);
        }
        return list;
    }

}
