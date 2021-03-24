package InterfaceFilm;

import entites.Film;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Text;
import org.controlsfx.control.Rating;

import javafx.scene.image.Image;

import java.awt.*;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.scene.control.Label;

public class InterfaceDetailFilmController implements Initializable {
    @FXML
    private Rating rating;

    @FXML
    private Label tfTitle;

    @FXML
    private ImageView imageView;

    @FXML
    private Label test;

    @FXML
    private Label tfType;

    @FXML
    private Text tfDescription;

    @FXML
    private Button linkRes;

    @FXML
    private AnchorPane reservationPage;


    private int currentMovieId;
    private Film currentFilm = null;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        rating.ratingProperty().addListener(new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> ov, Number old, Number t1) {
                test.setText("Rating: " +t1);
            }
        });
    }

    public void test (Film film, String urlImage){
        currentFilm = film;
        tfTitle.setText(film.getTitre());
        tfType.setText(film.getGenre());
        tfDescription.setText(film.getDescription());
        Image image = new Image("file:" + urlImage);
        imageView.setImage(image);
        imageView.setFitWidth(386d);
        imageView.setFitHeight(477d);
    }

    @FXML
    void lier(ActionEvent event) throws IOException {
        if(event.getSource() == linkRes){
            Parent fxml = FXMLLoader.load(getClass().getResource("InterfaceFilm.fxml"));
            reservationPage.getChildren().removeAll();
            reservationPage.getChildren().setAll(fxml);

        }
    }
}
