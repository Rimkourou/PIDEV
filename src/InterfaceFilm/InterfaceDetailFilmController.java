package InterfaceFilm;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;
import org.controlsfx.control.Rating;

import java.awt.*;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.scene.control.Label;

public class InterfaceDetailFilmController implements Initializable {
    @FXML
    private Rating rating;

    @FXML
    private Label test;
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        rating.ratingProperty().addListener(new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> ov, Number old, Number t1) {
                test.setText("Rating: " +t1);
            }
        });
    }
    @FXML
    private Button linkRes;

    @FXML
    private AnchorPane reservationPage;

    @FXML
    void lier(ActionEvent event) throws IOException {
        if(event.getSource() == linkRes){
            Parent fxml = FXMLLoader.load(getClass().getResource("/InterfaceReservation/InterfaceMyReservationList.fxml"));
            reservationPage.getChildren().removeAll();
            reservationPage.getChildren().setAll(fxml);

        }
    }
}
