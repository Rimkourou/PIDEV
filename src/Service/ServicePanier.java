/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Service;

import Entity.User;
import Entity.Panier;
import Entity.Produit;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import Service.mailing;

import utils.ConnexionBD;

/**
 *
 * @author Malvin
 */
public class ServicePanier {
     Connection c= ConnexionBD.getInstance().getCnx();
     
    public int creePanier(User C) throws SQLException {
        int idPP = 0;
       Statement st = c.createStatement();
       String requeteInsert = "INSERT INTO panier (`idUser`) VALUES ('" + C.getId() + "');";
        try {
            st=c.createStatement();
            st.executeUpdate(requeteInsert);
            
            
        } catch (SQLException ex) {
        System.out.println(ex.getMessage());
        }
         String rq = "select * from panier where idUser = '"+C.getId()+"'" ;
            Statement stt = c.createStatement() ;
            ResultSet rs = stt.executeQuery(rq) ;
            if (rs.first()) {
                
           
         try {
       idPP = rs.getInt(1);
             String upd = "update user set idPanier ='"+rs.getInt(1)+"' where id ='"+C.getId()+"'" ;
            Statement st1 = c.createStatement() ;
            st1.executeUpdate(upd) ;
        } catch (SQLException ex) {
        System.out.println(ex.getMessage());
        }
         }
            return idPP;
    }
    public void AjouterProduit (Produit Pd, Panier P,int qte) throws SQLException
    {
        System.out.print(Pd.getId());
         PreparedStatement pt2 = c.prepareStatement("select * from panier_produit where idPanier ='"+P.getId()+"'and idProduit='"+Pd.getId()+"'");
            ResultSet rs = pt2.executeQuery();
        
            if (rs.first()) {
                String upd = "update panier_produit set Qte ='"+(rs.getInt(3)+qte)+"' where idPanier ='"+P.getId()+"'and idProduit='"+Pd.getId()+"'" ;
            Statement st1 = c.createStatement() ;
            st1.executeUpdate(upd) ;
            }
            else {
       Statement st = c.createStatement();
       String requeteInsert = "INSERT INTO panier_produit (`idPanier`, `idProduit`,`Qte`) VALUES ('" + P.getId() + "', '" + Pd.getId() + "','"+(qte)+"');";
        try {
            st=c.createStatement();
            st.executeUpdate(requeteInsert);
            P.getProduits().add(Pd);
        } catch (SQLException ex) {
        System.out.println(ex.getMessage());
        }
            }
                
    }
     public  List<Produit> getPanier (Panier p) throws SQLException {
             List<Produit> Prod = new ArrayList<>();
             p.setProduits(Prod);
      PreparedStatement pt2 = c.prepareStatement("select * from panier_produit where idPanier ='"+p.getId()+"'");
            ResultSet rs2 = pt2.executeQuery();
            while (rs2.next()) { 

                   PreparedStatement pt3 = c.prepareStatement("select * from produit where id ='"+rs2.getInt(2)+"'");
            ResultSet rs3 = pt3.executeQuery();
            
            while (rs3.next()) { 

                Produit P = new Produit(rs3.getInt(1),rs3.getString(2),rs3.getDouble(3),rs3.getInt(4),rs3.getInt(5));
               p.getProduits().add(P);
              
            }
     }
     return p.getProduits();}
        
    
      public double calculePrix (Panier p) throws SQLException {
          double somme = 0 ;
      PreparedStatement pt2 = c.prepareStatement("select * from panier_produit where idPanier ='"+p.getId()+"'");
            ResultSet rs2 = pt2.executeQuery();
            while (rs2.next()) { 
                   PreparedStatement pt3 = c.prepareStatement("select * from produit where id ='"+rs2.getInt(2)+"'");
            ResultSet rs3 = pt3.executeQuery();
            while (rs3.next()) { 
                somme += rs3.getInt(3) * rs2.getInt(3);

              
            } }
            return somme ;
     }
     
     public void AjouterProduitPanier (Produit Pd, Panier P) throws SQLException
    {
          List<Produit> Prod = P.getProduits() ;
          if (Prod == null )  {
              Prod = new ArrayList<>() ;
                 Prod.add(Pd);
               P.setProduits(Prod);
          }
          else {
                Prod.add(Pd);
               P.setProduits(Prod);
          }
       

            
  
    }
 
 public void effaceProduit (Panier p,Produit P)
    {
        try {
            PreparedStatement pt = c.prepareStatement("delete from panier_produit where idProduit='"+P.getId()+"' and idPanier='"+p.getId()+"'");
            pt.executeUpdate();
        } catch (SQLException ex) {
                    System.out.println(ex.getMessage());

        }
    }    
    public void viderPanier (Panier p)
    {
        try {
            PreparedStatement pt = c.prepareStatement("delete from panier_produit where idPanier='"+p.getId()+"'");
            pt.executeUpdate();
        } catch (SQLException ex) {
                    System.out.println(ex.getMessage());

        }
    }    
 
        
      
    }

