package learn.goodgames.models;

public class ReservationAttendee {
    private int reservationAttendeeId;
    private int reservationId;
    private int userId;

    public ReservationAttendee() {
    }

    public ReservationAttendee(int reservationAttendeeId, int reservationId, int userId) {
        this.reservationAttendeeId = reservationAttendeeId;
        this.reservationId = reservationId;
        this.userId = userId;
    }

    public int getReservationAttendeeId() {
        return reservationAttendeeId;
    }

    public void setReservationAttendeeId(int reservationAttendeeId) {
        this.reservationAttendeeId = reservationAttendeeId;
    }

    public int getReservationId() {
        return reservationId;
    }

    public void setReservationId(int reservationId) {
        this.reservationId = reservationId;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }
}
