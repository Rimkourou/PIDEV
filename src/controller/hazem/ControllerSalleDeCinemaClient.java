package controller.hazem;

import entitie.hazem.share.MapSelect;
import entitie.hazem.SalleDeCinema;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import service.hazem.MapService;
import service.hazem.SaleDeCinemaService;
import entitie.hazem.share.Mapa;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class ControllerSalleDeCinemaClient implements Initializable {

    @FXML
    private TextField tfSearch;
    @FXML
    private TableView<SalleDeCinema> tvSalle;

    @FXML
    private TableColumn<SalleDeCinema, Integer> colId;
    @FXML
    private TableColumn<SalleDeCinema, String> colTitre;
    @FXML
    private TableColumn<SalleDeCinema, String> colDescription;

    @FXML
    private TableColumn<SalleDeCinema, Integer> colNbrPlace;

    @FXML
    private TableColumn<SalleDeCinema, String> colAdresse;

    static int idSalle = -1;
    private int idCelle = -1;
    private SaleDeCinemaService saleDeCinemaService = new SaleDeCinemaService();

    public ControllerSalleDeCinemaClient() {
    }


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        showSalleSpectacle();
    }


    public void showSalleSpectacle() {

        ObservableList<SalleDeCinema> listSalle = saleDeCinemaService.salleDeCinemaListe();
//        System.out.println(listSalle.size());
        colId.setCellValueFactory(new PropertyValueFactory<SalleDeCinema, Integer>("id"));
        colTitre.setCellValueFactory(new PropertyValueFactory<SalleDeCinema, String>("nom"));
        colNbrPlace.setCellValueFactory(new PropertyValueFactory<SalleDeCinema, Integer>("nbrPlace"));
        colDescription.setCellValueFactory(new PropertyValueFactory<SalleDeCinema, String>("Description"));
        colAdresse.setCellValueFactory(new PropertyValueFactory<SalleDeCinema, String>("adresse"));
        tvSalle.setItems(listSalle);

    }


    public void handleLineDetail(MouseEvent mouseEvent) {
        int index = tvSalle.getSelectionModel().getSelectedIndex();
        if (index > -1) {
            idSalle = colId.getCellData(index);
            System.out.println(idSalle);
            idCelle = colId.getCellData(index);
        }

    }

    public void handlePlanningFilm(MouseEvent mouseEvent) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("../GUI/share/PlanningFilmClient.fxml"));
        Parent root1 = (Parent) fxmlLoader.load();
        Stage stage = new Stage();
        stage.setTitle("Planning Film");
        stage.setScene(new Scene(root1, 1000, 500));
        stage.show();
    }

    public void handlePlanningSpectacle(MouseEvent mouseEvent) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("../GUI/share/PlanningSpectacleClient.fxml"));
        Parent root2 = (Parent) fxmlLoader.load();
        Stage stage = new Stage();
        stage.setTitle("Planning Spectacle");
        stage.setScene(new Scene(root2, 1000, 500));
        stage.show();

    }

    public void handleNavReclamation(MouseEvent mouseEvent) throws IOException {
        Parent reclamationParent = FXMLLoader.load(getClass().getResource("../GUI/membre/ReclamationClient.fxml"));
        Scene reclamationScene = new Scene(reclamationParent);

        Stage window = (Stage) ((Node) mouseEvent.getSource()).getScene().getWindow();
        window.setScene(reclamationScene);
        window.show();
    }


    public void handleSalle(MouseEvent mouseEvent) {
    }

    public void handleSearchSalle(KeyEvent keyEvent) {
        System.out.println("on change");
        ObservableList<SalleDeCinema> listSalle = saleDeCinemaService.rechercherSalleByName(tfSearch.getText());
        colId.setCellValueFactory(new PropertyValueFactory<SalleDeCinema, Integer>("id"));
        colTitre.setCellValueFactory(new PropertyValueFactory<SalleDeCinema, String>("nom"));
        colNbrPlace.setCellValueFactory(new PropertyValueFactory<SalleDeCinema, Integer>("nbrPlace"));
        colDescription.setCellValueFactory(new PropertyValueFactory<SalleDeCinema, String>("Description"));
        colAdresse.setCellValueFactory(new PropertyValueFactory<SalleDeCinema, String>("adresse"));
        tvSalle.setItems(listSalle);
    }

    public void showInMaps(ActionEvent actionEvent) {
        if (idSalle == -1) {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Warning"); //affichage dans la barre de titre
            alert.setHeaderText("You must select salle");
            alert.showAndWait();
        } else {
            SalleDeCinema salleDeCinema = saleDeCinemaService.getSalleById(idSalle);
            MapService mapService = new MapService();
            MapSelect mapSelect = mapService.salleInfoByName(salleDeCinema.getNom());
            if (mapSelect.getId() == 0 ) {
                Alert alert = new Alert(Alert.AlertType.WARNING);
                alert.setTitle("Warning"); //affichage dans la barre de titre
                alert.setHeaderText("come back later");
                alert.showAndWait();
            }else {

                Mapa temp = new Mapa(mapSelect.getNom(), mapSelect.getLat(), mapSelect.getLon());
            }
        }
    }
}
