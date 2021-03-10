package entites;


import java.sql.Timestamp;

public class Planning {
    private int id;
    private Timestamp date;
    private Timestamp dateDebut;
    private Timestamp dateFin;
    private int idFilm;
    private int idSpectacle;
    private int idSalle;

    public Planning() {
    }

    public Planning(Timestamp date, Timestamp dateDebut, Timestamp dateFin, int idFilm, int idSpectacle, int idSalle) {
        this.date = date;
        this.dateDebut = dateDebut;
        this.dateFin = dateFin;
        this.idFilm = idFilm;
        this.idSpectacle = idSpectacle;
        this.idSalle = idSalle;
    }

    public Planning(int id, Timestamp date, Timestamp dateDebut, Timestamp dateFin, int idFilm, int idSpectacle, int idSalle) {
        this.id = id;
        this.date = date;
        this.dateDebut = dateDebut;
        this.dateFin = dateFin;
        this.idFilm = idFilm;
        this.idSpectacle = idSpectacle;
        this.idSalle = idSalle;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Timestamp getDate() {
        return date;
    }

    public void setDate(Timestamp date) {
        this.date = date;
    }

    public Timestamp getDateDebut() {
        return dateDebut;
    }

    public void setDateDebut(Timestamp dateDebut) {
        this.dateDebut = dateDebut;
    }

    public Timestamp getDateFin() {
        return dateFin;
    }

    public void setDateFin(Timestamp dateFin) {
        this.dateFin = dateFin;
    }

    public int getIdFilm() {
        return idFilm;
    }

    public void setIdFilm(int idFilm) {
        this.idFilm = idFilm;
    }

    public int getIdSpectacle() {
        return idSpectacle;
    }

    public void setIdSpectacle(int idSpectacle) {
        this.idSpectacle = idSpectacle;
    }

    public int getIdSalle() {
        return idSalle;
    }

    public void setIdSalle(int idSalle) {
        this.idSalle = idSalle;
    }

    @Override
    public String toString() {
        return "Planning{" +
                "id=" + id +
                ", date=" + date +
                ", dateDebut=" + dateDebut +
                ", dateFin=" + dateFin +
                ", idFilm=" + idFilm +
                ", idSpectacle=" + idSpectacle +
                ", idSalle=" + idSalle +
                '}';
    }
}
