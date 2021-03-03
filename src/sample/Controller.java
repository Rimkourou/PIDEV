package sample;

import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;

import java.awt.event.ActionEvent;
import java.net.URL;
import java.util.ResourceBundle;

public class Controller implements Initializable {

        @FXML
        private AnchorPane tvFilm;

        @FXML
        private TextField tfId;

        @FXML
        private TextField tfTitre;

        @FXML
        private TextField tfDescription;

        @FXML
        private TextField tfAuteur;

        @FXML
        private TextField tfCategorie;

        @FXML
        private TextField tfGenre;

        @FXML
        private TextField tfIdUser;

        @FXML
        private TextField tfIdSalle;

        @FXML
        private TableColumn<?, ?> colId;

        @FXML
        private TableColumn<?, ?> ColTitre;

        @FXML
        private TableColumn<?, ?> ColDescription;

        @FXML
        private TableColumn<?, ?> colAuteur;

        @FXML
        private TableColumn<?, ?> ColCatégorie;

        @FXML
        private TableColumn<?, ?> ColGenre;

        @FXML
        private TableColumn<?, ?> ColIdUser;

        @FXML
        private TableColumn<?, ?> ColIdSalle;

        @FXML
        private Button btnEdit;

        @FXML
        private Button btnDelete;

        @FXML
        private Button btnInsert;

        @FXML
        void handleButtonAction(ActionEvent event) {

        }




    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }
}
