package learn.goodgames.models;

import org.apache.catalina.Host;

import java.time.LocalDate;
import java.time.LocalTime;

public class Reservation {
    private int reservationId;
    private LocalDate date;
    private LocalTime time;
    private int locationId;
    private int hostId;
    private Location location;
    private String hostName;
//    private List<ReservationAttendee> attendees;

    public Reservation() {
    }

    public Reservation(int reservationId, LocalDate date, LocalTime time, int locationId, int hostId, Location location) {
        this.reservationId = reservationId;
        this.date = date;
        this.time = time;
        this.locationId = locationId;
        this.hostId = hostId;
        this.location = location;
    }

    public int getReservationId() {
        return reservationId;
    }

    public void setReservationId(int reservationId) {
        this.reservationId = reservationId;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public LocalTime getTime() {
        return time;
    }

    public void setTime(LocalTime time) {
        this.time = time;
    }

    public int getLocationId() {
        return locationId;
    }

    public void setLocationId(int locationId) {
        this.locationId = locationId;
    }

    public int getHostId() {
        return hostId;
    }

    public void setHostId(int hostId) {
        this.hostId = hostId;
    }

    public Location getLocation() {
        return location;
    }

    public void setLocation(Location location) {
        this.location = location;
    }

    public String getHostName() {
        return hostName;
    }

    public void setHostName(String hostName) {
        this.hostName = hostName;
    }
}
