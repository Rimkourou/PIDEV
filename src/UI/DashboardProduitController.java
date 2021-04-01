/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package UI;

import Entity.Categorie;
import Entity.Produit;
import Service.ServiceCategorie;
import Service.serviceprod;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import static java.lang.Integer.parseInt;
import java.sql.SQLException;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import utils.ConnexionBD;
import java.net.URL;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.embed.swing.SwingFXUtils;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ComboBox;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javax.imageio.ImageIO;
import javax.swing.JOptionPane;



/**
 * FXML Controller class
 *
 * @author esprit
 */
public class DashboardProduitController implements Initializable {
    
    java.sql.Connection cnx = ConnexionBD.getInstance().getCnx();
    ObservableList<Produit> list = FXCollections.observableArrayList();
    String imgUrl  ="";
    private FileChooser uploadPic;
    private File picPath;
    int index= -1;
    serviceprod  SP=new serviceprod();




    @FXML
    private Button btnSettings;
    @FXML
    private TableColumn<?, ?> coloID;
    @FXML
    private TableColumn<?, ?> coloNom;
    @FXML
    private TableColumn<?, ?> coloPrix;
    @FXML
    private TableColumn<?, ?> coloQte;
    @FXML
    private TableColumn<?, ?> coloCat;
    @FXML
    private TextField tfNom;
    @FXML
    private TextField tfPrix;
    @FXML
    private TextField tfQte;
    @FXML
    private Button btnAdd;
    @FXML
    private Button btnEdit;
    @FXML
    private Button btnDelete;
    @FXML
    private TableView<Produit> tvProduit;
    @FXML
    private TableColumn<Produit, ImageView> coloImage;
    @FXML
    private Button addImage;
    @FXML
    private ImageView imageToPost;
    @FXML
    private TextField Fielterfield;
    @FXML
    private ComboBox<String> selectCat;
    @FXML
    private Button cat;
    @FXML
    private Button TRIE;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
//        ObservableList<String> om = ServiceCategorie.CategorieListeName();
//        selectCat.setItems(om);
ObservableList data = FXCollections.observableArrayList();
        ObservableList comboString = FXCollections.observableArrayList();

        
    java.sql.Connection cnx = ConnexionBD.getInstance().getCnx();
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
        
                                
        showProduit();
        
        recherche();
        
    
          
      
    }  
    
    public void showProduit(){
        
        
        serviceprod  SP = new serviceprod();
        list =  SP.afficher();

        
        coloID.setCellValueFactory(new PropertyValueFactory("id"));
        coloNom.setCellValueFactory(new PropertyValueFactory("nom"));
        coloPrix.setCellValueFactory(new PropertyValueFactory("prix"));
        coloQte.setCellValueFactory(new PropertyValueFactory("Qte"));
        coloCat.setCellValueFactory(new PropertyValueFactory("idCategorie"));
//        coloImage.setCellValueFactory(new PropertyValueFactory("image"));
                   coloImage.setCellValueFactory(new PropertyValueFactory<>("im"));
      
        tvProduit.setItems(list);
    }
    
