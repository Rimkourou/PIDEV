package Entités;

import java.util.Date;

public class Planning {
    private int id;
    private int idFilm;
    private int idSpectacle;
    private Date dateDebut;
    private Date dateFin;
    private Date date;


    public Planning() {
    }

    public Planning(int id, int idFilm, int idSpectacle, Date dateDebut, Date dateFin, Date date) {
        this.id = id;
        this.idFilm = idFilm;
        this.idSpectacle = idSpectacle;
        this.dateDebut = dateDebut;
        this.dateFin = dateFin;
        this.date = date;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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

    public Date getDateDebut() {
        return dateDebut;
    }

    public void setDateDebut(Date dateDebut) {
        this.dateDebut = dateDebut;
    }

    public Date getDateFin() {
        return dateFin;
    }

    public void setDateFin(Date dateFin) {
        this.dateFin = dateFin;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    @Override
    public String toString() {
        return "Planning{" +
                "id=" + id +
                ", idFilm=" + idFilm +
                ", idSpectacle=" + idSpectacle +
                ", dateDebut=" + dateDebut +
                ", dateFin=" + dateFin +
                ", date=" + date +
                '}';
    }
}


