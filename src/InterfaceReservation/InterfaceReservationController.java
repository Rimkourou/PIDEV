package InterfaceReservation;

import entites.Film;
import entites.Planning;
import entites.Reservation;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.*;
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
    private Button btnMovies;

    @FXML
    private DatePicker tfDate;


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        SpinnerValueFactory<Integer> tfPlace = new SpinnerValueFactory.IntegerSpinnerValueFactory(0,100);
        this.tfPlace.setValueFactory(tfPlace);
    }

    public void test (Planning p){
        currentReservation = p;
        tfFilm.setText(String.valueOf(p.getTitreEvent()));
        tfSalle.setText(String.valueOf(p.getNomSalle()));
    }
    public void AddReservation() {
        try {
            Reservation r = new Reservation( Date.valueOf(tfDate.getValue()),Integer.parseInt(tfIdUser.getText()),tfSalle.getText(),tfFilm.getText(),Integer.parseInt(tfPlace.getValue().toString()), tfFilm.getText());
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
