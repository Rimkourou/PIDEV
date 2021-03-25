package Entités;

import javafx.scene.image.ImageView;

import java.sql.Time;
import java.util.Date;

public class Planning {
    private int id;
    private String type;
    private Integer idEvent;
    private ImageView imageEvent;
    private int idSalle;
    private Date date;
    private Time heureDebut;
    private Time heureFin;

    public Planning() {
    }

    public Planning(String type, Integer idEvent, ImageView img, int idS, Date dte, Time hDeb, Time hFin) {
        this.type = type;
        this.idEvent = idEvent;
        this.imageEvent = img;
        this.idSalle = idS;
        this.date = dte;
        this.heureDebut = hDeb;
        this.heureFin = hFin;
    }

    public Planning(int id, String type, Integer idEvent, ImageView img, int idS, Date dte, Time hDeb, Time hFin) {
        this.id = id;
        this.type = type;
        this.idEvent = idEvent;
        this.imageEvent = img;
        this.idSalle = idS;
        this.date = dte;
        this.heureDebut = hDeb;
        this.heureFin = hFin;
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

    public Time getheureDebut() {
        return heureDebut;
    }

    public void setheureDebut(Time heureDebut) {
        this.heureDebut = heureDebut;
    }

    public Time getheureFin() {
        return heureFin;
    }

    public void setheureFin(Time heureFin) {
        this.heureFin = heureFin;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Integer getIdEvent() {
        return idEvent;
    }

    public void setIdEvent(Integer idEvent) {
        this.idEvent = idEvent;
    }

    public ImageView getImageEvent() {
        return imageEvent;
    }

    public void setImageEvent(ImageView imageEvent) {
        this.imageEvent = imageEvent;
    }

    public int getIdSalle() {
        return idSalle;
    }

    public void setIdSalle(int idSalle) {
        this.idSalle = idSalle;
    }

    public Time getHeureDebut() {
        return heureDebut;
    }

    public void setHeureDebut(Time heureDebut) {
        this.heureDebut = heureDebut;
    }

    public Time getHeureFin() {
        return heureFin;
    }

    public void setHeureFin(Time heureFin) {
        this.heureFin = heureFin;
    }

    @Override
    public String toString() {
        return "Planning{" +
                "id=" + id +
                ", type=" + type +
                ", idEvent=" + idEvent +
                ", image path='"+ imageEvent.getImage().getUrl().substring(6)+ "'" +
                ", idSalle=" + idSalle +
                ", date=" + date +
                ", heureDebut=" + heureDebut +
                ", heureFin=" + heureFin +
                '}';
    }
}


