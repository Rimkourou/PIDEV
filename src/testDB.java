import entites.Film;
import entites.Reservation;
import services.FilmService;
import services.ReservationService;

import java.util.List;

public class testDB {
    public static void main(String[] args) {

        /**********testAddFilm************/
        Film f = new Film("test", "drama", "aut", "Documentaire", "Animation", 1, 3);
        Film f1 = new Film(1,"title", "des", "aut", "Documentaire", "Animation", 1, 1);
        FilmService fs= new FilmService();
        //fs.addFilm(f);
        /**********testEditFilm************/
        //fs.editFilm(f1);
        /**********testDeleteFilm************/
        //fs.deleteFilm(4);
        /**********testListFilm************/
        /*List<Film> filmList = fs.filmList();
        System.out.println("size : " + filmList.size());
        for (Film fl:filmList) {
            System.out.println(fl);
        }*/
        /**********testListFilm************/
        List<Film> filmSalleList = fs.filmSalleList();
        System.out.println("size : " + filmSalleList.size());
        for (Film fl:filmSalleList) {
            System.out.println(fl);
        }




        /**********testAddReservation************/
        /*Reservation r = new Reservation(02-06-2010,"validé", 1, 1, 1);
        Reservation r1 = new Reservation(1,02-06-2010,"non validé", 1, 2, 1);
        ReservationService rs = new ReservationService();
        rs.addReservation(r);*/
        /**********testEditReservation************/
        //rs.editReservation(r1);
        /**********testDeleteFilm************/
        //rs.deleteReservation(2);
        /**********testListReservation************/
        /*List<Reservation> reservationList = rs.reservationList();
        System.out.println("size : " + reservationList.size());
        for (Reservation rl:reservationList) {
            System.out.println(rl);
        }*/




        /**********testAddPaiement************/
        /*Paiement p = new Paiement("3333333",33, 02-06-2010,"Tunis 2051");
        Paiement p1 = new Paiement(1,"44444",44, 02-06-2010,"Tunis 2000");
        PaiementService ps = new PaiementService();
        ps.addPaiement(p);*/
        /**********testEditPaiement************/
        //ps.editPaiement(p1);
        /**********testDeletePaiement************/
        //ps.deletePaiement(2);
        /**********testListPaiement************/
        /*List<Paiement> paiementList = ps.paiementList();
        System.out.println("size : " + paiementList.size());
        for (Paiement pl:paiementList) {
            System.out.println(pl);
        }*/
    }
}
