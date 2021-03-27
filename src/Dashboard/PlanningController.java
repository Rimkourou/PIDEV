package Dashboard;
import InterfaceReservation.InterfaceReservationController;
import entites.Planning;
import javafx.beans.property.ReadOnlyStringWrapper;
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
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import services.serviceplanningIMP;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.sql.Date;
import java.sql.SQLException;
import java.sql.Time;
import java.time.LocalDate;
import java.util.ResourceBundle;

public class PlanningController  implements Initializable{


    @FXML
    private AnchorPane reservationPage;

    @FXML
    private TextField searchfl;

    @FXML
    private DatePicker dteDeb;

    @FXML
    private DatePicker dteFin;

    @FXML
    private ImageView btnClrDate;

    @FXML
    private ComboBox<String> filterfl;

    @FXML
    private TableView<Planning> tableauPlanning;

    @FXML
    private TableColumn<Planning, Integer> idcol;

    @FXML
    private TableColumn<Planning, String> typeEventcol;

    @FXML
    private TableColumn<Planning, String> titreEventCol;

    @FXML
    private TableColumn<Planning, String> nomSalleCol;

    @FXML
    private TableColumn<Planning, Date> datecol;

    @FXML
    private TableColumn<Planning, Time> heurdebcol;

    @FXML
    private TableColumn<Planning, Time> heurfincol;

    @FXML
    private VBox vBoxCruds;

    @FXML
    private ComboBox<String> tfType;

    @FXML
    private Button btnReserve;

    @FXML
    private ComboBox<String> tfTitreEvent;

    @FXML
    private ComboBox<String> tfNomSalle;

    @FXML
    private DatePicker tfDteProjection;

    @FXML
    private Label lblHdeb;

    @FXML
    private TextField tfHhD;

    @FXML
    private TextField tfMmD;

    @FXML
    private Label lblHfin;

    @FXML
    private TextField tfHhF;

    @FXML
    private TextField tfMmF;

    @FXML
    private Button btnClearAll;

    @FXML
    private Button btnAdd;

    @FXML
    private Button btnEdit;

    @FXML
    private Button btnDelete;

    @FXML
    private HBox linkMovies;

    @FXML
    private HBox sbspectacle;

    @FXML
    private HBox linkRes;

    @FXML
    private HBox linkPlanning;

    @FXML
    private HBox linkShows;


    @FXML
    //TODO: change with the id of the current user
    //admin: id=1 || user: id=2
    private Integer idCurrentUser = 1;


    //appel au service
    @FXML
    private serviceplanningIMP sp = new serviceplanningIMP();
    private Planning plannigSelected;

    //Remplissage de combobox de Titre event ;
    @FXML
    void remplissageComboTitreEvent() {
        String typeE;
        if (tfType.getSelectionModel().getSelectedItem() == "Film") {
            typeE = "film";
        } else {
            typeE = "spectacle";
        }
        tfTitreEvent.getItems().setAll(sp.loadTitreEventSalleFromDB(typeE));
    }

    Time getHeure(String Hh, String Mm) {
        //Time t = Time.valueOf("10:00:00");
        Time t = Time.valueOf(Hh + ":" + Mm + ":00");
        //System.out.println(t);
        return t;
    }

