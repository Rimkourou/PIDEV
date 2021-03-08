package Dashboard;

import entites.Film;
import entites.Reservation;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.cell.PropertyValueFactory;
import services.FilmService;

import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import services.ReservationService;

import javax.swing.*;
import java.awt.event.MouseEvent;
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
    private TableColumn<Reservation, String> colValidation;

    @FXML
    private TableColumn<Reservation, Integer> colUser;

    @FXML
    private TableColumn<Reservation, Integer> colFacture;

    @FXML
    private TableColumn<Reservation, Integer> colPayment;

    @FXML
    private TableColumn<Reservation, Integer> colSalle;

    @FXML
    private TextField tfId;

    @FXML
    private TextField tfDate;

    @FXML
    private TextField tfValidation;

    @FXML
    private TextField tfUser;

    @FXML
    private TextField tfFacture;

    @FXML
    private TextField tfPayment;

    @FXML
    private TextField tfSalle;

    @FXML
    private Button btnAdd;

    @FXML
    private Button btnEdit;

    @FXML
    private Button btnDelete;

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
        tfValidation.setText(colValidation.getCellData(index).toString());
        tfUser.setText(colUser.getCellData(index).toString());
        tfFacture.setText(colFacture.getCellData(index).toString());
        tfPayment.setText(colPayment.getCellData(index).toString());
        tfSalle.setText(colSalle.getCellData(index).toString());
    }

    private void showFilm() {
        ObservableList<Reservation> list= rs.reservationList();
        colId.setCellValueFactory(new PropertyValueFactory<Reservation, Integer>("id"));
        colDate.setCellValueFactory(new PropertyValueFactory<Reservation, Date>("dateReservation"));
        colValidation.setCellValueFactory(new PropertyValueFactory<Reservation, String>("validation"));
        colUser.setCellValueFactory(new PropertyValueFactory<Reservation, Integer>("idUser"));
        colFacture.setCellValueFactory(new PropertyValueFactory<Reservation, Integer>("idFacture"));
        colPayment.setCellValueFactory(new PropertyValueFactory<Reservation, Integer>("idP"));
        colSalle.setCellValueFactory(new PropertyValueFactory<Reservation, Integer>("idSalle"));
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
                    if(reservation.getValidation().toLowerCase().contains(lowerCaseFilter)){
                        return true;
                    }
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
            java.util.Date date_util = new java.util.Date();
            Reservation r = new Reservation(new java.sql.Date(date_util.getTime()), tfValidation.getText(), Integer.parseInt(tfUser.getText()),Integer.parseInt(tfFacture.getText()),Integer.parseInt(tfPayment.getText()),Integer.parseInt(tfSalle.getText()));
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
            Reservation r = new Reservation(Integer.parseInt(tfId.getText()),new java.sql.Date(date_util.getTime()), tfValidation.getText(), Integer.parseInt(tfUser.getText()),Integer.parseInt(tfFacture.getText()),Integer.parseInt(tfPayment.getText()), Integer.parseInt(tfSalle.getText()));
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
            int id =Integer.parseInt(tfId.getText());
            rs.deleteReservation(id);
            showFilm();
            JOptionPane.showMessageDialog(null, "reservation deleted successfully");
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e);
            Logger.getLogger(DashboardFilmController.class.getName()).log(Level.SEVERE, null, e);
        }
    }

}

