package Entités;

import java.util.Date;

public class Planning {
    private int id;
    private Date date;
    private Date dateDebut;
    private Date dateFin;
    private int idFilm;
    private int idSpectacle;

    public Planning() {
    }

    public Planning(Date date, Date dateDebut, Date dateFin, int idFilm, int idSpectacle) {
        this.date = date;
        this.dateDebut = dateDebut;
        this.dateFin = dateFin;
        this.idFilm = idFilm;
        this.idSpectacle = idSpectacle;
    }

    public Planning(int id, Date date, Date dateDebut, Date dateFin, int idFilm, int idSpectacle) {
        this.id = id;
        this.date = date;
        this.dateDebut = dateDebut;
        this.dateFin = dateFin;
        this.idFilm = idFilm;
        this.idSpectacle = idSpectacle;
    }


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
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

    @Override
    public String toString() {
        return "Planning{" +
                "id=" + id +
                ", date=" + date +
                ", dateDebut=" + dateDebut +
                ", dateFin=" + dateFin +
                ", idFilm=" + idFilm +
                ", idSpectacle=" + idSpectacle +
                '}';
    }
}





