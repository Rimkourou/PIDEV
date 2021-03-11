package controller;

import controller.ControllerSalleDeCinemaAdmin;
import entitie.PlanningSpectacle;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import service.SaleDeCinemaService;

import java.net.URL;
import java.sql.Time;
import java.util.Date;
import java.util.ResourceBundle;

public class PlanningSpectacleControllerClient implements Initializable {
    private final SaleDeCinemaService saleDeCinemaService = new SaleDeCinemaService();

    @FXML
    private TableView<PlanningSpectacle> tvPlanningSpectacle;

    @FXML
    private TableColumn<PlanningSpectacle, String> colNomSpectacle;
    @FXML
    private TableColumn<PlanningSpectacle, Date> colDateSpectacle;
    @FXML
    private TableColumn<PlanningSpectacle, Time> colHDSpectacle;

    @FXML
    private TableColumn<PlanningSpectacle, Time> colHFSpectacle;
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        ObservableList<PlanningSpectacle> pf= saleDeCinemaService.afficherPlanningSpectacle(ControllerSalleDeCinemaClient.idSalle);
        colNomSpectacle.setCellValueFactory(new PropertyValueFactory<PlanningSpectacle, String>("nomSpectacle"));
        colDateSpectacle.setCellValueFactory(new PropertyValueFactory<PlanningSpectacle, Date>("date"));
        colHDSpectacle.setCellValueFactory(new PropertyValueFactory<PlanningSpectacle, Time>("heureD"));
        colHFSpectacle.setCellValueFactory(new PropertyValueFactory<PlanningSpectacle, Time>("heureF"));
        tvPlanningSpectacle.setItems(pf);
    }
}
