package tn.esprit.TuniShow.entity;

//import java.sql.Date;
import java.util.Comparator;
import java.util.Date;

public class
Reservation {
    private int id;
    private Date datedereservation;
    private String iduser;
    private String idsalle;
    private String idfilm;
    private int nbrplaceres;

    public Reservation() {
    }

    public Reservation(int id, Date datedereservation, String iduser, String idsalle, String idfilm, int nbrplaceres) {
        this.id = id;
        this.datedereservation = datedereservation;
        this.iduser = iduser;
        this.idsalle = idsalle;
        this.idfilm = idfilm;
        this.nbrplaceres = nbrplaceres;
    }

    public Reservation(Date datedereservation, String iduser, String idsalle, String idfilm, int nbrplaceres) {
        this.datedereservation = datedereservation;
        this.iduser = iduser;
        this.idsalle = idsalle;
        this.idfilm = idfilm;
        this.nbrplaceres = nbrplaceres;
    }

    public Reservation(String toString, String toString1, String toString2, int parseInt) {

        this.iduser = iduser;
        this.idsalle = idsalle;
        this.idfilm = idfilm;
        this.nbrplaceres = nbrplaceres;
    }


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Date getDatedereservation() {
        return datedereservation;
    }

    public void setDatedereservation(Date datedereservation) {
        this.datedereservation = datedereservation;
    }

    public String getIduser() {
        return iduser;
    }

    public void setIduser(String iduser) {
        this.iduser = iduser;
    }

    public String getIdsalle() {
        return idsalle;
    }

    public void setIdsalle(String idsalle) {
        this.idsalle = idsalle;
    }

    public String getIdfilm() {
        return idfilm;
    }

    public void setIdfilm(String idfilm) {
        this.idfilm = idfilm;
    }

    public int getNbrplaceres() {
        return nbrplaceres;
    }

    public void setNbrplaceres(int nbrplaceres) {
        this.nbrplaceres = nbrplaceres;
    }

    @Override
    public String toString() {
        return "Reservation{" +
                "id=" + id +
                ", datedereservation=" + datedereservation +
                ", iduser=" + iduser +
                ", idsalle='" + idsalle + '\'' +
                ", idfilm='" + idfilm + '\'' +
                ", nbrplaceres=" + nbrplaceres +
                '}';
    }

    public static Comparator<Reservation> MovieNameComparator = new Comparator<Reservation>() {
        @Override
        public int compare(Reservation r1, Reservation r2) {
            return (int) (r1.getIdfilm().toLowerCase().compareTo(r2.getIdfilm().toLowerCase()));
        }
    };
}
