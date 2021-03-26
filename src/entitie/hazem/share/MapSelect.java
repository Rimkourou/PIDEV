package entitie.hazem.share;

public class MapSelect {
    private int id;
    private String nom;
    private double lat;
    private double lon;

    public MapSelect() {
    }

    public MapSelect(int id, String nom, double lat, double lon) {
        this.id = id;
        this.nom = nom;
        this.lat = lat;
        this.lon = lon;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public double getLat() {
        return lat;
    }

    public void setLat(double lat) {
        this.lat = lat;
    }

    public double getLon() {
        return lon;
    }

    public void setLon(double lon) {
        this.lon = lon;
    }

    @Override
    public String toString() {
        return "MapSelect{" +
                "id=" + id +
                ", nom='" + nom + '\'' +
                ", lat=" + lat +
                ", lon=" + lon +
                '}';
    }
}
