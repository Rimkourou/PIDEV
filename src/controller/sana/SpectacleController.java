package controller.sana;

import entitie.sana.SpectacleE;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import org.json.JSONException;
import org.json.JSONObject;
import service.sana.servicespectacleIMP;

import java.io.*;
import java.net.*;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.sql.Date;
import java.sql.SQLException;
import java.text.ParseException;
import java.time.Duration;
import java.util.ResourceBundle;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

//import controller.sana.CrunchifyJavaAsynchronousHTTPClient;

import org.json.JSONObject;

import java.io.InputStream;
import java.net.URI;
import java.net.URL;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.Duration;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;


public class SpectacleController implements Initializable {

    @FXML
    private TableColumn<SpectacleE, Integer> idcol;

    @FXML
    private TableColumn<SpectacleE, String> titrecol;

    @FXML
    private TableColumn<SpectacleE, Date> datetcol;

    @FXML
    private TableColumn<SpectacleE, String> genrecol;

    @FXML
    private TableColumn<SpectacleE, Integer> idusercol;

    @FXML
    private TableColumn<SpectacleE, String> col_image;

    @FXML
    private TextField idfl;

    @FXML
    private TextField titrefl;

    @FXML
    private DatePicker datefl;

    @FXML
    private ComboBox<String> genrefl;


    @FXML
    private Button boutonAjout;

    @FXML
    private TableView<SpectacleE> tableauSpectacles;

    @FXML
    private servicespectacleIMP ss = new servicespectacleIMP();

    @FXML
    private SpectacleE spectacleSelected;

    @FXML
    private ImageView btnClrDate;

    @FXML
    private ComboBox<String> filterfl;

    @FXML
    private TextField searchfl;

    @FXML
    private DatePicker dteDeb;

    @FXML
    private DatePicker dteFin;

    @FXML
    private TextField tfImgView;

    @FXML
    private ImageView imgView;

    @FXML
    private VBox vBoxCruds;

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

    @FXML
    private HBox promotion_vBox;
    @FXML
    private HBox user_vBox;
    @FXML
    private AnchorPane page;


    @FXML
    private Button btnPlanningShowUser;

    @FXML
    private Button btnShowsShowUser;

    @FXML
    private Button btn_promotion;

    @FXML
    private Button signOut;

    @FXML
    private ComboBox<String> comboTypeSpectacleUser;

    @FXML
    private ScrollPane scrollPaneSpectacleUser;

    @FXML
    private GridPane gridPaneSpectacleUser;

    @FXML
    private TextField tfSearchSpectacleUser;

    @FXML
    private DatePicker dteDebSpectacleUser;

    @FXML
    private DatePicker dteFinSpectacleUser;

    @FXML
    private ImageView btnClrDateSpectacleUser;

    @FXML
    private ComboBox<String> imageSource;

    //fonction affichage de fenetre planning
    @FXML
    void handleButtonPlanning(MouseEvent event) throws IOException {
        Stage primaryStage = new Stage();
        Parent root = FXMLLoader.load(getClass().getResource("../../GUI/sana/Planning.fxml"));
        Node source = (Node) event.getSource();
        final Stage stage = (Stage) source.getScene().getWindow();
        Scene scene = new Scene(root, 1500, 750);
        primaryStage.setScene(scene);
        primaryStage.show();
        primaryStage.setTitle("Tuni Show");
        primaryStage.setMaximized(true);
        //primaryStage.getIcons().add(new Image(this.getClass().getResource("popcorn.jpg").toString()));
        stage.close();
    }

    //fonction affichage de fenetre spectacle

    @FXML
    void handleButtonShow(MouseEvent event) throws IOException {
        Stage primaryStage = new Stage();
        Parent root = FXMLLoader.load(getClass().getResource("../../GUI/sana/Spectacle.fxml"));
        Node source = (Node) event.getSource();
        final Stage stage = (Stage) source.getScene().getWindow();
        Scene scene = new Scene(root, 1500, 750);
        primaryStage.setScene(scene);
        primaryStage.show();
        primaryStage.setTitle("Tuni Show");
        primaryStage.setMaximized(true);
        //primaryStage.getIcons().add(new Image(this.getClass().getResource("popcorn.jpg").toString()));
        stage.close();
    }


