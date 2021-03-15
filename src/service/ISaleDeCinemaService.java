package service;


import entitie.PlanningFilm;
import entitie.PlanningSpectacle;
import entitie.SalleDeCinema;
import javafx.collections.ObservableList;

import java.util.List;

public interface ISaleDeCinemaService {
    void AddSalle (SalleDeCinema s);
    ObservableList<SalleDeCinema> salleDeCinemaListe();
    ObservableList<String> salleDeCinemaListeName();
    void deleteSalleDeCinema(int idSalleDeCinema);
    void updateSalleDeCinema(SalleDeCinema s);
    ObservableList<PlanningFilm> afficherPlanningFilm(int idSalleDeCinema);
    ObservableList<PlanningSpectacle> afficherPlanningSpectacle(int idSalleDeCinema);
    ObservableList<SalleDeCinema> rechercherSalleByName(String name);

}
