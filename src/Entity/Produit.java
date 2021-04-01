/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Entity;


import java.util.Comparator;
import javafx.scene.image.ImageView;

/**
 *
 * @author user
 */
public class Produit implements Comparator <Produit>{
    private int id ;
    private String nom;
    private float prix;
    private int Qte;
    private String idCategorie;
    private String image;
    ImageView im;

    public ImageView getIm() {
        return im;
    }

    public void setIm(ImageView im) {
        this.im = im;
    }
    
    

    public Produit(int id, String nom, float prix, int Qte, String idCategorie, String image) {
        this.id = id;
        this.nom = nom;
        this.prix = prix;
        this.Qte = Qte;
        this.idCategorie = idCategorie;
        this.image = image;
    }

    public Produit(int id, String nom, float prix, int Qte, String idCategorie) {
        this.id = id;
        this.nom = nom;
        this.prix = prix;
        this.Qte = Qte;
        this.idCategorie = idCategorie;
    }

    public Produit() {
    }

    public Produit(String nom, float prix, int Qte, String idCategorie) {
        this.nom = nom;
        this.prix = prix;
        this.Qte = Qte;
        this.idCategorie = idCategorie;
    }

    public Produit(String nom, float prix, int Qte, String idCategorie, String image) {
        this.nom = nom;
        this.prix = prix;
        this.Qte = Qte;
        this.idCategorie = idCategorie;
        this.image = image;
    }
    
    public Produit(int id, String nom, float prix, int Qte, String idCategorie, String image, ImageView im) {
        this.id = id;
        this.nom = nom;
        this.prix = prix;
        this.Qte = Qte;
        this.idCategorie = idCategorie;
        this.image = image;
        this.im= im;
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

    public String getIdCategorie() {
        return idCategorie;
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

    public void setIdCategorie(String idCategorie) {
        this.idCategorie = idCategorie;
    }

    public void setImage(String image) {
        this.image = image;
    }


    @Override
    public String toString() {
        return "Produit{" + "id=" + id + ", nom=" + nom + ", prix=" + prix + ", Qte=" + Qte + ", idCategorie=" + idCategorie + ", image=" + image + '}';
    }
    
    @Override
    public int compare(Produit o1, Produit o2) {
        return o1.getNom().compareTo(o2.getNom());
    }
}
