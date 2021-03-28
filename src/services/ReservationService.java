package services;

import entites.Planning;
import entites.Reservation;
import entites.SalleDeCinema;
import iServices.IServiceReservation;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import utils.SingletonConnection;
import javax.swing.*;
import java.awt.*;
import java.sql.*;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ReservationService implements IServiceReservation {
    private Statement ste;
    private PreparedStatement pst,pst1;
    private ResultSet rs;
    private Connection conn;

    public ReservationService() {
        conn = SingletonConnection.getInstance().getCnx();
    }

    @Override
    public void addReservation(Reservation r){
        String req = "insert into reservation (dateReservation, idUser, idSalle, idFilm, nbrPlaceRes, idSpectacle) values (?,?,?,?,?,?)";
        try {
            pst = conn.prepareStatement(req);
            pst.setDate(1, (Date) r.getDateReservation());
            pst.setInt(2, r.getIdUser());
            pst.setString(3, r.getIdSalle());
            pst.setString(4, r.getIdFilm());
            pst.setInt(5, r.getNbrPlaceRes());
            pst.setString(6, r.getIdSpectacle());
            pst.executeUpdate();
            pst.close();

            String req2 = "update salledecinema SET nbrPlace=? where id=?";
            SalleDeCinema s = getSalleById(r.getIdSalle());
            pst1=conn.prepareStatement(req2);
            pst1.setInt(1,s.getNbrPlace()-r.getNbrPlaceRes());
            pst1.setInt(2,s.getId());
            pst1.executeUpdate();

        } catch (SQLException ex) {
            Logger.getLogger(FilmService.class.getName()).log(Level.SEVERE, null, ex);
        }}


    public SalleDeCinema getSalleById(String nom){
        String req = "select * from salledecinema where nom =?";
        SalleDeCinema sc = new SalleDeCinema();
        try {

            pst = conn.prepareStatement(req);
            pst.setString(1, nom );
            rs = pst.executeQuery();
            while (rs.next()) {
                sc= new  SalleDeCinema(
                                rs.getInt("id"),
                                rs.getString("nom"),
                                rs.getInt("nbrPlace"),
                                rs.getString("Description"),
                                rs.getString("adresse"),
                                rs.getInt("idUser")
                );
            }

        } catch (SQLException ex) {
            Logger.getLogger(ReservationService.class.getName()).log(Level.SEVERE, null, ex);
        }
        return sc;

    }

    public void editReservation(Reservation r){
        String req = "update reservation SET dateReservation= ?, idUser=?, idSalle=?, idFilm=?, nbrPlaceRes=?, idSpectacle=? where id=?";
        try {
            pst = conn.prepareStatement(req);
            pst.setDate(1, (Date) r.getDateReservation());
            pst.setInt(2, r.getIdUser());
            pst.setString(3, r.getIdSalle());
            pst.setString(4, r.getIdFilm());
            pst.setInt(5, r.getNbrPlaceRes());
            pst.setString(6, r.getIdSalle());
            pst.setInt(7, r.getId());
            pst.executeUpdate();

            String req2 = "update salledecinema SET nbrPlace=? where nom=?";
            SalleDeCinema s = getSalleById(r.getIdSalle());
            pst1=conn.prepareStatement(req2);
            pst1.setInt(1,s.getNbrPlace());
            pst1.setInt(2,s.getId());
            pst1.executeUpdate();

        } catch (SQLException ex) {
            Logger.getLogger(ReservationService.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public ObservableList<Reservation> reservationList() {

        String req = "select * from Reservation";

        ObservableList<Reservation> list =  FXCollections.observableArrayList();
        try {
            ste = conn.createStatement();
            rs = ste.executeQuery(req);
            while (rs.next()) {
                list.add(new Reservation(rs.getInt("id"),
                        rs.getDate("dateReservation"),
                        rs.getInt("idUser"),
                        rs.getString("idSalle"),
                        rs.getString("idFilm"),
                        rs.getInt("nbrPlaceRes"),
                        rs.getString("idSpectacle")
                ));
            }

        } catch (SQLException ex) {
            Logger.getLogger(ReservationService.class.getName()).log(Level.SEVERE, null, ex);
        }
        return list;

    }


    @Override
    public void deleteReservation(Reservation r) {
        String sql = "delete from reservation where id=? ";
        try {
            String test ="2021-03-10 15:39:05";
            Timestamp timestamp = new Timestamp(System.currentTimeMillis());
            //Planning p = getPlanningById(r.getIdFilm());
            //if(timestamp.compareTo(p.getDateDebut())>30){
            pst = conn.prepareStatement(sql);
            pst.setInt(1, r.getId());
            pst.executeUpdate();
            System.out.println("reservation deleted successfully");

            String req2 = "update salledecinema SET nbrPlace=? where nom=?";
            SalleDeCinema s = getSalleById(r.getIdSalle());
            pst1=conn.prepareStatement(req2);
            pst1.setInt(1,s.getNbrPlace()+r.getNbrPlaceRes());
            pst1.setString(2,s.getNom());
            pst1.executeUpdate();
           /* }else {
                System.out.println("erreur");
            }*/

        } catch (SQLException ex) {
            Logger.getLogger(ReservationService.class.getName()).log(Level.SEVERE, null, ex);}

    }


}
