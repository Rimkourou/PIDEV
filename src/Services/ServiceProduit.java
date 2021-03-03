/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Services;

import Models.Produit;
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
public class ServiceProduit implements IService<Produit>{
    
        Connection cnx = DataSource.getInstance().getCnx();

    @Override
    public void ajouter(Produit t) {
         try {
            String requete = "INSERT INTO produit (id, nom , prix , Qte	, idCategorie) VALUES (?,?,?,?,?)";
            PreparedStatement pst = cnx.prepareStatement(requete);
            pst.setInt(1, t.getId());
            pst.setString(2, t.getNom());
            pst.setFloat(3, (float) t.getPrix());
            pst.setInt(4, t.getQte());
            pst.setInt(5, t.getIdCategorie());
            pst.executeUpdate();
            System.out.println("Produit Ajoutée!");
            
        } catch(SQLException ex) {
            System.err.println(ex.getMessage());
        }
    }

    @Override
    public void modifier(Produit t) {
        try {
            String requete = "UPDATE produit SET nom=? , prix=? , Qte=? , idCategorie=? WHERE id=?";
            PreparedStatement pst = cnx.prepareStatement(requete);
            pst.setString(1, t.getNom());
            pst.setFloat(2, (float) t.getPrix());
            pst.setInt(3, t.getQte());
            pst.setInt(4, t.getIdCategorie());
            pst.setInt(5, t.getId());
            pst.executeUpdate();
            System.out.println("Produit Modfié !");
        } catch(SQLException ex) {
            System.err.println(ex.getMessage());
        }
    }

    @Override
    public void supprimer(Produit t) {
        try {
            String requete = "DELETE FROM produit WHERE id=?";
            PreparedStatement pst = cnx.prepareStatement(requete);
            pst.setInt(1, t.getId());
            pst.executeUpdate();
            System.out.println("Produit Supprimée !");
        } catch(SQLException ex) {
            System.err.println(ex.getMessage());
        }
    }

    @Override
    public List<Produit> afficher() {
        List<Produit> list = new ArrayList<>();
        
        try {
            String requete = "SELECT * FROM produit";
            Statement pst = cnx.prepareStatement(requete);
            ResultSet rs = pst.executeQuery(requete);
            while (rs.next()) {
                list.add(new Produit(rs.getInt(1), rs.getString(2), rs.getFloat(3), rs.getInt(4), rs.getInt(5))); 
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
            PreparedStatement pre = cnx.prepareStatement(req);
            ResultSet rs = pre.executeQuery();
            while (rs.next()){
                d.setId(rs.getInt(1));
                d.setNom(rs.getString(2));              
                d.setPrix(rs.getFloat(3));
                d.setQte(rs.getInt(4));
                d.setIdCategorie(rs.getInt(5));
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
            PreparedStatement pre = cnx.prepareStatement(req);
            ResultSet rs = pre.executeQuery();
            while (rs.next()) {
                list.add(new Produit(rs.getInt("id"), rs.getString("nom"), rs.getFloat("prix"), rs.getInt("Qte"), rs.getInt("idCategorie")));
            }
            
            if(list.isEmpty()){
                System.out.println("N'existe pas dans la base de donnée");
            }else{
                System.out.println("Produit Trouvé!");
            }

        } catch (SQLException ex) {
            Logger.getLogger(ServiceProduit.class.getName()).log(Level.SEVERE, null, ex);
        }
        return list;
    }

    public List<Produit> getTrierParNom() throws SQLException {
        List<Produit> arr = new ArrayList<>();
        String req ="select * from produit ORDER BY nom ASC";
        PreparedStatement pre = cnx.prepareStatement(req);
        ResultSet rs = pre.executeQuery();
         while (rs.next()) {                
                   int id = rs.getInt(1);
                   String nom = rs.getString(2);
                   Float prix = rs.getFloat(3);
                   int Qte = rs.getInt(4);
                   int id_Categorie = rs.getInt(5);
                   Produit p = new Produit(id,nom,prix,Qte,id_Categorie);
            arr.add(p);
        }
         
        System.out.println("trié avec succés ");
        return arr;
    }
    
}
