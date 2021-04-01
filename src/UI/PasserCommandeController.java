/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package UI;

import Entity.User;
import Entity.Commande;
import Entity.Panier;
import Entity.Produit;
import Service.ServiceCommande;
import Service.ServicePanier;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;

/**
 * FXML Controller class
 *
 * @author Malvin
 */
public class PasserCommandeController implements Initializable {

    @FXML
    private AnchorPane chercher;
    @FXML
    private TableView<Produit> table;
    @FXML
    private TableColumn<User, String> nom;
    @FXML
    private TableColumn<Produit, String> prix;
    @FXML
    private TableColumn<?, ?> Qte;
    @FXML
    private TextField idClient;
        @FXML
   
    private TextField montant;
    @FXML
    private Button Retour;
    @FXML
    private TextField montant1;
                   User C = new User(1,"sarsour","rim.kourou@esprit.tn");  
         ServicePanier sv =new ServicePanier();
         ServiceCommande sc =new ServiceCommande();
    @FXML
    private TableColumn<?, ?> image;
    @FXML
    private TableColumn<?, ?> id;

  public void setResNom(TextField resNom) {
        this.idClient.setText(resNom.getText());
    }
  
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
                     

        ObservableList<Produit> listu  = FXCollections.observableArrayList();
        
         
     try {
     
            Panier P = Panier.getInstance(C);
                montant.setText(""+sv.calculePrix(P));
                double i = sv.calculePrix(P) +(sv.calculePrix(P)*0.1) ;
                montant1.setText(""+i);
         for(Produit bb: sv.getPanier(P)){
              Image imm = new Image(bb.getImage().substring(56));
                ImageView im = new ImageView() ;
               im.setFitHeight(100);
im.setFitWidth(100);
                im.setImage(imm);
               bb.setIm(im);
                listu.add(bb);
         }
            
      } catch (SQLException ex) {
            Logger.getLogger(ConsulterPanierController.class.getName()).log(Level.SEVERE, null, ex);
        }
            
     
     

         //mettre les données dans la table view: 
         id.setCellValueFactory(new PropertyValueFactory<>("id"));

         nom.setCellValueFactory(new PropertyValueFactory<>("id"));
         prix.setCellValueFactory(new PropertyValueFactory<>("prix"));
                          Qte.setCellValueFactory(new PropertyValueFactory<>("Qte"));
         image.setCellValueFactory(new PropertyValueFactory<>("im"));

        table.setItems(listu);
        
    }      

    @FXML
    private void Confirmer(ActionEvent event) throws SQLException {
          Panier P = Panier.getInstance(C);
       Commande a =  sc.passerCommande(P);
      
     
         Produit p = table.getSelectionModel().getSelectedItem();
    FXMLLoader loader = new FXMLLoader
                        (getClass()
                         .getResource("AfficherFacture.fxml"));
            try {
                Parent root = loader.load();
                AfficherFactureController apc = loader.getController();
                apc.setResNom(idClient);
                                apc.setEmail(C.getEmail());
                                apc.setCommande("REF"+a.getId());
                                                                apc.setDate(a.getDate());

                                apc.setTotal(montant.getText());
                                apc.setTotal2(montant1.getText());

                idClient.getScene().setRoot(root);
            } catch (IOException ex) {
                System.out.println(ex.getMessage());
                }
           
    }
     
    
    

    @FXML
    private void Annuler(ActionEvent event) {
                ((Node)(event.getSource())).getScene().getWindow().hide();

    }
    
}
