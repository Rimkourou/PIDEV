/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package UI;


import Entity.User;
import Entity.Panier;
import Entity.Produit;
import Service.ServicePanier;
import Service.serviceprod;
import java.io.IOException;
import static java.lang.Integer.parseInt;
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
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author Malvin
 */
public class ConsulterPanierController implements Initializable {

    @FXML
    private AnchorPane chercher;
    @FXML
    private TableView<Produit> table;
    @FXML
    private TableColumn<Produit, String> nom;
    @FXML
    private TableColumn<Produit, String> prix;
    @FXML
    private TextField idClient;
    @FXML
    private TextField montant;
    @FXML
    private Button Retour;
    @FXML
    private TableColumn<?, ?> Qte;

    /**
     * Initializes the controller class.
     */
     public void setResNom(TextField resNom) {
        this.idClient.setText(resNom.getText());
    }
         ServicePanier sv =new ServicePanier();
               User C = new User(1);

    @Override
    public void initialize(URL url, ResourceBundle rb) {
           // C.setId((int)parseInt(idClient.getText()));
      
        ObservableList<Produit> listu  = FXCollections.observableArrayList();
        
         
     try {
     
            Panier P = Panier.getInstance(C);
                montant.setText(""+sv.calculePrix(P));

         for(Produit bb: sv.getPanier(P))
             listu.add(bb);
      } catch (SQLException ex) {
            Logger.getLogger(ConsulterPanierController.class.getName()).log(Level.SEVERE, null, ex);
        }
            
     
     

         //mettre les données dans la table view: 

         nom.setCellValueFactory(new PropertyValueFactory<>("id"));
         prix.setCellValueFactory(new PropertyValueFactory<>("prix"));
                          Qte.setCellValueFactory(new PropertyValueFactory<>("Qte"));

        table.setItems(listu);
        
    }    

    @FXML
    private void Supprimer(ActionEvent event) throws SQLException {
         ObservableList<Produit> SelectedRows, allpeople;
     
     allpeople = table.getItems();
     // il donne les ligne qui vous avez déja séléctionner
     SelectedRows =table.getSelectionModel().getSelectedItems();
     
     for(Produit gg:SelectedRows){
       allpeople.remove(gg);
        Panier P = Panier.getInstance(C);
       sv.effaceProduit(P, gg);
     
      
             montant.setText(""+sv.calculePrix(P));

    }
     
    
    }

    @FXML
    private void PasserCommande(ActionEvent event) throws IOException {
         
        FXMLLoader loader = new FXMLLoader(getClass().getResource("PasserCommande.fxml"));
            Parent root = loader.load();
            PasserCommandeController scene2Controller = loader.getController();
                scene2Controller.setResNom(idClient);
                Stage stage = new Stage();
            stage.setScene(new Scene(root));
            stage.setTitle("Hello World");
            stage.show();
    }

    @FXML
    private void ViderPanier(ActionEvent event){
               ObservableList<Produit>  allpeople;
     
     allpeople = table.getItems();  
     allpeople.clear();    
      montant.setText("0");
     try {
            Panier P = Panier.getInstance(C);
            sv.viderPanier(P);
        } catch (SQLException ex) {
            Logger.getLogger(ConsulterPanierController.class.getName()).log(Level.SEVERE, null, ex);
        }
              

         
           

    
 
    }

    @FXML
    private void Retour(ActionEvent event) {
         try {
        javafx.scene.Parent tableview = FXMLLoader.load(getClass().getResource("AfficherProduit.fxml"));
        Scene sceneview = new Scene(tableview);
        Stage window = (Stage)((Node)event.getSource()).getScene().getWindow();
        window.setScene(sceneview);
        window.show();
        } catch (IOException ex) {
            System.out.println(ex.getMessage());
        }
    }
    
}