    /******************************************Methode ajout par boutton****************************************************/

    @FXML
    void loadImage() throws IOException {
        if (!tfImgView.getText().trim().isEmpty() && tfImgView.isVisible()) {
            FileInputStream input = new FileInputStream(tfImgView.getText());
            Image image = new Image(input);
            imgView.setImage(image);
        }
    }

    /****************************************Selectionner champ spectacle et afficher dans FILE Text****************************************************/


    public SpectacleE spectaleSelected() {
        SpectacleE s = tableauSpectacles.getSelectionModel().getSelectedItem();
        return (s);
    }

    /******Boutton clear ALL *********/
    public void clearAllFields() {
        titrefl.setText("");
        datefl.setValue(null);
        genrefl.setValue(null);
        tfImgView.setText("");
        imgView.setImage(null);
        spectacleSelected = null;
    }

    /**************Si on click 2 fois sur une linge dans le TableViewer va retourner le spectacle selectionner**************/


    public void handleDoubleMouseClicked(MouseEvent event) {
        //System.out.println(tableauSpectacles.getSelectionModel().getSelectedItem());
        if (event.isPrimaryButtonDown() && event.getClickCount() == 2) {
            SpectacleE s = tableauSpectacles.getSelectionModel().getSelectedItem();
            titrefl.setText(s.getTitre());
            datefl.setValue(new Date(s.getDate().getTime()).toLocalDate());
            genrefl.setValue(s.getGenre());
            tfImgView.setText(s.getImage().getImage().getUrl().substring(6));
            spectacleSelected = s; //load le spectacle selectionner dans la varibl  global
            System.out.println("selected spec: " + s);
        }
    }


    // other functions
    @FXML
    void handleButtonAction(ActionEvent event) {

    }


    @FXML
    void handleShowPromotion(MouseEvent event) {

    }


    @FXML
    void selectCategory(ActionEvent event) {

    }

    @FXML
    void selectType(ActionEvent event) {

    }

    /*************************Affichage des champs spectacle dans le tableaux de l'interface*******************/

    public void showSpectacle(ObservableList<SpectacleE> listSpectacle) {
        System.out.println("showSpectacle::liste spectacles:" + listSpectacle);
        //mon probleme
        if (tableauSpectacles != null) {
            tableauSpectacles.setItems(listSpectacle);
        } else if (gridPaneSpectacleUser != null && scrollPaneSpectacleUser != null) {

            // yfasakh les spectacles 9dom 9bel la yaffichi jdod
            gridPaneSpectacleUser.getChildren().setAll();

            gridPaneSpectacleUser.setHgap(15);
            gridPaneSpectacleUser.setVgap(15);

            int col = 0;
            int row = 0;
            ImageView[] spectacleAffiche = new ImageView[listSpectacle.size()];
            System.out.println(listSpectacle.size());
            scrollPaneSpectacleUser.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
            for (int i = 0; i < listSpectacle.size(); i++) {
                String path = listSpectacle.get(i).getImage().getImage().getUrl().substring(6);
                // System.out.println("path: "+path);
                File file = new File(path);
                Image image = new Image(file.toURI().toString());
                ImageView imgV = new ImageView(image);
                imgV.setFitWidth(205d);
                imgV.setFitHeight(260d);

                spectacleAffiche[i] = imgV;
                // spectacleAffiche[i].setMinWidth(205d);
                // spectacleAffiche[i].setMinHeight(260d);
                gridPaneSpectacleUser.add(spectacleAffiche[i], col, row);
                col++;

                if (col == 6) {
                    row++;
                    col = 0;
                }

                int index = i;
            }
            // System.out.println("spectacle images: "+spectacleAffiche);


        }


    }

