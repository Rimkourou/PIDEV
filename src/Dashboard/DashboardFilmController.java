package Dashboard;

import entites.Film;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;
import services.FilmService;

import javax.swing.*;
import java.io.IOException;
import java.net.URL;
import java.sql.Blob;
import java.util.ResourceBundle;
import java.util.function.Predicate;
import java.util.logging.Level;
import java.util.logging.Logger;

public class DashboardFilmController implements Initializable {
    FilmService fs= new FilmService();

    @FXML
    private TableView<Film> tvFilm;

    @FXML
    private TableColumn<Film, Integer> colId;

    @FXML
    private TableColumn<Film, String> colTitle;

    @FXML
    private TableColumn<Film, String> colDescription;

    @FXML
    private TableColumn<Film, String> colAuthor;

    @FXML
    private TableColumn<Film, String> colCategory;

    @FXML
    private TableColumn<Film, String> colType;

    @FXML
    private TableColumn<Film, Integer> colSalle;

    @FXML
    public TextField tfId;

    @FXML
    public TextField tfTitle;

    @FXML
    public TextField tfDescription;

    @FXML
    public TextField tfAuthor;

    @FXML
    public TextField tfCategory;

    @FXML
    public TextField tfType;

    @FXML
    public TextField tfSalle;

    @FXML
    public TextField tfSearch;

    @FXML
    private Button btnAdd;

    @FXML
    private Button btnEdit;

    @FXML
    private Button btnDelete;

    @FXML
    private HBox linkRes;

    @FXML
    void handleButtonAction(ActionEvent event) {
        if (event.getSource() == btnAdd){
            AddFilm();
        } else if (event.getSource() == btnEdit) {
            EditFilm();
        } else {
            DeleteFilm();
        }

    }

    @FXML
    public void handleMouseAction() {
        int index = tvFilm.getSelectionModel().getSelectedIndex();
        if(index<=-1){

            return;
        }
        tfId.setText(colId.getCellData(index).toString());
        tfTitle.setText(colTitle.getCellData(index));
        tfDescription.setText(colDescription.getCellData(index));
        tfAuthor.setText(colAuthor.getCellData(index));
        tfCategory.setText(colCategory.getCellData(index));
        tfType.setText(colType.getCellData(index));
        tfSalle.setText(colSalle.getCellData(index).toString());
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        showFilm();
        searchFilm();
    }

    public void showFilm(){
        ObservableList<Film> list= fs.filmList();
        colId.setCellValueFactory(new PropertyValueFactory<Film, Integer>("id"));
        colTitle.setCellValueFactory(new PropertyValueFactory<Film, String>("titre"));
        colDescription.setCellValueFactory(new PropertyValueFactory<Film, String>("description"));
        colAuthor.setCellValueFactory(new PropertyValueFactory<Film, String>("auteur"));
        colCategory.setCellValueFactory(new PropertyValueFactory<Film, String>("categorie"));
        colType.setCellValueFactory(new PropertyValueFactory<Film, String>("genre"));
        colSalle.setCellValueFactory(new PropertyValueFactory<Film, Integer>("idSalle"));
        tvFilm.setItems(list);
    }

    public void AddFilm() {
        try {
            Film f = new Film(tfTitle.getText(), tfDescription.getText(), tfAuthor.getText(),tfCategory.getText(),tfType.getText(),Integer.parseInt(tfSalle.getText()));
            FilmService fs = new FilmService();
            fs.addFilm(f);
            showFilm();
            JOptionPane.showMessageDialog(null, "film added successfully");
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e);
        }
    }

    public void EditFilm(){
        try {
            Film f = new Film(Integer.parseInt(tfId.getText()), tfTitle.getText(), tfDescription.getText(), tfAuthor.getText(), tfCategory.getText(), tfType.getText(), Integer.parseInt(tfSalle.getText()));
            FilmService fs = new FilmService();
            fs.editFilm(f);
            showFilm();
            JOptionPane.showMessageDialog(null, "film successfully updated");
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e);
            Logger.getLogger(DashboardFilmController.class.getName()).log(Level.SEVERE, null, e);
        }
    }

    public void DeleteFilm(){
        try {
            int id =Integer.parseInt(tfId.getText());
            FilmService fs = new FilmService();
            fs.deleteFilm(id);
            showFilm();
            JOptionPane.showMessageDialog(null, "film deleted successfully");
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e);
            Logger.getLogger(DashboardFilmController.class.getName()).log(Level.SEVERE, null, e);
        }
    }
    @FXML
    void searchFilm() {
        FilteredList<Film> filteredData = new FilteredList<>(fs.filmList(), b -> true);
        tfSearch.setOnKeyReleased(b -> {
            tfSearch.textProperty().addListener((observableValue, oldValue, newValue) ->{
                filteredData.setPredicate((Predicate<? super Film>) film ->{
                    if(newValue == null || newValue.isEmpty()){
                        return true;
                    }
                    String lowerCaseFilter = newValue.toLowerCase();
                    if(film.getTitre().toLowerCase().contains(lowerCaseFilter)){
                        return true;
                    } else if(film.getAuteur().toLowerCase().contains(lowerCaseFilter)){
                        return true;
                    }
                    return false;
                });
            });
            SortedList <Film> sortedList = new SortedList<>(filteredData);
            sortedList.comparatorProperty().bind(tvFilm.comparatorProperty());
            tvFilm.setItems(sortedList);
        });
    }
    @FXML
    private AnchorPane reservationPage;

    @FXML
    void lier(MouseEvent event) throws IOException {
        if(event.getSource() == linkRes){
            Parent fxml = FXMLLoader.load(getClass().getResource("DashboardReservation.fxml"));
            reservationPage.getChildren().removeAll();
            reservationPage.getChildren().setAll(fxml);

        }
    }

}