    //Ajout de planning
    @FXML
    void Ajout(ActionEvent event) throws SQLException {
        //vérifier si le titre  est vide ou non ; la date et genre et idsallefl sont vide//
        if (tfType.getSelectionModel().getSelectedItem() == null
                || tfTitreEvent.getSelectionModel().getSelectedItem() == null
                || tfNomSalle.getSelectionModel().getSelectedItem() == null
                || tfDteProjection.toString() == null
                || tfHhD.getText().trim().isEmpty()
                || tfMmD.getText().trim().isEmpty()
                || tfHhF.getText().trim().isEmpty()
                || tfMmF.getText().trim().isEmpty()) {
            //affichage d'un alerte : vous devez remplir tt les champs//
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Warning"); //affichage dans la barre de titre
            alert.setHeaderText("Tous les champs sont obligatoires");
            alert.showAndWait();

            //Sinon faire appel au servicePlanning et entité//

        } else {
            Planning p = new Planning();

            p.setType(tfType.getSelectionModel().getSelectedItem());
            p.setTitreEvent(tfTitreEvent.getSelectionModel().getSelectedItem());
            p.setNomSalle(tfNomSalle.getSelectionModel().getSelectedItem());
            Date date = Date.valueOf(tfDteProjection.getValue());
            p.setDate(date);
            Integer idE = sp.loadIdEventSalleFromDbByTitre(p.getType(), tfTitreEvent.getSelectionModel().getSelectedItem());
            String imgPath = sp.loadImageEventFromDB(idE, p.getType());

            //System.out.println("imgPath:"+imgPath.substring(6));
            File file = new File(imgPath);
            Image image = new Image(file.toURI().toString());
            ImageView imgV = new ImageView();
            imgV.setImage(image);
            p.setImageEvent(imgV);

            //System.out.println("heure fin : "+getHeure(tfHhF.getText(), tfMmF.getText()));
            //System.out.println("heure deb : "+getHeure(tfHhD.getText(), tfMmD.getText()));

            p.setHeureDebut(getHeure(tfHhD.getText(), tfMmD.getText()));
            p.setHeureFin(getHeure(tfHhF.getText(), tfMmF.getText()));

            //Siun salle est reserver un message d'alert sera afficher//

            if (sp.dHSReserve(p)) {
                Alert alert = new Alert(Alert.AlertType.WARNING);
                alert.setTitle("Warning");
                alert.setHeaderText("Cette salle est déjà réservé pour un autre " +
                        "évènnement dans la même date et des heure qui s'interceptent");
                alert.showAndWait();

                //SINON : L'ajout se fait avec succées //
            } else {
                sp.AddPlanning(p);
                showPlanning();
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Information"); //affichage dans la barre de titre
                alert.setHeaderText("Ajout effectué avec succés");
                alert.showAndWait();
            }

        }
    }

    //-Boutton modifier -
    public void handleDoubleMouseClickedOnPlanning(MouseEvent event) {

        //System.out.println(tableauSpectacles.getSelectionModel().getSelectedItem());
        if (event.isPrimaryButtonDown() && event.getClickCount() == 2) {
            Planning p = tableauPlanning.getSelectionModel().getSelectedItem();
            tfType.setValue(p.getType());

            tfTitreEvent.getItems().setAll(sp.loadTitreEventSalleFromDB(p.getType().toLowerCase()));
            tfTitreEvent.setValue(p.getTitreEvent());

            tfNomSalle.setValue(p.getNomSalle());
            tfDteProjection.setValue(new Date(p.getDate().getTime()).toLocalDate());
            tfHhD.setText(p.getHeureDebut().toString().substring(0, 2));
            tfMmD.setText(p.getHeureDebut().toString().substring(3, 5));
            tfHhF.setText(p.getHeureDebut().toString().substring(0, 2));
            tfMmF.setText(p.getHeureDebut().toString().substring(3, 5));
            plannigSelected = p;
            //System.out.println("selected planning: " + p);f
        }
    }

    //Modifier un champ de planning

