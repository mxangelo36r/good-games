package learn.goodgames.data;

import learn.goodgames.models.Reservation;

import java.util.List;

public interface ReservationRepository {
    List<Reservation> findAllReservations();

    Reservation findReservationById(int reservationId);

    // List<Reservation> findReservationsByHostId(int hostId);

    // List<Reservation> findReservationsByDate(LocalDate date);

    // List<Reservation> findReservationsByState(String state);

    // List<Reservation> findReservationsByCity(String city);

    // List<Reservation> findReservationsByPostalCode(String postalCode);
}
