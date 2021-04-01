/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Entity;

/**
 *
 * @author esprit
 */
public class ProduitCategorie {
    private int id ;
    private String nom;
    private float prix;
    private int Qte;
    private String nomCategorie;
   

    public ProduitCategorie() {
    }

    public ProduitCategorie(int id, String nom, float prix, int Qte, String nomCategorie, String image) {
        this.id = id;
        this.nom = nom;
        this.prix = prix;
        this.Qte = Qte;
        this.nomCategorie = nomCategorie;
       
    }

    public ProduitCategorie(int id, String nom, float prix, int Qte, String nomCategorie) {
        this.id = id;
        this.nom = nom;
        this.prix = prix;
        this.Qte = Qte;
        this.nomCategorie = nomCategorie;
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

    public float getPrix() {
        return prix;
    }

    public void setPrix(float prix) {
        this.prix = prix;
    }

    public int getQte() {
        return Qte;
    }

    public void setQte(int Qte) {
        this.Qte = Qte;
    }

    public String getNomCategorie() {
        return nomCategorie;
    }

    public void setNomCategorie(String nomCategorie) {
        this.nomCategorie = nomCategorie;
    }

    @Override
    public String toString() {
        return "ProduitCategorie{" + "nom=" + nom + ", nomCategorie=" + nomCategorie + '}';
    }
    

    
    
}
