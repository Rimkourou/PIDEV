package controller.wifek;

import entitie.wifek.Reservation;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;
import service.wifek.ReservationService;

import javax.swing.*;
import java.io.IOException;
import java.net.URL;
import java.util.Date;
import java.util.ResourceBundle;
import java.util.function.Predicate;
import java.util.logging.Level;
import java.util.logging.Logger;

public class DashboardReservationController implements Initializable {
    ReservationService rs= new ReservationService();

    @FXML
    private TextField tfSearch;

    @FXML
    private TableView<Reservation> tvReservation;

    @FXML
    private TableColumn<Reservation, Integer> colId;

    @FXML
    private TableColumn<Reservation, Date> colDate;

    @FXML
    private TableColumn<Reservation, Integer> colUser;

    @FXML
    private TableColumn<Reservation, Integer> colSalle;

    @FXML
    private TableColumn<Reservation, Integer> colFilm;

    @FXML
    private TableColumn<Reservation, Integer> colPlace;

    @FXML
    private TextField tfId;

    @FXML
    private TextField tfDate;

    @FXML
    private TextField tfUser;

    @FXML
    private TextField tfSalle;

    @FXML
    private TextField tfFilm;

    @FXML
    private TextField tfPlace;

    @FXML
    private Button btnAdd;

    @FXML
    private Button btnEdit;

    @FXML
    private Button btnDelete;

    @FXML
    private HBox linkRes;

    @FXML
    private HBox linkShows;

    @FXML
    private HBox linkPlanning;

    @FXML
    private AnchorPane page;

    @FXML
    private HBox promotion_vBox;
    @FXML
    private HBox user_vBox;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        showFilm();
        searchReservation();
    }

    @FXML
    void handleButtonAction(ActionEvent event) {
        if (event.getSource() == btnAdd){
            AddReservation();
        } else if (event.getSource() == btnEdit) {
            EditReservation();
        } else {
            DeleteReservation();
        }
    }

    @FXML
    void handleMouseAction() {
        int index = tvReservation.getSelectionModel().getSelectedIndex();
        if(index<=-1){

            return;
        }
        tfId.setText(colId.getCellData(index).toString());
        tfDate.setText(colDate.getCellData(index).toString());
        tfUser.setText(colUser.getCellData(index).toString());
        tfSalle.setText(colSalle.getCellData(index).toString());
        tfFilm.setText(colFilm.getCellData(index).toString());
        tfPlace.setText(colPlace.getCellData(index).toString());
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

    public void AddReservation() {
        try {
            Date date_util = new Date();
            Reservation r = new Reservation(new java.sql.Date(date_util.getTime()), Integer.parseInt(tfUser.getText()), tfSalle.getText(), tfFilm.getText(), Integer.parseInt(tfPlace.getText()), tfFilm.getText());
            ReservationService rs = new ReservationService();
            rs.addReservation(r);
            showFilm();
            JOptionPane.showMessageDialog(null, "reservation added successfully");
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e);
        }
    }

    public void EditReservation(){
        try {
            Date date_util = new Date();
            Reservation r = new Reservation(Integer.parseInt(tfId.getText()),new java.sql.Date(date_util.getTime()), Integer.parseInt(tfUser.getText()), tfSalle.getText(), tfFilm.getText(), Integer.parseInt(tfPlace.getText()), tfFilm.getText());
            rs.addReservation(r);
            showFilm();
            JOptionPane.showMessageDialog(null, "reservation successfully updated");
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e);
            Logger.getLogger(DashboardFilmController.class.getName()).log(Level.SEVERE, null, e);
        }
    }

    public void DeleteReservation(){
        try {
            Date date_util = new Date();
            Reservation r = new Reservation(Integer.parseInt(tfId.getText()),new java.sql.Date(date_util.getTime()), Integer.parseInt(tfUser.getText()), tfSalle.getText(), tfFilm.getText(), Integer.parseInt(tfPlace.getText()), tfFilm.getText());
            rs.deleteReservation(r);
            showFilm();
            JOptionPane.showMessageDialog(null, "reservation deleted successfully");
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e);
            Logger.getLogger(DashboardFilmController.class.getName()).log(Level.SEVERE, null, e);
        }
    }

    @FXML
    void lier(MouseEvent event) throws IOException {
        if(event.getSource() == linkRes){
            Parent fxml = FXMLLoader.load(getClass().getResource("../../GUI/wifek/DashboardFilm.fxml"));
            page.getChildren().removeAll();
            page.getChildren().setAll(fxml);

        } else if (event.getSource() == linkShows){
            Parent fxml = FXMLLoader.load(getClass().getResource("../../GUI/sana/Spectacle.fxml"));
            page.getChildren().removeAll();
            page.getChildren().setAll(fxml);

        } else if(event.getSource() == linkPlanning){
            Parent fxml = FXMLLoader.load(getClass().getResource("../../GUI/sana/Planning.fxml"));
            page.getChildren().removeAll();
            page.getChildren().setAll(fxml);

        } else if(event.getSource() == promotion_vBox){
            Parent fxml = FXMLLoader.load(getClass().getResource("../../GUI/ons/Promotion.fxml"));
            page.getChildren().removeAll();
            page.getChildren().setAll(fxml);
        }
        else if(event.getSource() ==user_vBox){
            Parent fxml = FXMLLoader.load(getClass().getResource("../../GUI/ons/Admin.fxml"));
            page.getChildren().removeAll();
            page.getChildren().setAll(fxml);
        }
    }

    public void handleShowRooms(MouseEvent mouseEvent) throws IOException {
        Parent movieParent = FXMLLoader.load(getClass().getResource("../../GUI/hazem/admin/SalledeCinemaAdmin.fxml"));
        Scene movieScene = new Scene(movieParent, 1200, 680);

        Stage window = (Stage) ((Node) mouseEvent.getSource()).getScene().getWindow();
        window.setScene(movieScene);
        window.show();

    }

    public void handleShowComplaint(MouseEvent mouseEvent) throws IOException {
        Parent complaintParent = FXMLLoader.load(getClass().getResource("../../GUI/hazem/admin/ReclamationAdmin.fxml"));
        Scene complaintScene = new Scene(complaintParent, 1200, 680);

        Stage window = (Stage) ((Node) mouseEvent.getSource()).getScene().getWindow();
        window.setScene(complaintScene);
        window.show();
    }

}

