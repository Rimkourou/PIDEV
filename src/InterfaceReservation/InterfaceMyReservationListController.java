package InterfaceReservation;


import Dashboard.DashboardFilmController;
import entites.Planning;
import entites.Reservation;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import services.FilmService;
import services.ReservationService;

import javax.swing.*;
import java.io.IOException;
import java.net.URL;
import java.sql.Date;
import java.util.ResourceBundle;
import java.util.function.Predicate;
import java.util.logging.Level;
import java.util.logging.Logger;

public class InterfaceMyReservationListController implements Initializable {
    ReservationService rs= new ReservationService();

    @FXML
    private TableView<Reservation> tvReservation;

    @FXML
    private TableColumn<Reservation, Integer> colId;

    @FXML
    private TableColumn<Reservation, Date> colDate;

    @FXML
    private TableColumn<Reservation, Integer> colUser;

    @FXML
    private TableColumn<Reservation, Integer> colFilm;

    @FXML
    private TableColumn<Reservation, Integer> colSalle;

    @FXML
    private TableColumn<Reservation, Integer> colPlace;

    @FXML
    private TextField tfId;

    @FXML
    private DatePicker tfDate;

    @FXML
    private TextField tfIdUser;

    @FXML
    private TextField tfFilm;

    @FXML
    private TextField tfSalle;

    @FXML
    private TextField tfPlace;

    @FXML
    private TextField tfSearch;

    @FXML
    private Button btnAdd;

    @FXML
    private Button btnEdit;

    @FXML
    private Button btnDelete;

    @FXML
    private Button btnMovies;

    private Planning currentReservation = null;

    public void test (Planning p){
        currentReservation = p;
        tfFilm.setText(String.valueOf(p.getTitreEvent()));
        tfSalle.setText(String.valueOf(p.getNomSalle()));
    }


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        showFilm();
        searchReservation();
    }

    @FXML
    void handleButtonAction(ActionEvent event) throws IOException {
        if (event.getSource() == btnDelete) {
            DeleteReservation();
        }
    }

    @FXML
    void handleMouseAction() {
        int index = tvReservation.getSelectionModel().getSelectedIndex();
        if(index<=-1){
            return;
        }
    }

    private void showFilm() {
        ObservableList<Reservation> list= rs.reservationList();
        colId.setCellValueFactory(new PropertyValueFactory<Reservation, Integer>("id"));
        colDate.setCellValueFactory(new PropertyValueFactory<Reservation, Date>("dateReservation"));
        colUser.setCellValueFactory(new PropertyValueFactory<Reservation, Integer>("idUser"));
        colSalle.setCellValueFactory(new PropertyValueFactory<Reservation, Integer>("idSalle"));
        colFilm.setCellValueFactory(new PropertyValueFactory<Reservation, Integer>("idFilm"));
        colPlace.setCellValueFactory(new PropertyValueFactory<Reservation, Integer>("nbrPlaceRes"));
        tvReservation.setItems(list);
    }

    @FXML
    void searchReservation() {
        FilteredList<Reservation> filteredData = new FilteredList<>(rs.reservationList(), b -> true);
        tfSearch.setOnKeyReleased(b -> {
            tfSearch.textProperty().addListener((observableValue, oldValue, newValue) ->{
                filteredData.setPredicate((Predicate<? super Reservation>) reservation ->{
                    if(newValue == null || newValue.isEmpty()){
                        return true;
                    }
                    String lowerCaseFilter = newValue.toLowerCase();
                    if(reservation.getIdSalle().toLowerCase().contains(lowerCaseFilter)){
                        return true;
                    }else if(reservation.getIdFilm().toLowerCase().contains(lowerCaseFilter)){
                        return true;
                    }else if(reservation.getIdSpectacle().toLowerCase().contains(lowerCaseFilter)){
                        return true;
                    }else
                    return false;
                });
            });
            SortedList<Reservation> sortedList = new SortedList<>(filteredData);
            sortedList.comparatorProperty().bind(tvReservation.comparatorProperty());
            tvReservation.setItems(sortedList);
        });
    }

    //.setValue(new Date(p.getDate().getTime()).toLocalDate());

    public void DeleteReservation(){
        try {
            ReservationService rs = new ReservationService();
            int index = tvReservation.getSelectionModel().getSelectedIndex();
            Reservation r = tvReservation.getItems().get(index);
            rs.deleteReservation(r);
            showFilm();
            JOptionPane.showMessageDialog(null, "reservation deleted successfully");
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e);
            Logger.getLogger(DashboardFilmController.class.getName()).log(Level.SEVERE, null, e);
        }
    }

    @FXML
    private Button linkRes;

    @FXML
    private AnchorPane reservationPage;

    @FXML
    void lier(ActionEvent event) throws IOException {
        if(event.getSource() == linkRes){
            Parent fxml = FXMLLoader.load(getClass().getResource("InterfaceReservation.fxml"));
            reservationPage.getChildren().removeAll();
            reservationPage.getChildren().setAll(fxml);

        } else if (event.getSource() == btnMovies){
            Parent fxml = FXMLLoader.load(getClass().getResource("/InterfaceFilm/InterfaceFilm.fxml"));
            reservationPage.getChildren().removeAll();
            reservationPage.getChildren().setAll(fxml);

        }
    }
}