    //file de recherche ; choix de date ou type ou ....
    public void changeSearchField(ActionEvent event) {
        ComboBox<String> typeRec = filterfl != null ? filterfl : comboTypeSpectacleUser;
        String critereRech = typeRec.getSelectionModel().getSelectedItem();
        TextField searField = searchfl != null ? searchfl : tfSearchSpectacleUser;
        DatePicker dDeb = dteDeb != null ? dteDeb : dteDebSpectacleUser;
        DatePicker dFin = dteFin != null ? dteFin : dteFinSpectacleUser;
        ImageView cBtn = btnClrDate != null ? btnClrDate : btnClrDateSpectacleUser;

        if (critereRech != null) {
            if (critereRech == "id" || critereRech == "idUser" || critereRech == "titre" || critereRech == "genre") {
                searField.setVisible(true);
                dFin.setVisible(false);
                dDeb.setVisible(false);
                cBtn.setVisible(false);
            } else {
                searField.setVisible(false);
                dFin.setVisible(true);
                dDeb.setVisible(true);
                cBtn.setVisible(true);
            }
        }
    }
 //api
    @FXML
    void showImageSourceFields(ActionEvent event) {
        String imgSrc = imageSource.getSelectionModel().getSelectedItem();
        if (imgSrc == "Depuis le Web") {
            tfImgView.setVisible(false);
            System.out.println("spectacle image path: "+titrefl.getText().replaceAll(" ","+"));
            try {
                CrunchifyJavaAsynchronousHTTPClient.downloadImage(titrefl.getText().replaceAll(" ","+"));
            } catch (Exception e) {
                e.printStackTrace();
            }

            tfImgView.setText("./TniShowDesktop/TniShowDesktop/src/Data/specImage-"+titrefl.getText().replaceAll(" ","+")+".png");
            FileInputStream input = null;
            try {
                 input = new FileInputStream("./TniShowDesktop/TniShowDesktop/src/Data/specImage-"+titrefl.getText().replaceAll(" ","+")+".png");
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
            if (input != null ) {
                Image image = new Image(input);
                imgView.setImage(image);
            }
        } else if (imgSrc == "Depuis mon ordinateur") {
            tfImgView.setVisible(true);
            // !tfImgView.getText().trim().isEmpty()
        }
    }


    //clear les chmaps de recherche date croix
    @FXML
    public void clearDate() {
        DatePicker dDeb = dteDeb != null ? dteDeb : dteDebSpectacleUser;
        DatePicker dFin = dteFin != null ? dteFin : dteFinSpectacleUser;
        dDeb.setValue(null);
        dFin.setValue(null);
    }

    @FXML
    public void Ajout(ActionEvent event) throws IOException, SQLException {
        /********vérifier si le titre  est vide ou non ; la date et genre et  sont vide***********/

        if (titrefl.getText().trim().isEmpty() || datefl.toString() == null ||
                genrefl.getSelectionModel().getSelectedItem() == null ||
                tfImgView.getText().trim().isEmpty()) {
            /*****affichage d'un alerte : vous devez remplir tt les champs******/
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Warning"); //affichage dans la barre de titre
            alert.setHeaderText("tous les champs sont obligatoires");
            alert.showAndWait();

            /**********************Sinon faire appel au serviceSpectacle et entité**********************/

        } else {
            SpectacleE s = new SpectacleE();

            /*********************Si un champs existe déja un message d'alert sera afficher***********************/

            if (ss.spectacleExist(titrefl.getText())) {
                Alert alert = new Alert(Alert.AlertType.WARNING);
                alert.setTitle("Warning");
                alert.setHeaderText("Spectacle existe deja");
                alert.showAndWait();

                /*******SINON : L'ajout se fait avec succées *******/
            } else {
                s.setTitre(titrefl.getText());
                Date date1 = Date.valueOf(datefl.getValue());
                s.setDate(date1);
                s.setGenre(genrefl.getSelectionModel().getSelectedItem());

                File file = new File(tfImgView.getText());
                Image image = new Image(file.toURI().toString());
                ImageView imgV = new ImageView();
                imgV.setImage(image);
                s.setImage(imgV);


                ss.AddSpectacle(s);
                clearAllFields();
                ObservableList<SpectacleE> listSpectacle = ss.LoadSpectacleFromDatabase(); //load tt spectacle from DB
                showSpectacle(listSpectacle); // yi3ayet el fct show pour actualiser  el tabelau mara pkhra
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Information"); //affichage dans la barre de titre
                alert.setHeaderText("Ajout effectué avec succés");
                alert.showAndWait();
            }
        }
    }


    /******************************************Methode Update par boutton****************************************************/


    public void Modifier(ActionEvent event) {
        SpectacleE s = spectacleSelected;
        System.out.println("old spectacle: " + s);
        if (s != null) {
            System.out.println("new imgV path: " + tfImgView.getText());

            File file = new File(tfImgView.getText());
            Image image = new Image(file.toURI().toString());
            ImageView imgV = new ImageView();
            imgV.setImage(image);
            System.out.println("new imgV: " + imgV);

            SpectacleE newSpecDetails = new SpectacleE(s.getId(), titrefl.getText(), Date.valueOf(datefl.getValue()),
                    genrefl.getSelectionModel().getSelectedItem(), s.getIdUser(), imgV);
            System.out.println("aprés modif: " + newSpecDetails);
            /************ update BD, actualiser tableView, clear tous les champs *****************/

            if (newSpecDetails != s) {
                ss.updateSpectacle(newSpecDetails);
                clearAllFields();
                ObservableList<SpectacleE> listSpectacle = ss.LoadSpectacleFromDatabase(); //load tt spectacle from DB
                showSpectacle(listSpectacle); // yi3ayet el fct show pour actualiser  el tabelau mara pkhra
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Information"); //affichage dans la barre de titre
                alert.setHeaderText("Modification effectuée avec succés");
                alert.showAndWait();
            } else {
                Alert alert = new Alert(Alert.AlertType.WARNING);
                alert.setTitle("Warning");
                alert.setHeaderText("Vous n'avez pas modifié les détails du spectacle");
                alert.showAndWait();
            }
        } else {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Warning");
            alert.setHeaderText("Veuillez selectionner un spectcale pour le modifier");
            alert.showAndWait();
        }
    }


    /******************************************Methode DELETE par boutton****************************************************/

    public void Suppression(ActionEvent event) throws SQLException {
        SpectacleE s = spectacleSelected;
        clearAllFields();

        if (s != null) {
            ss.deleteSpectacle(s.getId());
            ObservableList<SpectacleE> listSpectacle = ss.LoadSpectacleFromDatabase(); //load tt spectacle from DB
            showSpectacle(listSpectacle); // yi3ayet el fct show pour actualiser  el tabelau mara pkhra
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Information"); //affichage dans la barre de titre
            alert.setHeaderText("Suppresssion effectuée avec succés");
            alert.showAndWait();
        } else {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Warning");
            alert.setHeaderText("Veuillez selectionner un spectcale pour le supprimer");
            alert.showAndWait();
        }

    }


    /*************************recherche*******************/

    public void rapidSearch(KeyEvent event) throws ParseException {
        System.out.println("call method rapid search");

        ComboBox<String> filterField = filterfl != null ? filterfl : comboTypeSpectacleUser;
        TextField searchField = searchfl != null ? searchfl : tfSearchSpectacleUser;

        if (filterField.getSelectionModel().getSelectedItem() == null) {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Warning"); //affichage dans la barre de titre
            alert.setHeaderText("Vous devez spécifier critère de recherche avant");
            alert.showAndWait(); // matnajem tarja3 el fenetre mere ken matenzel ok 3ala el alert
        } else {
            System.out.println("recherche");
            ObservableList<SpectacleE> listSpectacle = FXCollections.observableArrayList(); // creer lister vide pour stocher les spectacle rechercher
            String critere = filterField.getSelectionModel().getSelectedItem();
            if (critere == "id" || critere == "idUser" || critere == "titre" || critere == "genre") {
                listSpectacle = ss.rechercheSpectacle(critere, searchField.getText());
            }
            System.out.println(listSpectacle);
            // gridPaneSpectacleUser.remove
            showSpectacle(listSpectacle);
        }

    }

    //recherche date
    public void dateRapidSearch(ActionEvent event) {
        System.out.println("call method rapid search");
        ComboBox<String> filterField = filterfl != null ? filterfl : comboTypeSpectacleUser;
        TextField searchField = searchfl != null ? searchfl : tfSearchSpectacleUser;

        if (filterField.getSelectionModel().getSelectedItem() == null) {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Warning"); //affichage dans la barre de titre
            alert.setHeaderText("Vous devez spécifier critère de recherche avant");
            alert.showAndWait();

        } else { //hedhi tfara9bin user w admin hasilou eli fiha not null el kol
            DatePicker dDeb = dteDeb != null ? dteDeb : dteDebSpectacleUser;
            DatePicker dFin = dteFin != null ? dteFin : dteFinSpectacleUser;

            if ((dDeb.getValue() != null && dFin.getValue() != null) &&
                    (dFin.getValue().compareTo(dDeb.getValue()) < 0)) { // ken date deb w date fin mech ferghin w
                //date fin asgher mel date debut
                Alert alert = new Alert(Alert.AlertType.WARNING);
                alert.setTitle("Warning"); //affichage dans la barre de titre
                alert.setHeaderText("Date Debut doit être inférieure à la date fin");
                alert.showAndWait();
            } else {
                System.out.println("dte deb: " + dDeb.getValue());
                System.out.println("dte fin: " + dFin.getValue());
                ObservableList<SpectacleE> listSpectacle = FXCollections.observableArrayList();

                Date vDteDeb = dDeb.getValue() == null ? null : Date.valueOf(dDeb.getValue()); // test est ce que date deb null , ken mech fergha nconverti  daye local dteBed en type date sinon tet3ada null
                Date vDteFin = dFin.getValue() == null ? null : Date.valueOf(dFin.getValue());

                listSpectacle = ss.rechercheSpectacle(filterField.getSelectionModel().getSelectedItem(), vDteDeb, vDteFin);
                System.out.println("liste spectacles2: " + listSpectacle);
                showSpectacle(listSpectacle); // yi3abi el tableview
            }
        }
    }


    // ouvrir planningUser  et spectacleUser
    @FXML
    void OpenPlanningUser(MouseEvent event) throws IOException {
        Stage primaryStage = new Stage();
        Parent root = FXMLLoader.load(getClass().getResource("../../GUI/sana/PlanningUser.fxml"));
        Node source = (Node) event.getSource();
        final Stage stage = (Stage) source.getScene().getWindow();
        Scene scene = new Scene(root, 1500, 750);
        primaryStage.setScene(scene);
        primaryStage.show();
        primaryStage.setTitle("Tuni Show");
        primaryStage.setMaximized(true);
        //primaryStage.getIcons().add(new Image(this.getClass().getResource("popcorn.jpg").toString()));
        stage.close();
        if (event.getSource() == linkShows) {
            Parent fxml = FXMLLoader.load(getClass().getResource("../../GUI/sana/PlanningUser.fxml"));
            page.getChildren().removeAll();
            page.getChildren().setAll(fxml);
        }

    }

    @FXML
    void OpenSpectacleUser(MouseEvent event) throws IOException {
        Stage primaryStage = new Stage();
        Parent root = FXMLLoader.load(getClass().getResource("../../GUI/sana/SpectacleUser.fxml"));
        Node source = (Node) event.getSource();
        final Stage stage = (Stage) source.getScene().getWindow();
        Scene scene = new Scene(root, 1500, 750);
        primaryStage.setScene(scene);
        primaryStage.show();
        primaryStage.setTitle("Tuni Show");
        primaryStage.setMaximized(true);
        //primaryStage.getIcons().add(new Image(this.getClass().getResource("popcorn.jpg").toString()));
        stage.close();
        if (event.getSource() == linkShows) {
            Parent fxml = FXMLLoader.load(getClass().getResource("../../GUI/sana/SpectacleUser.fxml"));
            page.getChildren().removeAll();
            page.getChildren().setAll(fxml);
        }

    }


    /************************Initialisation des elements qui sont dd*************************/
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) { // la 1er fonction qui s'execute apres l'ouverture de fentre


        // hideAdminFeaturesSpec(role);

        if (filterfl != null && genrefl != null) {
            filterfl.getItems().setAll("id", "titre", "date", "genre", "idUser");// yi3abi les valeur mta3 combobox ili bech nakhtar menha el type de filtre

            genrefl.getItems().setAll("danse", "musical", "piece theatral");
        } else if (comboTypeSpectacleUser != null) {
            comboTypeSpectacleUser.getItems().setAll("id", "titre", "date", "genre", "idUser");// yi3abi les valeur mta3 combobox ili bech nakhtar menha el type de filtre

        }
        if (imageSource != null) {
            imageSource.getItems().setAll("Depuis le Web", "Depuis mon ordinateur");// yi3abi les valeur mta3 combobox ili bech nakhtar menha el type de filtre
        }



/**************************Affichage dans le tableau de l'interface***********************/


        ObservableList<SpectacleE> listSpectacle = ss.LoadSpectacleFromDatabase(); //load tt spectacle from DB
        showSpectacle(listSpectacle); // yi3ayet el fct show pour actualiser  el tabelau mara pkhra

/************************initialisation des valeurs*************************/
        if (idcol != null && col_image != null && titrecol != null && datetcol != null && genrecol != null && idusercol != null) {
            idcol.setCellValueFactory(new PropertyValueFactory<>("id"));
            col_image.setCellValueFactory(new PropertyValueFactory<>("image"));
            titrecol.setCellValueFactory(new PropertyValueFactory<>("titre"));
            datetcol.setCellValueFactory(new PropertyValueFactory<>("date"));
            genrecol.setCellValueFactory(new PropertyValueFactory<>("genre"));
            idusercol.setCellValueFactory(new PropertyValueFactory<>("idUser"));
        }


    }

