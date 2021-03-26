package services;

import entites.Film;
import entites.Planning;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import utils.SingletonConnection;

import java.sql.*;
import java.util.logging.Level;
import java.util.logging.Logger;

public class PlanningService {
    private Statement ste;
    private PreparedStatement pst;
    private ResultSet rs;
    private Connection conn;

    public PlanningService(){
        conn = SingletonConnection.getInstance().getCnx();
    }

    public void addPlanning(Planning p) {
        String req = "insert into planning (date, dateDebut, dateFin, idFilm, idSpectacle, idSalle) values (?,?,?,?,?,?)";
        try {
            pst = conn.prepareStatement(req);
            pst.setTimestamp(1, p.getDate());
            pst.setTimestamp(2, p.getDateDebut());
            pst.setTimestamp(3, p.getDateFin());
            pst.setInt(4, p.getIdFilm());
            pst.setInt(5, p.getIdSpectacle());
            pst.setInt(6, p.getIdSalle());
            pst.executeUpdate();

        } catch (SQLException ex) {
            Logger.getLogger(FilmService.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public ObservableList<Planning> planningList() {
        String req = "select * from planning";

        ObservableList<Planning> list = FXCollections.observableArrayList();
        try {
            ste = conn.createStatement();
            rs = ste.executeQuery(req);
            while (rs.next()) {
                list.add(new Planning(rs.getInt("id"),
                        rs.getTimestamp("date"),
                        rs.getTimestamp("dateDebut"),
                        rs.getTimestamp("dateFin"),
                        rs.getInt("idFilm"),
                        rs.getInt("idSpectacle"),
                        rs.getInt("idSalle")
                ));

            }

        } catch (SQLException ex) {
            Logger.getLogger(PlanningService.class.getName()).log(Level.SEVERE, null, ex);
        }
        return list;

    }
}
