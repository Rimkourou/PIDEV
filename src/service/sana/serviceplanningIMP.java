package service.sana;


import entitie.sana.Planning;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import service.sana.servicePlanning;
import utils.SingletonConnection;

import java.io.File;
import java.sql.*;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

public class serviceplanningIMP implements servicePlanning {

    public serviceplanningIMP() {
    }


                                  /*************************************AJjouter Planning*******************************/
    @Override
    public void AddPlanning(Planning p) {
        String reqAdd = "INSERT INTO `planning`(`id`, `date`, `heureDebut`, `heureFin`, `idFilm`, `idSpectacle`, `idSalle`) VALUES (?,?,?,?,?,?,?)";

        try {
            SingletonConnection ds1 = SingletonConnection.getInstance();
            Connection conn = ds1.getCnx();
            PreparedStatement plAdd = conn.prepareStatement(reqAdd);

            plAdd.setInt(1, p.getId());
            plAdd.setDate(2, new Date(p.getDate().getTime()));
            plAdd.setTime(3, new Time(p.getHeureDebut().getTime()));
            plAdd.setTime(4, new Time(p.getHeureFin().getTime()));

            if (p.getType() == "Film") {
                plAdd.setInt(5, loadIdEventSalleFromDbByTitre(p.getType(), p.getTitreEvent()));
                plAdd.setInt(6, -1); // 3lech haka ????
            } else {
                plAdd.setInt(5, -1); // sinon ken spectacle
                plAdd.setInt(6, loadIdEventSalleFromDbByTitre(p.getType(), p.getTitreEvent()));
            }
            plAdd.setInt(7, loadIdEventSalleFromDbByTitre("salledecinema", p.getNomSalle()));
            plAdd.executeUpdate();
            plAdd.close();

        } catch (SQLException ex) {
            Logger.getLogger(servicePlanning.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
                    /************************************Suppression de planning************************************************/

    @Override
    public void deletePlanning(int id) {

        String req = "DELETE FROM planning WHERE id=?";
        try {
            SingletonConnection ds1 = SingletonConnection.getInstance();
            Connection conn = ds1.getCnx();
            PreparedStatement pst = conn.prepareStatement(req);


            pst.setInt(1, id);
            pst.executeUpdate();
            System.out.println("Planning deleted successfully");

        } catch (SQLException ex) {
            Logger.getLogger(servicePlanning.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

                /************************************Mise a jour ************************************************/
    @Override
    public void updatePlanning(Planning p) {
        String reqEdit = "UPDATE `planning` SET `id`=?,`date`=?,`heureDebut`=?," +
                "`heureFin`=?,`idFilm`=?,`idSpectacle`=?,`idSalle`=? WHERE id= ?";

        try {

            SingletonConnection ds1 = SingletonConnection.getInstance();
            Connection conn = ds1.getCnx();
            PreparedStatement plEdit = conn.prepareStatement(reqEdit);

            plEdit.setInt(1, p.getId());
            plEdit.setDate(2, new Date(p.getDate().getTime()));
            plEdit.setTime(3, new Time(p.getHeureDebut().getTime()));
            plEdit.setTime(4, new Time(p.getHeureFin().getTime()));

            Integer idE = loadIdEventSalleFromDbByTitre(p.getType(), p.getTitreEvent()); //3lech ???
            System.out.println("idE: " + idE);

            if (p.getType() == "Film") {
                plEdit.setInt(5, idE);
                plEdit.setInt(6, -1);
            } else {
                plEdit.setInt(5, -1);
                plEdit.setInt(6, idE);
            }

            Integer idS = loadIdEventSalleFromDbByTitre("salledecinema", p.getNomSalle());
            System.out.println("idS: " + idS);
            plEdit.setInt(7, idS);
            plEdit.setInt(8, p.getId());

            plEdit.executeUpdate();
            plEdit.close();

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

            /***********************************************************************************/

    @Override
    public ObservableList<Planning> LoadPlanningFromDatabase() {
        String req1Load = "SELECT * FROM `planning` WHERE 1 ORDER BY id ASC";

        ObservableList<Planning> table = FXCollections.observableArrayList();

        SingletonConnection ds12 = SingletonConnection.getInstance();
        Connection conn = ds12.getCnx();

        try {

            PreparedStatement pstLoad = conn.prepareStatement(req1Load);
            ResultSet rsLoad = pstLoad.executeQuery(req1Load);

            while (rsLoad.next()) {
                Integer idE = rsLoad.getInt("idFilm");
                String typeE = "Film";
                if (idE == -1) {
                    idE = rsLoad.getInt("idSpectacle");
                    typeE = "Spectacle";
                }


                File file = new File(loadImageEventFromDB(idE, typeE));
                Image image = new Image(file.toURI().toString());
                ImageView imgV = new ImageView(image);
                imgV.setFitHeight(80);
                imgV.setFitWidth(80);

                System.out.println(imgV.getImage().getUrl());

                String titreE = loadTitreEventSalleFromDbById(typeE, idE);
                String nomSalle = loadTitreEventSalleFromDbById("salledecinema", rsLoad.getInt("idSalle"));

                Planning p = new Planning(rsLoad.getInt("id"), typeE, titreE, imgV, nomSalle,
                        rsLoad.getDate("date"), rsLoad.getTime("heureDebut"),
                        rsLoad.getTime("heureFin"));
                table.add(p);
            }
        } catch (SQLException ex) {
            System.out.println("probleme req ou cnx base de donnée service load plannings");
            Logger.getLogger(servicePlanning.class.getName()).log(Level.SEVERE, null, ex);
        }
        return table;
    }

                /************************************Recupérer image from DB************************************************/
    @Override
    public String loadImageEventFromDB(Integer idE, String typeE) {
        //System.out.println("idE: "+idE+" | typeE: "+typeE);
        String reqImg = "select img from " + typeE + " as e WHERE e.id = " + idE;

        //System.out.println("req: "+req);

        String imgP = new String();
        try {
            SingletonConnection ds1 = SingletonConnection.getInstance();
            Connection conn = ds1.getCnx();
            PreparedStatement pst = conn.prepareStatement(reqImg);
            ResultSet rsImg = pst.executeQuery(reqImg);

            while (rsImg.next()) {
                //System.out.println("service loadImagePath:: image path: "+rs.getString("img"));
                imgP = rsImg.getString("img");

            }
        } catch (SQLException ex) {
            System.out.println("probleme req ou cnx base de donnée load image service");
            Logger.getLogger(servicePlanning.class.getName()).log(Level.SEVERE, null, ex);
        }
        return imgP;
    }

          /************cette req permet de selectionner designation des spectacles, films, salle cinema*********/

    @Override
    public ArrayList<String> loadTitreEventSalleFromDB(String tableName) {
        ArrayList<String> list = new ArrayList<String>();
        // puisque libéllé du col = nom ds table sallecinema et = titre ds film/spectacle on a crrer var col
        //????
        String col = tableName == "salledecinema" ? "nom" : "titre";

        String req = "SELECT " + col + " FROM " + tableName.toLowerCase();

        try {
            SingletonConnection ds1 = SingletonConnection.getInstance();
            ResultSet rs;
            Connection cnx;
            Connection conn = ds1.getCnx();
            PreparedStatement pst = conn.prepareStatement(req);
            rs = pst.executeQuery(req);

            while (rs.next()) {
                list.add(rs.getString(col));
            }

        } catch (SQLException ex) {
            System.out.println("probleme req ou cnx base de donnée");
            Logger.getLogger(servicePlanning.class.getName()).log(Level.SEVERE, null, ex);
        }
        //System.out.println("list id"+list);
        return list;
    }

                    /*********cette req permet de selectionner designation des spectacles, films, salle cinema***********/

    @Override
    public Integer loadIdEventSalleFromDbByTitre(String tableName, String titre) {
        Integer id = null;

        // puisque libéllé du col = nom ds table sallecinema et = titre ds film/spectacle on a crrer var col
        String col = tableName == "salledecinema" ? "nom" : "titre";


        String reqId = "SELECT id FROM " + tableName.toLowerCase() + " as t WHERE t." + col + " = '" + titre + "' ";

        System.out.println(reqId);

        try {
            SingletonConnection ds1 = SingletonConnection.getInstance();
            Connection conn = ds1.getCnx();
            PreparedStatement pstId = conn.prepareStatement(reqId);
            ResultSet rsId = pstId.executeQuery(reqId);

            while (rsId.next()) {
                id = rsId.getInt("id");
            }

        } catch (SQLException ex) {
            System.out.println("probleme req ou cnx base de donnée");
            Logger.getLogger(servicePlanning.class.getName()).log(Level.SEVERE, null, ex);
        }
        System.out.println("id saslle/event: "+id);
        return id;

    }

                //cette req permet de selectionner designation des spectacles, films, salle cinema // chbiha tet3awed ???
    @Override
    public String loadTitreEventSalleFromDbById(String tableName, Integer id) {
        String titre = "";
        // puisque libéllé du col = nom ds table sallecinema et = titre ds film/spectacle on a crrer var col
        String col = tableName == "salledecinema" ? "nom" : "titre";

        String reqId = "SELECT " + col + " FROM " + tableName + " as t WHERE t.id = " + id;

        try {
            SingletonConnection ds1 = SingletonConnection.getInstance();

            Connection conn = ds1.getCnx();
            PreparedStatement pst = conn.prepareStatement(reqId);
            ResultSet rsId = pst.executeQuery(reqId);

            while (rsId.next()) {
                titre = rsId.getString(col);
            }

        } catch (SQLException ex) {
            System.out.println("probleme req ou cnx base de donnée");
            Logger.getLogger(servicePlanning.class.getName()).log(Level.SEVERE, null, ex);
        }

        return titre;

    }
        /***************Reservation*********************/
    public boolean dHSReserve(Planning pln) throws SQLException {
        System.out.println("salle1: "+loadIdEventSalleFromDbByTitre(pln.getType(), pln.getNomSalle()));
        String reqRes = "select id from planning as p" +
                " WHERE p.date= '" + pln.getDate() + "'" +
                " AND (p.heureDebut BETWEEN  '" + pln.getHeureDebut() + "' AND '" + pln.getHeureFin() + "' " +
                " OR p.heureFin BETWEEN '" + pln.getHeureDebut() + "' AND '" + pln.getHeureFin() + "') " +
                " AND p.idSalle= " + loadIdEventSalleFromDbByTitre("salledecinema", pln.getNomSalle());
        System.out.println("reqRes: "+reqRes);
        System.out.println("salle2: "+loadIdEventSalleFromDbByTitre("salledecinema", pln.getNomSalle()));


        SingletonConnection ds1 = SingletonConnection.getInstance();
        Connection conn = ds1.getCnx();
        PreparedStatement pst = conn.prepareStatement(reqRes);
        ResultSet rsRes = pst.executeQuery(reqRes);
        if (rsRes.next()) return true;

        return false;
    }
                /********************Methode de recherche***********************/
    @Override
    public ObservableList<Planning> recherchePlanning(String critereRech, String valRech) {

        String req1 = "";
        String req2 = "";
        switch (critereRech) {
            case "ID":
                critereRech = "id";
                break;
        }

        if (critereRech == "id") {
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

        } else if (critereRech == "Type Event") {
            if (valRech.trim().isEmpty()) {
                System.out.println("ici");
                req1 = "SELECT * FROM `planning` WHERE 1=1 ORDER BY id ASC";
            } else if (valRech != "") {
                if ("film".startsWith(valRech.toLowerCase())) {
                    req1 = "SELECT * FROM `planning` WHERE idFilm <> -1 ORDER BY id ASC";
                } else if ("spectacle".startsWith(valRech.toLowerCase())) {
                    req1 = "SELECT * FROM `planning` WHERE idSpectacle <> -1 ORDER BY id ASC";
                }
            }
        } else if (critereRech == "Titre Event") {
            if (valRech.trim().isEmpty()) {
                req1 = "SELECT * FROM `planning` WHERE 1=1 ORDER BY id ASC";
            } else if (valRech != "") {
                req1 = "SELECT * FROM `planning`, spectacle as s, film as f" +
                        "WHERE  p.idSpectacle = s.id AND s.titre LIKE '" + valRech.toUpperCase() + "%' " +
                        "ORDER BY p.id ASC";
                req2 = "SELECT * FROM `planning`, film as f" +
                        "WHERE p.idFilm = f.id AND f.titre LIKE '" + valRech.toUpperCase() + "%' " +
                        "ORDER BY p.id ASC";
                System.out.println(req1);
            }
        } else if (critereRech == "Nom Salle") {
            if (valRech.trim().isEmpty()) {
                req1 = "SELECT * FROM `planning` WHERE 1=1 ORDER BY id ASC";
            } else if (valRech != "") {
                req1 = "SELECT * FROM `planning` as p, `salledecinema` as sc " +
                        "WHERE p.idSalle = sc.id " +
                        "AND sc.nom LIKE '" + valRech.toUpperCase() + "%' " +
                        "ORDER BY p.id ASC";
            }
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
                String typeE = "Film";
                if (idE == -1) {
                    idE = rsRech.getInt("idSpectacle");
                    typeE = "Spectacle";
                }

                ImageView imgV = new ImageView();
                File file = new File(loadImageEventFromDB(idE, typeE));
                Image image = new Image(file.toURI().toString());
                imgV.setImage(image);
                imgV.setFitHeight(80);
                imgV.setFitWidth(80);

                String titreE = loadTitreEventSalleFromDbById(typeE, idE);
                String nomSalle = loadTitreEventSalleFromDbById("salledecinema", rsRech.getInt("idSalle"));

                Planning p = new Planning(rsRech.getInt("id"), typeE, titreE, imgV, nomSalle,
                        rsRech.getDate("date"), rsRech.getTime("heureDebut"),
                        rsRech.getTime("heureFin"));
                table.add(p);
            }
        } catch (SQLException ex) {
            System.out.println("probleme req ou cnx base de donnée");
            Logger.getLogger(servicePlanning.class.getName()).log(Level.SEVERE, null, ex);
        }
        System.out.println(table);


        return table;
    }

                             /********************Methode de recherche Par DATE***********************/

    @Override
    public ObservableList<Planning> recherchePlanning(String critereRech, Date dteDeb, Date dteFin) {
        ObservableList<Planning> table = FXCollections.observableArrayList();

        String req = "";
        System.out.println("sdte deb " + dteDeb);
        System.out.println("sdte fin " + dteFin);
        if (dteDeb == null && dteFin == null) {
            req = "select * from planning ORDER BY id ASC";
        } else if (dteDeb != null && dteFin == null) {
            req = "SELECT * FROM `planning` WHERE `" + critereRech + "` >= '" + dteDeb + "' ORDER BY id ASC";
        } else if (dteDeb == null && dteFin != null) {
            req = "SELECT * FROM `planning` WHERE `" + critereRech + "` <= '" + dteFin + "' ORDER BY id ASC";
        } else {
            req = "SELECT * FROM `planning` WHERE `" + critereRech + "` BETWEEN '" + dteDeb + "'AND'" + dteFin + "' ORDER BY id ASC";
        }


        try {
            SingletonConnection ds1 = SingletonConnection.getInstance();
            ResultSet rsRech;
            Connection conn = ds1.getCnx();
            PreparedStatement pst = conn.prepareStatement(req);
            rsRech = pst.executeQuery(req);

            while (rsRech.next()) {

                Integer idE = rsRech.getInt("idFilm");
                String typeE = "Film";
                if (idE == -1) {
                    idE = rsRech.getInt("idSpectacle");
                    typeE = "Spectacle";
                }

                ImageView imgV = new ImageView();
                File file = new File(loadImageEventFromDB(idE, typeE));
                Image image = new Image(file.toURI().toString());
                imgV.setImage(image);
                imgV.setFitHeight(80);
                imgV.setFitWidth(80);

                String titreE = loadTitreEventSalleFromDbById(typeE, idE);
                String nomSalle = loadTitreEventSalleFromDbById("salledecinema", rsRech.getInt("idSalle"));

                //System.out.println(imgV.getImage().getUrl());
                Planning p = new Planning(rsRech.getInt("id"), typeE, titreE, imgV, nomSalle,
                        rsRech.getDate("date"), rsRech.getTime("heureDebut"),
                        rsRech.getTime("heureFin"));
                table.add(p);

            }
        } catch (SQLException ex) {
            System.out.println("probleme req ou cnx base de donnée");
            Logger.getLogger(servicePlanning.class.getName()).log(Level.SEVERE, null, ex);
        }
        return table;
    }

    @Override
    public String selectRoleCurrentUser(Integer id) {
        //System.out.println("idE: "+idE+" | typeE: "+typeE);
        String reqRole = "select role from user  WHERE id = " + id;

        //System.out.println("req: "+req);

        String role = new String();
        try {
            SingletonConnection ds1 = SingletonConnection.getInstance();
            Connection conn = ds1.getCnx();
            PreparedStatement pst = conn.prepareStatement(reqRole);
            ResultSet rsRole = pst.executeQuery(reqRole);

            while (rsRole.next()) {
                //System.out.println("service loadImagePath:: image path: "+rs.getString("img"));
                role = rsRole.getString("role");
            }
        } catch (SQLException ex) {
            System.out.println("probleme req ou cnx base de donnée load image service");
            Logger.getLogger(servicePlanning.class.getName()).log(Level.SEVERE, null, ex);
        }
        return role;
    }
}
