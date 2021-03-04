/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Services;

import Models.Categorie;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import utils.DataSource;

/**
 *
 * @author esprit
 */
public class ServiceCategorie implements IService<Categorie>{
    
    Connection cnx = DataSource.getInstance().getCnx();

    @Override
    public void ajouter(Categorie t) {
        try {
            String requete = "INSERT INTO categorie (id, nom) VALUES (?,?)";
            PreparedStatement pst = cnx.prepareStatement(requete);//analyse de requet
            pst.setInt(1, t.getId());
            pst.setString(2, t.getNom());
            pst.executeUpdate();
            System.out.println("Catégorie Ajoutée!");
            
        } catch(SQLException ex) {
            System.err.println(ex.getMessage());
        }
    }


    @Override
    public void modifier(Categorie t) {
        try {
            String requete = "UPDATE categorie SET nom=? WHERE id=?";
            PreparedStatement pst = cnx.prepareStatement(requete);
            pst.setString(1, t.getNom());
            pst.setInt(2, t.getId());
            pst.executeUpdate();
            System.out.println("categorie Modfié !");
        } catch(SQLException ex) {
            System.err.println(ex.getMessage());
        }
    }

     @Override
    public void supprimer(Categorie t) {
        try {
            String requete = "DELETE FROM categorie WHERE id=?";
            PreparedStatement pst = cnx.prepareStatement(requete);
            pst.setInt(1, t.getId());
            pst.executeUpdate();
            System.out.println("Categorie Supprimée !");
        } catch(SQLException ex) {
            System.err.println(ex.getMessage());
        }
    }
    
    @Override
    public List<Categorie> afficher() {
        List<Categorie> list = new ArrayList<>();
        
        try {
            String requete = "SELECT * FROM categorie";
            Statement pst = cnx.prepareStatement(requete);
            ResultSet rs = pst.executeQuery(requete);
            while (rs.next()) {
                list.add(new Categorie(rs.getInt(1), rs.getString(2))); 
            }
            
        } catch(SQLException ex) {
            System.err.println(ex.getMessage());
        }
        return list;
    }
    
    public Categorie rechercheParId(int id) {
        Categorie d = new Categorie();
        try {
            String req = "SELECT * FROM categorie where id = '"+id+"'";
            PreparedStatement pre = cnx.prepareStatement(req);
            ResultSet rs = pre.executeQuery();
            while (rs.next()){
                d.setId(rs.getInt(1));
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
                list.add(new Categorie(rs.getInt("id"), rs.getString("nom")));
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


}