    @FXML
    void lier(MouseEvent event) throws IOException {
        if (event.getSource() == linkRes) {
            Parent fxml = FXMLLoader.load(getClass().getResource("../../GUI/wifek/DashboardReservation.fxml"));
            page.getChildren().removeAll();
            page.getChildren().setAll(fxml);

        } else if (event.getSource() == linkMovies) {
            Parent fxml = FXMLLoader.load(getClass().getResource("../../GUI/wifek/DashboardFilm.fxml"));
            page.getChildren().removeAll();
            page.getChildren().setAll(fxml);

        } else if (event.getSource() == linkPlanning) {
            Parent fxml = FXMLLoader.load(getClass().getResource("../../GUI/sana/Planning.fxml"));
            page.getChildren().removeAll();
            page.getChildren().setAll(fxml);

        } else if (event.getSource() == promotion_vBox) {
            Parent fxml = FXMLLoader.load(getClass().getResource("../../GUI/ons/Promotion.fxml"));
            page.getChildren().removeAll();
            page.getChildren().setAll(fxml);
        } else if (event.getSource() == user_vBox) {
            Parent fxml = FXMLLoader.load(getClass().getResource("../../GUI/ons/Admin.fxml"));
            page.getChildren().removeAll();
            page.getChildren().setAll(fxml);
        }
    }

