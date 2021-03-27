package entites;

import javafx.scene.image.ImageView;

import java.time.LocalDate;
import java.util.Date;

public class SpectacleE {
    private int id;
    private String titre;
    private Date date;
    private String genre ;
    private ImageView image;
    private int idUser;


    public SpectacleE() {
    }

    public SpectacleE(String titre, Date date, String genre,  int idUser, ImageView image) {
        this.titre = titre;
        this.date = date;
        this.genre = genre;
        this.image = image;
        this.idUser = idUser;
    }

    public SpectacleE(int id, String titre, Date date, String genre, int idUser, ImageView image) {
        this.id = id;
        this.titre = titre;
        this.date = date;
        this.genre = genre;
        this.image = image;
        this.idUser = idUser;
    }

    @Override
    public String toString() {
        return "SpectacleE{" +
                "id=" + id +
                " image path='"+ image.getImage().getUrl().substring(6)+ "'" +
                ", titre='" + titre + '\'' +
                ", date=" + date +
                ", genre='" + genre + '\'' +
                ", idUser=" + idUser +
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

    public ImageView getImage() {
        return image;
    }

    public void setImage(ImageView image) {
        this.image = image;
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



}
