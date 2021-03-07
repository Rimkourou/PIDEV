package InterfaceFilm;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import org.controlsfx.control.Rating;

import java.awt.*;
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
}
