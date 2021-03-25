package service;


import entitie.Reclamation;
import entitie.SalleDeCinema;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import utils.SingletonConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class RecalamationService implements IRecalamationService {
    private Statement ste;
    private PreparedStatement pst;
    private ResultSet rs;

    private Connection conn;

    public RecalamationService() {
        conn = SingletonConnection.getInstance().getCnx();
    }

    @Override
    public void AddReclamation(Reclamation r) {
        String req = "insert into reclamation (objet, descripton, state, idSalle) values (?,?,?,?)";

        try {
            pst = conn.prepareStatement(req);
            pst.setString(1, r.getObjet());
            pst.setString(2, r.getDescription());
            pst.setString(3, r.getState());
            pst.setInt(4, r.getIdSalle());
            pst.executeUpdate();

        } catch (SQLException ex) {
            Logger.getLogger(RecalamationService.class.getName()).log(Level.SEVERE, null, ex);
        }
    }


    @Override
    public ObservableList<Reclamation> reclamationListe(int idUser) {
        String req = "select r.id, r.objet, r.descripton, r.state, r.idSalle FROM reclamation r INNER JOIN salledecinema s on r.idSalle = s.id INNER JOIN user u ON s.idUser = u.id WHERE u.id=?";

        ObservableList<Reclamation> list = FXCollections.observableArrayList();
        try {
            pst = conn.prepareStatement(req);
            pst.setInt(1, idUser);
            rs = pst.executeQuery();
            while (rs.next()) {
                list.add(new Reclamation(rs.getInt("id"),
                        rs.getString("objet"),
                        rs.getString("descripton"),
                        rs.getString("state"),
                        rs.getInt("idSalle")
                ));
            }

        } catch (SQLException ex) {
            Logger.getLogger(RecalamationService.class.getName()).log(Level.SEVERE, null, ex);
        }
        return list;
    }


    @Override
    public void deleteReclamation(int idReclamation) {
        String sql = "delete from reclamation where id=? ";
        try {
            pst = conn.prepareStatement(sql);
            pst.setInt(1, idReclamation);
            pst.executeUpdate();
            System.out.println("Record deleted successfully");

        } catch (SQLException ex) {
            Logger.getLogger(RecalamationService.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public void updateReclamation(Reclamation r) {
        String req = "update reclamation SET objet= ?, descripton=?, state=?, idSalle=? where id=?";
        try {
            pst = conn.prepareStatement(req);
            pst.setString(1, r.getObjet());
            pst.setString(2, r.getDescription());
            pst.setString(3, r.getState());
            pst.setInt(4, r.getIdSalle());
            pst.setInt(5, r.getId());
            pst.executeUpdate();
            System.out.println("update done");

        } catch (SQLException ex) {
            Logger.getLogger(RecalamationService.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public Reclamation getReclamationById(int idReclamation) {
        String req = "select * FROM reclamation WHERE id=?";

        Reclamation r = new Reclamation();
        try {
            pst = conn.prepareStatement(req);
            pst.setInt(1, idReclamation);
            rs = pst.executeQuery();
            while (rs.next()) {
                r.setId(rs.getInt("id"));
                r.setObjet(rs.getString("objet"));
                r.setDescription(rs.getString("descripton"));
                r.setState(rs.getString("state"));
                r.setIdSalle(rs.getInt("idSalle"));
            }

        } catch (SQLException ex) {
            Logger.getLogger(RecalamationService.class.getName()).log(Level.SEVERE, null, ex);
        }
        return r;
    }

    @Override
    public ObservableList<Reclamation> rechercherReclamationByObject(String name) {
        String req = "select * from reclamation where objet like ?";
        ObservableList<Reclamation> sc = FXCollections.observableArrayList();
        try {

            pst = conn.prepareStatement(req);
            pst.setString(1,"%" + name + "%");
            rs = pst.executeQuery();
            while (rs.next()) {
                sc.add(new Reclamation(rs.getInt("id"),
                                rs.getString("objet"),
                                rs.getString("descripton"),
                                rs.getString("state"),
                                rs.getInt("idSalle")
                        ));
            }

        } catch (SQLException ex) {
            Logger.getLogger(SaleDeCinemaService.class.getName()).log(Level.SEVERE, null, ex);
        }
        return sc;
    }
}