    public void handleShowRooms(MouseEvent mouseEvent) throws IOException {
        Parent movieParent = FXMLLoader.load(getClass().getResource("../../GUI/hazem/admin/SalledeCinemaAdmin.fxml"));
        Scene movieScene = new Scene(movieParent, 1200, 680);

        Stage window = (Stage) ((Node) mouseEvent.getSource()).getScene().getWindow();
        window.setScene(movieScene);
        window.show();

    }

    public void handleShowComplaint(MouseEvent mouseEvent) throws IOException {
        Parent complaintParent = FXMLLoader.load(getClass().getResource("../../GUI/hazem/admin/ReclamationAdmin.fxml"));
        Scene complaintScene = new Scene(complaintParent, 1200, 680);

        Stage window = (Stage) ((Node) mouseEvent.getSource()).getScene().getWindow();
        window.setScene(complaintScene);
        window.show();
    }



//    public static final HttpClient crunchifyHttpClient = HttpClient.newBuilder()
//            .version(HttpClient.Version.HTTP_1_1)
//            .connectTimeout(Duration.ofSeconds(5))
//            .build();
//
//    // HttpClient: An HttpClient can be used to send requests and retrieve their responses. An HttpClient is created through a builder.
//
//    // Duration: A time-based amount of time, such as '5 seconds'.
//
//
//    public static String crunchifyAsyncHTTPClient(String keyword) {
//
//        HttpRequest crunchifyRequest = HttpRequest.newBuilder()
//                .GET()
//                .uri(URI.create("https://google-search3.p.rapidapi.com/api/v1/images/q="+keyword))
//                .setHeader("x-rapidapi-key", "0bf50a4a12msh026a7ca947dcfb8p11d6ddjsn0fed481adc94")
//                .setHeader("x-rapidapi-host", "google-search3.p.rapidapi.com")
//                .build();
//
//        CompletableFuture<HttpResponse<String>> crunchifyAsyncResponse = null;
//
//        // sendAsync(): Sends the given request asynchronously using this client with the given response body handler.
//        //Equivalent to: sendAsync(request, responseBodyHandler, null).
//        crunchifyAsyncResponse = crunchifyHttpClient.sendAsync(crunchifyRequest, HttpResponse.BodyHandlers.ofString());
//
//        String crunchifyAsyncResultBody = null;
//        int crunchifyAsyncResultStatusCode = 0;
//
//        try {
//            crunchifyAsyncResultBody = crunchifyAsyncResponse.thenApply(HttpResponse::body).get(5, TimeUnit.SECONDS);
//            crunchifyAsyncResultStatusCode = crunchifyAsyncResponse.thenApply(HttpResponse::statusCode).get(5, TimeUnit.SECONDS);
//
//        } catch (InterruptedException | ExecutionException | TimeoutException e) {
//            e.printStackTrace();
//        }
//
//        crunnchifyPrint("=============== AsyncHTTPClient Body:===============  \n" + crunchifyAsyncResultBody);
//        crunnchifyPrint("\n=============== AsyncHTTPClient Status Code:===============  \n" + crunchifyAsyncResultStatusCode);
//        return crunchifyAsyncResultBody;
//    }
//
//    public static void crunnchifyPrint(Object data) {
//        System.out.println(data);
//
//    }
//
//    public static void downloadImage (String keyword) {
//        //Read JSON response and print
//        JSONObject myResponse = null;
//        try {
//            myResponse = new JSONObject(crunchifyAsyncHTTPClient(keyword));
//        } catch (JSONException e) {
//            e.printStackTrace();
//        }
//
//        System.out.println("result after Reading JSON Response");
//        try {
//            System.out.printf("image: "+myResponse.getJSONArray("image_results").getJSONObject(1).getJSONObject("image").getString("src"));
//        } catch (JSONException e) {
//            e.printStackTrace();
//        }
//        String uri = null;
//        try {
//            uri = myResponse.getJSONArray("image_results").getJSONObject(1).getJSONObject("image").getString("src");
//        } catch (JSONException e) {
//            e.printStackTrace();
//        }
//        System.out.println("statusCode- " + uri);
//
//
//        try (InputStream inS = new URL(uri).openStream()) {
//            Files.copy(inS, Paths.get("./Data/"+keyword+".png"));
//        } catch (MalformedURLException e) {
//            e.printStackTrace();
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//    }
}





