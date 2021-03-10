package entites;

import java.util.Date;

public class Reservation {
    private int id;
    private Date dateReservation;
    int idUser;
    int idSalle;
    int idFilm;
    private int nbrPlaceRes;

    public Reservation() {
    }

    public Reservation(Date dateReservation, int idUser, int idSalle, int idFilm, int nbrPlaceRes) {
        this.dateReservation = dateReservation;
        this.idUser = idUser;
        this.idSalle = idSalle;
        this.idFilm=idFilm;
        this.nbrPlaceRes = nbrPlaceRes;
    }

    public Reservation(int id, Date dateReservation, int idUser, int idSalle, int idFilm, int nbrPlaceRes) {
        this.id = id;
        this.dateReservation = dateReservation;
        this.idUser = idUser;
        this.idSalle = idSalle;
        this.idFilm=idFilm;
        this.nbrPlaceRes = nbrPlaceRes;
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

    public int getNbrPlaceRes() {
        return nbrPlaceRes;
    }

    public void setNbrPlaceRes(int nbrPlaceRes) {
        this.nbrPlaceRes = nbrPlaceRes;
    }

    public int getIdFilm() {
        return idFilm;
    }

    public void setIdFilm(int idFilm) {
        this.idFilm = idFilm;
    }

    @Override
    public String toString() {
        return "Reservation{" +
                "id=" + id +
                ", dateReservation=" + dateReservation +
                ", idUser=" + idUser +
                ", idSalle=" + idSalle +
                ", idFilm=" + idFilm +
                ", nbrPlaceRes=" + nbrPlaceRes +
                '}';
    }
}
