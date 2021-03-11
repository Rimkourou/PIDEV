package service;

import Entités.SpectacleE;
import connectionBD.SingletonConnection;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/*******************************Connection avec la BD et implementation des methodes aupres de service**************************************/

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

        String req = "INSERT INTO spectacle(id, titre, date, genre, idUser, idSalle) VALUES (?,?,?,?,?,?)";


        try {
            SingletonConnection ds1 = SingletonConnection.getInstance();
            Connection conn = ds1.getCnx();
            PreparedStatement pst = conn.prepareStatement(req);

                  pst.setInt(1, s.getId());
                  pst.setString(2, s.getTitre());
                  pst.setDate(3, new java.sql.Date(s.getDate().getTime()));
                  pst.setString(4, s.getGenre());
                  pst.setInt(5, s.getIdUser());
                  pst.setInt(6, s.getIdSalle());

            pst.executeUpdate();
            pst.close();

        } catch (SQLException ex) {
            Logger.getLogger(serviceSpectacle.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
/******************************************Lister liste des spectacle dans la console******************************************/
/*
    @Override
    public List<SpectacleE> spectacleEList() {

             String req = "select * from spectacle";

        List<SpectacleE> list = new ArrayList<>();

        try {
            SingletonConnection ds1 = SingletonConnection.getInstance();
            Connection conn = ds1.getCnx();
            PreparedStatement pst = conn.prepareStatement(req);
            rs = pst.executeQuery(req);

            while (rs.next()) {
                list.add(new SpectacleE(
                        rs.getString("titre"),
                        rs.getDate("date"),
                        rs.getString("genre"),
                        rs.getInt("idUser"),
                        rs.getInt("idSalle")
                ));
            }

        } catch (SQLException ex) {
            Logger.getLogger(serviceSpectacle.class.getName()).log(Level.SEVERE, null, ex);
        }
        return list;
    }
    */

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

        String req = "UPDATE spectacle SET titre=?, date=?, genre=?, idUser=? , idSalle=? where id= ?";

        try {

            SingletonConnection ds1 = SingletonConnection.getInstance();
            Connection conn = ds1.getCnx();
            PreparedStatement pst = conn.prepareStatement(req);

                  pst.setString(1, s.getTitre());
                  pst.setDate(2, new java.sql.Date(s.getDate().getTime()));
                  pst.setInt(3, 1);
                  pst.setInt(4, s.getIdUser());
                  pst.setInt(5, s.getIdSalle());
                  pst.setInt(6, s.getId());

                  System.out.println(s.getId());

            pst.executeUpdate();
            pst.close();


        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    /***********************Ajouter methode recherche par titre ************************/

    public boolean spectacleExist(String t) throws SQLException {

        String req = "SELECT `titre` FROM `spectacle` WHERE `titre` = '" + t.toUpperCase() + "'";
        SingletonConnection ds1 = SingletonConnection.getInstance();
        Connection conn = ds1.getCnx();
        PreparedStatement pst = conn.prepareStatement(req);
        rs = pst.executeQuery(req);


        if (rs.next()) return true;

        return false;

        //close connection

    }


/******************************************SELECTION DE TT LES SPECTACLES******************************************/

   public ObservableList<SpectacleE> LoadSpectacleFromDatabase() {

        String req = "select * from spectacle";

        ObservableList<SpectacleE> table = FXCollections.observableArrayList();

        try {
            SingletonConnection ds1 = SingletonConnection.getInstance();
            Connection conn = ds1.getCnx();
            PreparedStatement pst = conn.prepareStatement(req);
            rs = pst.executeQuery(req);

            while (rs.next()) {
                table.add(new SpectacleE(
                        rs.getInt("id"),
                        rs.getString("titre"),
                        rs.getDate("date"),
                        rs.getString("genre"),
                        rs.getInt("idUser"),
                        rs.getInt("idSalle")));
            }
        } catch (SQLException ex) {
            System.out.println("probleme req ou cnx base de donnée");
            Logger.getLogger(serviceSpectacle.class.getName()).log(Level.SEVERE, null, ex);
        }
       FXCollections.reverse(table);
       //System.out.println(table);

       return table;
    }

    /**************Selection des id des salles existants combobox******************/

    public ArrayList<Integer> LoadSalleSpecFromDatabase() {

        ArrayList<Integer> salleSpectacle = new ArrayList<Integer>();

        String req = "select id from salledecinema";


        try {
            SingletonConnection ds1 = SingletonConnection.getInstance();
            Connection conn = ds1.getCnx();
            PreparedStatement pst = conn.prepareStatement(req);
            rs = pst.executeQuery(req);

            while (rs.next()) {
                salleSpectacle.add(rs.getInt("id"));
            }

        } catch (SQLException ex) {
            System.out.println("probleme req ou cnx base de donnée");
            Logger.getLogger(serviceSpectacle.class.getName()).log(Level.SEVERE, null, ex);
        }
        //System.out.println(salleSpectacle);

        return salleSpectacle;
    }



}


