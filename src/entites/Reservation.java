package entites;

import java.util.Date;

public class Reservation {
    private int id;
    private Date dateReservation;
    private String validation;
    int idUser;
    int idFacture;
    int idP;
    int idSalle;

    public Reservation() {
    }

    public Reservation(Date dateReservation, String validation, int idUser, int idFacture, int idP, int idSalle) {
        this.dateReservation = dateReservation;
        this.validation = validation;
        this.idUser = idUser;
        this.idFacture = idFacture;
        this.idP = idP;
        this.idSalle = idSalle;
    }

    public Reservation(int id, Date dateReservation, String validation, int idUser, int idFacture, int idP, int idSalle) {
        this.id = id;
        this.dateReservation = dateReservation;
        this.validation = validation;
        this.idUser = idUser;
        this.idFacture = idFacture;
        this.idP = idP;
        this.idSalle = idSalle;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Date getDateReservation() {
        return dateReservation;
    }

    public void setDateReservation(Date dateReservation) {
        this.dateReservation = dateReservation;
    }

    public String getValidation() {
        return validation;
    }

    public void setValidation(String validation) {
        this.validation = validation;
    }

    public int getIdUser() {
        return idUser;
    }

    public void setIdUser(int idUser) {
        this.idUser = idUser;
    }

    public int getIdFacture() {
        return idFacture;
    }

    public void setIdFacture(int idFacture) {
        this.idFacture = idFacture;
    }

    public int getIdP() {
        return idP;
    }

    public void setIdP(int idP) {
        this.idP = idP;
    }

    public int getIdSalle() {
        return idSalle;
    }

    public void setIdSalle(int idSalle) {
        this.idSalle = idSalle;
    }


}
