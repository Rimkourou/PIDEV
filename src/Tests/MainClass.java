/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Tests;

import Models.Categorie;
import Models.Produit;
import Services.ServiceCategorie;
import Services.ServiceProduit;
import java.sql.SQLException;

/**
 *
 * @author esprit
 */
public class MainClass {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws SQLException {
        ServiceCategorie  S=new ServiceCategorie();
        
        ServiceProduit  SP=new ServiceProduit();
        
        Categorie C = new Categorie(1,"AFFICHE");
        Categorie C1 = new Categorie(1,"A");
        Categorie C2 = new Categorie(2,"T-shirt");
        
        Produit P = new Produit(1,"affiche_Xmen,",5,40,1);
        Produit P1 = new Produit(1,"affiche_Winx,",10,30,1);
        Produit P2 = new Produit(3,"T-shirt_ironman,",20,20,2);
        //S.ajouter(C2);
        //S.modifier(C1);
//        S.supprimer(C2);
//        S.afficher().forEach(System.out::println);
        
        //S.afficher().forEach(System.out::println);
        
        //SP.ajouter(P2);
        //SP.afficher().forEach(System.out::println);
        //SP.modifier(P1);
        //SP.supprimer(P);
        //S.rechercheParId(1);
        //S.rechercheParId(1);
        //SP.rechercheParId(1);
        //SP.RechercheParNom("T-shirt_ironman,").forEach(System.out::println);
        SP.getTrierParNom().forEach(System.out::println);
    }
    
}
