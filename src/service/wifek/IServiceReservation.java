package service.wifek;


import entitie.wifek.Reservation;
import javafx.collections.ObservableList;

import java.sql.SQLException;

public interface IServiceReservation {
    void addReservation (Reservation r) throws SQLException;
    void editReservation(Reservation r);
    ObservableList<Reservation> reservationList() throws SQLException;
    void deleteReservation(Reservation r);
    //List<Reservation> reservationSalleList();
}
