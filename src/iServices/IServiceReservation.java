package iServices;


import entites.Reservation;
import javafx.collections.ObservableList;

import java.sql.SQLException;
import java.util.List;

public interface IServiceReservation {
    void addReservation (Reservation r) throws SQLException;
    void editReservation(Reservation r);
    ObservableList<Reservation> reservationList() throws SQLException;
    void deleteReservation(Reservation r);
    //List<Reservation> reservationSalleList();
}
