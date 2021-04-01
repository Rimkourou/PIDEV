/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package UI;

import Entity.Categorie;
import Entity.Panier;
import Entity.Produit;
import Entity.User;
import Service.ServicePanier;
import Service.serviceprod;
import java.awt.event.MouseEvent;
import java.io.IOException;
import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Region;
import javafx.stage.Stage;
import javax.activation.DataSource;
import utils.ConnexionBD;

/**
 * FXML Controller class
 *
 * @author esprit
 */
public class InterfaceShopController implements Initializable {
    
    ObservableList<Produit> list = FXCollections.observableArrayList();
    ObservableList<Categorie> listC = FXCollections.observableArrayList();


    @FXML
    private TextField Fielterfield;
  
    @FXML
    private GridPane grid;
    @FXML
    private Button state;
    @FXML
    private ScrollPane Scroll;
    private TableView<Produit> table;
    @FXML
    private ComboBox<?> selectCat;
      

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO

        serviceprod  SP = new serviceprod();
        list =  SP.afficher();
          int column = 1;
            int row = 0;
            try {
                for (int i = 0; i < list.size(); i++) {
                    FXMLLoader fxmlLoader = new FXMLLoader();
                    fxmlLoader.setLocation(getClass().getResource("itemP.fxml"));
                    AnchorPane anchorPane = fxmlLoader.load();
                    
                    ItemPController itemController = fxmlLoader.getController();
                    itemController.setData(list.get(i));
                    
            
             if(column>3)
             { column=1;
             row++;}
                    
                    grid.add(anchorPane, column, row); //(child,column,row)
                    //set grid width
                     grid.setMinWidth(Region.USE_COMPUTED_SIZE);
                grid.setPrefWidth(Region.USE_COMPUTED_SIZE);
                grid.setMaxWidth(Region.USE_PREF_SIZE);

                //set grid height
                grid.setMinHeight(Region.USE_COMPUTED_SIZE);
                grid.setPrefHeight(Region.USE_COMPUTED_SIZE);
                grid.setMaxHeight(Region.USE_PREF_SIZE);

                GridPane.setMargin(anchorPane, new Insets(10));
                column++;     
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
                        ObservableList data = FXCollections.observableArrayList();
        ObservableList comboString = FXCollections.observableArrayList();

        
        java.sql.Connection cnx =ConnexionBD.getInstance().getCnx();
        String query = "Select nom from categorie";
        Statement st;
        ResultSet rs;
try {
             st=cnx.createStatement();
            rs=st.executeQuery(query);

             
            while (rs.next()) {
                        data.add(new Categorie(rs.getString("nom")));
                        comboString.add(rs.getString("nom"));
                        
                                 }

        } catch (SQLException ex) {
            System.err.println("Error"+ex);
        }
        selectCat.setItems(null);
        selectCat.setItems(comboString); 
    } 
    
    
    

//    private void cat(ActionEvent event) throws IOException {
//        javafx.scene.Parent tableview = FXMLLoader.load(getClass().getResource("categorieSHOP.fxml"));
//        Scene sceneview = new Scene(tableview);
//        Stage window = (Stage)((Node)event.getSource()).getScene().getWindow();
//        window.setScene(sceneview);
//        window.show();
//    }

    @FXML
    private void State(ActionEvent event) throws IOException {
        javafx.scene.Parent tableview = FXMLLoader.load(getClass().getResource("Statstique.fxml"));
        Scene sceneview = new Scene(tableview);
        Stage window = (Stage)((Node)event.getSource()).getScene().getWindow();
        window.setScene(sceneview);
        window.show();
    
        
    }
    
//    private void recherche(){
//        // Wrap the ObservableList in a FilteredList (initially display all data).
//        FilteredList<Produit> filteredData = new FilteredList<>(list, b -> true);
//		
//        // 2. Set the filter Predicate whenever the filter changes.
//        Fielterfield.textProperty().addListener((observable, oldValue, newValue) -> {
//                filteredData.setPredicate(Produit -> {
//                    // If filter text is empty, display all persons.
//
//                    if (newValue == null || newValue.isEmpty()) {
//                            return true;
//                    }
//
//                    // Compare first name and last name of every person with filter text.
//                    String lowerCaseFilter = newValue.toLowerCase();
//
//                    if (Produit.getNom().toLowerCase().indexOf(lowerCaseFilter) != -1 ) {
//                        return true; // Filter matches first name.
//                    }else if (String.valueOf(Produit.getId()).indexOf(lowerCaseFilter)!=-1)
//                        return true;
//                    else if (String.valueOf(Produit.getPrix()).indexOf(lowerCaseFilter)!=-1)
//                        return true;
//                    else if (String.valueOf(Produit.getQte()).indexOf(lowerCaseFilter)!=-1)
//                        return true;
//                    else if (String.valueOf(Produit.getIdCategorie()).indexOf(lowerCaseFilter)!=-1)
//                        return true;
//                    else  
//                        return false; // Does not match.
//                });
//        });
//
//        // 3. Wrap the FilteredList in a SortedList. 
//        SortedList<Produit> sortedData = new SortedList<>(filteredData);
//
//        // 4. Bind the SortedList comparator to the TableView comparator.
//        // 	  Otherwise, sorting the TableView would have no effect.
//        sortedData.comparatorProperty().bind(Scroll.comparatorProperty());
//
//        // 5. Add sorted (and filtered) data to the table.
//        Scroll.setItems(sortedData);
//    }
    

    @FXML
    private void ConsulterPanier(ActionEvent event)throws IOException  {
       
    javafx.scene.Parent tableview = FXMLLoader.load(getClass().getResource("ConsulterPanier.fxml"));
        Scene sceneview = new Scene(tableview);
        Stage window = (Stage)((Node)event.getSource()).getScene().getWindow();
        window.setScene(sceneview);
        window.show();
    
    }
   

        
    

    

  
//@FXML
//    private void click(MouseEvent mouseEvent) {
//        myListener.onClickListener(produit);
//    }
//
//    private Produit produit;
//    private MyListener myListener;
//
//    public void setData(Produit produit, MyListener myListener) {
//        this.produit= produit;
//        this.myListener = myListener;
//        nom.setText(produit.getNom());
//        prix.setText(String.valueOf(produit.getPrix()));
////        Image image = new Image(getClass().getResourceAsStream(produit.getImgSrc()));
////        img.setImage(image);
//    }


        @FXML
    private void handleSearchByCategorie(ActionEvent event) throws IOException {
          
                        serviceprod  SP = new serviceprod();

               // ObservableList<Produit> listeP = SP.afficherCombo(String.valueOf(selectCat.getValue()));
                 ObservableList<Produit> list = SP.afficherCombo(String.valueOf(selectCat.getValue()));
                  int column = 1;
            int row = 0;
            
            grid.getChildren().clear();
          
            try {
                for (int i = 0; i < list.size(); i++) {
                    FXMLLoader fxmlLoader = new FXMLLoader();
                    fxmlLoader.setLocation(getClass().getResource("itemP.fxml"));
                    AnchorPane anchorPane = fxmlLoader.load();

                    ItemPController itemController = fxmlLoader.getController();
                    itemController.setData(list.get(i));



             if(column>3)
             { column=1;
             row++;}

                    grid.add(anchorPane, column, row); //(child,column,row)
                    //set grid width
                     grid.setMinWidth(Region.USE_COMPUTED_SIZE);
                grid.setPrefWidth(Region.USE_COMPUTED_SIZE);
                grid.setMaxWidth(Region.USE_PREF_SIZE);

                //set grid height
                grid.setMinHeight(Region.USE_COMPUTED_SIZE);
                grid.setPrefHeight(Region.USE_COMPUTED_SIZE);
                grid.setMaxHeight(Region.USE_PREF_SIZE);

                GridPane.setMargin(anchorPane, new Insets(10));
                column++;     
                }
            } catch (IOException e) {
                e.printStackTrace();
            }

    }
    

    
}


