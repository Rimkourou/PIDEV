package controller;

import entitie.Reclamation;
import entitie.SalleDeCinema;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import service.RecalamationService;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class ReclamationControllerAdmin implements Initializable {

    @FXML
    private TextField tfStateReclamation;

    @FXML
    private TextField tfSearchR;
    @FXML

    private TableView<Reclamation> tvReclamation;

    @FXML
    private TableColumn<Reclamation, Integer> colId;
    @FXML
    private TableColumn<Reclamation, String> colObject;
    @FXML
    private TableColumn<Reclamation, String> colDescription;

    @FXML
    private TableColumn<Reclamation, String> colState;

    private RecalamationService recalamationService = new RecalamationService();

    private int idReclamation;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        showReclamationListe();
    }

    private void showReclamationListe() {
        ObservableList<Reclamation> pf = recalamationService.reclamationListe(1);
        colId.setCellValueFactory(new PropertyValueFactory<Reclamation, Integer>("id"));
        colObject.setCellValueFactory(new PropertyValueFactory<Reclamation, String>("objet"));
        colDescription.setCellValueFactory(new PropertyValueFactory<Reclamation, String>("description"));
        colState.setCellValueFactory(new PropertyValueFactory<Reclamation, String>("state"));
        tvReclamation.setItems(pf);
    }

    public void handleReclamationDetail(MouseEvent mouseEvent) {
        int index = tvReclamation.getSelectionModel().getSelectedIndex();
        if (index > -1) {
            idReclamation = colId.getCellData(index);
            System.out.println(idReclamation);
//            System.out.println(colState.getCellData(index));
//            tfObjectReclamation.setText(colObject.getCellData(index));
//            tfDesReclamation.setText(colDescription.getCellData(index));
            tfStateReclamation.setText(colState.getCellData(index));
        }
    }

//    public void handleAddReclamation(MouseEvent mouseEvent) {
//        Reclamation r = new Reclamation(tfObjectReclamation.getText(),
//                tfDesReclamation.getText(),
//                tfStateReclamation.getText(),
//                1);
//        recalamationService.AddReclamation(r);
//        showReclamationListe();
//
//    }

    public void handleUpdateReclamation(MouseEvent mouseEvent) {
        System.out.println(idReclamation);

        Reclamation reclamation = recalamationService.getReclamationById(idReclamation);
        System.out.println(reclamation.toString());
        Reclamation r = new Reclamation(reclamation.getId(),
                reclamation.getObjet(),
                reclamation.getDescription(),
                tfStateReclamation.getText(),
                reclamation.getIdSalle());
        recalamationService.updateReclamation(r);
        showReclamationListe();
    }

    public void handleDeleteReclamation(MouseEvent mouseEvent) {
        recalamationService.deleteReclamation(idReclamation);
        showReclamationListe();

    }

    public void handleSalle(MouseEvent mouseEvent) throws IOException {
        System.out.println("handle salle");
        Parent salleDeCinemaParent = FXMLLoader.load(getClass().getResource("../GUI/admin/SalledeCinemaAdmin.fxml"));
        Scene salleDeCinemaScene = new Scene(salleDeCinemaParent);

        Stage window = (Stage) ((Node)mouseEvent.getSource()).getScene().getWindow();
        window.setScene(salleDeCinemaScene);
        window.show();
    }

    public void handleSearchReclamation(KeyEvent keyEvent) {
        ObservableList<Reclamation> pf = recalamationService.rechercherReclamationByObject(tfSearchR.getText());
        colId.setCellValueFactory(new PropertyValueFactory<Reclamation, Integer>("id"));
        colObject.setCellValueFactory(new PropertyValueFactory<Reclamation, String>("objet"));
        colDescription.setCellValueFactory(new PropertyValueFactory<Reclamation, String>("description"));
        colState.setCellValueFactory(new PropertyValueFactory<Reclamation, String>("state"));
        tvReclamation.setItems(pf);


    }
}
