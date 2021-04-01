package controller.hazem;

import controller.ons.FXMLDocumentController;
import entitie.hazem.SalleDeCinema;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import service.hazem.SaleDeCinemaService;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class ControllerSalleDeCinemaAdmin implements Initializable {

    @FXML
    private TextField tfSearch;
    @FXML
    private TextField tfTitreSalle;
    @FXML
    private TextField tfNbrPlace;
    @FXML
    private TextField tfDescriptipn;
    @FXML
    private TextField tfAdresse;
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

    private int idU = FXMLDocumentController.currentUser;
    static int idSalle;
    private int idCelle = -1;
    private SaleDeCinemaService saleDeCinemaService = new SaleDeCinemaService();

    public ControllerSalleDeCinemaAdmin() {
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

    private int checkInt(String nombre) {
        for (int i=0; i<nombre.length(); i++){
            System.out.println((int) nombre.charAt(i));
            System.out.println("char " + (int) nombre.charAt(i));
            if ((int) nombre.charAt(i) < 48 || (int) nombre.charAt(i) > 57) {
                return -1;
            }

        }
        return 1;
    }
    public void handleAddSalle(MouseEvent mouseEvent) {
//        System.out.println(checkInt(tfNbrPlace.getText())+ "idU "+idU);
        System.out.println(checkInt(tfNbrPlace.getText()));

        if (tfTitreSalle.getText().trim().isEmpty() ||
                tfAdresse.getText().trim().isEmpty() ||
                tfDescriptipn.getText().trim().isEmpty() ||
                tfNbrPlace.getText().trim().isEmpty()) {
            /*****affichage d'un alerte : vous devez remplir tt les champs******/
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Warning"); //affichage dans la barre de titre
            alert.setHeaderText("tous les champs sont obligatoires");
            alert.showAndWait();
        } else {
            if( checkInt(tfNbrPlace.getText()) == -1) {
//
                Alert alert = new Alert(Alert.AlertType.WARNING);
                alert.setTitle("Warning"); //affichage dans la barre de titre
                alert.setHeaderText("le champs nombre de place doit être un nombre");
                alert.showAndWait();
            }else {
                SalleDeCinema salle = new SalleDeCinema(tfTitreSalle.getText(),
                        Integer.parseInt(tfNbrPlace.getText()),
                        tfDescriptipn.getText(),
                        tfAdresse.getText(),
                        idU);
                saleDeCinemaService.AddSalle(salle);
                showSalleSpectacle();
            }

        }

    }

    public void handleUpdateSalle(MouseEvent mouseEvent) {
        System.out.println("update work");
        System.out.println(idCelle);
        SalleDeCinema s = new SalleDeCinema(idCelle,
                tfTitreSalle.getText(),
                Integer.parseInt(tfNbrPlace.getText()),
                tfDescriptipn.getText(),
                tfAdresse.getText(),
                idU);
        System.out.println(s.toString());
        saleDeCinemaService.updateSalleDeCinema(s);
        showSalleSpectacle();


    }

    public void handleDeleteSalle(MouseEvent mouseEvent) {
        System.out.println(idCelle);
        saleDeCinemaService.deleteSalleDeCinema(idCelle);
        showSalleSpectacle();
    }


    public void handleLineDetail(MouseEvent mouseEvent) {
        int index = tvSalle.getSelectionModel().getSelectedIndex();
        if (index > -1) {
            idSalle = colId.getCellData(index);
            idCelle = colId.getCellData(index);
//            System.out.println(idCelle);
            System.out.println(idSalle);
            tfTitreSalle.setText(colTitre.getCellData(index));
            tfNbrPlace.setText(String.valueOf(colNbrPlace.getCellData(index)));
            tfDescriptipn.setText(colDescription.getCellData(index));
            tfAdresse.setText(colAdresse.getCellData(index));
        }

    }

    public void handlePlanningFilm(MouseEvent mouseEvent) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("../GUI/share/PlanningFilmAdmin.fxml"));
        Parent root1 = (Parent) fxmlLoader.load();
        Stage stage = new Stage();
        stage.setTitle("Planning Film");
        stage.setScene(new Scene(root1, 1000, 500));
        stage.show();
    }

    public void handlePlanningSpectacle(MouseEvent mouseEvent) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("../GUI/share/PlanningSpectacleAdmin.fxml"));
        Parent root2 = (Parent) fxmlLoader.load();
        Stage stage = new Stage();
        stage.setTitle("Planning Spectacle");
        stage.setScene(new Scene(root2, 1000, 500));
        stage.show();

    }

    public void handleNavReclamation(MouseEvent mouseEvent) throws IOException {
        Parent reclamationParent = FXMLLoader.load(getClass().getResource("../../GUI/hazem/admin/ReclamationAdmin.fxml"));
        Scene reclamationScene = new Scene(reclamationParent, 1200, 680);

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

    public void handleShowPromotion(MouseEvent mouseEvent) throws IOException {
        Parent promotiontParent = FXMLLoader.load(getClass().getResource("../../GUI/ons/Promotion.fxml"));
        Scene promotionScene = new Scene(promotiontParent, 1200, 680);

        Stage window = (Stage) ((Node) mouseEvent.getSource()).getScene().getWindow();
        window.setScene(promotionScene);
        window.show();
    }

    public void handleShowUsers(MouseEvent mouseEvent) throws IOException {
        Parent usersParent = FXMLLoader.load(getClass().getResource("../../GUI/ons/Admin.fxml"));
        Scene usersScene = new Scene(usersParent, 1200, 680);

        Stage window = (Stage) ((Node) mouseEvent.getSource()).getScene().getWindow();
        window.setScene(usersScene);
        window.show();
    }
    public void handleShowFilm(MouseEvent mouseEvent) throws IOException {
        Parent Parent3 = FXMLLoader.load(getClass().getResource("../../GUI/wifek/DashboardFilm.fxml"));
        Scene Scene3 = new Scene(Parent3, 1200, 680);

        Stage window = (Stage) ((Node) mouseEvent.getSource()).getScene().getWindow();
        window.setScene(Scene3);
        window.show();
    }

    public void handleShowReservation(MouseEvent mouseEvent) throws IOException {
        Parent Parent4 = FXMLLoader.load(getClass().getResource("../../GUI/wifek/DashboardReservation.fxml"));
        Scene Scene4 = new Scene(Parent4, 1200, 680);

        Stage window = (Stage) ((Node) mouseEvent.getSource()).getScene().getWindow();
        window.setScene(Scene4);
        window.show();
    }

    public void handleShowPlanning(MouseEvent mouseEvent) throws IOException {
        Parent Parent5 = FXMLLoader.load(getClass().getResource("../../GUI/sana/Planning.fxml"));
        Scene Scene5 = new Scene(Parent5, 1200, 680);

        Stage window = (Stage) ((Node) mouseEvent.getSource()).getScene().getWindow();
        window.setScene(Scene5);
        window.show();
    }

    public void handleShowSpectacle(MouseEvent mouseEvent) throws IOException {
        Parent Parent6 = FXMLLoader.load(getClass().getResource("../../GUI/sana/Spectacle.fxml"));
        Scene Scene6 = new Scene(Parent6, 1200, 680);

        Stage window = (Stage) ((Node) mouseEvent.getSource()).getScene().getWindow();
        window.setScene(Scene6);
        window.show();
    }
}
