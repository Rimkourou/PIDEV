package service;


import entitie.Reclamation;
import entitie.SalleDeCinema;
import javafx.collections.ObservableList;

import java.util.List;

public interface IRecalamationService {

    void AddReclamation (Reclamation r);
    ObservableList<Reclamation> reclamationListe(int idUser);
    void deleteReclamation(int idReclamation);
    void updateReclamation(Reclamation r);
    Reclamation getReclamationById(int idReclamation);
    ObservableList<Reclamation> rechercherReclamationByObject(String name);


}
