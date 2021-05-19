/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tn.esprit.TuniShow.entity;

import java.util.Comparator;
import javafx.scene.image.ImageView;


public class Produit {
    private int id ;
    private String nom;
    private float prix;
    private int Qte;
    private int idCategorie;
    private String image;
    private ImageView image1;

    public Produit( String nom, float prix, int Qte, int idCategorie, String image) {
        this.nom = nom;
        this.prix = prix;
        this.Qte = Qte;
        this.idCategorie = idCategorie;
        this.image = image;
    }

    public Produit(int id, String nom, float prix, int Qte, int idCategorie, String image) {
        this.id = id;
        this.nom = nom;
        this.prix = prix;
        this.Qte = Qte;
        this.idCategorie = idCategorie;
        this.image = image;
    }

    public int getIdCategorie() {
        return idCategorie;
    }

    public void setIdCategorie(int idCategorie) {
        this.idCategorie = idCategorie;
    }

    public Produit(int id, String nom, float prix, int Qte, int idCategorie, String image, ImageView image1) {
        this.id = id;
        this.nom = nom;
        this.prix = prix;
        this.Qte = Qte;
        this.idCategorie = idCategorie;
        this.image = image;
        this.image1 = image1;
    }
    
    
    



    public ImageView getImage1() {
        return image1;
    }

    public void setImage1(ImageView image1) {
        this.image1 = image1;
    }

    public Produit(int id, String nom, float prix, int Qte, int idCategorie, ImageView image1) {
        this.id = id;
        this.nom = nom;
        this.prix = prix;
        this.Qte = Qte;
        this.idCategorie = idCategorie;
        this.image1 = image1;
    }

    public Produit(int id, String nom, float prix, int Qte, int idCategorie) {
        this.id = id;
        this.nom = nom;
        this.prix = prix;
        this.Qte = Qte;
        this.idCategorie = idCategorie;
    }

    public Produit() {
    }

    public Produit(String nom, float prix, int Qte, int idCategorie) {
        this.nom = nom;
        this.prix = prix;
        this.Qte = Qte;
        this.idCategorie = idCategorie;
    }

    

    public int getId() {
        return id;
    }

    public String getNom() {
        return nom;
    }

    public float getPrix() {
        return prix;
    }

    public int getQte() {
        return Qte;
    }

   

    public String getImage() {
        return image;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public void setPrix(float prix) {
        this.prix = prix;
    }

    public void setQte(int Qte) {
        this.Qte = Qte;
    }

  
    public void setImage(String image) {
        this.image = image;
    }


    @Override
    public String toString() {
        return "Produit{" + "id=" + id + ", nom=" + nom + ", prix=" + prix + ", Qte=" + Qte + ", idCategorie=" + idCategorie + ", image=" + image + '}';
    }
    

}