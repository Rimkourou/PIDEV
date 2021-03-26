package entitie.hazem;

public class SalleDeCinema {

    private int id;
    private String nom;
    private int nbrPlace;
    private String Description;
    private String adresse;
    private int idUser;

    public SalleDeCinema() {
    }

    public SalleDeCinema(int id, String nom, int nbrPlace, String description, String adresse, int idUser) {
        this.id = id;
        this.nom = nom;
        this.nbrPlace = nbrPlace;
        Description = description;
        this.adresse = adresse;
        this.idUser = idUser;
    }

    public SalleDeCinema(String nom, int nbrPlace, String description, String adresse, int idUser) {
        this.nom = nom;
        this.nbrPlace = nbrPlace;
        Description = description;
        this.adresse = adresse;
        this.idUser = idUser;
    }

    public SalleDeCinema(int id, String nom, int nbrPlace, String description, String adresse) {
        this.id = id;
        this.nom = nom;
        this.nbrPlace = nbrPlace;
        Description = description;
        this.adresse = adresse;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public int getNbrPlace() {
        return nbrPlace;
    }

    public void setNbrPlace(int nbrPlace) {
        this.nbrPlace = nbrPlace;
    }

    public String getDescription() {
        return Description;
    }

    public void setDescription(String description) {
        Description = description;
    }

    public String getAdresse() {
        return adresse;
    }

    public void setAdresse(String adresse) {
        this.adresse = adresse;
    }

    public int getIdUser() {
        return idUser;
    }

    public void setIdUser(int idUser) {
        this.idUser = idUser;
    }

    @Override
    public String toString() {
        return "SalleDeCinema{" +
                "id=" + id +
                ", nom='" + nom + '\'' +
                ", nbrPlace=" + nbrPlace +
                ", Description='" + Description + '\'' +
                ", adresse='" + adresse + '\'' +
                ", idUser=" + idUser +
                '}';
    }
}
