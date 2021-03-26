package InterfaceReservation;

import entites.Film;
import entites.Planning;
import entites.Reservation;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.Spinner;
import javafx.scene.control.SpinnerValueFactory;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import services.ReservationService;

import javax.swing.*;
import java.io.IOException;
import java.net.URL;
import java.sql.Date;
import java.util.ResourceBundle;

public class InterfaceReservationController implements Initializable {

    @FXML
    private AnchorPane reservationPage;

    private Planning currentReservation = null;

    @FXML
    private Button linkRes;

    @FXML
    private Button payBtn;

    @FXML
    private Spinner tfPlace;

    @FXML
    private TextField tfSalle;

    @FXML
    private TextField tfIdUser;

    @FXML
    private TextField tfFilm;

    @FXML
    private TextField tfDate;

    @FXML
    private Button btnMovies;


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        SpinnerValueFactory<Integer> tfPlace = new SpinnerValueFactory.IntegerSpinnerValueFactory(0,100);
        this.tfPlace.setValueFactory(tfPlace);
    }

    public void test (Planning data){
        currentReservation = data;
        tfFilm.setText(String.valueOf(data.getIdFilm()));
        tfSalle.setText(String.valueOf(data.getIdSalle()));
    }
    public void AddReservation() {
        try {
            Reservation r = new Reservation( Date.valueOf(tfDate.getText()),Integer.parseInt(tfIdUser.getText()),Integer.parseInt(tfSalle.getText()),Integer.parseInt(tfFilm.getText()),Integer.parseInt(tfPlace.getValue().toString()));
            ReservationService rs = new ReservationService();
            rs.addReservation(r);
            JOptionPane.showMessageDialog(null, "reservation added successfully");
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e);
        }
    }

    @FXML
    void lier(ActionEvent event) throws IOException {
        if(event.getSource() == linkRes){
            Parent fxml = FXMLLoader.load(getClass().getResource("InterfaceMyReservationList.fxml"));
            reservationPage.getChildren().removeAll();
            reservationPage.getChildren().setAll(fxml);
        } else if(event.getSource() == payBtn){
            AddReservation();
            Parent fxml = FXMLLoader.load(getClass().getResource("InterfacePaiement.fxml"));
            reservationPage.getChildren().removeAll();
            reservationPage.getChildren().setAll(fxml);
        }else if(event.getSource() == btnMovies)
        {
            Parent fxml = FXMLLoader.load(getClass().getResource("/InterfaceFilm/InterfaceFilm.fxml"));
            reservationPage.getChildren().removeAll();
            reservationPage.getChildren().setAll(fxml);

        }
    }
}
