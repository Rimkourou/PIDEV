/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package UI;

import Entity.Categorie;
import Service.ServiceCategorie;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;
import javax.swing.JOptionPane;

/**
 * FXML Controller class
 *
 * @author esprit
 */
public class DashboardCategoriesController implements Initializable {

    ServiceCategorie  SC=new ServiceCategorie();
    ObservableList<Categorie> list = FXCollections.observableArrayList();


    @FXML
    private Button btnSettings;
    @FXML
    private TableColumn<?, ?> coloID;
    @FXML
    private TableColumn<?, ?> coloNom;
    @FXML
    private TextField tfNom;
    @FXML
    private Button btnAdd;
    @FXML
    private Button btnEdit;
    @FXML
    private Button btnDelete;
    @FXML
    private TableView<Categorie> tvCat;
    @FXML
    private Button prod;
    @FXML
    private TextField Fielterfield;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        showCategorie();
        recherche();

    }    

        public void showCategorie(){      
        
        ServiceCategorie  SC=new ServiceCategorie();
        list =  SC.afficher();        
        
        coloID.setCellValueFactory(new PropertyValueFactory("idCategorie"));
        coloNom.setCellValueFactory(new PropertyValueFactory("nom"));
                
        tvCat.setItems(list);
    }
    @FXML
    private void btnAdd(ActionEvent event) throws IOException,SQLException{
        if (tfNom.toString() == null) {
            //affichage d'un alerte : vous devez remplir tt les champs//
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Warning"); //affichage dans la barre de titre
            alert.setHeaderText("Ces champs sont obligatoires");
            alert.showAndWait();

            //Sinon faire appel au servicePlanning et entité//

        } else {
        String nomP = tfNom.getText();
        
        ServiceCategorie  SC=new ServiceCategorie();
        SC.ajouter(new Categorie(nomP));
//        JOptionPane.showMessageDialog(null, "Cbon !");
        showCategorie();
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Information"); //affichage dans la barre de titre
        alert.setHeaderText("PRODUIT AJOUTER !");
        alert.showAndWait();
    
        }
    }

    @FXML
    private void btnEdit(ActionEvent event) {
        if (tfNom.toString() == null) {
            //affichage d'un alerte : vous devez remplir tt les champs//
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Warning"); //affichage dans la barre de titre
            alert.setHeaderText("Ces champs sont obligatoires");
            alert.showAndWait();

            //Sinon faire appel au servicePlanning et entité//

        } else {
        int id = tvCat.getSelectionModel().getSelectedItem().getIdCategorie();
        String nomC = tfNom.getText();
        
        ServiceCategorie  SC=new ServiceCategorie();
        SC.modifier(new Categorie(id, nomC));
        showCategorie();
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Information"); //affichage dans la barre de titre
        alert.setHeaderText("PRODUIT MODIFIER !");
        alert.showAndWait();}
    }

    @FXML
    private void btnDelete(ActionEvent event) {
            ServiceCategorie  SC=new ServiceCategorie();
    ObservableList<Categorie> SelectedRows, allpeople;
     
     allpeople = tvCat.getItems();
     // il donne les ligne qui vous avez déja séléctionner
     SelectedRows =tvCat.getSelectionModel().getSelectedItems();
     
        for(Categorie gg:SelectedRows){
           allpeople.remove(gg);
           SC.supprimerparID(gg.getIdCategorie());
       }
    }

        @FXML
    private void prod(ActionEvent event)throws IOException {
        javafx.scene.Parent tableview = FXMLLoader.load(getClass().getResource("DashboardProduit.fxml"));
        Scene sceneview = new Scene(tableview);
        Stage window = (Stage)((Node)event.getSource()).getScene().getWindow();
        window.setScene(sceneview);
        window.show();
    }
    
    private void recherche(){
        // Wrap the ObservableList in a FilteredList (initially display all data).
        FilteredList<Categorie> filteredData = new FilteredList<>(list, b -> true);
		
        // 2. Set the filter Predicate whenever the filter changes.
        Fielterfield.textProperty().addListener((observable, oldValue, newValue) -> {
                filteredData.setPredicate(Categorie -> {
                    // If filter text is empty, display all persons.

                    if (newValue == null || newValue.isEmpty()) {
                            return true;
                    }

                    // Compare first name and last name of every person with filter text.
                    String lowerCaseFilter = newValue.toLowerCase();

                    if (Categorie.getNom().toLowerCase().indexOf(lowerCaseFilter) != -1 ) {
                        return true; // Filter matches first name.
                    }else if (String.valueOf(Categorie.getIdCategorie()).indexOf(lowerCaseFilter)!=-1)
                        return true;
                    else  
                        return false; // Does not match.
                });
        });

        // 3. Wrap the FilteredList in a SortedList. 
        SortedList<Categorie> sortedData = new SortedList<>(filteredData);

        // 4. Bind the SortedList comparator to the TableView comparator.
        // 	  Otherwise, sorting the TableView would have no effect.
        sortedData.comparatorProperty().bind(tvCat.comparatorProperty());

        // 5. Add sorted (and filtered) data to the table.
        tvCat.setItems(sortedData);
    }
    
}
