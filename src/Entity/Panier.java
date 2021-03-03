/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Entity;

import Service.ServicePanier;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import utils.ConnexionBD;

/**
 *
 * @author user
 */
public class Panier {
      int id ;
  List<Produit> Produits ;
   User c ;
   double  Prix;
   int Qte;
       static Panier instance;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public List<Produit> getProduits() {
        return Produits;
    }

    public void setProduits(List<Produit> Produits) {
        this.Produits = Produits;
    }

    public User getC() {
        return c;
    }

    public void setC(User c) {
        this.c = c;
    }

    public double getPrix() {
        return Prix;
    }

    public void setPrix(double Prix) {
        this.Prix = Prix;
    }

    public int getQte() {
        return Qte;
    }

    public void setQte(int Qte) {
        this.Qte = Qte;
    }

    public static Panier getInstance() {
        return instance;
    }

    public static void setInstance(Panier instance) {
        Panier.instance = instance;
    }
       
private Panier(User c ){
     this.id = id;
 this.c = c;
 this.Prix = 0.00 ;
 this.Qte = 0;
 Produits = new ArrayList<>() ;
         
    }
 private Panier(int id ,User c ){
     this.id = id;
 this.c = c;
 this.Prix = 0.00 ;
 Produits = new ArrayList<>() ;
         
    }
        public static Panier getInstance(User C) throws SQLException {
                 Connection c= ConnexionBD.getInstance().getCnx();

           
                 
            PreparedStatement pt = c.prepareStatement("select * from panier where idUser ='"+C.getId()+"'");
           ResultSet rs = null;
             rs = pt.executeQuery();
               
                if (rs.first()  == false) {
                    
                    ServicePanier sp = new ServicePanier();
                    int id = sp.creePanier(C);
                    Panier P = new Panier(id,C);
                    return P ;
                }
                else {
                    Panier Pn = new Panier(rs.getInt(1),C);
                                        ServicePanier sp = new ServicePanier();
                    sp.getPanier(Pn);
                     return  Pn;
                 }
           
                 
        }}