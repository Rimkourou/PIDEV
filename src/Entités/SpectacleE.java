package Entités;

import java.util.Date;

public class SpectacleE {
    private int id;
    private String titre;
    private Date date;
    private String genre ;
    private int idUser;
    private int idSalle;


    public SpectacleE(int id, Date date, String genre, int idUser, int idSalle) {
    }

    public SpectacleE(String titre, Date date, String genre, int idUser, int idSalle) {
        this.titre = titre;
        this.date = date;
        this.genre = genre;
        this.idUser = idUser;
        this.idSalle =idSalle;
    }

    public SpectacleE(int id, String titre, Date date, String genre, int idUser, int idSalle) {
        this.id = id;
        this.titre = titre;
        this.date = date;
        this.genre = genre;
        this.idUser = idUser;
        this.idSalle = idSalle;
    }

    @Override
    public String toString() {
        return "SpectacleE{" +
                "id=" + id +
                ", titre='" + titre + '\'' +
                ", date=" + date +
                ", genre='" + genre + '\'' +
                ", idUser=" + idUser +
                ", idSalle=" + idSalle +
                '}';
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitre() {
        return titre;
    }

    public void setTitre(String titre) {
        this.titre = titre;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getGenre() {
        return genre;
    }

    public void setGenre(String genre) {
        this.genre = genre;
    }

    public int getIdUser() {
        return idUser;
    }

    public void setIdUser(int idUser) {
        this.idUser = idUser;
    }

    public int getIdSalle() {
        return idSalle;
    }

    public void setIdSalle(int idSalle) {
        this.idSalle = idSalle;
    }

}
