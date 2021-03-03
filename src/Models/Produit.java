/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Models;

/**
 *
 * @author user
 */
public class Produit {
    int id ;
    String nom;
    double prix;
    int Qte;
    int idCategorie;
    
        public Produit(int id, String nom, double prix, int Qte, int idCategorie) {
        this.id = id;
        this.nom = nom;
        this.prix = prix;
        this.Qte = Qte;
        this.idCategorie = idCategorie;
    }

    public Produit(String nom, double prix, int Qte, int idCategorie) {
        this.nom = nom;
        this.prix = prix;
        this.Qte = Qte;
        this.idCategorie = idCategorie;
    }

    public Produit() {
    }


    public Produit(String nom, double prix, int idCategorie) {
        this.nom = nom;
        this.prix = prix;
        this.idCategorie = idCategorie;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public double getPrix() {
        return prix;
    }

    public void setPrix(double prix) {
        this.prix = prix;
    }

    public int getQte() {
        return Qte;
    }

    public void setQte(int Qte) {
        this.Qte = Qte;
    }

    public int getIdCategorie() {
        return idCategorie;
    }

    public void setIdCategorie(int idCategorie) {
        this.idCategorie = idCategorie;
    }

    @Override
    public String toString() {
        return "Produit{" + "id=" + id + ", nom=" + nom + ", prix=" + prix + ", Qte=" + Qte + ", idCategorie=" + idCategorie + '}';
    }
    
    
}