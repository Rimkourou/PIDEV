package controller.hazem;

import entitie.hazem.Reclamation;
import entitie.hazem.SalleDeCinema;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import service.hazem.RecalamationService;
import service.hazem.SaleDeCinemaService;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class ReclamationControllerClient implements Initializable {
    @FXML
    private TextField tfObjectReclamation;
    @FXML
    private TextField tfDesReclamation;

    @FXML
    private TableView<Reclamation> tvReclamation;

    @FXML
    private TextField tfSearchR;

    @FXML
    private TableColumn<Reclamation, Integer> colId;
    @FXML
    private TableColumn<Reclamation, String> colObject;
    @FXML
    private TableColumn<Reclamation, String> colDescription;

    @FXML
    private TableColumn<Reclamation, String> colState;

    @FXML
    private ComboBox<String> selectSalle;

    @FXML
    private ComboBox<String> selectState;



    private RecalamationService recalamationService = new RecalamationService();
    private SaleDeCinemaService saleDeCinemaService = new SaleDeCinemaService();
    private int idReclamation;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        ObservableList<String> ols = saleDeCinemaService.salleDeCinemaListeName();
            selectSalle.setItems(ols);
            selectState.setItems(FXCollections.observableArrayList("All", "pas encore", "en cours","resolu"));
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
//            System.out.println(idCelle);
            tfObjectReclamation.setText(colObject.getCellData(index));
            tfDesReclamation.setText(colDescription.getCellData(index));
            Reclamation r = recalamationService.getReclamationById(colId.getCellData(index));
//            System.out.println(r.toString());
            SalleDeCinema s = saleDeCinemaService.getSalleById(r.getIdSalle());
//            System.out.println(s.toString());
            selectSalle.getSelectionModel().select(s.getNom());
        }
    }

    public void handleAddReclamation(MouseEvent mouseEvent) {

        ObservableList<SalleDeCinema> ols = saleDeCinemaService.rechercherSalleByName(selectSalle.getValue());
        Reclamation r = new Reclamation(tfObjectReclamation.getText(),
                tfDesReclamation.getText(),
                "pas encore",
                ols.get(0).getId());
        recalamationService.AddReclamation(r);
        showReclamationListe();

    }

    public void handleUpdateReclamation(MouseEvent mouseEvent) {
        System.out.println(idReclamation);
        ObservableList<SalleDeCinema> s = saleDeCinemaService.rechercherSalleByName(selectSalle.getValue());
        System.out.println(s.get(0).toString());
        Reclamation reclamation = recalamationService.getReclamationById(idReclamation);
        Reclamation r = new Reclamation(reclamation.getId(),
                tfObjectReclamation.getText(),
                tfDesReclamation.getText(),
                reclamation.getState(),
                s.get(0).getId());
        recalamationService.updateReclamation(r);
        showReclamationListe();
    }

    public void handleDeleteReclamation(MouseEvent mouseEvent) {
        recalamationService.deleteReclamation(idReclamation);
        showReclamationListe();

    }

    public void handleSalle(MouseEvent mouseEvent) throws IOException {
        System.out.println("handle salle");
        Parent salleDeCinemaParent = FXMLLoader.load(getClass().getResource("../../GUI/hazem/membre/SalledeCinemaClient.fxml"));
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


    public void handleSearchByState(ActionEvent actionEvent) {
        String state = selectState.getValue();
        if (state == "All") {
            showReclamationListe();
        } else {
            ObservableList<Reclamation> pf = recalamationService.rechercherReclamationByState(selectState.getValue());
            colId.setCellValueFactory(new PropertyValueFactory<Reclamation, Integer>("id"));
            colObject.setCellValueFactory(new PropertyValueFactory<Reclamation, String>("objet"));
            colDescription.setCellValueFactory(new PropertyValueFactory<Reclamation, String>("description"));
            colState.setCellValueFactory(new PropertyValueFactory<Reclamation, String>("state"));
            tvReclamation.setItems(pf);
        }

    }
}
