package service;

import Entités.SpectacleE;
import com.mysql.jdbc.ConnectionImpl;
import connectionBD.SingletonConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;


public class servicespectacleIMP implements serviceSpectacle {

    private ResultSet rs;
    private Statement ste;
    public Statement st;
    public Connection cnx;

    public servicespectacleIMP(){

    }



    //ajout

    @Override
    public void AddSpectacle(SpectacleE s) {

        String req = "INSERT INTO spectacle(id, titre, date, genre, idUser, idSalle) VALUES (?,?,?,?,?,?)";


        try {
            SingletonConnection ds1 = SingletonConnection.getInstance();
            Connection conn = ds1.getCnx();
            PreparedStatement pst =conn.prepareStatement(req);

            pst.setInt(1, s.getId());
            pst.setString(2, s.getTitre());
            pst.setDate(3,new java.sql.Date(s.getDate().getTime()));
            pst.setInt(4, 1);
            pst.setInt(5, s.getIdUser());
            pst.setInt(6, s.getIdSalle());

            pst.executeUpdate();
            pst.close();

        } catch (SQLException ex) {
            Logger.getLogger(serviceSpectacle.class.getName()).log(Level.SEVERE, null, ex);
        }
    }


                        //afficher - Rechercher

    @Override

    public  List<SpectacleE> spectacleEList(){
        String req = "select * from spectacle";

        List<SpectacleE> list = new ArrayList<>();
        try {
            SingletonConnection ds1 = SingletonConnection.getInstance();
            Connection conn = ds1.getCnx();
            PreparedStatement pst =conn.prepareStatement(req);
            rs = pst.executeQuery(req);

            while (rs.next()) {
                list.add(new SpectacleE(rs.getInt("id"),
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


    public void deleteSpectacle(int id) throws SQLException {

        String req = "DELETE FROM spectacle WHERE id=?";

        SingletonConnection ds1 = SingletonConnection.getInstance();
        Connection conn = ds1.getCnx();
        PreparedStatement pst =conn.prepareStatement(req);

        pst.setInt(1, id);

        pst.executeUpdate();


    }

    @Override
    public void updateSpectacle(SpectacleE s) {

        String req = "UPDATE spectacle SET titre=?, date=?, genre=?, idUser=? , idSalle=? where id= ?";

        try {

            SingletonConnection ds1 = SingletonConnection.getInstance();
            Connection conn = ds1.getCnx();
            PreparedStatement pst =conn.prepareStatement(req);

            pst.setString(1, s.getTitre());
            pst.setDate(2,new java.sql.Date(s.getDate().getTime()));
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



    }

