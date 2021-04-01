package service.sana;

import entitie.sana.Planning;
import javafx.collections.ObservableList;

import java.sql.Date;
import java.util.ArrayList;

public interface servicePlanning {

    //Suppression de planning
    void deletePlanning(int id);

    //Mise à jour
    void updatePlanning(Planning p);

    //Selectionner plannings from db
    ObservableList<Planning> LoadPlanningFromDatabase();

    //methode de l'importation de l'image
    String loadImageEventFromDB(Integer idE, String typeE);

    //Ajout Planning
    void AddPlanning(Planning p);

    // yijib les champs el met3al9in bel planning mel DB
    ArrayList<String> loadTitreEventSalleFromDB(String tableName);

    //cette req permet de selectionner designation des spectacles, films, salle cinema par id
    Integer loadIdEventSalleFromDbByTitre(String tableName, String titre);

    //cette req permet de selectionner designation des spectacles, films, salle cinema par titre
    String loadTitreEventSalleFromDbById(String tableName, Integer id);

    //permet de la recherche par nomde salle , titre de spectacle ou film , idUser
    ObservableList<Planning> recherchePlanning(String filtreRech, String text);

    //permet de la recherche par date debut et date fin
    ObservableList<Planning> recherchePlanning(String selectedItem, Date dateDeb, Date dateFin);


    String selectRoleCurrentUser(Integer id);

}
