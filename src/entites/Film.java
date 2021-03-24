package entites;

import javafx.scene.control.Button;

import java.sql.Blob;

public class Film {
    private int id;
    private String titre;
    private String description;
    private String auteur;
    private String categorie;
    private String genre;
    private String img;

    public Film() {
    }

    public Film(String titre, String description, String auteur, String categorie, String genre, String img) {
        this.titre = titre;
        this.description = description;
        this.auteur = auteur;
        this.categorie = categorie;
        this.genre = genre;
        this.img=img;
    }

    public Film(int id, String titre, String description, String auteur, String categorie, String genre, String img) {
        this.id = id;
        this.titre = titre;
        this.description = description;
        this.auteur = auteur;
        this.categorie = categorie;
        this.genre = genre;
        this.img=img;
    }

    public Film(int id, String titre, String description, String genre) {
        this.id = id;
        this.titre = titre;
        this.description = description;
        this.genre = genre;
    }

    public int getId() {
        return id;
    }

    public String getTitre() {
        return titre;
    }

    public String getDescription() {
        return description;
    }

    public String getAuteur() {
        return auteur;
    }

    public String getCategorie() {
        return categorie;
    }

    public String getGenre() {
        return genre;
    }


    public void setId(int id) {
        this.id = id;
    }

    public void setTitre(String titre) {
        this.titre = titre;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setAuteur(String auteur) {
        this.auteur = auteur;
    }

    public void setCategorie(String categorie) {
        this.categorie = categorie;
    }

    public void setGenre(String genre) {
        this.genre = genre;
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }

    @Override
    public String toString() {
        return "Film{" +
                "id=" + id +
                ", titre='" + titre + '\'' +
                ", description='" + description + '\'' +
                ", auteur='" + auteur + '\'' +
                ", categorie='" + categorie + '\'' +
                ", genre='" + genre + '\'' +
                ", img='" + img + '\'' +
                '}';
    }
}
