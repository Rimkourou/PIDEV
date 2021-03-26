package service.hazem;


import entitie.hazem.Reclamation;
import javafx.collections.ObservableList;

public interface IRecalamationService {

    void AddReclamation (Reclamation r);
    ObservableList<Reclamation> reclamationListe(int idUser);
    void deleteReclamation(int idReclamation);
    void updateReclamation(Reclamation r);
    Reclamation getReclamationById(int idReclamation);
    ObservableList<Reclamation> rechercherReclamationByObject(String name);
    ObservableList<Reclamation> rechercherReclamationByState(String state);


}
