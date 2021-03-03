/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Gui;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;

/**
 * FXML Controller class
 *
 * @author user
 */
public class ConsulterPanierController implements Initializable {

    @FXML
    private AnchorPane chercher;
    @FXML
    private TableView<?> table;
    @FXML
    private TableColumn<?, ?> nom;
    @FXML
    private TableColumn<?, ?> prix;
    @FXML
    private TableColumn<?, ?> Qte;
    @FXML
    private TextField idClient;
    @FXML
    private TextField montant;
    @FXML
    private Button Retour;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    

    @FXML
    private void Supprimer(ActionEvent event) {
    }

    @FXML
    private void PasserCommande(ActionEvent event) {
    }

    @FXML
    private void ViderPanier(ActionEvent event) {
    }

    @FXML
    private void Retour(ActionEvent event) {
    }
    
}
