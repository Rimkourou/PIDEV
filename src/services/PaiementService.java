package services;

import entites.Film;
import entites.Paiement;
import iServices.IServicePaiement;
import utils.SingletonConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class PaiementService implements IServicePaiement{
    private Statement ste;
    private PreparedStatement pst;
    private ResultSet rs;
    private Connection conn;

    public PaiementService() {

        conn = SingletonConnection.getInstance().getCnx();
    }

    @Override
    public void addPaiement(Paiement p) {
        String req = "insert into paiement (numCarte, code, date, codePostale) values (?,?,?,?)";
        try {
            pst = conn.prepareStatement(req);
            pst.setString(1, p.getNumCarte());
            pst.setInt(2, p.getCode());
            pst.setDate(3, (Date) p.getDate());
            pst.setString(4, p.getCodePostale());

            pst.executeUpdate();

        } catch (SQLException ex) {
            Logger.getLogger(PaiementService.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public void editPaiement(Paiement p) {
        String req = "update paiement SET numCarte= ?, code=?, date=?, codePostale=? where id=?";
        try {
            pst = conn.prepareStatement(req);
            pst.setString(1, p.getNumCarte());
            pst.setInt(2, p.getCode());
            pst.setDate(3, (Date) p.getDate());
            pst.setString(4, p.getCodePostale());
            pst.setInt(5, p.getId());

            pst.executeUpdate();

        } catch (SQLException ex) {
            Logger.getLogger(PaiementService.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public List paiementList() {
        String req = "select * from paiement";

        List<Paiement> list = new ArrayList<>();
        try {
            ste = conn.createStatement();
            rs = ste.executeQuery(req);
            while (rs.next()) {
                list.add(new Paiement(rs.getInt("id"),
                        rs.getString("numCarte"),
                        rs.getInt("code"),
                        rs.getDate("date"),
                        rs.getString("codePostale")
                ));
            }

        } catch (SQLException ex) {
            Logger.getLogger(PaiementService.class.getName()).log(Level.SEVERE, null, ex);
        }
        return list;
    }

    @Override
    public void deletePaiement(int id) {
        String sql = "delete from paiement where id=? ";
        try {
            pst = conn.prepareStatement(sql);
            pst.setInt(1, id);
            pst.executeUpdate();
            System.out.println("paiement deleted successfully");

        } catch (SQLException ex) {
            Logger.getLogger(PaiementService.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
