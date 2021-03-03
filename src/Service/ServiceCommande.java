/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Service;

import Entity.User;
import Entity.Commande;
import Entity.Panier;
import Entity.Produit;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import utils.ConnexionBD;

/**
 *
 * @author Malvin
 */
public class ServiceCommande {
         Connection c= ConnexionBD.getInstance().getCnx();
     ServicePanier sp = new ServicePanier();
     
     
     
     
     
     
    public  Commande passerCommande (Panier p) throws SQLException {
        Commande C = new Commande(p.getC().getId(),sp.getPanier(p),sp.calculePrix(p));
          String pattern = "yyyy-MM-dd";
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);
        String date = simpleDateFormat.format(new Date());
          String rq1 = "INSERT INTO commande (`idUser`, `date`, `montant`) values ('"+C.getIdUser()+"','"+date+"','"+C.getMontant()+"')" ;
            Statement st1 = c.createStatement() ;
            st1.executeUpdate(rq1) ;
              String rq = "select * from commande where id =(Select max(id) from commande)" ;
            Statement stt = c.createStatement() ;
            ResultSet rs = stt.executeQuery(rq) ;
            if (rs.first()) {
        for (Produit P : C.getProduits()) {
            System.out.println("id :"+P.getId());
             String rq3 = "insert into commande_produit (idProduit,idCommande,Qte) values ('"+P.getId()+"','"+rs.getInt(1)+"','"+P.getQte()+"')" ;
            Statement st3 = c.createStatement() ;
            st3.executeUpdate(rq3) ;
        }}
            sp.viderPanier(p);
             String r = "select * from commande where id =(Select max(id) from commande)" ;
            Statement s = c.createStatement() ;
            ResultSet ra = s.executeQuery(r) ;
            if (ra.first()) {
                C.setId(ra.getInt(1));                C.setDate(ra.getString(3));

            }
             try {
                 mailing.sendMail(p.getC().getEmail(),"Bonjour, \n Nous somme ravis de vous annoncer que votre Commande a été confirmer \n Cordialement.");
             } catch (Exception ex) {
             }
        
            return C;
       
    }
    public int getLast() throws SQLException{
         String r = "select * from commande where id =(Select max(id) from commande)" ;
            Statement s = c.createStatement() ;
            ResultSet ra = s.executeQuery(r) ;
            if (ra.first()) {
               return ra.getInt(1);
            }
            return 1;
    }
      public  List<Produit> getProduitCommande (Commande p) throws SQLException {
             List<Produit> Prod = new ArrayList<>();
             p.setProduits(Prod);
      PreparedStatement pt2 = c.prepareStatement("select * from commande_produit where idCommande ='"+p.getId()+"'");
            ResultSet rs2 = pt2.executeQuery();
            while (rs2.next()) { 
 Produit P = new Produit();
                    P.setQte(rs2.getInt(3));
                   PreparedStatement pt3 = c.prepareStatement("select * from produit where id ='"+rs2.getInt(1)+"'");
            ResultSet rs3 = pt3.executeQuery();
            
            while (rs3.next()) { 
                P.setPrix(rs3.getInt(3));
                 P.setId(rs3.getInt(1));
                 P.setNom(rs3.getString(2));
               p.getProduits().add(P);
              
            }
     }
     return p.getProduits();}
      
      
      
       public void effaceCommande (Commande C )
    {
        try {
            PreparedStatement pt = c.prepareStatement("delete from commande_produit where idCommande='"+C.getId()+"'");
            pt.executeUpdate();
             PreparedStatement pt1 = c.prepareStatement("delete from commande where idCommande='"+C.getId()+"'");
            pt1.executeUpdate();
        } catch (SQLException ex) {
                    System.out.println(ex.getMessage());

        }
    }   
       public List<Commande> afficherCommandes() {
         List<Commande> list = new ArrayList<>() ;
        try {
            ServiceCommande sc = new ServiceCommande();
            String rq = "select * from commande" ;
            Statement st = c.createStatement() ;
            ResultSet rs = st.executeQuery(rq) ;
            while(rs.next()) {
                Commande c1 = new Commande() ;
                c1.setId(rs.getInt(1));
                c1.setDate(rs.getString("date"));
                c1.setMontant(rs.getDouble("montant"));
                 String rq2 = "select * from user where id='"+rs.getInt("idUser")+"'";
            Statement st2 = c.createStatement() ;
            ResultSet rs3 = st2.executeQuery(rq2) ;
            if (rs3.first()) {

                                User U = new User();
                                U.setEmail(rs3.getString("email")); 
                                            U.setId(rs3.getInt("id")); 
                                        c1.setIdUser(U.getId());
            }
           
                c1.setProduits(sc.getProduitCommande(c1));
                list.add(c1) ;
            }
        } catch (SQLException ex) {
        }
                    return list ;
     }
        public Commande ConsulterCommande(Commande F) throws SQLException {
                              Commande c1 = new Commande() ;
       
            ServiceCommande sc = new ServiceCommande();
            String rq = "select * from commande where id='"+F.getId()+"'" ;
            Statement st = c.createStatement() ;
            ResultSet rs = st.executeQuery(rq) ;
            while(rs.next()) {
                c1.setId(rs.getInt(1));
                c1.setDate(rs.getString("date"));
                c1.setMontant(rs.getDouble("montant"));
                c1.setProduits(sc.getProduitCommande(c1));
            }
            return c1;
        }
        
}
     
     
