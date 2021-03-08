package iServices;


import entites.Reservation;
import javafx.collections.ObservableList;

import java.util.List;

public interface IServiceReservation {
    void addReservation (Reservation r);
    void editReservation(Reservation r);
    ObservableList<Reservation> reservationList();
    void deleteReservation(int idR);
    List<Reservation> reservationSalleList();
}
