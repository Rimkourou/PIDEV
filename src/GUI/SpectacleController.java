package GUI;


import Entités.SpectacleE;
import connectionBD.SingletonConnection;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;

import java.io.IOException;
import java.net.URL;
import java.sql.Date;
import java.sql.SQLException;
//import java.util.Date;
import java.sql.SQLOutput;
import java.time.LocalDate;
import java.util.ResourceBundle;
import java.util.logging.Logger;

import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.text.Text;
import service.serviceSpectacle;
import service.servicespectacleIMP;


public class SpectacleController implements Initializable {

    @FXML
    private TableColumn<SpectacleE, Integer> idcol;

    @FXML
    private TableColumn<SpectacleE, String> titrecol;

    @FXML
    private TableColumn<SpectacleE, Date> datetcol;

    @FXML
    private TableColumn<SpectacleE, String> genrecol;

    @FXML
    private TableColumn<SpectacleE, Integer> idusercol;

    @FXML
    private TableColumn<SpectacleE, Integer> idsallecol;

    @FXML
    private TextField idfl;

    @FXML
    private TextField titrefl;

    @FXML
    private DatePicker datefl;

    @FXML
    private ComboBox<String> genrefl;

    @FXML
    private ComboBox<Integer> idsallefl;

    @FXML
    private Button boutonAjout;

    @FXML
    private TableView<SpectacleE> tableauSpectacles;

    @FXML
    private servicespectacleIMP ss = new servicespectacleIMP();

    @FXML
    private SpectacleE spectacleSelected;

    @FXML
    private HBox sbspectacle;

    @FXML
    private Label sbplanning;

    @FXML
    private Button boutonEffacerTous;

    @FXML
    void handleButtonShow(MouseEvent event) {
    }


    @FXML
    void handleButtonPlanning(MouseEvent event) {
    }


    /******************************************Methode ajout par boutton****************************************************/
    @FXML
    public void Ajout(ActionEvent event) throws IOException, SQLException {
      /********vérifier si le titre  est vide ou non ; la date et genre et idsallefl sont vide***********/

        if (titrefl.getText().trim().isEmpty() || datefl.toString() == null ||
                genrefl.getSelectionModel().getSelectedItem() == null ||
                idsallefl.getSelectionModel().getSelectedItem() == null) {
/*****affichage d'un alerte : vous devez remplir tt les champs******/
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Warning"); //affichage dans la barre de titre
            alert.setHeaderText("tous les champs sont obligatoires");
            alert.showAndWait();

            /**********************Sinon faire appel au serviceSpectacle et entité**********************/

        } else {
            servicespectacleIMP ss = new servicespectacleIMP();
            SpectacleE s = new SpectacleE();

            /*********************Si un champs existe déja un message d'alert sera afficher***********************/

             if (ss.spectacleExist(titrefl.getText())) {
                Alert alert = new Alert(Alert.AlertType.WARNING);
                alert.setTitle("Warning");
                alert.setHeaderText("Spectacle existe deja");
                alert.showAndWait();

            /*******SINON : L'ajout se fait avec succées *******/
             } else {
                s.setTitre(titrefl.getText());
                Date date1 = Date.valueOf(datefl.getValue());
                s.setDate(date1);
                s.setGenre(genrefl.getSelectionModel().getSelectedItem());

                //to adjust with the current connected user
                s.setIdUser(1);
                s.setIdSalle(idsallefl.getSelectionModel().getSelectedItem());
                ss.AddSpectacle(s);
                showSpectacle();
            }
        }
    }

    /****************************************Selectionner champ spectacle et afficher dans FILE Text****************************************************/

    public SpectacleE spectaleSelected() {
        SpectacleE s = tableauSpectacles.getSelectionModel().getSelectedItem();
        return (s);
    }
   /******Boutton clear ALL *********/
    public void clearAllFields() {
        titrefl.setText("");
        datefl.setValue(null);
        genrefl.setValue(null);
        idsallefl.setValue(null);
        spectacleSelected = null;
    }

/**************Si on click 2 fois sur le TableViewer les champs seront executer sur le file text**************/
    public void handleDoubleMouseClicked(MouseEvent event) {
        //System.out.println(tableauSpectacles.getSelectionModel().getSelectedItem());
        if (event.isPrimaryButtonDown() && event.getClickCount() == 2) {
            SpectacleE s = tableauSpectacles.getSelectionModel().getSelectedItem();
            titrefl.setText(s.getTitre());
            datefl.setValue(new Date(s.getDate().getTime()).toLocalDate());
            genrefl.setValue(s.getGenre());
            idsallefl.setValue(s.getIdSalle());
            spectacleSelected = s;
        }
    }

    /******************************************Methode Update par boutton****************************************************/


    public void Modifier(ActionEvent event) {
        SpectacleE s = spectacleSelected;
        if (s != null) {
            SpectacleE newSpecDetails = new SpectacleE(s.getId(), titrefl.getText(), new Date(s.getDate().getTime()), genrefl.getSelectionModel().getSelectedItem(), s.getIdUser(), idsallefl.getSelectionModel().getSelectedItem());
            /************ update BD, actualiser tableView, clear tous les champs*****************/

            if (newSpecDetails != s) {
                ss.updateSpectacle(newSpecDetails);
                clearAllFields();
                showSpectacle();
            } else {
                Alert alert = new Alert(Alert.AlertType.WARNING);
                alert.setTitle("Warning");
                alert.setHeaderText("Vous n'avez pas modifié les détails du spectacle");
                alert.showAndWait();
            }
           } else {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Warning");
            alert.setHeaderText("Veuillez selectionner un spectcale pour le modifier");
            alert.showAndWait();
        }
    }


    /******************************************Methode DELETE par boutton****************************************************/

    public void Suppression(ActionEvent event) throws SQLException {

        SpectacleE s = spectacleSelected;
        clearAllFields();

        if (s != null) {
            ss.deleteSpectacle(s.getId());
            showSpectacle();

        } else {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Warning");
            alert.setHeaderText("Veuillez selectionner un spectcale pour le supprimer");
            alert.showAndWait();
        }

    }


    /*************************Affichage des champs spectacle dans le tableaux de l'interface*******************/

    public void showSpectacle() {
        servicespectacleIMP ss = new servicespectacleIMP();
        ObservableList<SpectacleE> listSpectacle = ss.LoadSpectacleFromDatabase();
        //System.out.println(listSpectacle);
        tableauSpectacles.setItems(listSpectacle);
    }


    /************************Initialisation des elements qui sont dd*************************/
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        genrefl.getItems().addAll("danse", "musical", "pièce théâtral");

        idsallefl.getItems().addAll(ss.LoadSalleSpecFromDatabase());


/**************************Affichage dans le tableau de l'interface***********************/


        ObservableList<SpectacleE> listSalle = ss.LoadSpectacleFromDatabase();
        showSpectacle();

/************************initialisation des valeurs*************************/

        idcol.setCellValueFactory(new PropertyValueFactory<>("id"));
        titrecol.setCellValueFactory(new PropertyValueFactory<>("titre"));
        datetcol.setCellValueFactory(new PropertyValueFactory<>("date"));
        genrecol.setCellValueFactory(new PropertyValueFactory<>("genre"));
        idusercol.setCellValueFactory(new PropertyValueFactory<>("idUser"));
        idsallecol.setCellValueFactory(new PropertyValueFactory<>("idSalle"));


    }


}


