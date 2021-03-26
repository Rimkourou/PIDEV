package service.hazem;

import entitie.hazem.share.MapSelect;
import utils.SingletonConnection;

import java.sql.*;
import java.util.logging.Level;
import java.util.logging.Logger;

public class MapService implements IMapService{
    private Statement ste;
    private PreparedStatement pst;
    private ResultSet rs;

    private Connection conn;

    public MapService() {
        conn = SingletonConnection.getInstance().getCnx();

    }

    @Override
    public MapSelect salleInfoByName(String name) {
        String req = "select * FROM mapposition WHERE name LIKE ?";

        MapSelect r = new MapSelect();
        try {
            pst = conn.prepareStatement(req);
            pst.setString(1, "%" + name + "%");
            rs = pst.executeQuery();
            while (rs.next()) {
                r.setId(rs.getInt("id"));
                r.setNom(rs.getString("name"));
                r.setLat(rs.getDouble("lat"));
                r.setLon(rs.getDouble("lon"));
            }

        } catch (SQLException ex) {
            Logger.getLogger(RecalamationService.class.getName()).log(Level.SEVERE, null, ex);
        }
        return r;
    }
}
