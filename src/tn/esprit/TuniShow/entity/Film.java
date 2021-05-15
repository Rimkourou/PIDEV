/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tn.esprit.TuniShow.entity;

import java.util.Comparator;

/**
 *
 * @author msi
 */
public class Film {
    private int id;
    private String titre;
    private String description;
    private String auteur;
    private String categorie;
    private String genre;
    private String img;

    public Film(int id, String titre, String description, String auteur, String categorie, String genre, String img) {
        this.id = id;
        this.titre = titre;
        this.description = description;
        this.auteur = auteur;
        this.categorie = categorie;
        this.genre = genre;
        this.img = img;
    }

    public Film(String titre, String description, String auteur, String categorie, String genre, String img) {
        this.titre = titre;
        this.description = description;
        this.auteur = auteur;
        this.categorie = categorie;
        this.genre = genre;
        this.img = img;
    }

    public Film() {
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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getAuteur() {
        return auteur;
    }

    public void setAuteur(String auteur) {
        this.auteur = auteur;
    }

    public String getCategorie() {
        return categorie;
    }

    public void setCategorie(String categorie) {
        this.categorie = categorie;
    }

    public String getGenre() {
        return genre;
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
        return "Film{" + "id=" + id + ", titre=" + titre + ", description=" + description + ", auteur=" + auteur + ", categorie=" + categorie + ", genre=" + genre + ", img=" + img + '}';
    }

    public static Comparator<Film> titleComparator = new Comparator<Film>() {
        @Override
        public int compare(Film f1, Film f2) {
            return (int) (f1.getTitre().toLowerCase().compareTo(f2.getTitre().toLowerCase()));
        }
    };
}
