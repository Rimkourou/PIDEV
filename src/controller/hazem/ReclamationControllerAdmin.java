package controller.hazem;

import entitie.hazem.share.JavaMailTransaction;
import entitie.hazem.Reclamation;
import javafx.collections.FXCollections;
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
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import service.hazem.RecalamationService;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class ReclamationControllerAdmin implements Initializable {


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

    @FXML
    private ComboBox<String> selectState;

    @FXML
    private TextArea tfDescriptionMail;

    @FXML
    private AnchorPane page;

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
        selectState.setItems(FXCollections.observableArrayList("pas encore", "en cours","resolu"));
    }

    public void handleReclamationDetail(MouseEvent mouseEvent) {
        int index = tvReclamation.getSelectionModel().getSelectedIndex();
        if (index > -1) {
            idReclamation = colId.getCellData(index);
            System.out.println(idReclamation);
//            System.out.println(colState.getCellData(index));
//            tfObjectReclamation.setText(colObject.getCellData(index));
//            tfDesReclamation.setText(colDescription.getCellData(index));
            selectState.getSelectionModel().select(colState.getCellData(index));
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

    public void handleUpdateReclamation(MouseEvent mouseEvent) throws Exception {
        System.out.println(idReclamation);
//        System.out.println("tt : " + tfDescriptionMail.getText());
        Reclamation reclamation = recalamationService.getReclamationById(idReclamation);
        System.out.println(reclamation.toString());
        // text if state changed or not
        System.out.println(selectState.getValue());
//            if (selectState.getValue().trim().equals(reclamation.getState())){
//                System.out.println("meme stage");
//            }

        if (selectState.getSelectionModel().getSelectedItem() == null) {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Warning"); //affichage dans la barre de titre
            alert.setHeaderText("you must change state");
            alert.showAndWait();
        }else {
            Reclamation r = new Reclamation(reclamation.getId(),
                    reclamation.getObjet(),
                    reclamation.getDescription(),
                    selectState.getValue(),
                    reclamation.getIdSalle());
            recalamationService.updateReclamation(r);

            if (tfDescriptionMail.getText().isEmpty()) {
                JavaMailTransaction.sendMail("tunishow.tn@gmail.com", "reply to your complaint id :"+String.valueOf(r.getId()), "your complaint state changed to "+selectState.getValue());
            }else {
                JavaMailTransaction.sendMail("tunishow.tn@gmail.com", "hazem", "your complaint state changed to "+selectState.getValue() + "<br><br>" +tfDescriptionMail.getText());
            }
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Succes"); //affichage dans la barre de titre
            alert.setHeaderText("you state has been changed ");
            alert.showAndWait();
            tfDescriptionMail.setText("");
        }
        showReclamationListe();
    }

    public void handleDeleteReclamation(MouseEvent mouseEvent) {
        recalamationService.deleteReclamation(idReclamation);
        showReclamationListe();

    }

    public void handleSalle(MouseEvent mouseEvent) throws IOException {
        System.out.println("handle salle");
        Parent salleDeCinemaParent = FXMLLoader.load(getClass().getResource("../../GUI/hazem/admin/SalledeCinemaAdmin.fxml"));
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
