package controller.wifek;

import controller.ons.PromotionController;
import entitie.wifek.Film;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import org.controlsfx.control.Rating;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;

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

    @FXML
    private Button signOut;


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
