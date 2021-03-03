package entites;

public class Film {
    private int id;
    private String titre;
    private String description;
    private String auteur;
    private String categorie;
    private String genre;
    int idUser;
    int idSalle;

    public Film() {
    }

    public Film(String titre, String description, String auteur, String categorie, String genre, int idUser, int idSalle) {
        this.titre = titre;
        this.description = description;
        this.auteur = auteur;
        this.categorie = categorie;
        this.genre = genre;
        this.idUser = idUser;
        this.idSalle = idSalle;
    }

    public Film(int id, String titre, String description, String auteur, String categorie, String genre, int idUser, int idSalle) {
        this.id = id;
        this.titre = titre;
        this.description = description;
        this.auteur = auteur;
        this.categorie = categorie;
        this.genre = genre;
        this.idUser = idUser;
        this.idSalle = idSalle;
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

    public int getIdUser() {
        return idUser;
    }

    public int getIdSalle() {
        return idSalle;
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

    public void setIdUser(int idUser) {
        this.idUser = idUser;
    }

    public void setIdSalle(int idSalle) {
        this.idSalle = idSalle;
    }

    @Override
    public String toString() {
        return "Film{" + "id=" + id + ", titre=" + titre + ", description=" + description + ", auteur=" + auteur + ", categorie=" + categorie + ", genre=" + genre + ", idUser=" + idUser + ", idSalle=" + idSalle + '}';
    }
}
