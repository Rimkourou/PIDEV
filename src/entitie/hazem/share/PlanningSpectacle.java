package entitie.hazem.share;

import java.sql.Date;
import java.sql.Time;

public class PlanningSpectacle {
    private String nomSpectacle;
    private Date date;
    private Time heureD;
    private Time heureF;

    public PlanningSpectacle() {
    }

    public PlanningSpectacle(String nomSpectacle, Date date, Time heureD, Time heureF) {
        this.nomSpectacle = nomSpectacle;
        this.date = date;
        this.heureD = heureD;
        this.heureF = heureF;
    }

    public String getNomSpectacle() {
        return nomSpectacle;
    }

    public void setNomSpectacle(String nomSpectacle) {
        this.nomSpectacle = nomSpectacle;
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
        return "PlanningSpectacle{" +
                "nomSpectacle='" + nomSpectacle + '\'' +
                ", date=" + date +
                ", heureD=" + heureD +
                ", heureF=" + heureF +
                '}';
    }
}
