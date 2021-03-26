package entitie.hazem.share;

import java.sql.Date;
import java.sql.Time;

public class PlanningFilm {

    private String nomFilm;
    private Date date;
    private Time heureD;
    private Time heureF;

    public PlanningFilm() {
    }

    public PlanningFilm(String nomFilm, Date date, Time heureD, Time heureF) {
        this.nomFilm = nomFilm;
        this.date = date;
        this.heureD = heureD;
        this.heureF = heureF;
    }



    public String getNomFilm() {
        return nomFilm;
    }

    public void setNomFilm(String nomFilm) {
        this.nomFilm = nomFilm;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public Time getHeureD() {
        return heureD;
    }

    public void setHeureD(Time heureD) {
        this.heureD = heureD;
    }

    public Time getHeureF() {
        return heureF;
    }

    public void setHeureF(Time heureF) {
        this.heureF = heureF;
    }

    @Override
    public String toString() {
        return "PlanningFilm{" +
                "nomFilm='" + nomFilm + '\'' +
                ", date=" + date +
                ", heureD=" + heureD +
                ", heureF=" + heureF +
                '}';
    }
}
