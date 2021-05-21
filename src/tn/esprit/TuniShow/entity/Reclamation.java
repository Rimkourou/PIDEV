package tn.esprit.TuniShow.entity;

public class Reclamation {
    private int id;
    private String objet;
    private String description;
    private String state;
    private int idSalle;
    private String salleName;
    private int userId;

    public Reclamation() {
    }

//    public Reclamation(String objet, String description, String state, int idSalle) {
//        this.objet = objet;
//        this.description = description;
//        this.state = state;
//        this.idSalle = idSalle;
//    }

    public Reclamation(String objet, String description, String state, int idSalle, int userId) {
        this.objet = objet;
        this.description = description;
        this.state = state;
        this.idSalle = idSalle;
        this.userId = userId;
    }

//    public Reclamation(int id, String objet, String description, String state, int idSalle) {
//        this.id = id;
//        this.objet = objet;
//        this.description = description;
//        this.state = state;
//        this.idSalle = idSalle;
//    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getObjet() {
        return objet;
    }

    public void setObjet(String objet) {
        this.objet = objet;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getIdSalle() {
        return idSalle;
    }

    public void setIdSalle(int idSalle) {
        this.idSalle = idSalle;
    }

    public String getSalleName() {
        return salleName;
    }

    public void setSalleName(String salleName) {
        this.salleName = salleName;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    @Override
    public String toString() {
        return "Reclamation{" +
                "id=" + id +
                ", objet='" + objet + '\'' +
                ", description='" + description + '\'' +
                ", idSalle=" + idSalle +
                '}';
    }
}
