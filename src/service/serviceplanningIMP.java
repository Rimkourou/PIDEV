package service;

import Entités.Planning;
import Entités.SpectacleE;
import connectionBD.SingletonConnection;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.io.File;
import java.sql.*;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;


public class serviceplanningIMP implements servicePlanning {

    private ResultSet rs;
    private ResultSet rs1;
    private Statement ste;
    public Statement st;
    public Connection cnx;

    public serviceplanningIMP() {
    }


    //AJjouterPlanning
    @Override
    public void AddPlanning(Planning p) {
        String req = "INSERT INTO `planning`(`id`, `date`, `heureDebut`, `heureFin`, `idFilm`, `idSpectacle`, `idSalle`) VALUES (?,?,?,?,?,?,?)";

        try {
            SingletonConnection ds1 = SingletonConnection.getInstance();
            Connection conn = ds1.getCnx();
            PreparedStatement pl = conn.prepareStatement(req);

            pl.setInt(1, p.getId());
            pl.setDate(2, new java.sql.Date(p.getDate().getTime()));
            pl.setTime(3, new java.sql.Time(p.getheureDebut().getTime()));
            pl.setTime(4, new java.sql.Time(p.getheureFin().getTime()));
            if (p.getType() == "Film") {
                pl.setInt(5, p.getIdEvent());
                pl.setInt(6, -1);
            } else {
                pl.setInt(5, -1);
                pl.setInt(6, p.getIdEvent());
            }
            pl.setInt(7, p.getIdSalle());
            pl.executeUpdate();
            pl.close();

        } catch (SQLException ex) {
            Logger.getLogger(servicePlanning.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    //Suppression de planning
    @Override
    public void deletePlanning(int id) {

        String req = "DELETE FROM planning WHERE id=?";
        try {
            SingletonConnection ds1 = SingletonConnection.getInstance();
            Connection conn = ds1.getCnx();
            PreparedStatement pst = conn.prepareStatement(req);


            pst.setInt(1, id);

            pst.executeUpdate();
            System.out.println("film deleted successfully");

        } catch (SQLException ex) {
            Logger.getLogger(servicePlanning.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    //Mise à jour
    @Override
    public void updatePlanning(Planning p) {

        //String req = "UPDATE planning SET date=?, dateDebut=?, dateFin=?, idFilm=? , idSpectacle=? where id= ?";
        String req1 = "UPDATE `planning` SET `id`=?,`date`=?,`heureDebut`=?," +
                "`heureFin`=?,`idFilm`=?,`idSpectacle`=?,`idSalle`=? WHERE id= ?";

        try {

            SingletonConnection ds1 = SingletonConnection.getInstance();
            Connection conn = ds1.getCnx();
            PreparedStatement pl = conn.prepareStatement(req1);

            pl.setInt(1, p.getId());
            pl.setDate(2, new java.sql.Date(p.getDate().getTime()));
            pl.setTime(3, new java.sql.Time(p.getheureDebut().getTime()));
            pl.setTime(4, new java.sql.Time(p.getheureFin().getTime()));
            if (p.getType() == "Film") {
                pl.setInt(5, p.getIdEvent());
                pl.setInt(6, -1);
            } else {
                pl.setInt(5, -1);
                pl.setInt(6, p.getIdEvent());
            }
            pl.setInt(7, p.getIdSalle());
            pl.setInt(8, p.getId());
            pl.executeUpdate();
            pl.close();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    @Override
    public ObservableList<Planning> LoadPlanningFromDatabase() {
        String req1 = "SELECT * FROM `planning` WHERE 1 ORDER BY id ASC";

        ObservableList<Planning> table = FXCollections.observableArrayList();

        SingletonConnection ds12 = SingletonConnection.getInstance();
        Connection conn = ds12.getCnx();

        try {

            PreparedStatement pst1 = conn.prepareStatement(req1);
            rs1 = pst1.executeQuery(req1);

            while (rs1.next()) {
                Integer idE = rs1.getInt("idFilm");
                String typeE = "film";
                if (idE == -1) {
                    idE = rs1.getInt("idSpectacle");
                    typeE = "spectacle";
                }

                ImageView imgV = new ImageView();
                File file = new File(loadImageEventFromDB(idE, typeE));
                Image image = new Image(file.toURI().toString());
                imgV.setImage(image);
                imgV.setFitHeight(80);
                imgV.setFitWidth(80);


                //System.out.println(imgV.getImage().getUrl());
                Planning p = new Planning(rs1.getInt("id"), typeE, idE, imgV,
                        rs1.getInt("idSalle"), rs1.getDate("date"),
                        rs1.getTime("heureDebut"), rs1.getTime("heureFin"));
                p.setImageEvent(imgV);
                table.add(p);
            }
        } catch (SQLException ex) {
            System.out.println("probleme req ou cnx base de donnée service load plannings");
            Logger.getLogger(serviceSpectacle.class.getName()).log(Level.SEVERE, null, ex);
        }
        return table;
    }

    //Selectionner planings from db
    @Override
    public String loadImageEventFromDB(Integer idE, String typeE) {
        //System.out.println("idE: "+idE+" | typeE: "+typeE);
        String req = "select img from " + typeE + " as e WHERE e.id = " + idE;

        //System.out.println("req: "+req);

        String imgP = new String();
        try {
            SingletonConnection ds1 = SingletonConnection.getInstance();
            Connection conn = ds1.getCnx();
            PreparedStatement pst = conn.prepareStatement(req);
            rs = pst.executeQuery(req);

            while (rs.next()) {
                //System.out.println("service loadImagePath:: image path: "+rs.getString("img"));
                imgP = rs.getString("img");
                ;
            }
        } catch (SQLException ex) {
            System.out.println("probleme req ou cnx base de donnée load image service");
            Logger.getLogger(serviceSpectacle.class.getName()).log(Level.SEVERE, null, ex);
        }
        return imgP;
    }

    @Override
    public ArrayList<Integer> loadIdEventSalleFromDB(String tableName) {
        ArrayList<Integer> list = new ArrayList<Integer>();

        String req = "SELECT id FROM " + tableName;


        try {
            SingletonConnection ds1 = SingletonConnection.getInstance();
            Connection conn = ds1.getCnx();
            PreparedStatement pst = conn.prepareStatement(req);
            rs = pst.executeQuery(req);

            while (rs.next()) {
                list.add(rs.getInt("id"));
            }

        } catch (SQLException ex) {
            System.out.println("probleme req ou cnx base de donnée");
            Logger.getLogger(serviceSpectacle.class.getName()).log(Level.SEVERE, null, ex);
        }
        //System.out.println("list id"+list);

        return list;

    }

    public boolean dHSReserve(Planning pln) throws SQLException {
        String rq = "select id from planning as p " +
                "WHERE p.date= '" + pln.getDate() + "'"
                + " AND (p.heureDebut BETWEEN '" + pln.getheureDebut() + "' AND '" + pln.getheureFin() + "'"
                + " OR p.heureFin BETWEEN '" + pln.getheureDebut() + "' AND '" + pln.getheureFin() + "') "
                + "AND p.idSalle= " + pln.getIdSalle();

        String req = "select id from planning as p" +
                " WHERE p.date= '" + pln.getDate() + "'" +
                " AND (p.heureDebut BETWEEN  '" + pln.getheureDebut() + "' AND '" + pln.getheureFin() + "' " +
                " OR p.heureFin BETWEEN '" + pln.getheureDebut() + "' AND '" + pln.getheureFin() + "') " +
                " AND p.idSalle= " + pln.getIdSalle();
        SingletonConnection ds1 = SingletonConnection.getInstance();
        Connection conn = ds1.getCnx();
        PreparedStatement pst = conn.prepareStatement(req);
        rs = pst.executeQuery(req);
        if (rs.next()) return true;

        return false;
    }

    @Override
    public ObservableList<Planning> recherchePlanning(String critereRech, String valRech) {
        if (valRech == null) {
            System.out.println("val rech : chaine vide");
        } else {
            System.out.println("val rech : " + valRech);
        }
        String req1 = "";
        switch (critereRech) {
            case "ID":
                critereRech = "id";
                break;
            case "ID Salle":
                critereRech = "idSalle";
                break;
        }

        if (critereRech == "id" || critereRech == "idSalle") {

            if (valRech.trim().isEmpty()) {
                req1 = "select * from `planning` ORDER BY id ASC";
            } else {
                int schfl = 0;
                try {
                    schfl = Integer.parseInt(valRech.trim());
                    req1 = "SELECT * FROM `planning` WHERE `" + critereRech + "` LIKE '" + schfl + "%' ORDER BY id ASC";
                } catch (Exception e) {
                    req1 = "select * from `planning` ORDER BY id ASC";
                    System.out.println("service recherche rapide planning:: probleme conversion string vers entier");
                }
            }
        } else if (critereRech == "Type") {
            if (valRech.trim().isEmpty()) {
                System.out.println("ici");
                req1 = "SELECT * FROM `planning` WHERE 1=1 ORDER BY id ASC";
            } else if (valRech != "") {
                if ("film".startsWith(valRech.toLowerCase())) {
                    req1 = "SELECT * FROM `planning` WHERE `idFilm` <> -1 ORDER BY id ASC";
                } else if ("spectacle".startsWith(valRech.toLowerCase())) {
                    req1 = "SELECT * FROM `planning` WHERE `idSpectacle` <> -1 ORDER BY id ASC";
                }
                /*
                else if("film".startsWith(valRech.toLowerCase())){
                    req1 = "SELECT * FROM `planning` WHERE `idFilm` <> -1 ORDER BY id ASC";
                }

                 */
            }
        } else if (critereRech == "ID Event") {
            req1 = "SELECT * FROM `planning` " +
                    "WHERE `idSpectacle` LIKE '" + valRech.toUpperCase() + "%' " +
                    "OR `idFilm` LIKE '" + valRech.toUpperCase() + "%' " +
                    "ORDER BY id ASC";
        }

        ObservableList<Planning> table = FXCollections.observableArrayList();

        try {
            SingletonConnection ds1 = SingletonConnection.getInstance();
            ResultSet rsRech;
            Connection conn = ds1.getCnx();
            PreparedStatement pst = conn.prepareStatement(req1);
            rsRech = pst.executeQuery(req1);

            while (rsRech.next()) {

                Integer idE = rsRech.getInt("idFilm");
                String typeE = "film";
                if (idE == -1) {
                    idE = rsRech.getInt("idSpectacle");
                    typeE = "spectacle";
                }

                ImageView imgV = new ImageView();
                File file = new File(loadImageEventFromDB(idE, typeE));
                Image image = new Image(file.toURI().toString());
                imgV.setImage(image);
                imgV.setFitHeight(80);
                imgV.setFitWidth(80);


                //System.out.println(imgV.getImage().getUrl());
                Planning p = new Planning(rsRech.getInt("id"), typeE, idE, imgV,
                        rsRech.getInt("idSalle"), rsRech.getDate("date"),
                        rsRech.getTime("heureDebut"), rsRech.getTime("heureFin"));
                p.setImageEvent(imgV);
                table.add(p);
            }
        } catch (SQLException ex) {
            System.out.println("probleme req ou cnx base de donnée");
            Logger.getLogger(servicePlanning.class.getName()).log(Level.SEVERE, null, ex);
        }
        //FXCollections.reverse(table);
        System.out.println(table);

        return table;
    }

    @Override
    public ObservableList<Planning> recherchePlanning(String critereRech, Date dteDeb, Date dteFin) {
        String req = "";
        System.out.println("sdte deb "+dteDeb);
        System.out.println("sdte fin "+dteFin);
        if (dteDeb == null && dteFin == null) {
            req = "select * from planning ORDER BY id ASC";
        } else if (dteDeb != null && dteFin == null) {
            req = "SELECT * FROM `planning` WHERE `" + critereRech + "` >= '" + dteDeb + "' ORDER BY id ASC";
        } else if (dteDeb == null && dteFin != null) {
            req = "SELECT * FROM `planning` WHERE `" + critereRech + "` <= '" + dteFin + "' ORDER BY id ASC";
        } else {
            req = "SELECT * FROM `planning` WHERE `" + critereRech + "` BETWEEN '" + dteDeb + "'AND'" + dteFin + "' ORDER BY id ASC";
        }

        ObservableList<Planning> table = FXCollections.observableArrayList();
        try {
            SingletonConnection ds1 = SingletonConnection.getInstance();
            ResultSet rsRech;
            Connection conn = ds1.getCnx();
            PreparedStatement pst = conn.prepareStatement(req);
            rsRech = pst.executeQuery(req);

            while (rsRech.next()) {

                Integer idE = rsRech.getInt("idFilm");
                String typeE = "film";
                if (idE == -1) {
                    idE = rsRech.getInt("idSpectacle");
                    typeE = "spectacle";
                }

                ImageView imgV = new ImageView();
                File file = new File(loadImageEventFromDB(idE, typeE));
                Image image = new Image(file.toURI().toString());
                imgV.setImage(image);
                imgV.setFitHeight(80);
                imgV.setFitWidth(80);


                //System.out.println(imgV.getImage().getUrl());
                Planning p = new Planning(rsRech.getInt("id"), typeE, idE, imgV,
                        rsRech.getInt("idSalle"), rsRech.getDate("date"),
                        rsRech.getTime("heureDebut"), rsRech.getTime("heureFin"));
                p.setImageEvent(imgV);
                table.add(p);

            }
        } catch (SQLException ex) {
            System.out.println("probleme req ou cnx base de donnée");
            Logger.getLogger(serviceSpectacle.class.getName()).log(Level.SEVERE, null, ex);
        }
        return table;
    }

    @Override
    public String selectRoleCurrentUser(Integer id){
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
                role = rs.getString("role");
            }
        } catch (SQLException ex) {
            System.out.println("probleme req ou cnx base de donnée load image service");
            Logger.getLogger(serviceSpectacle.class.getName()).log(Level.SEVERE, null, ex);
        }
        return role;
    }
}


