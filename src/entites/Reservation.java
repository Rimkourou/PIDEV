package entites;

import java.util.Date;

public class Reservation {
    private int id;
    private Date dateReservation;
    int idUser;
    int idSalle;
    int idFilm;
    private int nbrPlaceRes;
    SalleDeCinema s;
    Film f;

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

    public Reservation(int id, Date dateReservation, int idSalle, int idFilm, int nbrPlaceRes) {
        this.id = id;
        this.dateReservation = dateReservation;
        this.idSalle = idSalle;
        this.idFilm = idFilm;
        this.nbrPlaceRes = nbrPlaceRes;
    }

    public Reservation(int id, Date dateReservation, int idUser, int nbrPlaceRes, String s1, String f1) {
        this.id = id;
        this.dateReservation = dateReservation;
        this.idUser = idUser;
        this.nbrPlaceRes = nbrPlaceRes;
        s1 = s.getNom();
        f1 = f.getTitre();
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
