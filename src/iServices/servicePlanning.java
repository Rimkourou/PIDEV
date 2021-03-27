package iServices;

import entites.Planning;
import javafx.collections.ObservableList;
import services.serviceplanningIMP;

import java.sql.Date;
import java.util.ArrayList;

public interface servicePlanning{
    //Suppression de planning
    void deletePlanning(int id);
    //Mise à jour
    void updatePlanning(Planning p);

    //Selectionner planings from db
    ObservableList<Planning> LoadPlanningFromDatabase();

    //Selectionner planings from db
    String loadImageEventFromDB(Integer idE, String typeE);


    void AddPlanning(Planning p);

    ArrayList<String> loadTitreEventSalleFromDB(String tableName);

    //cette req permet de selectionner designation des spectacles, films, salle cinema
    Integer loadIdEventSalleFromDbByTitre(String tableName, String titre);

    //cette req permet de selectionner designation des spectacles, films, salle cinema
    String loadTitreEventSalleFromDbById(String tableName, Integer id);

    ObservableList<Planning> recherchePlanning(String filtreRech, String text);

    ObservableList<Planning> recherchePlanning(String selectedItem, Date dateDeb, Date dateFin);


    String selectRoleCurrentUser(Integer id);

}
