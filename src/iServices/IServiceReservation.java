package iServices;


import entites.Reservation;

import java.util.List;

public interface IServiceReservation {
    void addReservation (Reservation r);
    void editReservation(Reservation r);
    List<Reservation> reservationList();
    void deleteReservation(int idR);
    List<Reservation> reservationSalleList();
}
