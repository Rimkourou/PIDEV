package controller.hazem;

import entitie.hazem.share.PlanningFilm;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import service.hazem.SaleDeCinemaService;

import java.net.URL;
import java.sql.Time;
import java.util.Date;
import java.util.ResourceBundle;

public class PlanningFilmControllerAdmin implements Initializable {

    private final SaleDeCinemaService saleDeCinemaService = new SaleDeCinemaService();

    @FXML
    private TableView<PlanningFilm> tvPlanningFilm;

    @FXML
    private TableColumn<PlanningFilm, String> colNomFilm;
    @FXML
    private TableColumn<PlanningFilm, Date> colDateFilm;
    @FXML
    private TableColumn<PlanningFilm, Time> colHDFilm;

    @FXML
    private TableColumn<PlanningFilm, Time> colHFFilm;
    @Override
    public void initialize(URL location, ResourceBundle resources) {
//        System.out.println(ControllerSalleDeCinemaAdmin.idSalle);
        ObservableList<PlanningFilm> pf= saleDeCinemaService.afficherPlanningFilm(ControllerSalleDeCinemaAdmin.idSalle);
        colNomFilm.setCellValueFactory(new PropertyValueFactory<PlanningFilm, String>("nomFilm"));
        colDateFilm.setCellValueFactory(new PropertyValueFactory<PlanningFilm, Date>("date"));
        colHDFilm.setCellValueFactory(new PropertyValueFactory<PlanningFilm, Time>("heureD"));
        colHFFilm.setCellValueFactory(new PropertyValueFactory<PlanningFilm, Time>("heureF"));
        tvPlanningFilm.setItems(pf);

    }
}
