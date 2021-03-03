/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pidev;

import Entity.Commande;
import Entity.Panier;
import Entity.Produit;
import Service.ServiceCommande;
import Service.ServicePanier;
import Service.serviceprod;
import java.sql.SQLException;
import utils.ConnexionBD;
import Entity.User;

/**
 *
 * @author Malvin
 */
public class PIDEV1 {

    /**
     * @param args the command line arguments
     * @throws java.sql.SQLException
     */
    public static void main(String[] args) throws SQLException {

        User c = new User (1);
        c.setEmail("rim.kourou@esprit.tn");
        Panier P = Panier.getInstance(c);
        ServicePanier SP = new ServicePanier();
        serviceprod sp = new serviceprod();
        Produit pd = new Produit();
        pd.setId(1);
        pd.setPrix(100);
        pd.setQte(10);
        Produit pd2 = new Produit();
        pd2.setId(2);
        pd2.setPrix(80);
        pd2.setQte(8);
          Produit pd3 = new Produit();
        pd.setId(3);
        pd3.setPrix(80);
        pd3.setQte(8);
        SP.AjouterProduit(pd,P,2);
        SP.AjouterProduit(pd,P,5);
        SP.AjouterProduit(pd2,P,3);
        SP.AjouterProduit(pd3,P,3);
                            System.out.println("9bel suppresion");

        for(Produit c1 : SP.getPanier(P)) {
            System.out.print(c1.getId()+ " ");
        }
        SP.effaceProduit(P, pd3);
            System.out.println(" ba");

        for(Produit c1 : SP.getPanier(P)) {
            System.out.print(c1.getId()+ " ");
        }
      ServiceCommande Sc = new ServiceCommande();
//for(Produit c1 : SP.getPanier(P)) {
//            System.out.println("3");
//            System.out.println(c1.getId());
//        }
       Sc.passerCommande(P);
       Sc.afficherCommandes();
         for(Produit c1 : SP.getPanier(P)) {
            System.out.print(c1.getId()+ " ");
        }
    }
}
