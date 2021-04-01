package entitie.sana;


import javafx.scene.image.ImageView;

import java.sql.Date;
import java.sql.Time;

public class Planning {
    private int id;
    private String type;
    private String titreEvent;
    private ImageView imageEvent;
    private String nomSalle;
    private Date date;
    private Time heureDebut;
    private Time heureFin;
    public Planning() {
    }

    public Planning(String type, String titreEvent, ImageView img, String nomSalle, Date dte, Time hDeb, Time hFin) {
        this.type = type;
        this.titreEvent = titreEvent;
        this.imageEvent = img;
        this.nomSalle = nomSalle;
        this.date = dte;
        this.heureDebut = hDeb;
        this.heureFin = hFin;
    }

    public Planning(int id, String type, String titreEvent, ImageView img, String nomSalle, Date dte, Time hDeb, Time hFin) {
        this.id = id;
        this.type = type;
        this.titreEvent = titreEvent;
        this.imageEvent = img;
        this.nomSalle = nomSalle;
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

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getTitreEvent() {
        return titreEvent;
    }

    public void setTitreEvent(String titreEvent) {
        this.titreEvent = titreEvent;
    }

    public ImageView getImageEvent() {
        return imageEvent;
    }

    public void setImageEvent(ImageView imageEvent) {
        this.imageEvent = imageEvent;
    }

    public String getNomSalle() {
        return nomSalle;
    }

    public void setNomSalle(String nomSalle) {
        this.nomSalle = nomSalle;
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
                ", titre Event=" + titreEvent +
                ", image path= " + imageEvent.getImage().getUrl() +
                ", nomSalle=" + nomSalle +
                ", date=" + date +
                ", heureDebut=" + heureDebut +
                ", heureFin=" + heureFin +
                '}';
    }

    public Boolean ComparePlanning(Planning p1, Planning p2) {
        return p1.getType() == p2.getType() &&
                p1.getTitreEvent() == p2.getTitreEvent() &&
                p1.getImageEvent() == p2.getImageEvent() &&
                p1.getNomSalle() == p2.getNomSalle() &&
                p1.getDate() == p2.getDate() &&
                p1.getHeureDebut() == p2.getHeureDebut() &&
                p1.getHeureFin() == p2.getHeureFin();
    }
}
