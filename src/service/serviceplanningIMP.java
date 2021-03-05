package service;

import Entités.Planning;
import connectionBD.SingletonConnection;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;


public class serviceplanningIMP implements servicePlanning{

    private ResultSet rs;
    private Statement ste;
    public Statement st;
    public Connection cnx;

    public serviceplanningIMP() {
    }
                                //AJjouterPlanning

    @Override
    public void AddPlanning(Planning p) {
        String req = "INSERT INTO planning (id, date, dateDebut, dateFin, idFilm, idSpectacle) VALUES (?,?,?,?,?,?)";
        try {
            SingletonConnection ds1 = SingletonConnection.getInstance();
            Connection conn = ds1.getCnx();
            PreparedStatement pl =conn.prepareStatement(req);

            pl.setInt(1, p.getId());
            pl.setDate(2,new java.sql.Date(p.getDate().getTime()));
            pl.setTime(3,new java.sql.Time(p.getDateDebut().getTime()));
            pl.setTime(4,new java.sql.Time(p.getDateFin().getTime()));
            pl.setInt(5, p.getIdFilm());
            pl.setInt(6, p.getIdSpectacle());


            pl.executeUpdate();
            pl.close();

        } catch (SQLException ex) {
            Logger.getLogger(servicePlanning.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

                                //Mise à jour
    @Override
    public void updatePlanning(Planning p) {

        String req = "UPDATE planning SET date=?, dateDebut=?, dateFin=?, idFilm=? , idSpectacle=? where id= ?";

        try {

            SingletonConnection ds1 = SingletonConnection.getInstance();
            Connection conn = ds1.getCnx();
            PreparedStatement pl = conn.prepareStatement(req);


            pl.setDate(1, new java.sql.Date(p.getDate().getTime()));
            pl.setTime(2, new java.sql.Time(p.getDateDebut().getTime()));
            pl.setTime(3, new java.sql.Time(p.getDateFin().getTime()));
            pl.setInt(4, p.getIdFilm());
            pl.setInt(5, p.getIdSpectacle());
            pl.setInt(6, p.getId());


            pl.executeUpdate();
            pl.close();


        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }


    }
                                            //ListerPlanningConsole
   @Override
    public List<Planning> planningList() {

       String req = "select * from planning";

       List<Planning> list = new ArrayList<>();


       try {
           SingletonConnection ds1 = SingletonConnection.getInstance();
           Connection conn = ds1.getCnx();
           PreparedStatement pst =conn.prepareStatement(req);
           rs = pst.executeQuery(req);

           while (rs.next()) {

               list.add(new Planning(rs.getInt("id"),
                       rs.getDate("date"),rs.getTime("dateDebut"),rs.getTime("dateFin"),
                       rs.getInt("idFilm"),rs.getInt("idSpectacle")

               ));
           }

       } catch (SQLException ex) {
           Logger.getLogger(servicePlanning.class.getName()).log(Level.SEVERE, null, ex);
       }
       return list;
    }
                                    //Suppression de planning
    public void deletePlanning(int id) throws SQLException {

        String req = "DELETE FROM planning WHERE id=?";

        SingletonConnection ds1 = SingletonConnection.getInstance();
        Connection conn = ds1.getCnx();
        PreparedStatement pst =conn.prepareStatement(req);

        pst.setInt(1, id);

        pst.executeUpdate();
    }


}
