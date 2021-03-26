package service.hazem;

import entitie.hazem.share.PlanningFilm;
import entitie.hazem.share.PlanningSpectacle;
import entitie.hazem.SalleDeCinema;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import utils.SingletonConnection;

import java.sql.*;
import java.util.logging.Level;
import java.util.logging.Logger;

public class SaleDeCinemaService implements ISaleDeCinemaService{
    private Statement ste;
    private PreparedStatement pst;
    private PreparedStatement pst1;
    private ResultSet rs;
    private Connection conn;

    public SaleDeCinemaService() {

        conn = SingletonConnection.getInstance().getCnx();
    }

    @Override
    public void AddSalle(SalleDeCinema s) {
        String req = "insert into salledecinema (nom, nbrPlace, Description, adresse, idUser) values (?,?,?,?,?)";

        try {
            pst = conn.prepareStatement(req);
            pst.setString(1, s.getNom());
            pst.setInt(2, s.getNbrPlace());
            pst.setString(3, s.getDescription());
            pst.setString(4, s.getAdresse());
            pst.setInt(5, s.getIdUser());
            pst.executeUpdate();

        } catch (SQLException ex) {
            Logger.getLogger(SaleDeCinemaService.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public ObservableList<SalleDeCinema> salleDeCinemaListe() {
        String req = "select * from salledecinema";

        ObservableList<SalleDeCinema> list = FXCollections.observableArrayList()    ;
        try {
            ste = conn.createStatement();
            rs = ste.executeQuery(req);
            while (rs.next()) {
                list.add(new SalleDeCinema(rs.getInt("id"),
                        rs.getString("nom"),
                        rs.getInt("nbrPlace"),
                        rs.getString("Description"),
                        rs.getString("adresse"),
                        rs.getInt("idUser")
                ));
            }

        } catch (SQLException ex) {
            Logger.getLogger(SaleDeCinemaService.class.getName()).log(Level.SEVERE, null, ex);
        }
        return list;
    }

    @Override
    public ObservableList<String> salleDeCinemaListeName() {
        String req = "select * from salledecinema";

        ObservableList<String> list = FXCollections.observableArrayList()    ;
        try {
            ste = conn.createStatement();
            rs = ste.executeQuery(req);
            while (rs.next()) {
                list.add(rs.getString("nom"));
            }

        } catch (SQLException ex) {
            Logger.getLogger(SaleDeCinemaService.class.getName()).log(Level.SEVERE, null, ex);
        }
        return list;
    }

    @Override
    public void deleteSalleDeCinema(int idSalleDeCinema) {
        String sql = "delete from salledecinema where id=? ";
        try {
            pst = conn.prepareStatement(sql);
            pst.setInt(1, idSalleDeCinema);
            pst.executeUpdate();
            System.out.println("Record deleted successfully");

        } catch (SQLException ex) {
            Logger.getLogger(SaleDeCinemaService.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public void updateSalleDeCinema(SalleDeCinema s) {
        String req = "update salledecinema SET nom= ?, Description=?, adresse=?, nbrPlace=? where id=?";
        try {
            pst = conn.prepareStatement(req);
            pst.setString(1, s.getNom());
            pst.setString(2, s.getDescription());
            pst.setString(3, s.getAdresse());
            pst.setInt(4, s.getNbrPlace());
            pst.setInt(5, s.getId());
            pst.executeUpdate();
            System.out.println("update done");

        } catch (SQLException ex) {
            Logger.getLogger(SaleDeCinemaService.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public ObservableList<PlanningFilm> afficherPlanningFilm(int idSalleDeCinema) {
        /*
        planning film
                -> chercher liste film mta3 salle
                -> chercher liste planing ta3 film
        */

//        List<FilmTest> listeFN = new ArrayList<FilmTest>();
        ObservableList<PlanningFilm> pf = FXCollections.observableArrayList();
//        String req = "select * from film where idSalle=? ";
        String req = "select * from film INNER JOIN planning ON film.id = planning.idFilm WHERE film.idSalle = ? ";

        try {
            pst = conn.prepareStatement(req);
            pst.setInt(1, idSalleDeCinema);
            ResultSet rs = pst.executeQuery();
            while (rs.next()) {
                pf.add(new PlanningFilm(
                        rs.getString("titre"),
                        rs.getDate("date"),
                        rs.getTime("dateDebut"),
                        rs.getTime("dateFin")
                ));
            }




        } catch (SQLException ex) {
            Logger.getLogger(SaleDeCinemaService.class.getName()).log(Level.SEVERE, null, ex);

        }

        return pf;

    }

    @Override
    public ObservableList<PlanningSpectacle> afficherPlanningSpectacle(int idSalleDeCinema) {
        ObservableList<PlanningSpectacle> ps =FXCollections.observableArrayList();
        String req = "select * from spectacle INNER JOIN planning ON spectacle.id = planning.idSpectacle WHERE spectacle.idSalle = ? ";

        try {
            pst = conn.prepareStatement(req);
            pst.setInt(1, idSalleDeCinema);
            ResultSet rs = pst.executeQuery();
            while (rs.next()) {
                ps.add(new PlanningSpectacle(
                        rs.getString("titre"),
                        rs.getDate("date"),
                        rs.getTime("dateDebut"),
                        rs.getTime("dateFin")
                ));
            }

        } catch (SQLException ex) {
            Logger.getLogger(SaleDeCinemaService.class.getName()).log(Level.SEVERE, null, ex);

        }

        return ps;

    }

    @Override
    public ObservableList<SalleDeCinema> rechercherSalleByName(String name) {
        String req = "select * from salledecinema where nom like ?";
        ObservableList<SalleDeCinema> sc = FXCollections.observableArrayList();
        try {

            pst = conn.prepareStatement(req);
            pst.setString(1,"%" + name + "%");
            rs = pst.executeQuery();
            while (rs.next()) {
                sc.add(
                        new  SalleDeCinema(
                                rs.getInt("id"),
                                rs.getString("nom"),
                                rs.getInt("nbrPlace"),
                                rs.getString("Description"),
                                rs.getString("adresse"),
                                rs.getInt("idUser")
                        )
                );
            }

        } catch (SQLException ex) {
            Logger.getLogger(SaleDeCinemaService.class.getName()).log(Level.SEVERE, null, ex);
        }
        return sc;
    }

    @Override
    public SalleDeCinema getSalleById(int idSalle) {
        String req = "select * FROM salledecinema WHERE id=?";

        SalleDeCinema s = new SalleDeCinema();
        try {
            pst = conn.prepareStatement(req);
            pst.setInt(1, idSalle);
            rs = pst.executeQuery();
            while (rs.next()) {
                s.setId(rs.getInt("id"));
                s.setNom(rs.getString("nom"));
                s.setNbrPlace(Integer.parseInt(rs.getString("nbrPlace")));
                s.setDescription(rs.getString("Description"));
                s.setAdresse(rs.getString("adresse"));
                s.setIdUser(rs.getInt("idUser"));
            }

        } catch (SQLException ex) {
            Logger.getLogger(RecalamationService.class.getName()).log(Level.SEVERE, null, ex);
        }
        return s;
    }
}
