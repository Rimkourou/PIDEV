package controller.wifek;

import controller.ons.PromotionController;
import entitie.wifek.Film;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import service.wifek.FilmService;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.function.Predicate;
import java.util.logging.Level;
import java.util.logging.Logger;

public class InterfaceFilmController implements Initializable {

    FilmService fs= new FilmService();

    @FXML
    private ImageView linkRes;

    @FXML
    private AnchorPane reservationPage;

    @FXML
    public TextField tfSearch;

    @FXML
    private ComboBox comboCategory;

    @FXML
    private ComboBox comboType;

    @FXML
    private ScrollPane scrollPane;

    @FXML
    private GridPane gridPane;

    @FXML
    private Button signOut;

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

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        searchFilm();
        ObservableList<String> CategoryList= FXCollections.observableArrayList("Long Movie", "Short Movie");
        comboCategory.setItems(CategoryList);
        ObservableList<String> TypeList= FXCollections.observableArrayList("Action","Animation","Comedy","Documentary","Drama","Horror");
        comboType.setItems(TypeList);

        ObservableList<Film> list= fs.filmList();
        gridPane.setHgap(15);
        gridPane.setVgap(15);
        int col = 0;
        int row = 0;
        Button[] movieBtn = new Button[list.size()];
        scrollPane.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
        for(int i = 0; i < list.size(); i++){

            String path = list.get(i).getImg();
            Image image = new Image("file:"+path);
            ImageView imageView = new ImageView(image);
            imageView.setFitWidth(205d);
            imageView.setFitHeight(260d);

            movieBtn[i] = new Button("",imageView);
            movieBtn[i].setMinWidth(205d);
            movieBtn[i].setMinHeight(260d);
            gridPane.add(movieBtn[i], col, row);
            col ++;

            if(col == 6){
                row ++;
                col = 0;}

        int index = i;

        movieBtn[i].setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                int movieId = list.get(index).getId();
                String movieName = list.get(index).getTitre();
                String movieDescription = list.get(index).getDescription();
                String movieType = list.get(index).getGenre();
                Film film = new Film(movieId, movieName, movieDescription, movieType);
                FXMLLoader fxmlLoader = loadFXML("../../GUI/wifek/InterfaceDetailFilm.fxml");
                try {
                    Parent root = fxmlLoader.load();
                    Stage stage = loadStage(root);
                    InterfaceDetailFilmController stageController = fxmlLoader.getController();
                    stageController.test(film, path);
                    stage.initOwner(reservationPage.getScene().getWindow());
                    stage.initModality(Modality.WINDOW_MODAL);
                    stage.show();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });

    }}

    public Stage loadStage(Parent root){
        Scene scene = new Scene(root);
        Stage stage = new Stage();
        stage.setScene(scene);
        stage.setResizable(false);
        scene.getStylesheets().add(this.getClass().getResource("../../GUI/wifek/DashboardFilm.css").toExternalForm());
        return stage;
    }

    public FXMLLoader loadFXML(String url){
        FXMLLoader fxmlLoader = new FXMLLoader();
        fxmlLoader.setLocation(getClass().getResource(url));
        return fxmlLoader;
    }

    public void selectCategory(ActionEvent event) {
        ObservableList<Film> list = fs.searchFilmByCategory(comboCategory.getValue().toString());
        showFilmByCategory();
    }

    public void selectType(ActionEvent event) {
        ObservableList<Film> list = fs.searchFilmByType(comboType.getValue().toString());
        searchFilmByType();
    }

    public void searchFilmByType(){
        ObservableList<Film> list= fs.searchFilmByType(comboType.getValue().toString());
        gridPane.setHgap(15);
        gridPane.setVgap(15);
        int col = 0;
        int row = 0;
        Button[] movieBtn = new Button[list.size()];
        scrollPane.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
        for(int i = 0; i < list.size(); i++){

            String path = list.get(i).getImg();
            Image image = new Image("file:"+path);
            ImageView imageView = new ImageView(image);
            imageView.setFitWidth(205d);
            imageView.setFitHeight(260d);

            movieBtn[i] = new Button("",imageView);
            movieBtn[i].setMinWidth(205d);
            movieBtn[i].setMinHeight(260d);
            gridPane.add(movieBtn[i], col, row);
            col ++;

            if(col == 6){
                row ++;
                col = 0;}
        }
    }

    public void showFilmByCategory(){
        ObservableList<Film> list= fs.searchFilmByCategory(comboCategory.getValue().toString());
        gridPane.setHgap(15);
        gridPane.setVgap(15);
        int col = 0;
        int row = 0;
        Button[] movieBtn = new Button[list.size()];
        scrollPane.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
        for(int i = 0; i < list.size(); i++){

            String path = list.get(i).getImg();
            Image image = new Image("file:"+path);
            ImageView imageView = new ImageView(image);
            imageView.setFitWidth(205d);
            imageView.setFitHeight(260d);

            movieBtn[i] = new Button("",imageView);
            movieBtn[i].setMinWidth(205d);
            movieBtn[i].setMinHeight(260d);
            gridPane.add(movieBtn[i], col, row);
            col ++;

            if(col == 6){
                row ++;
                col = 0;}
        }    }

    @FXML
    void searchFilm() {
        FilteredList<Film> filteredData = new FilteredList<>(fs.searchFilmByTitle(tfSearch.getText()), b -> true);
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
        gridPane.setHgap(15);
        gridPane.setVgap(15);
        int col = 0;
        int row = 0;
        Button[] movieBtn = new Button[filteredData.size()];
        scrollPane.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
        for(int i = 0; i < filteredData.size(); i++){

            String path = filteredData.get(i).getImg();
            Image image = new Image("file:"+path);
            ImageView imageView = new ImageView(image);
            imageView.setFitWidth(205d);
            imageView.setFitHeight(260d);

            movieBtn[i] = new Button("",imageView);
            movieBtn[i].setMinWidth(205d);
            movieBtn[i].setMinHeight(260d);
            gridPane.add(movieBtn[i], col, row);
            col ++;

            if(col == 6){
                row ++;
                col = 0;}
        }    });}



}
