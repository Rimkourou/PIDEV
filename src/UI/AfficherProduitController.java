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
import Service.serviceprod;
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
import javafx.scene.Parent;
import javafx.scene.control.Alert;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;

/**
 * FXML Controller class
 *
 * @author user
 */
public class AfficherProduitController implements Initializable {

    @FXML
    private AnchorPane chercher;
    @FXML
    private TableView<Produit> table;
    @FXML
    private TableColumn<?, ?> nom;
    @FXML
    private TableColumn<?, ?> prix;
  
    @FXML
    private TextField idClient;
    
    serviceprod sv =new serviceprod();

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
       ObservableList<Produit> listu  = FXCollections.observableArrayList();
        
         
     try {
         for(Produit bb: sv.getListProd())
             listu.add(bb);
     } catch (SQLException ex) {
     }
            
     
     

         //mettre les données dans la table view:    
         nom.setCellValueFactory(new PropertyValueFactory<>("id"));
         prix.setCellValueFactory(new PropertyValueFactory<>("prix"));
        
        table.setItems(listu);
        
    }    

    @FXML
    private void AjouterAP(ActionEvent event) throws SQLException {
         ObservableList<Produit> SelectedRows, allpeople;
     ServicePanier sp = new ServicePanier();
   //int  idclient = idClient.getText();
  User c = new User(1);
        Panier P = Panier.getInstance(c);
     allpeople = table.getItems();
     // il donne les ligne qui vous avez déja séléctionner
     SelectedRows =table.getSelectionModel().getSelectedItems();
     
     for(Produit gg:SelectedRows){
             try {
                 sp.AjouterProduit(gg, P,1);
                 Alert alert = new Alert(Alert.AlertType.INFORMATION);
                 alert.setTitle("Produit Ajouter");
                 alert.setContentText("produit "+gg.getId()+" est bien ajouter");
                 alert.showAndWait();
             } catch (SQLException ex) {
             }
    }

    }

    @FXML
    private void ConsulterPanier(ActionEvent event) {
        idClient.setText("1");
         Produit p = table.getSelectionModel().getSelectedItem();
    FXMLLoader loader = new FXMLLoader
                        (getClass()
                         .getResource("ConsulterPanier.fxml"));
            try {
                Parent root = loader.load();
                ConsulterPanierController apc = loader.getController();
                apc.setResNom(idClient);
                idClient.getScene().setRoot(root);
            } catch (IOException ex) {
                System.out.println(ex.getMessage());
                }
    }
    
}
