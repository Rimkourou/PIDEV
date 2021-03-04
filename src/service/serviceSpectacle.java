package service;

import Entités.SpectacleE;

import java.sql.SQLException;
import java.util.List;

public interface serviceSpectacle {

    void AddSpectacle(SpectacleE s);

    List<SpectacleE> spectacleEList();

    void deleteSpectacle(int id) throws SQLException;

    void updateSpectacle(SpectacleE s);


}
