package controller.sana;


import controller.wifek.InterfaceReservationController;
import entitie.sana.Planning;
import entitie.sana.SpectacleE;
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
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import service.sana.serviceplanningIMP;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.sql.Date;
import java.sql.SQLException;
import java.sql.Time;
import java.time.LocalDate;
import java.util.ResourceBundle;

public class PlanningController implements Initializable {


    @FXML
    private AnchorPane page;

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
    private HBox promotion_vBox;
    @FXML
    private HBox user_vBox;
    @FXML
    private AnchorPane reservationPage;

    @FXML
    private Button btn_promotion;

    @FXML
    private Button signOut;

    @FXML
    private TextField tfSearchPlanningUser;

    @FXML
    private DatePicker dteDebPlanningUser;

    @FXML
    private DatePicker dteFinPlanningUser;

    @FXML
    private ComboBox<String> comboTypePlanningUser;

    @FXML
    private ScrollPane scrollPanePlanningUser;

    @FXML
    private GridPane gridPanePlanningUser;

    @FXML
    private ImageView btnClrDatePlanningUser;





    //appel au service
    @FXML
    private serviceplanningIMP sp = new serviceplanningIMP();
    private Planning plannigSelected;

    //Remplissage de combobox de Type event(film | spectacle) ;
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

    /*******3lech??*********/
    Time getHeure(String Hh, String Mm) {
        Time t = Time.valueOf(Hh + ":" + Mm + ":00");
        return t;
    }