    @FXML
    void Modifier(ActionEvent event) {

        Planning p = plannigSelected;
        System.out.println("old planning: " + p);

        if (p != null) {
            Planning newPlanDetails = new Planning();

            newPlanDetails.setId(p.getId());
            newPlanDetails.setType(tfType.getSelectionModel().getSelectedItem());
            newPlanDetails.setTitreEvent(tfTitreEvent.getSelectionModel().getSelectedItem());
            newPlanDetails.setNomSalle(tfNomSalle.getSelectionModel().getSelectedItem());
            Date date = Date.valueOf(tfDteProjection.getValue());
            newPlanDetails.setDate(date);
            newPlanDetails.setImageEvent(p.getImageEvent());
            newPlanDetails.setHeureDebut(getHeure(tfHhD.getText(), tfMmD.getText()));
            newPlanDetails.setHeureFin(getHeure(tfHhF.getText(), tfMmF.getText()));
            System.out.println("aprés modif: " + newPlanDetails);

            // update BD, actualiser tableView, clear tous les champs

            if (newPlanDetails != p) {
                sp.updatePlanning(newPlanDetails);
                clearAllFields(event);
                showPlanning();
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

    //-Boutton supprimer-
    @FXML
    void Suppression(ActionEvent event) {
        Planning p = plannigSelected;
        clearAllFields(event);
        if (p != null) {
            sp.deletePlanning(p.getId());
            showPlanning();
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Information"); //affichage dans la barre de titre
            alert.setHeaderText("Supprission effectuée avec succés");
            alert.showAndWait();
        } else {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Warning");
            alert.setHeaderText("Veuillez selectionner un spectcale pour le supprimer");
            alert.showAndWait();
        }
    }

//Affichage planning

    public void showPlanning() {
        ObservableList<Planning> listPlanning = sp.LoadPlanningFromDatabase();
        System.out.println("liste planning a affichée:: " + listPlanning);
        //mon probleme
        tableauPlanning.setItems(listPlanning);
    }

    //-Bouton -champs- rechercher -
    @FXML
    public void changeSearchField(ActionEvent event) {
        String filtreRech = filterfl.getSelectionModel().getSelectedItem();
        if (filtreRech != null) {
            if (filtreRech == "ID" || filtreRech == "Type Event" || filtreRech == "Titre Event" || filtreRech == "Nom Salle") {
                searchfl.setVisible(true);
                dteFin.setVisible(false);
                dteDeb.setVisible(false);
                btnClrDate.setVisible(false);

            } else if (filtreRech == "Date Proj") {
                searchfl.setVisible(false);
                dteFin.setVisible(true);
                dteDeb.setVisible(true);
                btnClrDate.setVisible(true);
            }
        }
    }

    @FXML
    public void hideAdminFeaturesPlan(String role) {
        if (role.trim().equalsIgnoreCase("simpleUser")) {
            tfType.setVisible(false);
            tfNomSalle.setVisible(false);
            tfTitreEvent.setVisible(false);
            tfDteProjection.setVisible(false);
            lblHdeb.setVisible(false);
            lblHfin.setVisible(false);
            tfHhD.setVisible(false);
            tfHhF.setVisible(false);
            tfMmD.setVisible(false);
            tfMmF.setVisible(false);
            btnAdd.setVisible(false);
            btnClearAll.setVisible(false);
            btnDelete.setVisible(false);
            btnEdit.setVisible(false);
            btnReserve.setVisible(true);
            /*
            tableauPlanning.setPrefWidth(1318);
            idEventcol.setPrefWidth(188.2857);
            typeEventcol.setPrefWidth(188.2857);
            idEventcol.setPrefWidth(188.2857);
            idsallecol.setPrefWidth(188.2857);
            datecol.setPrefWidth(188.2857);
            heurdebcol.setPrefWidth(188.2857);
            heurfincol.setPrefWidth(190);
                         */
        } else if (role.trim().equalsIgnoreCase("admin") || role.trim().equalsIgnoreCase("supeAdmin")) {
            System.out.println("ici admin");
            vBoxCruds.setVisible(true);
        }
    }

    // Clear all
    @FXML
    void clearAllFields(ActionEvent event) {
        tfType.setValue(null);
        tfNomSalle.setValue(null);
        tfTitreEvent.setValue(null);
        tfDteProjection.setValue(null);
        tfHhD.setText("");
        tfMmD.setText("");
        tfHhF.setText("");
        tfMmF.setText("");
        serviceplanningIMP sp = new serviceplanningIMP();
        int index = tableauPlanning.getSelectionModel().getSelectedIndex();
        ObservableList<Planning> LoadPlanningFromDatabase = sp.LoadPlanningFromDatabase() ;
        int planningId = LoadPlanningFromDatabase.get(index).getId();
        String type = LoadPlanningFromDatabase.get(index).getType();
        String titreEvent = LoadPlanningFromDatabase.get(index).getTitreEvent();
        ImageView imageEvent = LoadPlanningFromDatabase.get(index).getImageEvent();
        String nomSalle = LoadPlanningFromDatabase.get(index).getNomSalle();
        Date date = LoadPlanningFromDatabase.get(index).getDate();
        Time heureDebut = LoadPlanningFromDatabase.get(index).getHeureDebut();
        Time heureFin = LoadPlanningFromDatabase.get(index).getHeureFin();
        Planning p = new Planning(planningId,type,titreEvent,imageEvent,nomSalle,date,heureDebut,heureFin);
        FXMLLoader fxmlLoader = loadFXML("/InterfaceReservation/InterfaceReservation.fxml");
        try {
            Parent root = fxmlLoader.load();
            Stage stage = loadStage(root);
            InterfaceReservationController stageController = fxmlLoader.getController();
            stageController.test(p);
            stage.initOwner(reservationPage.getScene().getWindow());
            stage.initModality(Modality.WINDOW_MODAL);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public Stage loadStage(Parent root){
        Scene scene = new Scene(root);
        Stage stage = new Stage();
        stage.setScene(scene);
        stage.setResizable(false);
        scene.getStylesheets().add(this.getClass().getResource("DashboardFilm.css").toExternalForm());
        return stage;
    }

    public FXMLLoader loadFXML(String url){
        FXMLLoader fxmlLoader = new FXMLLoader();
        fxmlLoader.setLocation(getClass().getResource(url));
        return fxmlLoader;
    }

    // clear date avec croix
    @FXML
    public void clearDate() {
        dteDeb.setValue(null);
        dteFin.setValue(null);
    }


    // recherche ; alerte
    @FXML
    void rapidSearch(KeyEvent event) {

        if (filterfl.getSelectionModel().getSelectedItem() == null) {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Warning"); //affichage dans la barre de titre
            alert.setHeaderText("Vous devez spécifier critère de recherche avant");
            alert.showAndWait();
        } else {
            ObservableList<Planning> listPlanning = FXCollections.observableArrayList();
            String filtreRech = filterfl.getSelectionModel().getSelectedItem();
            if (filtreRech == "ID" || filtreRech == "Type Event" || filtreRech == "Titre Event"
                    || filtreRech == "Nom Salle" ) {
                listPlanning = sp.recherchePlanning(filtreRech, searchfl.getText());
            }
            //System.out.println(listSpectacle);
            tableauPlanning.setItems(listPlanning);
        }


    }


    //Methode supprimer
    @FXML
    void dateRapidSearch(ActionEvent event) {
        if (filterfl.getSelectionModel().getSelectedItem() == null) {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Warning"); //affichage dans la barre de titre
            alert.setHeaderText("Vous devez spécifier critère de recherche avant");
            alert.showAndWait();
        } else {
            LocalDate dteD = dteDeb.getValue();
            LocalDate dteF = dteFin.getValue();
            if ((dteD != null && dteF != null) && (dteF.compareTo(dteD) < 0)) {
                Alert alert = new Alert(Alert.AlertType.WARNING);
                alert.setTitle("Warning"); //affichage dans la barre de titre
                alert.setHeaderText("Date Debut doit être inférieure à la date fin");
                alert.showAndWait();
            } else {
                System.out.println("dte deb" + dteDeb.getValue());
                System.out.println("dte fin " + dteFin.getValue());
                ObservableList<Planning> listPlanning = FXCollections.observableArrayList();
                Date dateDeb = dteD == null ? null : Date.valueOf(dteD);
                Date dateFin = dteF == null ? null : Date.valueOf(dteF);
                listPlanning = sp.recherchePlanning("date", dateDeb, dateFin);
                System.out.println("liste plannings2: " + listPlanning);
                tableauPlanning.setItems(listPlanning);
            }
        }
    }

    //methode d'affichage planning (main)
    @FXML
    void handleButtonPlanning(MouseEvent event) throws IOException {
        Stage primaryStage = new Stage();
        Parent root = FXMLLoader.load(getClass().getResource("Planning.fxml"));
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

    //methode d'affichage spectacle (main)
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
        if (event.getSource() == linkShows){
            Parent fxml = FXMLLoader.load(getClass().getResource("Spectacle.fxml"));
            reservationPage.getChildren().removeAll();
            reservationPage.getChildren().setAll(fxml);}
    }

    /*
    @FXML
    void heureRapidSearch(ActionEvent event) {
        if (filterfl.getSelectionModel().getSelectedItem() == null) {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Warning"); //affichage dans la barre de titre
            alert.setHeaderText("Vous devez spécifier critère de recherche avant");
            alert.showAndWait();
        } else {
            Time tDeb;
            if(tfHhD1.getText().trim().isEmpty()){
                 tDeb = getHeure("00",tfMmD1.getText());
            } else if(tfMmD1.getText().trim().isEmpty()){
                 tDeb = getHeure(tfHhD1.getText(),"00");
            }else if(tfMmD1.getText().trim().isEmpty()){
                tDeb = getHeure(tfHhD1.getText(),tfMmD1.getText());
            }
            Time tDeb = getHeure(tfHhD1.getText(),tfMmD1.getText());
            Time tFin = getHeure(tfHhF1.getText(),tfMmF1.getText());
            if ((tDeb != null && tFin != null) && (tFin.compareTo(tDeb) < 0)) {
                Alert alert = new Alert(Alert.AlertType.WARNING);
                alert.setTitle("Warning"); //affichage dans la barre de titre
                alert.setHeaderText("Date Debut doit être inférieure à la date fin");
                alert.showAndWait();
            } else {
                System.out.println("h deb" + tDeb);
                System.out.println("h fin " + tFin);
                ObservableList<Planning> listPlanning;
                Time timeDeb = tDeb == null ? null : tDeb;
                Time timeFin = tFin == null ? null : tFin;
                listPlanning = sp.recherchePlanning(filterfl.getSelectionModel().getSelectedItem(), timeDeb, timeFin);
                System.out.println("liste plannings heure: " + listPlanning);
                tableauPlanning.setItems(listPlanning);
            }
        }
    }
     */


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        hideAdminFeaturesPlan(sp.selectRoleCurrentUser(idCurrentUser));


        tfType.getItems().setAll("Film", "Spectacle");
        tfNomSalle.getItems().setAll(sp.loadTitreEventSalleFromDB("salledecinema"));
        filterfl.getItems().setAll("ID", "Type Event", "Nom Salle", "Date Proj");

        showPlanning();


        //test ajout
        idcol.setCellValueFactory(new PropertyValueFactory<Planning, Integer>("id"));
        typeEventcol.setCellValueFactory(new PropertyValueFactory<Planning, String>("Type"));
        titreEventCol.setCellValueFactory(new PropertyValueFactory<Planning, String>("TitreEvent"));

        //imageViewTableColumn.setCellValueFactory(new PropertyValueFactory<Planning,ImageView>("img"));
        nomSalleCol.setCellValueFactory(data -> new ReadOnlyStringWrapper(data.getValue().getNomSalle()));
        //nomSalleCol.setCellValueFactory(new PropertyValueFactory<Planning, String>("TitreSalle"));
        datecol.setCellValueFactory(new PropertyValueFactory<Planning, Date>("Date"));
        heurdebcol.setCellValueFactory(new PropertyValueFactory<Planning, Time>("HeureDebut"));
        heurfincol.setCellValueFactory(new PropertyValueFactory<Planning, Time>("HeureFin"));

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

        }
    }
}
