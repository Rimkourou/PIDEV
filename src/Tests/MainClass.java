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
        
        Produit P = new Produit(1,"affiche_Xmen,",5,40,1);
        Produit P1 = new Produit(2,"affiche_Winx,",10,30,1);

        //S.ajouter(C);
        //S.afficher().forEach(System.out::println);
        //S.modifier(C1);
        //S.afficher().forEach(System.out::println);
        //S.supprimer(C);
        SP.ajouter(P1);
        //SP.afficher().forEach(System.out::println);
        //SP.modifier(P1);
        //SP.supprimer(P1);
        //S.rechercheParId(1);
        //S.rechercheParId(1);
        //SP.rechercheParId(1);
        //SP.RechercheParNom("affiche_Xmen,").forEach(System.out::println);
        SP.getTrierParNom().forEach(System.out::println);
    }
    
}
