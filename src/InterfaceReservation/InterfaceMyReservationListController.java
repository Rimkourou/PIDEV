package InterfaceReservation;


import Dashboard.DashboardFilmController;
import entites.Reservation;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import services.ReservationService;

import javax.swing.*;
import java.io.IOException;
import java.net.URL;
import java.util.Date;
import java.util.ResourceBundle;
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
    private TableColumn<Reservation, Integer> colFilm;

    @FXML
    private TableColumn<Reservation, Integer> colSalle;

    @FXML
    private TableColumn<Reservation, Integer> colPlace;

    @FXML
    private TableColumn<Reservation, Integer> colUser;

    @FXML
    private TextField tfId,tfUser;

    @FXML
    private TextField tfDate;

    @FXML
    private TextField tfFilm;

    @FXML
    private TextField tfSalle;

    @FXML
    private TextField tfPlace;

    @FXML
    private Button btnEdit;

    @FXML
    private Button btnDelete;

    @FXML
    void handleButtonAction(ActionEvent event) {
        if (event.getSource() == btnEdit) {
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
    public void AddReservation() {
        try {
            java.util.Date date_util = new java.util.Date();
            Reservation r = new Reservation(new java.sql.Date(date_util.getTime()), Integer.parseInt(tfUser.getText()), Integer.parseInt(tfSalle.getText()), Integer.parseInt(tfFilm.getText()), Integer.parseInt(tfPlace.getText()));
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
            java.util.Date date_util = new java.util.Date();
            Reservation r = new Reservation(Integer.parseInt(tfId.getText()),new java.sql.Date(date_util.getTime()), Integer.parseInt(tfUser.getText()), Integer.parseInt(tfSalle.getText()), Integer.parseInt(tfFilm.getText()), Integer.parseInt(tfPlace.getText()));
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
            java.util.Date date_util = new java.util.Date();
            Reservation r = new Reservation(Integer.parseInt(tfId.getText()),new java.sql.Date(date_util.getTime()), Integer.parseInt(tfUser.getText()), Integer.parseInt(tfSalle.getText()), Integer.parseInt(tfFilm.getText()), Integer.parseInt(tfPlace.getText()));
            rs.deleteReservation(r);
            showFilm();
            JOptionPane.showMessageDialog(null, "reservation deleted successfully");
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e);
            Logger.getLogger(DashboardFilmController.class.getName()).log(Level.SEVERE, null, e);
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        showFilm();

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

        }
    }
}
