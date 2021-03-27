package Dashboard;

import entites.Film;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.embed.swing.SwingFXUtils;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.image.WritableImage;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.stage.FileChooser;
import services.FilmService;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.image.BufferedImage;
import java.io.File;
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
    private TableColumn<Film, String> colPic;

    @FXML
    public TextField tfId;

    @FXML
    public TextField tfTitle;

    @FXML
    public TextField tfDescription;

    @FXML
    public TextField tfAuthor;

    @FXML
    private ComboBox comboCateory;

    @FXML
    private ComboBox comboType;

    @FXML
    private ComboBox comboCategory;

    @FXML
    private ComboBox comboType1;

    @FXML
    public TextField tfSearch;

    @FXML
    private Button btnAdd;

    @FXML
    private Button btnEdit;

    @FXML
    private Button btnDelete;

    @FXML
    private Button btnPic;

    @FXML
    private ImageView picId;

    @FXML
    private HBox linkRes;

    @FXML
    private HBox linkMovies;

    @FXML
    private HBox linkShows;

    @FXML
    private HBox linkPlanning;

    @FXML
    private AnchorPane reservationPage;

    String picName;

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
    }


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        showFilm();
        searchFilm();
        comboCateory.getItems().addAll("Long Movie", "Short Movie");
        comboType.getItems().addAll("Action","Animation","Comedy","Documentary","Drama","Horror");
        ObservableList<String> CategoryList= FXCollections.observableArrayList("Long Movie", "Short Movie");
        comboCategory.setItems(CategoryList);
        ObservableList<String> TypeList= FXCollections.observableArrayList("Action","Animation","Comedy","Documentary","Drama","Horror");
        comboType1.setItems(TypeList);
    }

    public void showFilm(){
        ObservableList<Film> list= fs.filmList();
        colId.setCellValueFactory(new PropertyValueFactory<Film, Integer>("id"));
        colTitle.setCellValueFactory(new PropertyValueFactory<Film, String>("titre"));
        colDescription.setCellValueFactory(new PropertyValueFactory<Film, String>("description"));
        colAuthor.setCellValueFactory(new PropertyValueFactory<Film, String>("auteur"));
        colCategory.setCellValueFactory(new PropertyValueFactory<Film, String>("categorie"));
        colType.setCellValueFactory(new PropertyValueFactory<Film, String>("genre"));
        colPic.setCellValueFactory(new PropertyValueFactory<Film, String>("img"));
        tvFilm.setItems(list);
    }

    public void AddFilm() {
        try {
            Film f = new Film(tfTitle.getText(), tfDescription.getText(), tfAuthor.getText(),comboCateory.getValue().toString(),comboType.getValue().toString(),picName);
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
            /*FileChooser fileChooser = new FileChooser();
            File file = fileChooser.showOpenDialog(null);
            URL url = file.toURI().toURL();
            picId.setImage(new Image(url.toExternalForm()));*/
            Film f = new Film(Integer.parseInt(tfId.getText()), tfTitle.getText(), tfDescription.getText(), tfAuthor.getText(), comboCateory.getValue().toString(), comboType.getValue().toString(),picName);
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
    public void selectCategory(ActionEvent event) {
        ObservableList<Film> list = fs.searchFilmByCategory(comboCategory.getValue().toString());
        showFilmByCategory();
    }

    public void selectType(ActionEvent event) {
        ObservableList<Film> list = fs.searchFilmByType(comboType1.getValue().toString());
        searchFilmByType();
    }

    public void searchFilmByType(){
        ObservableList<Film> list= fs.searchFilmByType(comboType1.getValue().toString());
        colId.setCellValueFactory(new PropertyValueFactory<Film, Integer>("id"));
        colTitle.setCellValueFactory(new PropertyValueFactory<Film, String>("titre"));
        colDescription.setCellValueFactory(new PropertyValueFactory<Film, String>("description"));
        colAuthor.setCellValueFactory(new PropertyValueFactory<Film, String>("auteur"));
        colCategory.setCellValueFactory(new PropertyValueFactory<Film, String>("categorie"));
        colType.setCellValueFactory(new PropertyValueFactory<Film, String>("genre"));
        colPic.setCellValueFactory(new PropertyValueFactory<Film, String>("img"));
        tvFilm.setItems(list);
    }

    public void showFilmByCategory(){
        ObservableList<Film> list= fs.searchFilmByCategory(comboCategory.getValue().toString());
        colId.setCellValueFactory(new PropertyValueFactory<Film, Integer>("id"));
        colTitle.setCellValueFactory(new PropertyValueFactory<Film, String>("titre"));
        colDescription.setCellValueFactory(new PropertyValueFactory<Film, String>("description"));
        colAuthor.setCellValueFactory(new PropertyValueFactory<Film, String>("auteur"));
        colCategory.setCellValueFactory(new PropertyValueFactory<Film, String>("categorie"));
        colType.setCellValueFactory(new PropertyValueFactory<Film, String>("genre"));
        colPic.setCellValueFactory(new PropertyValueFactory<Film, String>("img"));
        tvFilm.setItems(list);
    }

    public void setOnAction() throws IOException {
        FileChooser fileChooser = new FileChooser();
        FileChooser.ExtensionFilter extFilterJPG = new FileChooser.ExtensionFilter("JPG files (.jpg)", "*.JPG");
        FileChooser.ExtensionFilter extFilterPNG = new FileChooser.ExtensionFilter("PNG files (.png)", "*.PNG");
        fileChooser.getExtensionFilters().addAll(extFilterJPG, extFilterPNG);
        File file = fileChooser.showOpenDialog(null);
        picName = file.getAbsolutePath();

        try {
            BufferedImage bufferedImage = ImageIO.read(file);
            WritableImage image = SwingFXUtils.toFXImage(bufferedImage, null);
            picId.setImage(image);
        } catch (IOException ignored) {

        }
    }

    @FXML
    void lier(MouseEvent event) throws IOException {
        if(event.getSource() == linkRes){
            Parent fxml = FXMLLoader.load(getClass().getResource("DashboardReservation.fxml"));
            reservationPage.getChildren().removeAll();
            reservationPage.getChildren().setAll(fxml);

        } else if(event.getSource() == linkPlanning){
            Parent fxml = FXMLLoader.load(getClass().getResource("Planning.fxml"));
            reservationPage.getChildren().removeAll();
            reservationPage.getChildren().setAll(fxml);

        } else if(event.getSource() == linkShows){
            Parent fxml = FXMLLoader.load(getClass().getResource("Spectacle.fxml"));
            reservationPage.getChildren().removeAll();
            reservationPage.getChildren().setAll(fxml);

        }
    }
}
