package services;

import entites.SpectacleE;
import iServices.serviceSpectacle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import utils.SingletonConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.sql.*;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

public class servicespectacleIMP implements serviceSpectacle {

    private ResultSet rs;
    private Statement ste;
    public Statement st;
    public Connection cnx;

    public servicespectacleIMP() {

    }


    /******************************************Implémentation de la méthode ajouter******************************************/
    @Override
    public void AddSpectacle(SpectacleE s) {

        String req = "INSERT INTO spectacle(id, titre, date, genre, idUser, img) VALUES (?,?,?,?,?,?)";


        try {
            SingletonConnection ds1 = SingletonConnection.getInstance();
            Connection conn = ds1.getCnx();
            PreparedStatement pst = conn.prepareStatement(req);

            pst.setInt(1, s.getId());
            pst.setString(2, s.getTitre());
            pst.setDate(3, new java.sql.Date(s.getDate().getTime()));
            pst.setString(4, s.getGenre());
            pst.setInt(5, s.getIdUser());
            System.out.println("img: "+s.getImage().getImage().getUrl());
            pst.setString(6, s.getImage().getImage().getUrl());

            pst.executeUpdate();
            pst.close();

        } catch (SQLException ex) {
            Logger.getLogger(serviceSpectacle.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /******************************************Implement methode Delete******************************************/

    public void deleteSpectacle(int id) throws SQLException {

        String req = "DELETE FROM spectacle WHERE id=?";

        SingletonConnection ds1 = SingletonConnection.getInstance();
        Connection conn = ds1.getCnx();
        PreparedStatement pst = conn.prepareStatement(req);

        pst.setInt(1, id);

        pst.executeUpdate();


    }

    /******************************************implement methode Update******************************************/

    @Override
    public void updateSpectacle(SpectacleE s) {

        String req = "UPDATE spectacle SET titre=?, date=?, genre=?, idUser=? , img=? where id= ?";

        try {

            SingletonConnection ds1 = SingletonConnection.getInstance();
            Connection conn = ds1.getCnx();
            PreparedStatement pst = conn.prepareStatement(req);
            System.out.println("service update: "+s);
            pst.setString(1, s.getTitre());
            pst.setDate(2, new java.sql.Date(s.getDate().getTime()));
            pst.setString(3, s.getGenre());
            pst.setInt(4, s.getIdUser());
            pst.setInt(6, s.getId());
            pst.setString(5, s.getImage().getImage().getUrl());

            //System.out.println(s.getId());

            pst.executeUpdate();
            pst.close();


        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }
    // service de recherche date
    @Override
    public ObservableList<SpectacleE> rechercheSpectacle(String critereRech, Date dteDeb, Date dteFin) {
        String req = "";
        System.out.println("sdte deb "+dteDeb);
        System.out.println("sdte fin "+dteFin);
        if (dteDeb == null && dteFin == null) {
            req = "select * from spectacle ORDER BY id ASC";
        } else if (dteDeb != null && dteFin == null) {
            req = "SELECT * FROM `spectacle` WHERE `" + critereRech + "` >= '" + dteDeb + "'";
        } else if (dteDeb == null && dteFin != null) {
            req = "SELECT * FROM `spectacle` WHERE `" + critereRech + "` <= '" + dteFin + "'";
        } else {
            req = "SELECT * FROM `spectacle` WHERE `" + critereRech + "` BETWEEN '" + dteDeb + "'AND'" + dteFin + "'";
        }

        ObservableList<SpectacleE> table = FXCollections.observableArrayList();
        try {
            SingletonConnection ds1 = SingletonConnection.getInstance();
            Connection conn = ds1.getCnx();
            PreparedStatement pst = conn.prepareStatement(req);
            rs = pst.executeQuery(req);

            while (rs.next()) {

                Image image = new Image(rs.getString("img"));
                ImageView imgV = new ImageView(image);
                imgV.setFitHeight(80);
                imgV.setFitWidth(80);


                table.add(new SpectacleE(
                        rs.getInt("id"),
                        rs.getString("titre"),
                        rs.getDate("date"),
                        rs.getString("genre"),
                        rs.getInt("idUser"),
                        imgV));
            }
        } catch (SQLException ex) {
            System.out.println("probleme req ou cnx base de donnée");
            Logger.getLogger(serviceSpectacle.class.getName()).log(Level.SEVERE, null, ex);
        }
        return table;
    }

    @Override
    public ObservableList<SpectacleE> rechercheSpectacle(String critereRech, String valRech) throws ParseException {
        String req = ""; //initialisation req vide pour empecher un prob java

        if (critereRech == "id" || critereRech == "idUser") {
            System.out.println("val rech : "+valRech);
            if (valRech.trim().isEmpty()) { // si la valeur de recherche est vide il va affficher tt les spectacle
                req = "select * from spectacle ORDER BY id ASC";
            } else {
                int schfl = 0;
                try {
                    schfl = Integer.parseInt(valRech.trim()); // conversion string en integer
                    req = "SELECT * FROM `spectacle` WHERE `" + critereRech + "` LIKE '" + schfl + "%'";
                } catch (Exception e) {
                    req = "select * from spectacle ORDER BY id ASC";
                    System.out.println("service recherche rapide:: probleme conversion string vers entier");
                }


            }
        } else if (critereRech == "titre" || critereRech == "genre") {
            req = "SELECT * FROM `spectacle` WHERE `" + critereRech + "` LIKE '" + valRech.toUpperCase() + "%'";
        }

        //creation liste vide

        ObservableList<SpectacleE> table = FXCollections.observableArrayList();

        try {
            SingletonConnection ds1 = SingletonConnection.getInstance();
            Connection conn = ds1.getCnx();
            PreparedStatement pst = conn.prepareStatement(req);
            rs = pst.executeQuery(req);
            // parcourir req
            while (rs.next()) {

                Image image = new Image(rs.getString("img"));
                ImageView imgV = new ImageView(image);
                imgV.setFitHeight(80);
                imgV.setFitWidth(80);
                //creat et ajouter spectacle
                table.add(new SpectacleE(
                        rs.getInt("id"),
                        rs.getString("titre"),
                        rs.getDate("date"),
                        rs.getString("genre"),
                        rs.getInt("idUser"),
                        imgV));
            }
        } catch (SQLException ex) {
            System.out.println("probleme req ou cnx base de donnée");
            Logger.getLogger(serviceSpectacle.class.getName()).log(Level.SEVERE, null, ex);
        }
        //FXCollections.reverse(table);
        //System.out.println(table);

        return table;
    }


    /***********************return true si spectacle deja existant ************************/

    public boolean spectacleExist(String t) throws SQLException {

        String req = "SELECT `titre` FROM `spectacle` WHERE `titre` = '" + t.toUpperCase() + "'";
        SingletonConnection ds1 = SingletonConnection.getInstance();
        Connection conn = ds1.getCnx();
        PreparedStatement pst = conn.prepareStatement(req);
        rs = pst.executeQuery(req);


        if (rs.next()) return true;

        return false;

    }



    /******************************************SELECTION DE TT LES SPECTACLES******************************************/

    public ObservableList<SpectacleE> LoadSpectacleFromDatabase() {

        String req = "select * from spectacle ORDER BY id ASC";

        ObservableList<SpectacleE> table = FXCollections.observableArrayList();

        try {
            SingletonConnection ds1 = SingletonConnection.getInstance();
            ResultSet rs;
            Connection cnx;
            Connection conn = ds1.getCnx();
            PreparedStatement pst = conn.prepareStatement(req);
            rs = pst.executeQuery(req);

            while (rs.next()) {
                System.out.println("service load path image"+rs.getString("img"));
                Image image = new Image(rs.getString("img")); //creat img
                ImageView imgV = new ImageView(image);
                imgV.setFitHeight(80);
                imgV.setFitWidth(80);

                SpectacleE s = new SpectacleE(rs.getInt("id"),rs.getString("titre"),
                        rs.getDate("date"),rs.getString("genre"),rs.getInt("idUser"),
                        imgV);

                System.out.println("service load:: spectacle: "+s);
                table.add(s); //ajout au spectacle au tableau
            }
        } catch (SQLException ex) {
            System.out.println("probleme req ou cnx base de donnée");
            Logger.getLogger(serviceSpectacle.class.getName()).log(Level.SEVERE, null, ex);
        }
        //FXCollections.reverse(table);
        //System.out.println(table);

        return table;
    }

    public String selectRoleCurrentUser(Integer id) {
        //System.out.println("idE: "+idE+" | typeE: "+typeE);
        String req = "select role from user  WHERE id = " + id;

        //System.out.println("req: "+req);

        String role = new String();
        try {
            SingletonConnection ds1 = SingletonConnection.getInstance();
            Connection conn = ds1.getCnx();
            PreparedStatement pst = conn.prepareStatement(req);
            rs = pst.executeQuery(req);

            while (rs.next()) {
                //System.out.println("service loadImagePath:: image path: "+rs.getString("img"));
                role = rs.getString("role"); // stock valeur role fil chaine de caractere role
            }
        } catch (SQLException ex) {
            System.out.println("probleme req ou cnx base de donnée load image service");
            Logger.getLogger(serviceSpectacle.class.getName()).log(Level.SEVERE, null, ex);
        }
        return role;
    }



}