    //Ajout de planning
    @FXML
    void Ajout(ActionEvent event) throws SQLException {
        //vérifier si le titre  est vide ou non ; la date et genre et idsallefl sont vide
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

            //Sinon fait appel au servicePlanning et entité

        } else {
            Planning p = new Planning();

            p.setType(tfType.getSelectionModel().getSelectedItem());
            p.setTitreEvent(tfTitreEvent.getSelectionModel().getSelectedItem());
            p.setNomSalle(tfNomSalle.getSelectionModel().getSelectedItem());
            Date date = Date.valueOf(tfDteProjection.getValue());
            p.setDate(date);
            Integer idE = sp.loadIdEventSalleFromDbByTitre(p.getType(), tfTitreEvent.getSelectionModel().getSelectedItem());
            String imgPath = sp.loadImageEventFromDB(idE, p.getType());

            File file = new File(imgPath);
            Image image = new Image(file.toURI().toString());
            ImageView imgV = new ImageView();
            imgV.setImage(image);
            p.setImageEvent(imgV);

            p.setHeureDebut(getHeure(tfHhD.getText(), tfMmD.getText()));
            p.setHeureFin(getHeure(tfHhF.getText(), tfMmF.getText()));

            //Si un salle est reserver un message d'alert sera afficher//

            if (sp.dHSReserve(p)) {
                Alert alert = new Alert(Alert.AlertType.WARNING);
                alert.setTitle("Warning");
                alert.setHeaderText("Cette salle est déjà réservé pour un autre " +
                        "évènnement dans la même date et des heure qui s'interceptent");
                alert.showAndWait();

                //SINON : L'ajout se fait avec succées //
            } else {
                sp.AddPlanning(p);

                ObservableList<Planning> listPlanning = sp.LoadPlanningFromDatabase();
                showPlanning(listPlanning);

                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Information"); //affichage dans la barre de titre
                alert.setHeaderText("Ajout effectué avec succés");
                alert.showAndWait();
            }

        }
    }

    //-Boutton modifier ; lors de clique de fois sur le champs elle sera afficher au txt filed
    public void handleDoubleMouseClickedOnPlanning(MouseEvent event) {

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
        }
    }

    //Modifier un champ de planning

    @FXML
    void Modifier(ActionEvent event) {
        // selectionner un champs et modifier
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

            // si la modification est fait , un msg sera afficher

            if (newPlanDetails != p) {
                sp.updatePlanning(newPlanDetails);
                clearAllFields(event);

                ObservableList<Planning> listPlanning = sp.LoadPlanningFromDatabase();
                showPlanning(listPlanning);

                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Information"); //affichage dans la barre de titre
                alert.setHeaderText("Modification effectuée avec succés");
                alert.showAndWait();
            } else { //sinon un msg d'alert sera afficher si on n'as pas modifier un champs de spec

                Alert alert = new Alert(Alert.AlertType.WARNING);
                alert.setTitle("Warning");
                alert.setHeaderText("Vous n'avez pas modifié les détails du spectacle");
                alert.showAndWait();
            }
        } else { // si on pas selection un champs pour le modifier un msg sera afficher
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

            ObservableList<Planning> listPlanning = sp.LoadPlanningFromDatabase();
            showPlanning(listPlanning);

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

    public void showPlanning(ObservableList<Planning> listPlanning) {
        System.out.println("showPlanning::liste planning:" + listPlanning);

        // 3lech ??
        if (tableauPlanning != null) {
            tableauPlanning.setItems(listPlanning);
        } else if (gridPanePlanningUser != null && scrollPanePlanningUser != null) {
            System.out.println("afficher planning for user");

            // yfasakh les spectacles 9dom 9bel la yaffichi jdod
            gridPanePlanningUser.getChildren().setAll();

            gridPanePlanningUser.setHgap(15);
            gridPanePlanningUser.setVgap(15);

            //???
            int col = 0;
            int row = 0;
            ImageView[] planningAffiche = new ImageView[listPlanning.size()];
            System.out.println(listPlanning.size());
            scrollPanePlanningUser.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
            for (int i = 0; i < listPlanning.size(); i++) {
                String path = listPlanning.get(i).getImageEvent().getImage().getUrl().substring(6);
                System.out.println("path: " + path);
                File file = new File(path);
                Image image = new Image(file.toURI().toString());
                ImageView imgV = new ImageView(image);
                imgV.setFitWidth(205d);
                imgV.setFitHeight(260d);

                planningAffiche[i] = imgV;
                gridPanePlanningUser.add(planningAffiche[i], col, row);
                col++;

                if (col == 6) {
                    row++;
                    col = 0;
                }

                int index = i;
            }
        }
    }


    //-Bouton -champs- rechercher -
    @FXML
    public void changeSearchField(ActionEvent event) {
        ComboBox<String> typeRec = filterfl != null ? filterfl : comboTypePlanningUser;
        String critereRech = typeRec.getSelectionModel().getSelectedItem();
        TextField searField = searchfl != null ? searchfl : tfSearchPlanningUser;
        DatePicker dDeb = dteDeb != null ? dteDeb : dteDebPlanningUser;
        DatePicker dFin = dteFin != null ? dteFin : dteFinPlanningUser;
        ImageView cBtn = btnClrDate != null ? btnClrDate : btnClrDatePlanningUser;

        if (critereRech != null) {
            if (critereRech == "ID" || critereRech == "Type Event" || critereRech == "Titre Event" || critereRech == "Nom Salle") {
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
        ObservableList<Planning> LoadPlanningFromDatabase = sp.LoadPlanningFromDatabase();
        int planningId = LoadPlanningFromDatabase.get(index).getId();
        String type = LoadPlanningFromDatabase.get(index).getType();
        String titreEvent = LoadPlanningFromDatabase.get(index).getTitreEvent();
        ImageView imageEvent = LoadPlanningFromDatabase.get(index).getImageEvent();
        String nomSalle = LoadPlanningFromDatabase.get(index).getNomSalle();
        Date date = LoadPlanningFromDatabase.get(index).getDate();
        Time heureDebut = LoadPlanningFromDatabase.get(index).getHeureDebut();
        Time heureFin = LoadPlanningFromDatabase.get(index).getHeureFin();


    }


    public Stage loadStage(Parent root) {
        Scene scene = new Scene(root);
        Stage stage = new Stage();
        stage.setScene(scene);
        stage.setResizable(false);
        scene.getStylesheets().add(this.getClass().getResource("../../GUI/wifek/DashboardFilm.css").toExternalForm());
        return stage;
    }

    public FXMLLoader loadFXML(String url) {
        FXMLLoader fxmlLoader = new FXMLLoader();
        fxmlLoader.setLocation(getClass().getResource(url));
        return fxmlLoader;
    }

    // clear date avec croix
    @FXML
    public void clearDate() {
        DatePicker dDeb = dteDeb != null ? dteDeb : dteDebPlanningUser;
        DatePicker dFin = dteFin != null ? dteFin : dteFinPlanningUser;
        dDeb.setValue(null);
        dFin.setValue(null);
    }


    // recherche ; alerte
    @FXML
    void rapidSearch(KeyEvent event) {

        System.out.println("call method rapid search");
        ComboBox<String> filterField = filterfl != null ? filterfl : comboTypePlanningUser;
        TextField searchField = searchfl != null ? searchfl : tfSearchPlanningUser;

        if (filterField.getSelectionModel().getSelectedItem() == null) {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Warning"); //affichage dans la barre de titre
            alert.setHeaderText("Vous devez spécifier critère de recherche avant");
            alert.showAndWait(); // matnajem tarja3 el fenetre mere ken matenzel ok 3ala el alert
        } else {
            System.out.println("recherche");
            ObservableList<Planning> listPlanning = FXCollections.observableArrayList();
            String critere = filterField.getSelectionModel().getSelectedItem();
            if (critere == "ID" || critere == "Type Event" || critere == "Titre Event"
                    || critere == "Nom Salle") {
                listPlanning = sp.recherchePlanning(critere, searchField.getText());
            }
            System.out.println(listPlanning);
            // gridPaneSpectacleUser.remove
            showPlanning(listPlanning);
        }


    }

    //Methode recherche par date
    @FXML
    void dateRapidSearch(ActionEvent event) {

        System.out.println("call method rapid search");
        ComboBox<String> filterField = filterfl != null ? filterfl : comboTypePlanningUser;
        TextField searchField = searchfl != null ? searchfl : tfSearchPlanningUser;

        if (filterField.getSelectionModel().getSelectedItem() == null) {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Warning"); //affichage dans la barre de titre
            alert.setHeaderText("Vous devez spécifier critère de recherche avant");
            alert.showAndWait();
        } else {
            DatePicker dDeb = dteDeb != null ? dteDeb : dteDebPlanningUser;
            DatePicker dFin = dteFin != null ? dteFin : dteFinPlanningUser;

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
                ObservableList<Planning> listPlanning = FXCollections.observableArrayList();
                Date vDteDeb = dDeb.getValue() == null ? null : Date.valueOf(dDeb.getValue()); // test est ce que date deb null , ken mech fergha nconverti  daye local dteBed en type date sinon tet3ada null
                Date vDteFin = dFin.getValue() == null ? null : Date.valueOf(dFin.getValue());
                listPlanning = sp.recherchePlanning("date", vDteDeb, vDteFin);
                System.out.println("liste plannings2: " + listPlanning);
                showPlanning(listPlanning); // yi3abi el tableview
            }
        }
    }


    //methode d'affichage planning (main)
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

    //methode d'affichage spectacle (main)
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
        if (event.getSource() == linkShows) {
            Parent fxml = FXMLLoader.load(getClass().getResource("../../GUI/sana/Spectacle.fxml"));
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

    @FXML
    void handleShowPromotion(MouseEvent event) {

    }

    @FXML
    void handleButtonAction(ActionEvent event) {

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

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        //hideAdminFeaturesPlan(sp.selectRoleCurrentUser(idCurrentUser));


        if (filterfl != null) {
            filterfl.getItems().setAll("ID", "Type Event", "Nom Salle", "Date Proj");// yi3abi les valeur mta3 combobox ili bech nakhtar menha el type de filtre

        } else if (comboTypePlanningUser != null) {
            comboTypePlanningUser.getItems().setAll("ID", "Type Event", "Nom Salle", "Date Proj");// yi3abi les valeur mta3 combobox ili bech nakhtar menha el type de filtre
        }

// yi3abi juste les champs mmta3 el admin mta3 l combo
        if (tfNomSalle != null) {
            tfNomSalle.getItems().setAll(sp.loadTitreEventSalleFromDB("salledecinema"));// yi3abi les valeur mta3 combobox ili bech nakhtar menha el type de filtre
        }

        if (tfType != null) {
            tfType.getItems().setAll("Film", "Spectacle");// yi3abi les valeur mta3 combobox ili bech nakhtar menha el type de filtre
        }


        ObservableList<Planning> listPlanning = sp.LoadPlanningFromDatabase();
        showPlanning(listPlanning);


        //test ajout
        if (idcol != null && typeEventcol != null && titreEventCol != null && nomSalleCol != null && datecol != null && heurdebcol != null && heurfincol != null) {

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


}
