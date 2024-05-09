package learn.goodgames.data;

import learn.goodgames.models.ReservationAttendee;

import java.util.List;

public interface ReservationAttendeeRepository {
    List<ReservationAttendee> findByReservationId(int reservationId);
}