//    @FXML
//    public void getSelected(){
//        Produit p;
//
//        index =tvProduit.getSelectionModel().getSelectedIndex();
//
//        if(index<=-1){
//            
//            return;
//        }
//        tfNom.setText(coloNom.getCellData(index).toString());
//        tfPrix.setText(coloPrix.getCellData(index).toString());
//        tfQte.setText(coloQte.getCellData(index).toString());
//        selectCat.setValue(coloCat.getCellData(index).toString().);
//
//        imageToPost.setImage(new Image(coloImage.getCellData(index).toString().substring(82)));
//
//       
//    }
    
    
    @FXML
    public void SelectRow(){
        int id=tvProduit.getSelectionModel().getSelectedItem().getId();
        tfNom.setText((tvProduit.getSelectionModel().getSelectedItem().getNom()));
        tfPrix.setText(Integer.toString((int) tvProduit.getSelectionModel().getSelectedItem().getPrix()));
        tfQte.setText(Integer.toString(tvProduit.getSelectionModel().getSelectedItem().getQte()));
        selectCat.setValue((tvProduit.getSelectionModel().getSelectedItem().getIdCategorie()));
//        imageToPost.setImage(new Image(coloImage.getCellData(index).toString().substring(82)));
        
        Image image;
        image = new Image(((Produit)this.tvProduit.getSelectionModel().getSelectedItem()).getImage());
        this.imageToPost.setImage(image);


    }
    
    
    @FXML
    private void btnDelete(ActionEvent event) {
    serviceprod  SP=new serviceprod();
    ObservableList<Produit> SelectedRows, allpeople;
     
     allpeople = tvProduit.getItems();
     // il donne les ligne qui vous avez déja séléctionner
     SelectedRows =tvProduit.getSelectionModel().getSelectedItems();
     
        for(Produit gg:SelectedRows){
           allpeople.remove(gg);
           SP.supprimerparID(gg.getId());
       }
     showProduit();
     JOptionPane.showMessageDialog(null, "Cbon !");


    }

    @FXML
    private void btnAdd(ActionEvent event) throws IOException,SQLException{
        if (tfNom.toString() == null
                || tfPrix.toString() == null                 
                || tfQte.toString() == null                
                || selectCat.getSelectionModel().getSelectedItem() == null) {
            //affichage d'un alerte : vous devez remplir tt les champs//
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Warning"); //affichage dans la barre de titre
            alert.setHeaderText("Ces champs sont obligatoires");
            alert.showAndWait();

            //Sinon faire appel au servicePlanning et entité//

        } else {
        String nomP = tfNom.getText();
        int Qte1 = parseInt(tfQte.getText());
        float Prix1= Float.parseFloat(tfPrix.getText());
        String Categorie1= (String) selectCat.getValue();
        String image = imgUrl;
        serviceprod sp = new serviceprod();       
        
        sp.ajouter(new Produit(nomP, Prix1, Qte1,Categorie1, image));
        showProduit();
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Information"); //affichage dans la barre de titre
        alert.setHeaderText("PRODUIT AJOUTER");
        alert.showAndWait();
            
        
//        sp.ajouter(new Produit(nomP, Prix1, Qte1,Categorie1, image));
//        showProduit();
//        JOptionPane.showMessageDialog(null, "Cbon !");
        }
    }

    @FXML
    private void addImage(ActionEvent event) {
        Stage stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        uploadPic = new FileChooser();
        uploadPic.setTitle("Select the image you want to add");
        picPath = uploadPic.showOpenDialog(stage);
        System.out.println(picPath.toString());
        try {
            imgUrl = picPath.toURI().toURL().toExternalForm();

            BufferedImage buffImage = ImageIO.read(picPath);
            Image up = SwingFXUtils.toFXImage(buffImage, null);
            imageToPost.setImage(up);
        } catch(IOException ex){
            System.err.println(ex.getMessage());
        }
    }
    
    
 
    @FXML
    private void btnEdit(ActionEvent event) {
        if (tfNom.toString() == null
                || tfPrix.toString() == null
                || tfQte.toString() == null                
                || selectCat.getSelectionModel().getSelectedItem() == null) {
            //affichage d'un alerte : vous devez remplir tt les champs//
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Warning"); //affichage dans la barre de titre
            alert.setHeaderText("Ces champs sont obligatoires");
            alert.showAndWait();

            //Sinon faire appel au servicePlanning et entité//

        } else {
        int id = tvProduit.getSelectionModel().getSelectedItem().getId();
        String nomP = tfNom.getText();
        int Qte1 = parseInt(tfQte.getText());
        float Prix1= Float.parseFloat(tfPrix.getText());
        String Categorie1= (String) selectCat.getValue();
        String image = imgUrl;
        SP.modifier(new Produit(id, nomP, Prix1, Qte1, Categorie1, image));
        showProduit();
//        JOptionPane.showMessageDialog(null, "Cbon !");
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Information"); //affichage dans la barre de titre
        alert.setHeaderText("PRODUIT MODIFIER !");
        alert.showAndWait();}
    
    }
    
    private void recherche(){
        // Wrap the ObservableList in a FilteredList (initially display all data).
        FilteredList<Produit> filteredData = new FilteredList<>(list, b -> true);
		
        // 2. Set the filter Predicate whenever the filter changes.
        Fielterfield.textProperty().addListener((observable, oldValue, newValue) -> {
                filteredData.setPredicate(Produit -> {
                    // If filter text is empty, display all persons.

                    if (newValue == null || newValue.isEmpty()) {
                            return true;
                    }

                    // Compare first name and last name of every person with filter text.
                    String lowerCaseFilter = newValue.toLowerCase();

                    if (Produit.getNom().toLowerCase().indexOf(lowerCaseFilter) != -1 ) {
                        return true; // Filter matches first name.
                    }else if (String.valueOf(Produit.getId()).indexOf(lowerCaseFilter)!=-1)
                        return true;
                    else if (String.valueOf(Produit.getPrix()).indexOf(lowerCaseFilter)!=-1)
                        return true;
                    else if (String.valueOf(Produit.getQte()).indexOf(lowerCaseFilter)!=-1)
                        return true;
                    else if (String.valueOf(Produit.getIdCategorie()).indexOf(lowerCaseFilter)!=-1)
                        return true;
                    else  
                        return false; // Does not match.
                });
        });

        // 3. Wrap the FilteredList in a SortedList. 
        SortedList<Produit> sortedData = new SortedList<>(filteredData);

        // 4. Bind the SortedList comparator to the TableView comparator.
        // 	  Otherwise, sorting the TableView would have no effect.
        sortedData.comparatorProperty().bind(tvProduit.comparatorProperty());

        // 5. Add sorted (and filtered) data to the table.
        tvProduit.setItems(sortedData);
    }

    private void State(ActionEvent event) throws IOException{
        javafx.scene.Parent tableview = FXMLLoader.load(getClass().getResource("Statstique.fxml"));
        Scene sceneview = new Scene(tableview);
        Stage window = (Stage)((Node)event.getSource()).getScene().getWindow();
        window.setScene(sceneview);
        window.show();
    
    }

    @FXML
    private void cat(ActionEvent event) throws IOException {
        javafx.scene.Parent tableview = FXMLLoader.load(getClass().getResource("DashboardCategorie.fxml"));
        Scene sceneview = new Scene(tableview);
        Stage window = (Stage)((Node)event.getSource()).getScene().getWindow();
        window.setScene(sceneview);
        window.show();
    }

    @FXML
    private void TrieParNom(ActionEvent event) throws SQLException {
        ObservableList<Produit> list = SP.TrieParNom();
        coloID.setCellValueFactory(new PropertyValueFactory("id"));
        coloNom.setCellValueFactory(new PropertyValueFactory("nom"));
        coloPrix.setCellValueFactory(new PropertyValueFactory("prix"));
        coloQte.setCellValueFactory(new PropertyValueFactory("Qte"));
        coloCat.setCellValueFactory(new PropertyValueFactory("idCategorie"));
        coloImage.setPrefWidth(80);
        coloImage.setCellValueFactory(new PropertyValueFactory("image"));
        


        tvProduit.setItems(list);
    }

       
}
