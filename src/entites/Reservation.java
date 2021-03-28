package entites;

import java.util.Date;

public class Reservation {
    private int id;
    private Date dateReservation;
    int idUser;
    String idSalle;
    String idFilm;
    private int nbrPlaceRes;
    String idSpectacle;

    public Reservation() {
    }

    public Reservation(Date dateReservation, int idUser, String idSalle, String idFilm, int nbrPlaceRes, String idSpectacle) {
        this.dateReservation = dateReservation;
        this.idUser = idUser;
        this.idSalle = idSalle;
        this.idFilm=idFilm;
        this.nbrPlaceRes = nbrPlaceRes;
        this.idSpectacle = idSpectacle;
    }

    public Reservation(int id, Date dateReservation, int idUser, String idSalle, String idFilm, int nbrPlaceRes, String idSpectacle) {
        this.id = id;
        this.dateReservation = dateReservation;
        this.idUser = idUser;
        this.idSalle = idSalle;
        this.idFilm=idFilm;
        this.nbrPlaceRes = nbrPlaceRes;
        this.idSpectacle = idSpectacle;
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

    public String getIdSalle() {
        return idSalle;
    }

    public void setIdSalle(String idSalle) {
        this.idSalle = idSalle;
    }

    public int getNbrPlaceRes() {
        return nbrPlaceRes;
    }

    public void setNbrPlaceRes(int nbrPlaceRes) {
        this.nbrPlaceRes = nbrPlaceRes;
    }

    public String getIdFilm() {
        return idFilm;
    }

    public void setIdFilm(String idFilm) {
        this.idFilm = idFilm;
    }

    public String getIdSpectacle() {
        return idSpectacle;
    }

    public void setIdSpectacle(String idSpectacle) {
        this.idSpectacle = idSpectacle;
    }

    @Override
    public String toString() {
        return "Reservation{" +
                "id=" + id +
                ", dateReservation=" + dateReservation +
                ", idUser=" + idUser +
                ", idSalle='" + idSalle + '\'' +
                ", idFilm='" + idFilm + '\'' +
                ", nbrPlaceRes=" + nbrPlaceRes +
                ", idSpectacle='" + idSpectacle + '\'' +
                '}';
    }
}
