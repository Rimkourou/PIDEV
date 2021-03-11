package InterfaceReservation;

import entites.Reservation;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import services.ReservationService;

import javax.swing.*;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class InterfaceReservationController implements Initializable {

    @FXML
    private AnchorPane reservationPage;

    @FXML
    private Button payBtn;

    @FXML
    private Button linkRes;

    @FXML
    private TextField tfPlace;

    @FXML
    private TextField tfIdUser;

    @FXML
    private TextField tfSalle;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }
    /*public void AddReservation() {
        try {
            java.util.Date date_util = new java.util.Date();
            Reservation r = new Reservation(new java.sql.Date(date_util.getTime()), Integer.parseInt(tfIdUser.getText()), Integer.parseInt(tfSalle.getText()), Integer.parseInt(tfPlace.getText()));
            ReservationService rs = new ReservationService();
            rs.addR(r);
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
        }
    }*/
}
