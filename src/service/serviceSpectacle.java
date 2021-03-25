package service;

import Entités.SpectacleE;
import javafx.collections.ObservableList;

import java.sql.Date;
import java.sql.SQLException;
import java.text.ParseException;
import java.util.List;

public interface serviceSpectacle {

    void AddSpectacle(SpectacleE s);

    //List<SpectacleE> spectacleEList();

    void deleteSpectacle(int id) throws SQLException;

    void updateSpectacle(SpectacleE s);


    ObservableList<SpectacleE> rechercheSpectacle(String critereRech, Date dteDeb, Date dteFin);

    ObservableList<SpectacleE> rechercheSpectacle(String critereRech, String valRech) throws ParseException;

    boolean spectacleExist(String titre) throws SQLException;
}
