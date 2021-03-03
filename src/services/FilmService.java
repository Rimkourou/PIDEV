package services;

import iServices.IServiceFilm;
import entites.Film;
import utils.SingletonConnection;

import javax.swing.*;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class FilmService implements IServiceFilm {
    private Statement ste;
    private PreparedStatement pst;
    private ResultSet rs;
    private Connection conn;

    public FilmService() {
        conn = SingletonConnection.getInstance().getCnx();
    }

    @Override
    public void addFilm(Film f) {
        String req = "insert into film (titre, description, auteur, categorie, genre, idUser, idSalle) values (?,?,?,?,?,?,?)";
        try {
            pst = conn.prepareStatement(req);
            pst.setString(1, f.getTitre());
            pst.setString(2, f.getDescription());
            pst.setString(3, f.getAuteur());
            pst.setString(4, f.getCategorie());
            pst.setString(5, f.getGenre());
            pst.setInt(6, f.getIdUser());
            pst.setInt(7, f.getIdSalle());

            pst.executeUpdate();

        } catch (SQLException ex) {
            Logger.getLogger(FilmService.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void editFilm(Film f){
        String req = "update film SET titre= ?, description=?, auteur=?, categorie=?, genre=?, idUser=?, idSalle=? where id=?";
        try {
            pst = conn.prepareStatement(req);
            pst.setString(1, f.getTitre());
            pst.setString(2, f.getDescription());
            pst.setString(3, f.getAuteur());
            pst.setString(4, f.getCategorie());
            pst.setString(5, f.getGenre());
            pst.setInt(6, f.getIdUser());
            pst.setInt(7, f.getIdSalle());
            pst.setInt(8, f.getId());

            pst.executeUpdate();

        } catch (SQLException ex) {
            Logger.getLogger(FilmService.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public List <Film>filmList() {
        String req = "select * from film";

        List<Film> list = new ArrayList<>();
        try {
            ste = conn.createStatement();
            rs = ste.executeQuery(req);
            while (rs.next()) {
                list.add(new Film(rs.getInt("id"),
                        rs.getString("titre"),
                        rs.getString("description"),
                        rs.getString("auteur"),
                        rs.getString("categorie"),
                        rs.getString("genre"),
                        rs.getInt("idUser"),
                        rs.getInt("idSalle")
                ));
            }

        } catch (SQLException ex) {
            Logger.getLogger(FilmService.class.getName()).log(Level.SEVERE, null, ex);
        }
        return list;

    }

    @Override
    public void deleteFilm(int id) {
        String sql = "delete from film where id=? ";
        try {
            pst = conn.prepareStatement(sql);
            pst.setInt(1, id);
            pst.executeUpdate();
            System.out.println("film deleted successfully");

        } catch (SQLException ex) {
            Logger.getLogger(FilmService.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    public List <Film>filmSalleList() {
        String req = "select * from film INNER JOIN salledecinema ON film.idSalle = salledecinema.id";

        List<Film> list = new ArrayList<>();
        try {
            ste = conn.createStatement();
            rs = ste.executeQuery(req);
            while (rs.next()) {
                list.add(new Film(rs.getInt("id"),
                        rs.getString("titre"),
                        rs.getString("description"),
                        rs.getString("auteur"),
                        rs.getString("categorie"),
                        rs.getString("genre"),
                        rs.getInt("idUser"),
                        rs.getInt("idSalle")
                ));
            }

        } catch (SQLException ex) {
            Logger.getLogger(FilmService.class.getName()).log(Level.SEVERE, null, ex);
        }
        return list;

    }
}
