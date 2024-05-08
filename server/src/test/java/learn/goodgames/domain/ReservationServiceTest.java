package learn.goodgames.domain;

import learn.goodgames.data.ReservationRepository;
import learn.goodgames.models.Location;
import learn.goodgames.models.Reservation;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.NONE)
class ReservationServiceTest {

    @Autowired
    ReservationService service;

    @MockBean
    ReservationRepository repository;

    @Test
    void shouldFindAllReservations() {
        List<Reservation> expected = List.of(makeReservation());
        when(repository.findAllReservations()).thenReturn(expected);

        List<Reservation> actual = service.findAllReservations();
        assertEquals(expected, actual);
    }

    @Test
    void shouldFindReservationById() {
        Reservation expected = makeReservation();
        when(repository.findReservationById(1)).thenReturn(expected);

        Reservation actual = service.findReservationById(1);
        assertEquals(expected, actual);
    }

    @Test
    void shouldFindNullIfInvalidId() {
        Reservation actual = service.findReservationById(1111);
        assertNull(actual);
    }

    Reservation makeReservation() {
        Reservation reservation = new Reservation();
        reservation.setDate(LocalDate.of(2024, 6, 1));
        reservation.setTime(LocalTime.of(18, 0,0));
        reservation.setLocationId(1);
        reservation.setHostId(1);
        reservation.setReservationId(1);
        reservation.setHostName("Kevin");
        reservation.setLocation(makeLocation1());
        return reservation;
    }

    Location makeLocation1() {
        Location location = new Location();
        location.setLocationId(1);
        location.setName("Test Location");
        location.setAddress("123 Test Ave.");
        location.setCity("Test City");
        location.setState("RI");
        location.setPostalCode("02914");

        return location;
    }
}