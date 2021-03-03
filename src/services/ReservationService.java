package services;

import entites.Reservation;
import iServices.IServiceReservation;
import utils.SingletonConnection;
import javax.swing.*;
import java.awt.*;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ReservationService implements IServiceReservation {
    private Statement ste;
    private PreparedStatement pst;
    private ResultSet rs;
    private Connection conn;

    public ReservationService() {
        conn = SingletonConnection.getInstance().getCnx();
    }

    @Override
    public void addReservation(Reservation r) {
        String req = "insert into reservation (dateReservation, validation, idUser, idFacture, idP, idSalle) values (?,?,?,?,?,?)";
        try {
            pst = conn.prepareStatement(req);
            pst.setDate(1, (Date) r.getDateReservation());
            pst.setString(2, r.getValidation());
            pst.setInt(3, r.getIdUser());
            pst.setInt(4, r.getIdFacture());
            pst.setInt(5, r.getIdP());
            pst.setInt(6, r.getIdSalle());

            pst.executeUpdate();

        } catch (SQLException ex) {
            Logger.getLogger(FilmService.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void editReservation(Reservation r){
        String req = "update reservation SET dateReservation= ?, validation=?, idUser=?, idFacture=?, idP=?, idSalle=? where id=?";
        try {
            pst = conn.prepareStatement(req);
            pst.setDate(1, (Date) r.getDateReservation());
            pst.setString(2, r.getValidation());
            pst.setInt(3, r.getIdUser());
            pst.setInt(4, r.getIdFacture());
            pst.setInt(5, r.getIdP());
            pst.setInt(6, r.getIdSalle());
            pst.setInt(7, r.getId());
            pst.executeUpdate();

        } catch (SQLException ex) {
            Logger.getLogger(ReservationService.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public List<Reservation> reservationList() {
        String req = "select * from Reservation";

        List<Reservation> list = new ArrayList<>();
        try {
            ste = conn.createStatement();
            rs = ste.executeQuery(req);
            while (rs.next()) {
                list.add(new Reservation(rs.getInt("id"),
                        rs.getDate("dateReservation"),
                        rs.getString("validation"),
                        rs.getInt("idUser"),
                        rs.getInt("idFacture"),
                        rs.getInt("idP"),
                        rs.getInt("idSalle")
                ));
            }

        } catch (SQLException ex) {
            Logger.getLogger(ReservationService.class.getName()).log(Level.SEVERE, null, ex);
        }
        return list;
    }

    @Override
    public void deleteReservation(int idR) {
        String sql = "delete from reservation where id=? ";
        try {
            pst = conn.prepareStatement(sql);
            pst.setInt(1, idR);
            pst.executeUpdate();
            System.out.println("reservation deleted successfully");

        } catch (SQLException ex) {
            Logger.getLogger(ReservationService.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public List<Reservation> reservationSalleList() {
        String req = "select * from planning p INNER JOIN salledecinema s ON p.idSalle=s.id INNER JOIN film f ON f.idSalle = s.id";

        List<Reservation> list = new ArrayList<>();
        try {
            ste = conn.createStatement();
            rs = ste.executeQuery(req);
            while (rs.next()) {
                list.add(new Reservation(rs.getInt("id"),
                        rs.getDate("dateReservation"),
                        rs.getString("validation"),
                        rs.getInt("idUser"),
                        rs.getInt("idFacture"),
                        rs.getInt("idP"),
                        rs.getInt("idSalle")
                ));
            }

        } catch (SQLException ex) {
            Logger.getLogger(ReservationService.class.getName()).log(Level.SEVERE, null, ex);
        }
        return list;
    }
}
