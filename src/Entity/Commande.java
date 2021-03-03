/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Entity;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 *
 * @author user
 */
public class Commande {
    int id ;
    int idUser;
    String date;
  List<Produit> Produits ;
    double montant;

    public Commande() {
    }

    public Commande(int idUser, List<Produit> Produits, double montant) {
        this.idUser = idUser;
        this.Produits = Produits;
        this.montant = montant;
    }

    public List<Produit> getProduits() {
        return Produits;
    }

    public void setProduits(List<Produit> Produits) {
        this.Produits = Produits;
    }

    public double getMontant() {
        return montant;
    }

    public void setMontant(double montant) {
        this.montant = montant;
    }
    
    public Commande(int idUser) {
        this.idUser = idUser;
      
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getIdUser() {
        return idUser;
    }

    public void setIdUser(int idUser) {
        this.idUser = idUser;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }
    
}
