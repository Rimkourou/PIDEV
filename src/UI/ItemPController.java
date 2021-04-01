/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package UI;

import Entity.Panier;
import Entity.Produit;
import Entity.User;
import Service.ServicePanier;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;

/**
 * FXML Controller class
 *
 * @author asus
 */
public class ItemPController implements Initializable {

    @FXML
    private Label nom;
    @FXML
    private Label prix;

    /**
     * Initializes the controller class.
     */
    private Produit produit=new Produit();
    @FXML
    private ImageView img;
    @FXML
    private Label id;
    
     public void setData(Produit produit) {
        this.produit = produit;
        id.setText(produit.getId()+"");
        nom.setText(produit.getNom());
        prix.setText( String.valueOf(produit.getPrix())+ " TND");
        System.out.println(produit.getImage());
        String i=produit.getImage().substring(56);
        Image image = new Image(getClass().getResourceAsStream(i));
        img.setImage(image);
    }
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    

    @FXML
    private void click(MouseEvent event) throws SQLException {
          Alert alert = new Alert(Alert.AlertType.INFORMATION);
          //Produit p = (Produit) event.getSource();
//String idd = "" ;
//String nomm ="";
//String prixx="";
                 alert.setTitle("Produit Ajouter");
                 AnchorPane p = (AnchorPane) event.getSource();
                 Produit prod = new Produit();
                 int i = 0;
                 for(Node n : p.getChildren()) {
                      
                     if (n instanceof Label) {
                         if (i==0) {
                             String      nomm = ((Label)n).getText() ;
                             prod.setNom(nomm);
                         }
                           if (i==1) {
                             String   prixx = ((Label)n).getText() ;
                             int pos =  prixx.indexOf(" ");  
                             String pr = prixx.substring(0, pos);
                             prod.setPrix(Float.parseFloat(pr));
                         }
                             if (i==2) {
                             String idd = ((Label)n).getText() ;
                             prod.setId(Integer.parseInt(idd));
                         }
                     i++;
        // clear
        //((TextField)node).setText("");          
    }        
                 }
                      ServicePanier sp = new ServicePanier();

                   User c = new User(1);
                         Panier P = Panier.getInstance(c);
  sp.AjouterProduit(prod, P,1);
                 alert.setTitle("Produit Ajouter");
                 alert.setContentText("produit "+prod.getNom()+" est bien ajouter");
                 alert.showAndWait();
                
    
    }
    
}
