package Dashboard;

import entites.SpectacleE;
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
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.InputMethodEvent;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import services.servicespectacleIMP;

import java.io.File;
import java.io.InputStream;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;


import java.io.FileInputStream;
import java.io.IOException;
import java.net.URL;
import java.sql.Date;
import java.sql.SQLException;
import java.text.ParseException;
import java.util.ResourceBundle;

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
    //TODO: change with the id of the current user
    //admin: id=1 || user: id=2
    private Integer idCurrentUser = 2;

    //fonction affichage de fenetre planning
    @FXML
    void handleButtonPlanning(MouseEvent event) throws IOException {
        Stage primaryStage = new Stage();
        Parent root = FXMLLoader.load(getClass().getResource("/GUI/Planning.fxml"));
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
        Parent root = FXMLLoader.load(getClass().getResource("/GUI/Spectacle.fxml"));
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
    void loadImage(ActionEvent event) throws IOException {
        if (!tfImgView.getText().trim().isEmpty()) {
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

    /*************************Affichage des champs spectacle dans le tableaux de l'interface*******************/

    public void showSpectacle() {
        ObservableList<SpectacleE> listSpectacle = ss.LoadSpectacleFromDatabase(); //load tt spectacle from DB
        System.out.println("showSpectacle::liste spectacles:" + listSpectacle);
        //mon probleme
        tableauSpectacles.setItems(listSpectacle);
    }

    //file de recherche ; choix de date ou type ou ....
    public void changeSearchField(ActionEvent event) {
        String critereRech = filterfl.getSelectionModel().getSelectedItem();
        if (critereRech != null) {
            if (critereRech == "id" || critereRech == "idUser" || critereRech == "titre" || critereRech == "genre") {
                searchfl.setVisible(true);
                dteFin.setVisible(false);
                dteDeb.setVisible(false);
                btnClrDate.setVisible(false);
            } else {
                searchfl.setVisible(false);
                dteFin.setVisible(true);
                dteDeb.setVisible(true);
                btnClrDate.setVisible(true);
            }
        }
    }


    //manipuler l'affichage de fentre  user ou admin
    @FXML
    public void hideAdminFeaturesSpec(String role) {
        if (role.trim().equalsIgnoreCase("membre")) {
            System.out.println("ici user");
            vBoxCruds.setVisible(false); //ya5o l blasa eli fiha el tableau lkol w bech yina7ih
            tableauSpectacles.setPrefWidth(1115);
            tableauSpectacles.setTranslateX(55);
            idcol.setPrefWidth(185.34);
            col_image.setPrefWidth(185.34);
            titrecol.setPrefWidth(185.34);
            datetcol.setPrefWidth(185.34);
            genrecol.setPrefWidth(185.34);
            idusercol.setPrefWidth(185.34);
// optionnelle pour ne pas avoir des erreurs
        } else if (role.trim().equalsIgnoreCase("admin") || role.trim().equalsIgnoreCase("supeAdmin")) {
            System.out.println("ici admin");
            vBoxCruds.setVisible(true);
        }
    }


    //clear les chmaps de recherche date croix
    @FXML
    public void clearDate() {
        dteDeb.setValue(null);
        dteFin.setValue(null);
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
                //TODO: to change with the id of the cuurent login user
                s.setIdUser(idCurrentUser);

                ss.AddSpectacle(s);
                clearAllFields();
                showSpectacle(); // yi3ayet el fct show pour actualiser  el tabelau mara pkhra
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
                showSpectacle();
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
            showSpectacle();
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
        if (filterfl.getSelectionModel().getSelectedItem() == null) {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Warning"); //affichage dans la barre de titre
            alert.setHeaderText("Vous devez spécifier critère de recherche avant");
            alert.showAndWait(); // matnajem tarja3 el fenetre mere ken matenzel ok 3ala el alert
        } else {
            ObservableList<SpectacleE> listSpectacle = FXCollections.observableArrayList(); // creer lister vide pour stocher les spectacle rechercher
            if (filterfl.getSelectionModel().getSelectedItem() == "id" || filterfl.getSelectionModel().getSelectedItem() == "idUser" || filterfl.getSelectionModel().getSelectedItem() == "titre" || filterfl.getSelectionModel().getSelectedItem() == "genre") {
                listSpectacle = ss.rechercheSpectacle(filterfl.getSelectionModel().getSelectedItem(), searchfl.getText());
            }
            //System.out.println(listSpectacle);
            tableauSpectacles.setItems(listSpectacle);
        }
    }

    //recherche date
    public void dateRapidSearch(ActionEvent event) {
        if (filterfl.getSelectionModel().getSelectedItem() == null) {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Warning"); //affichage dans la barre de titre
            alert.setHeaderText("Vous devez spécifier critère de recherche avant");
            alert.showAndWait();
        } else {
            if ((dteDeb.getValue() != null && dteFin.getValue() != null) && (dteFin.getValue().compareTo(dteDeb.getValue()) < 0)) { // ken date deb w date fin mech ferghin w
                //date fin asgher mel date debut

                Alert alert = new Alert(Alert.AlertType.WARNING);
                alert.setTitle("Warning"); //affichage dans la barre de titre
                alert.setHeaderText("Date Debut doit être inférieure à la date fin");
                alert.showAndWait();
            } else {
                System.out.println("dte deb" + dteDeb.getValue());
                System.out.println("dte fin " + dteFin.getValue());
                ObservableList<SpectacleE> listSpectacle = FXCollections.observableArrayList();
                Date dateDeb = dteDeb.getValue() == null ? null : Date.valueOf(dteDeb.getValue()); // test est ce que date deb null , ken mech fergha nconverti  daye local dteBed en type date sinon tet3ada null
                Date dateFin = dteFin.getValue() == null ? null : Date.valueOf(dteFin.getValue());
                listSpectacle = ss.rechercheSpectacle(filterfl.getSelectionModel().getSelectedItem(), dateDeb, dateFin);
                System.out.println("liste spectacles2: " + listSpectacle);
                tableauSpectacles.setItems(listSpectacle); // yi3abi el tableview
            }
        }
    }

    /************************Initialisation des elements qui sont dd*************************/
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) { // la 1er fonction qui s'execute apres l'ouverture de fentre

        System.out.println(ss.selectRoleCurrentUser(idCurrentUser));
        String role = ss.selectRoleCurrentUser(idCurrentUser);
        hideAdminFeaturesSpec(role);

        filterfl.getItems().setAll("id", "titre", "date", "genre", "idUser");// yi3abi les valeur mta3 combobox ili bech nakhtar menha el type de filtre

        genrefl.getItems().setAll("danse", "musical", "piece theatral");


/**************************Affichage dans le tableau de l'interface***********************/


        showSpectacle();

/************************initialisation des valeurs*************************/

        idcol.setCellValueFactory(new PropertyValueFactory<>("id"));
        col_image.setCellValueFactory(new PropertyValueFactory<>("image"));
        titrecol.setCellValueFactory(new PropertyValueFactory<>("titre"));
        datetcol.setCellValueFactory(new PropertyValueFactory<>("date"));
        genrecol.setCellValueFactory(new PropertyValueFactory<>("genre"));
        idusercol.setCellValueFactory(new PropertyValueFactory<>("idUser"));


    }
    @FXML
    void lier(MouseEvent event) throws IOException {
        if(event.getSource() == linkRes){
            Parent fxml = FXMLLoader.load(getClass().getResource("DashboardReservation.fxml"));
            reservationPage.getChildren().removeAll();
            reservationPage.getChildren().setAll(fxml);

        } else if(event.getSource() == linkMovies){
            Parent fxml = FXMLLoader.load(getClass().getResource("DashboardFilm.fxml"));
            reservationPage.getChildren().removeAll();
            reservationPage.getChildren().setAll(fxml);

        } else if(event.getSource() == linkPlanning){
            Parent fxml = FXMLLoader.load(getClass().getResource("Planning.fxml"));
            reservationPage.getChildren().removeAll();
            reservationPage.getChildren().setAll(fxml);

        }
    }

}

