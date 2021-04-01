package controller.wifek;


import controller.ons.PromotionController;
import entitie.sana.Planning;
import entitie.wifek.Reservation;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import service.wifek.ReservationService;

import javax.swing.*;
import java.io.IOException;
import java.net.URL;
import java.sql.Date;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;

public class InterfaceReservationController implements Initializable {

    @FXML
    private AnchorPane reservationPage;

    private Planning currentReservation = null;

    @FXML
    private Button linkRes;

    @FXML
    private Button payBtn;

    @FXML
    private Spinner<Integer> tfPlace;

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

    @FXML
    private Label nbrplace;

    @FXML
    private Button signOut;

    ReservationService rs = new ReservationService();


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        SpinnerValueFactory<Integer> tfPlace = new SpinnerValueFactory.IntegerSpinnerValueFactory(0,100);
        this.tfPlace.setValueFactory(tfPlace);
    }

    public void test (Planning p){
        currentReservation = p;
        tfFilm.setText(String.valueOf(p.getTitreEvent()));
        tfSalle.setText(String.valueOf(p.getNomSalle()));
        nbrplace.setText(String.valueOf(rs.getSalleById(p.getNomSalle()).getNbrPlace()));
    }

    @FXML
    public void AddReservation(ActionEvent event) throws IOException {
        try {
            Reservation r = new Reservation( Date.valueOf(tfDate.getValue()),Integer.parseInt(tfIdUser.getText()),tfSalle.getText(),tfFilm.getText(),Integer.parseInt(tfPlace.getValue().toString()), tfFilm.getText());
            if(tfPlace.getValue()<= rs.getSalleById(r.getIdSalle()).getNbrPlace())
            {
            ReservationService rs = new ReservationService();
                if(event.getSource() == payBtn){
                    rs.addReservation(r);
                    JOptionPane.showMessageDialog(null, "reservation added successfully");
                    Parent fxml = FXMLLoader.load(getClass().getResource("../../GUI/wifek/InterfacePaiement.fxml"));
                    reservationPage.getChildren().removeAll();
                    reservationPage.getChildren().setAll(fxml);
                    String nomFilm = tfFilm.getText();
                    String nomSalle = tfSalle.getText();
                    int nbrPlace = tfPlace.getValue();
                    Reservation r1 = new Reservation(nomFilm,nomSalle,nbrPlace);
                }
            }else {
            JOptionPane.showMessageDialog(null, "Please check the number of places!");
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e);
        }
    }

    @FXML
    void lier(ActionEvent event) throws IOException {
        if(event.getSource() == linkRes){
            Parent fxml = FXMLLoader.load(getClass().getResource("../../GUI/wifek/InterfaceMyReservationList.fxml"));
            reservationPage.getChildren().removeAll();
            reservationPage.getChildren().setAll(fxml);
        } else if(event.getSource() == btnMovies)
        {
            Parent fxml = FXMLLoader.load(getClass().getResource("../../GUI/wifek/InterfaceFilm.fxml"));
            reservationPage.getChildren().removeAll();
            reservationPage.getChildren().setAll(fxml);

        }
    }

    public void handleShowRooms(MouseEvent mouseEvent) throws IOException {
        Parent movieParent = FXMLLoader.load(getClass().getResource("../../GUI/hazem/membre/SalledeCinemaClient.fxml"));
        Scene movieScene = new Scene(movieParent, 1200, 680);

        Stage window = (Stage) ((Node) mouseEvent.getSource()).getScene().getWindow();
        window.setScene(movieScene);
        window.show();

    }

    public void handleShowComplaint(MouseEvent mouseEvent) throws IOException {
        Parent complaintParent = FXMLLoader.load(getClass().getResource("../../GUI/hazem/membre/ReclamationClient.fxml"));
        Scene complaintScene = new Scene(complaintParent, 1200, 680);

        Stage window = (Stage) ((Node) mouseEvent.getSource()).getScene().getWindow();
        window.setScene(complaintScene);
        window.show();
    }

    public void handleShowPromotion(MouseEvent mouseEvent) throws IOException {
        Parent Parent2 = FXMLLoader.load(getClass().getResource("../../GUI/ons/FrontPromotion.fxml"));
        Scene Scene2 = new Scene(Parent2, 1200, 680);

        Stage window = (Stage) ((Node) mouseEvent.getSource()).getScene().getWindow();
        window.setScene(Scene2);
        window.show();

    }
    public void handleShowFilm(MouseEvent mouseEvent) throws IOException {
        Parent Parent3 = FXMLLoader.load(getClass().getResource("../../GUI/wifek/InterfaceFilm.fxml"));
        Scene Scene3 = new Scene(Parent3, 1200, 680);

        Stage window = (Stage) ((Node) mouseEvent.getSource()).getScene().getWindow();
        window.setScene(Scene3);
        window.show();

    }
    @FXML
    void handleButtonAction(ActionEvent event) {

        if(event.getSource()==signOut){
            Parent view5;
            try {
                view5= FXMLLoader.load(getClass().getResource("../../GUI/ons/FXMLDocument.fxml"));

                Scene scene5=new Scene(view5);
                Stage window =(Stage)((Node)event.getSource()).getScene().getWindow();
                window.setScene(scene5);
                window.show();
            }
            catch (IOException ex) {
                Logger.getLogger(PromotionController.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
}
