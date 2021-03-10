import java.text.ParseException;

public class testDB {

    public static void main(String[] args) throws ParseException {

        /**********testAddFilm************/

        /*Film f = new Film("tunis", "drama", "aut", "Documentaire", "Animation", 3);
        Film f1 = new Film(1,"testsuivie", "des", "mohamed", "Documentaire", "Animation", 1, 1);
        FilmService fs= new FilmService();
        List<Film> filmList = fs.filmList();
        fs.addFilm(f);*/
        /**********testEditFilm************/
        //fs.editFilm(f1);
        /**********testDeleteFilm************/
        //fs.deleteFilm(9);
        /**********testListFilm************/
        /*System.out.println("size : " + filmList.size());
        for (Film fl:filmList) {
            System.out.println(fl);
        }*/
        /**********testListFilm************/
        /*List<Film> filmSalleList = fs.filmSalleList();
        System.out.println("size : " + filmSalleList.size());
        for (Film fl:filmSalleList) {
            System.out.println(fl);
        }*/
        /**********testSearchFilmByTitle************/
        /*List<Film> list = fs.SearchFilmByTitle("test");
        for(Film film:list) {
            System.out.println(film.toString());
        }*/
        /**********testSearchFilmByAuthor************/
        /*List<Film> list = fs.SearchFilmByAuthor("mohamed");
        for(Film film:list) {
            System.out.println(film.toString());
        }*/




        /**********testAddReservation************/
        /*java.util.Date date_util = new java.util.Date();
        Reservation r = new Reservation( new java.sql.Date(date_util.getTime()),"validé", 2, 1, 1,1);
        Reservation r2 = new Reservation(1,new java.sql.Date(date_util.getTime())," non validé", 1, 2, 1,1);
        ReservationService rs = new ReservationService();
        List<Reservation> reservationList = rs.reservationList();
        rs.addReservation(r);*/
        /**********testEditReservation************/
        //rs.editReservation(r2);
        /**********testDeleteFilm************/
        //rs.deleteReservation(4);
        /**********testListReservation************/
        /*List<Reservation> reservationList = rs.reservationList();
        System.out.println("size : " + reservationList.size());
        for (Reservation rl:reservationList) {
            System.out.println(rl.toString());
        }*/
        /**********testListSalleReservation************/
        /*List<Reservation> reservationSalleList = rs.reservationSalleList();
        System.out.println("size : " + reservationSalleList.size());
        for (Reservation rl:reservationSalleList) {
            System.out.println(rl);
        }*/




        /**********testAddPaiement************/
        /*java.util.Date date_util = new java.util.Date();
        Paiement p = new Paiement("3333333",33, new java.sql.Date(date_util.getTime()),"Tunis 2051");
        Paiement p1 = new Paiement(1,"55555",44, new java.sql.Date(date_util.getTime()),"Tunis 2000");
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
