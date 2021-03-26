package InterfaceReservation;

import InterfaceFilm.InterfaceDetailFilmController;
import entites.Film;
import entites.Planning;
import entites.Reservation;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.util.Callback;
import services.PlanningService;

import java.io.IOException;
import java.net.URL;
import java.sql.Timestamp;
import java.util.Date;
import java.util.ResourceBundle;

public class InterfacePlanningController implements Initializable {
    PlanningService ps = new PlanningService();
    @FXML
    private TableView<Planning> tvPlanning;

    @FXML
    private TableColumn<Planning, Integer> colId;

    @FXML
    private TableColumn<Planning, Timestamp> colDate;

    @FXML
    private TableColumn<Planning, Timestamp> colDatedeb;

    @FXML
    private TableColumn<Planning, Timestamp> colDateFin;

    @FXML
    private TableColumn<Planning, Integer> colFilm;

    @FXML
    private TableColumn<Planning, Integer> colS;

    @FXML
    private TableColumn<Planning, Integer> colSalle;

    @FXML
    private AnchorPane reservationPage;


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        showPlanning();

    }

    private void showPlanning() {
        ObservableList<Planning> list= ps.planningList();
        colId.setCellValueFactory(new PropertyValueFactory<Planning, Integer>("id"));
        colDate.setCellValueFactory(new PropertyValueFactory<Planning, Timestamp>("date"));
        colDatedeb.setCellValueFactory(new PropertyValueFactory<Planning, Timestamp>("dateDebut"));
        colDateFin.setCellValueFactory(new PropertyValueFactory<Planning, Timestamp>("dateFin"));
        colFilm.setCellValueFactory(new PropertyValueFactory<Planning, Integer>("idFilm"));
        colS.setCellValueFactory(new PropertyValueFactory<Planning, Integer>("idSpectacle"));
        colSalle.setCellValueFactory(new PropertyValueFactory<Planning, Integer>("idSalle"));
        addButtonToTable();
        tvPlanning.setItems(list);
    }

    private void addButtonToTable() {
        TableColumn<Planning, Void> colBtn = new TableColumn("Booking");
        Callback<TableColumn<Planning, Void>, TableCell<Planning, Void>> cellFactory = new Callback<TableColumn<Planning, Void>, TableCell<Planning, Void>>() {
            @Override
            public TableCell<Planning, Void> call(final TableColumn<Planning, Void> param) {
                final TableCell<Planning, Void> cell = new TableCell<Planning, Void>() {

                    private final Button btn = new Button("Book a ticket");

                    {
                        btn.setOnAction((ActionEvent event) -> {
                            Planning data = getTableView().getItems().get(getIndex());
                            System.out.println("selectedData: " + data);
                            FXMLLoader fxmlLoader = loadFXML("InterfaceReservation.fxml");
                            try {
                                Parent root = fxmlLoader.load();
                                Stage stage = loadStage(root);
                                InterfaceReservationController stageController = fxmlLoader.getController();
                                stageController.test(data);
                                stage.initOwner(reservationPage.getScene().getWindow());
                                stage.initModality(Modality.WINDOW_MODAL);
                                stage.show();
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                        });
                    }
                    public Stage loadStage(Parent root){
                        Scene scene = new Scene(root);
                        Stage stage = new Stage();
                        stage.setScene(scene);
                        stage.setResizable(false);
                        scene.getStylesheets().add(this.getClass().getResource("InterfacePaiement.css").toExternalForm());
                        return stage;
                    }

                    public FXMLLoader loadFXML(String url){
                        FXMLLoader fxmlLoader = new FXMLLoader();
                        fxmlLoader.setLocation(getClass().getResource(url));
                        return fxmlLoader;
                    }

                    @Override
                    public void updateItem(Void item, boolean empty) {
                        super.updateItem(item, empty);
                        if (empty) {
                            setGraphic(null);
                        } else {
                            setGraphic(btn);
                        }
                    }
                };
                return cell;
            }
        };

        colBtn.setCellFactory(cellFactory);

        tvPlanning.getColumns().add(colBtn);

    }

}
