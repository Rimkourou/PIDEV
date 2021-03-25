package service;


import Entités.Planning;
import javafx.collections.ObservableList;

import java.sql.Date;
import java.sql.Time;
import java.util.ArrayList;

public interface servicePlanning {

    //Suppression de planning
    void deletePlanning(int id);
        //Mise à jour
    void updatePlanning(Planning p);

    //Selectionner planings from db
    ObservableList<Planning> LoadPlanningFromDatabase();

    //Selectionner planings from db
    String loadImageEventFromDB(Integer idE, String typeE);

    ArrayList<Integer> loadIdEventSalleFromDB(String tableName);

    void AddPlanning(Planning p);

    ObservableList<Planning> recherchePlanning(String filtreRech, String text);

    ObservableList<Planning> recherchePlanning(String selectedItem, Date dateDeb, Date dateFin);


    String selectRoleCurrentUser(Integer id);
}
