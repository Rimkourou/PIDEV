package service.wifek;


import entitie.wifek.Film;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import utils.SingletonConnection;

import java.sql.*;
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
        String req = "insert into film (titre, description, auteur, categorie, genre, img) values (?,?,?,?,?,?)";
        try {
            pst = conn.prepareStatement(req);
            pst.setString(1, f.getTitre());
            pst.setString(2, f.getDescription());
            pst.setString(3, f.getAuteur());
            pst.setString(4, f.getCategorie());
            pst.setString(5, f.getGenre());
            pst.setString(6, f.getImg());
            pst.executeUpdate();

        } catch (SQLException ex) {
            Logger.getLogger(FilmService.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void editFilm(Film f){
        String req = "update film SET titre= ?, description=?, auteur=?, categorie=?, genre=?, img=? where id=?";
        try {
            pst = conn.prepareStatement(req);
            pst.setString(1, f.getTitre());
            pst.setString(2, f.getDescription());
            pst.setString(3, f.getAuteur());
            pst.setString(4, f.getCategorie());
            pst.setString(5, f.getGenre());
            pst.setString(6, f.getImg());
            pst.setInt(7, f.getId());

            pst.executeUpdate();

        } catch (SQLException ex) {
            Logger.getLogger(FilmService.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public ObservableList<Film> filmList() {
        String req = "select * from film";

        ObservableList<Film> list = FXCollections.observableArrayList();
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
                        rs.getString("img")
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

    @Override
    public ObservableList<Film> searchFilmByType(String genre) {
        String req = "select * from film where genre like ?";
        ObservableList<Film> list = FXCollections.observableArrayList();
        try {

            pst = conn.prepareStatement(req);
            pst.setString(1,"%" + genre + "%");
            rs = pst.executeQuery();
            while (rs.next()) {
                list.add(
                        new Film(
                                rs.getInt("id"),
                                rs.getString("titre"),
                                rs.getString("description"),
                                rs.getString("auteur"),
                                rs.getString("categorie"),
                                rs.getString("genre"),
                                rs.getString("img")
                        )
                );
            }

        } catch (SQLException ex) {
            Logger.getLogger(FilmService.class.getName()).log(Level.SEVERE, null, ex);
        }
        return list;
    }

    @Override
    public ObservableList<Film> searchFilmByCategory(String categorie) {
        String req = "select * from film where categorie like ?";
        ObservableList<Film> list = FXCollections.observableArrayList();
        try {

            pst = conn.prepareStatement(req);
            pst.setString(1,"%" + categorie + "%");
            rs = pst.executeQuery();
            while (rs.next()) {
                list.add(
                        new Film(
                                rs.getInt("id"),
                                rs.getString("titre"),
                                rs.getString("description"),
                                rs.getString("auteur"),
                                rs.getString("categorie"),
                                rs.getString("genre"),
                                rs.getString("img")
                        )
                );
            }

        } catch (SQLException ex) {
            Logger.getLogger(FilmService.class.getName()).log(Level.SEVERE, null, ex);
        }
        return list;
    }

    @Override
    public ObservableList<Film> searchFilmByTitle(String titre) {
        String req = "select * from film where titre like ?";
        ObservableList<Film> list = FXCollections.observableArrayList();
        try {

            pst = conn.prepareStatement(req);
            pst.setString(1,"%" + titre + "%");
            rs = pst.executeQuery();
            while (rs.next()) {
                list.add(
                        new Film(
                                rs.getInt("id"),
                                rs.getString("titre"),
                                rs.getString("description"),
                                rs.getString("auteur"),
                                rs.getString("categorie"),
                                rs.getString("genre"),
                                rs.getString("img")
                        )
                );
            }

        } catch (SQLException ex) {
            Logger.getLogger(FilmService.class.getName()).log(Level.SEVERE, null, ex);
        }
        return list;
    }
}
