package service.hazem;


import entitie.hazem.share.PlanningFilm;
import entitie.hazem.share.PlanningSpectacle;
import entitie.hazem.SalleDeCinema;
import javafx.collections.ObservableList;

public interface ISaleDeCinemaService {
    void AddSalle (SalleDeCinema s);
    ObservableList<SalleDeCinema> salleDeCinemaListe();
    ObservableList<String> salleDeCinemaListeName();
    void deleteSalleDeCinema(int idSalleDeCinema);
    void updateSalleDeCinema(SalleDeCinema s);
    ObservableList<PlanningFilm> afficherPlanningFilm(int idSalleDeCinema);
    ObservableList<PlanningSpectacle> afficherPlanningSpectacle(int idSalleDeCinema);
    ObservableList<SalleDeCinema> rechercherSalleByName(String name);
    SalleDeCinema getSalleById(int idSalle);

}
